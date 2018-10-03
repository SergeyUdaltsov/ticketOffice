var vocabulary;
var language;
var user = new Object();



$(window).ready(function () {



    if(JSON.parse(window.localStorage.getItem('status')) !== 'admin'){
        $(location).attr('href', 'http://localhost:9999/index.jsp');
    }

    vocabulary = getVocabulary();

    language = JSON.parse(window.localStorage.getItem('lang'));

    if (language !== null && language !== undefined) {
        translatePage(language);
    }

    console.log(language);

    var url = 'http://localhost:9999/railways/station/get/all';

    loadStations(url);


    $("#createNewStation").click(function () {

        $(location).attr('href', 'http://localhost:9999/html/station/CreateNewStation.html');

    });



    $("#home").click(function () {

        $(location).attr('href', 'http://localhost:9999/html/admin/AdminStartPage.html');

    });

    $('.translate').click(function () {
        var transLang = $(this).attr('id');

        translatePage(transLang);

        location.reload();

        window.localStorage.setItem('lang', JSON.stringify(transLang));
    });


});

$(window).onunload(function () {
    localStorage.setItem('user', null);
});

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
                var nodText = document.createTextNode((language === 'ru') ? this.nameRu : this.name);

                nod.setAttribute('href', 'http://localhost:9999/html/station/EditStation.html?id=' + this.id);
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
            'stationName': 'Название',
            'exists': 'Пользователь с таким адресом уже зарегистрирован.',
            'fillUp': 'Заполните все поля.'
        },
        en: {
            'title': 'Stations',
            'id': 'Id',
            'name': 'Name',
            'new station': 'New station',
            'home': 'Cancel',
            'cancel': 'Cancel',
            'stationName': 'Name',
            'exists': 'User with this email already registered.',
            'fillUp': 'Fill up all the fields'
        }

    };
}