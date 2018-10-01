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
            'exists': 'Пользователь с таким адресом уже зарегистрирован.',
            'fillUp': 'Заполните все поля.'
        },
        en: {
            'title': 'Registration',
            'email': 'Email',
            'fName': 'First name',
            'lName': 'Last name',
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
        },
        complete: function (data) {
            if (data.status === 200) {

                window.localStorage.setItem('status', JSON.stringify('registered'));
                window.localStorage.setItem('user', user);
                $(location).attr('href', 'http://localhost:9999/html/user/UserStartPage.html');
            }
        }
    });
}