var vocabulary;
var language;
var user = new Object();
$(window).ready(function () {

    vocabulary = getVocabulary();

    language = JSON.parse(window.localStorage.getItem('lang'));

    if (language !== null && language !== undefined) {
        translatePage(language);
    }

    $("#stations").click(function () {

        $(location).attr('href', 'http://localhost:9999/html/station/StationsList.html');

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
            'title': 'Украинские ЖД',
            'stations': 'Станции',
            'routs': 'Маршруты',
            'lName': 'Фамилия',
            'register': 'Зарегистрировать',
            'cancel': 'Отмена',
            'pass': 'Пароль',
            'exists': 'Пользователь с таким адресом уже зарегистрирован.',
            'fillUp': 'Заполните все поля.'
        },
        en: {
            'title': 'Ukrainian railways',
            'stations': 'Stations',
            'routs': 'Routs',
            'lName': 'Last name',
            'register': 'Register',
            'cancel': 'Cancel',
            'pass': 'Password',
            'exists': 'User with this email already registered.',
            'fillUp': 'Fill up all the fields'
        }

    };
}
