var vocabulary;
var language;
var station = new Object();
$(window).ready(function () {

    if(JSON.parse(window.localStorage.getItem('status')) !== 'admin'){
        $(location).attr('href', 'http://localhost:9999/index.jsp');
    }

    vocabulary = getVocabulary();

    language = JSON.parse(window.localStorage.getItem('lang'));

    if (language !== null && language !== undefined) {
        translatePage(language);
    }

    $("#createStation").click(function () {

        station.name = $("#name").val();

        var data = JSON.stringify(station);

        (station.name === "")
            ? alert(vocabulary[language]['fillUp'])
            : createStation(data);

    });


    $("#cancelButton").click(function () {

        window.history.back();

    });

    $('.translate').click(function () {
        var transLang = $(this).attr('id');

        translatePage(transLang);

        window.localStorage.setItem('lang', JSON.stringify(transLang));
    });
});

function translatePage(transLang) {

    $('.lang').each(function (index, element) {
        $(this).text(vocabulary[transLang][$(this).attr('key')]);
    });

}

function getVocabulary() {

    return {
        ru: {
            'title': 'Новая станция',
            'create': 'Создать',
            'stName': 'Название',
            'lName': 'Фамилия',
            'скуфеу': 'Зарегистрировать',
            'cancel': 'Отмена',
            'pass': 'Пароль',
            'exists': 'Cтанция уже есть в базе.',
            'fillUp': 'Заполните все поля.'
        },
        en: {
            'title': 'New station',
            'create': 'Create',
            'stName': 'Station name',
            'lName': 'Last name',
            'register': 'Register',
            'cancel': 'Cancel',
            'pass': 'Password',
            'exists': 'Station already exists.',
            'fillUp': 'Fill up all the fields'
        }

    };
}

function createStation(data) {
    $.ajax({
        type: 'post',
        url: 'http://localhost:9999/railways/station/new',
        dataType: 'JSON',
        data: {
            jsonStation: data
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
                $(location).attr('href', 'http://localhost:9999/html/station/StationsList.html');

            }
        }
    });
}