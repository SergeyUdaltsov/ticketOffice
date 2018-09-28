var vocabulary;
var language;
var train = new Object();
$(window).ready(function () {

    vocabulary = getVocabulary();

    language = JSON.parse(window.localStorage.getItem('lang'));

    if (language !== null && language !== undefined) {
        translatePage(language);
    }

    $("#createButton").click(function () {

        train.name = $("#trainName").val();
        train.economy = $("#econNumber").val();
        train.business = $("#businessNumber").val();
        train.comfort = $("#comfortNumber").val();

        var data = JSON.stringify(train);

        (train.name === "" || train.economy === "" || train.business === "" || train.comfort === "")
            ? alert(vocabulary[language]['fillUp'])
            : createTrain(data);

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

function createTrain(data) {
    $.ajax({
        type: 'post',
        url: 'http://localhost:9999/railways/train/new',
        dataType: 'JSON',
        data: {
            jsonTrain: data
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
                $(location).attr('href', 'http://localhost:9999/html/train/TrainsList.html');

            }
        }
    });
}