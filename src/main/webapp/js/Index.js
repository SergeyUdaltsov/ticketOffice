var vocabulary;
var language;
var login = new Object();
$(window).ready(function () {

    vocabulary = getVocabulary();

    language = JSON.parse(window.localStorage.getItem('lang'));

    if (language !== null && language !== undefined) {
        translatePage(language);
    }

    $("#enter").click(function () {
        login.email = $("#username").val();
        login.password = $("#password").val();

        var data = JSON.stringify(login);

        (login.email === "" || login.password === "")
            ? alert(vocabulary[language]['fillUp'])
            : validateUser(data);


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
            'email': 'Эл.почта',
            'pass': 'Пароль',
            'log': 'Войти',
            'reg': 'Зарегистрироваться',
            'exists': 'Пароль или почта не верны.',
            'fillUp': 'Заполните все поля.'
        },
        en: {
            'title': 'Ukrainian railways',
            'email': 'Email',
            'pass': 'Password',
            'log': 'Log in',
            'reg': 'Register',
            'exists': 'Password or email is wrong.',
            'fillUp': 'Fill up all the fields'
        }

    };
}

function validateUser(data) {
    $.ajax({
        type: 'post',
        url: 'http://localhost:9999/railways/user/validate',
        dataType: 'JSON',
        data: {
            jsonLogin: data,
        },
        success: function (data) {
            if (data.administrator === true) {
                $(location).attr('href', 'http://localhost:9999/html/admin/AdminStartPage.html');

            }

        },
        error: function (data) {
            if (data.status === 406) {
                alert(vocabulary[language]['exists']);
            }
        },
        complete: function (data) {

            if (data.status === 200) {
                // $(location).attr('href', 'http://localhost:9999/html/info/Info.html');
            }
        }
    });
}