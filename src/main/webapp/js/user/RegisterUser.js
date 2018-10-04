var vocabulary;
var language;
var user = new Object();
$(window).ready(function () {

    vocabulary = getVocabulary();

    language = JSON.parse(window.localStorage.getItem('lang'));

    if (language !== null && language !== undefined) {
        translatePage(language);
    }

    $("#registerUserButton").click(function () {

        user.firstName = $("#firstName").val();
        user.lastName = $("#lastName").val();
        user.password = $("#password").val();
        user.email = $("#email").val();

        var data = JSON.stringify(user);

        (user.email === "" || user.password === "" || user.firstName === "" || user.lastName === "")
            ? alert(vocabulary[language]['fillUp'])
            : registerUser(data);

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
            'title': 'Регистрация',
            'email': 'Эл.почта',
            'fName': 'Имя',
            'lName': 'Фамилия',
            'register': 'Зарегистрировать',
            'cancel': 'Отмена',
            'pass': 'Пароль',
            'mailWrong': 'Неверный формат эл. адреса',
            'exists': 'Пользователь с таким адресом уже зарегистрирован.',
            'fillUp': 'Заполните все поля.'
        },
        en: {
            'title': 'Registration',
            'email': 'Email',
            'fName': 'First name',
            'lName': 'Last name',
            'mailWrong': 'Wrong mail format',
            'register': 'Register',
            'cancel': 'Cancel',
            'pass': 'Password',
            'exists': 'User with this email already registered.',
            'fillUp': 'Fill up all the fields'
        }

    };
}

function registerUser(user) {
    $.ajax({
        type: 'post',
        url: 'http://localhost:9999/railways/user/register',
        dataType: 'JSON',
        data: {
            jsonUser: user
        },
        success: function (data) {

        },
        error: function (data) {
            if (data.status === 406) {
                alert(vocabulary[language]['exists']);
            }
            if (data.status === 405) {
                alert(vocabulary[language]['mailWrong']);
            }
        },
        complete: function (data) {
            if (data.status === 200) {

                window.localStorage.setItem('status', JSON.stringify('registered'));
                writeCookie('password', JSON.parse(user).password, 10);
                window.localStorage.setItem('user', user);
                $(location).attr('href', 'http://localhost:9999/html/user/UserStartPage.html');
            }
        }
    });
}

function writeCookie(name,value,min) {
    var date, expires;
    if (min) {
        date = new Date();
        date.setTime(date.getTime()+(min*24*60*60*1000));
        expires = "; expires=" + date.toGMTString();
    }else{
        expires = "";
    }
    document.cookie = name + "=" + value + expires + "; path=/";
}