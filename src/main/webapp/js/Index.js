var vocabulary;
var language;
var login = new Object();
var user = new Object();
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

    $("#guest").click(function () {

        window.localStorage.setItem('status', JSON.stringify(''));

        $(location).attr('href', 'http://localhost:9999/html/user/UserStartPage.html');

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
            'guest': 'Войти как гость',
            'log': 'Войти',
            'reg': 'Зарегистрироваться',
            'exists': 'Пароль или почта не верны.',
            'fillUp': 'Заполните все поля.'
        },
        en: {
            'title': 'Ukrainian railways',
            'email': 'Email',
            'pass': 'Password',
            'guest': 'Enter as guest',
            'log': 'Log in',
            'reg': 'Register',
            'exists': 'Password or email is wrong.',
            'fillUp': 'Fill up all the fields'
        }

    };
}

function writeCookie(name,value,min) {
    var date, expires;
    if (min) {
        date = new Date();
        date.setTime(date.getTime()+(min*60*1000));
        expires = "; expires=" + date.toGMTString();
    }else{
        expires = "";
    }
    document.cookie = name + "=" + value + expires + "; path=/";
}

function validateUser(login) {
    $.ajax({
        type: 'post',
        url: 'http://localhost:9999/railways/user/validate',
        dataType: 'JSON',
        data: {
            jsonLogin: login
        },
        success: function (data) {

            if (data.administrator === true) {

                window.localStorage.setItem('status', JSON.stringify('admin'));

                $(location).attr('href', 'http://localhost:9999/html/admin/AdminStartPage.html');
            }else {

                user.firstName = data.firstName;
                user.lastName = data.lastName;
                user.email = data.email;
                user.password = data.password;

                window.localStorage.setItem('user', JSON.stringify(user));

                writeCookie('password', data.password, 10);

                window.localStorage.setItem('status', JSON.stringify('entered'));

                $(location).attr('href', 'http://localhost:9999/html/user/UserStartPage.html');
            }
        },
        error: function (data) {
            if (data.status === 406) {
                alert(vocabulary[language]['exists']);
            }
        }
    });
}