var vocabulary;
var language;
var user = new Object();
$(window).ready(function () {

    vocabulary = getVocabulary();

    language = JSON.parse(window.localStorage.getItem('lang'));

    if (language !== null && language !== undefined) {
        translatePage(language);
    }

    var url = 'http://localhost:9999/railways/station/get/all';

    loadStations(url);


    $("#createNewStation").click(function () {

        $(location).attr('href', 'http://localhost:9999/html/station/CreateNewStation.html');

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
            'title': 'Станции',
            'id': '№',
            'name': 'Название',
            'new station': 'Добавить станцию',
            'home': 'Отмена',
            'cancel': 'Отмена',
            'pass': 'Пароль',
            'exists': 'Пользователь с таким адресом уже зарегистрирован.',
            'fillUp': 'Заполните все поля.'
        },
        en: {
            'title': 'Stations',
            'id': 'Id',
            'name': 'Name',
            'new station': 'New station',
            'home': 'Отмена',
            'cancel': 'Cancel',
            'pass': 'Password',
            'exists': 'User with this email already registered.',
            'fillUp': 'Fill up all the fields'
        }

    };
}

function loadStations(url) {
    $.ajax({
        url: url,
        type: 'get',
        dataType: 'json',

        success: function (data) {
            var new_tbody = document.createElement('tbody');

            if (data.length === 0) {

                document.getElementsByTagName("tbody").item(0).parentNode
                    .replaceChild(new_tbody, document.getElementsByTagName("tbody").item(0));

                return;
            }

            $.each(data, function () {

                var row = document.createElement("tr");

                cell1 = document.createElement("td");
                cell2 = document.createElement("td");

                textNode1 = document.createTextNode(this.id);
                textNode2 = document.createTextNode(this.name);

                var nod = document.createElement('a');
                var nodText = document.createTextNode(this.name);

                nod.setAttribute('href', 'http://localhost:9999/HTML/client/ShowClient.html?id=' + this.id);
                nod.appendChild(nodText);


                cell1.appendChild(textNode1);
                cell2.appendChild(nod);

                row.appendChild(cell1);
                row.appendChild(cell2);

                new_tbody.appendChild(row);

                document.getElementsByTagName("tbody").item(0).parentNode
                    .replaceChild(new_tbody, document.getElementsByTagName("tbody").item(0));

            });
        }

    });
}