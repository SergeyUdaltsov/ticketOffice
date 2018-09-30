var vocabulary;
var language;
var train = new Object();
$(window).ready(function () {

    if(JSON.parse(window.localStorage.getItem('status')) !== 'admin'){
        $(location).attr('href', 'http://localhost:9999/index.jsp');
    }

    vocabulary = getVocabulary();

    language = JSON.parse(window.localStorage.getItem('lang'));

    if (language !== null && language !== undefined) {
        translatePage(language);
    }

    var url = 'http://localhost:9999/railways/train/get/all';

    loadTrains(url);


    $("#createNewTrain").click(function () {

        $(location).attr('href', 'http://localhost:9999/html/train/CreateNewTrain.html');

    });



    $("#home").click(function () {

        $(location).attr('href', 'http://localhost:9999/html/admin/AdminStartPage.html');

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
            'title': 'Поезда',
            'id': '№',
            'name': 'Код',
            'econ': 'Общий',
            'bus': 'Плацкарт',
            'comf': 'Купе',
            'new train': 'Добавить поезд',
            'home': 'Отмена',
            'cancel': 'Отмена',
            'stationName': 'Название',
            'exists': 'Поезд уже зарегистрирован.',
            'fillUp': 'Заполните все поля.'
        },
        en: {
            'title': 'Trains',
            'name': 'Name',
            'econ': 'Economy',
            'bus': 'Business',
            'comf': 'Сomfort',
            'new train': 'New train',
            'home': 'home',
            'cancel': 'Cancel',
            'stationName': 'Name',
            'exists': 'Train already registered.',
            'fillUp': 'Fill up all the fields'
        }

    };
}

function loadTrains(url) {
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
                cell3 = document.createElement("td");
                cell4 = document.createElement("td");

                textNode1 = document.createTextNode(this.name);
                textNode2 = document.createTextNode(this.economyPlacesCount);
                textNode3 = document.createTextNode(this.businessPlacesCount);
                textNode4 = document.createTextNode(this.comfortPlacesCount);

                var nod = document.createElement('a');
                var nodText = document.createTextNode(this.name);

                nod.setAttribute('href', 'http://localhost:9999/html/train/EditTrain.html?id=' + this.id);
                nod.appendChild(nodText);


                cell1.appendChild(nod);
                cell2.appendChild(textNode2);
                cell3.appendChild(textNode3);
                cell4.appendChild(textNode4);

                row.appendChild(cell1);
                row.appendChild(cell2);
                row.appendChild(cell3);
                row.appendChild(cell4);

                new_tbody.appendChild(row);

                document.getElementsByTagName("tbody").item(0).parentNode
                    .replaceChild(new_tbody, document.getElementsByTagName("tbody").item(0));

            });
        }

    });
}