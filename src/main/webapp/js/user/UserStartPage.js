var trip = new Object();
var vocabulary;
var language;
$(window).ready(function () {


    vocabulary = getVocabulary();

    language = JSON.parse(window.localStorage.getItem('lang'));

    if (language !== null && language !== undefined) {
        translatePage(language);
    }

    console.log(JSON.parse(window.localStorage.getItem('user')));

    var select = $("#depSelect");
    url = 'http://localhost:9999/railways/station/get/all';
    loadItems(select, url);

    select = $("#arrSelect");
    loadItems(select, url);

    $("#cancelButton").click(function () {

        window.localStorage.setItem('status', JSON.stringify(''));

        $(location).attr('href', 'http://localhost:9999/index.jsp');

    });

    $("#showButton").click(function () {

        trip.depSt = $("#depSelect").val();
        trip.arrSt = $("#arrSelect").val();
        trip.date = $("#depDate").val();

        window.localStorage.setItem('trip', JSON.stringify(trip));

        $(location).attr('href', 'http://localhost:9999/html/user/ShowTours.html');

    });

    $('.translate').click(function () {

        location.reload();
        var transLang = $(this).attr('id');

        translatePage(transLang);

        window.localStorage.setItem('lang', JSON.stringify(transLang));
    });

});



function loadItems(select, url) {
    $.ajax({
        url: url,
        type: 'get',
        dataType: 'json',

        success: function (data) {
            $.each(data, function () {

                var opt = $("<option value='" + this.id + "'></option>").text((language === 'ru') ? this.nameRu : this.name);
                select.append(opt);
            });
        }
    });
}

function translatePage(transLang) {

    $('.lang').each(function (index, element) {
        $(this).text(vocabulary[transLang][$(this).attr('key')]);
    });

}

function getVocabulary() {
    return {
        ru: {
            'title': 'Маршруты',
            'show' : 'Показать поезда',
            'cancel' :'Выход',
            'stFrom': 'Станция отправления',
            'stTo': 'Станция прибытия',
            'code' : 'Код',
            'st_start' : 'Ст. отпр.',
            'depart_time': 'Время отпр',
            'st_finish': 'Ст приб.',
            'arr_time': 'Время приб.'

        },
        en: {
            'title': 'Routes',
            'show' : 'Show trains',
            'stFrom': 'Departure station',
            'stTo': 'Arrival station',
            'cancel' :'Exit',
            'code' : 'Code',
            'st_start' : 'Dep st.',
            'depart_time': 'Dep. time',
            'st_finish': 'Arr. st.',
            'arr_time': 'Arr. time'
        }

    };
}
