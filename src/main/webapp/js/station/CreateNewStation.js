var vocabulary;
var language;
var station = new Object();
$(window).ready(function () {

   validateUser();

    vocabulary = getVocabulary();

    language = JSON.parse(window.localStorage.getItem('lang'));

    if (language !== null && language !== undefined) {
        translatePage(language);
    }

    $("#createStation").click(function () {

        station.name = $("#name").val();
        station.nameRu = $("#nameRu").val();

        var data = JSON.stringify(station);

        (station.name === "" || station.nameRu === "")
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

function validateUser() {
    var password = readCookie('password');
    var user = JSON.parse(window.localStorage.getItem('user'));

    if (password !== user.password ||
        JSON.parse(window.localStorage.getItem('status')) !== 'admin') {

        $(location).attr('href', 'http://localhost:9999/index.jsp');
    }
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

function readCookie(name) {
    var i, c, ca, nameEQ = name + "=";
    ca = document.cookie.split(';');
    for (i = 0; i < ca.length; i++) {
        c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1, c.length);
        }
        if (c.indexOf(nameEQ) == 0) {
            return c.substring(nameEQ.length, c.length);
        }
    }
    return '';
}

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
            'stName': 'Название EN',
            'stNameRu': 'Название RU',
            'lName': 'Фамилия',
            'cancel': 'Отмена',
            'pass': 'Пароль',
            'exists': 'Cтанция уже есть в базе.',
            'fillUp': 'Заполните все поля.'
        },
        en: {
            'title': 'New station',
            'create': 'Create',
            'stName': 'Station name EN',
            'stNameRu': 'Station name RU',
            'lName': 'Last name',
            'register': 'Register',
            'cancel': 'Cancel',
            'pass': 'Password',
            'exists': 'Station already exists.',
            'fillUp': 'Fill up all the fields'
        }

    };
}