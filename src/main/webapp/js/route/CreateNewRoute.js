var newRoute = new Object();
var vocabulary;
var language;

$(window).ready(function () {

    vocabulary = getVocabulary();

    language = JSON.parse(window.localStorage.getItem('lang'));

    if (language !== null && language !== undefined) {
        translatePage(language);
    }

    var url = 'http://localhost:9999/railways/station/get/all';
    var select = $("#startStSelect");
    loadStations(url, select);
    select = $("#finishStSelect");
    loadStations(url, select);


    $("#createRouteButton").click(function () {

        newRoute.code = $("#code").val();
        newRoute.stStart = $("#startStSelect").val();
        newRoute.departureTime = $("#depTime").val();
        newRoute.stFinish = $("#finishStSelect").val();
        newRoute.arrivalTime = $("#arrTime").val();
        newRoute.departureDate = $("#depDate").val();
        newRoute.arrivalDate = $("#arrDate").val();

        var data = JSON.stringify(newRoute);

        (newRoute.code === "" || newRoute.departureTime === "" || newRoute.arrivalTime === ""
        || newRoute.departureDate === "" || newRoute.arrivalDate === "")
            ? alert(vocabulary[language]['fillUp'])
            : (newRoute.arrivalDate < newRoute.departureDate)
            ? alert(vocabulary[language]['dateError'])
            : (newRoute.arrivalDate === newRoute.departureDate && newRoute.departureTime > newRoute.arrivalTime)
                ? alert(vocabulary[language]['timeError'])
                : createRoute(data);

    });

    $("#cancelButton").click(function () {

        window.history.back();

    });

    $('.translate').click(function () {
        var transLang = $(this).attr('id');

        translatePage(transLang);

        window.localStorage.setItem('lang', JSON.stringify(transLang));
        location.reload();
    });

});

function getVocabulary() {
    return {
        ru: {
            'title': 'Новый маршрут',
            'chStationDep': 'Начальная станция :',
            'depTime': 'Время отправления',
            'dateError': 'Дата прибытия раньше даты отправления',
            'chStationArr': 'Конечная станция :',
            'arrTime': 'Время прибытия на кон. станцию.',
            'timeError': 'Время прибытия меньше времени отправления.',
            'code': 'Код маршрута',
            'cancel': 'Отмена',
            'create': 'Создать',
            'fillUp': 'Заполните все поля.',
            'exists': 'Маршрут уже существует.'
        },
        en: {
            'title': 'New route',
            'chStationDep': 'Start station :',
            'depTime': 'Departure time :',
            'chStationArr': 'Finish station :',
            'arrTime': 'Arrival time',
            'dateError': 'Arrival date is before departure date',
            'timeError': 'Arrival time is before departure time.',
            'code': 'Code',
            'cancel': 'Cancel',
            'create': 'Create',
            'fillUp': 'Please fill up all the fields.',
            'exists': 'Route already exists'
        }

    };
}

function translatePage(transLang) {

    $('.lang').each(function (index, element) {
        $(this).text(vocabulary[transLang][$(this).attr('key')]);
    });

}

function loadStations(url, select) {
    $.ajax({
        url: url,
        type: 'get',
        dataType: 'json',

        success: function (data) {
            $.each(data, function () {

                var opt = $("<option value='" + this.id + "'></option>").text(this.name);
                select.append(opt);
            });
        }
    });
}

function createRoute(data) {
    $.ajax({
        type: 'post',
        url: 'http://localhost:9999/railways/route/new',
        dataType: 'JSON',
        data: {
            jsonRoute: data
        },
        success: function (data) {

        },
        error: function (data) {
            if (data.status === 406) {
                alert(vocabulary[language]['exists']);
            }
        },
        complete: function (data) {
            if (data.status === 200) {
                $(location).attr('href', 'http://localhost:9999/html/route/RoutesList.html');
            }
        }
    });
}