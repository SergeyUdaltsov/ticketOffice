var vocabulary;
var language;
var order = new Object();
var trip;

$(window).ready(function () {


    vocabulary = getVocabulary();

    language = JSON.parse(window.localStorage.getItem('lang'));

    if (language !== null && language !== undefined) {
        translatePage(language);
    }

    trip = window.localStorage.getItem('trip');

    loadTrains(trip, language);

    window.localStorage.setItem('order', JSON.stringify(order));


    $("#cancelButton").click(function () {

        window.history.back();

    });

    $('.translate').click(function () {

        var transLang = $(this).attr('id');

        translatePage(transLang);

        window.localStorage.setItem('lang', JSON.stringify(transLang));

        location.reload();

    });

});

function loadTrains(data, lang) {
    $.ajax({
        url: 'http://localhost:9999/railways/train/show',
        type: 'get',
        dataType: 'json',
        data: {
            jsonTrip: data,
            jsLang: lang
        },

        success: function (data) {
            var new_tbody = document.createElement('tbody');

            if (data.length === 0) {

                document.getElementsByTagName("tbody").item(0).parentNode.replaceChild(new_tbody,
                    document.getElementsByTagName("tbody").item(0));

                return;
            }

            $.each(data, function () {

                var row = document.createElement("tr");
                cell1 = document.createElement("td");
                cell2 = document.createElement("td");
                cell3 = document.createElement("td");
                cell4 = document.createElement("td");
                cell5 = document.createElement("td");
                cell6 = document.createElement("td");
                cell7 = document.createElement("td");
                cell8 = document.createElement("td");
                cell9 = document.createElement("td");
                cell10 = document.createElement("td");

                textNode2 = document.createTextNode(this.arrivalTimeDateStart);
                textNode3 = document.createTextNode(this.departureTime);
                textNode4 = document.createTextNode((language === 'ru') ? this.departureStationRu : this.departureStation);
                textNode5 = document.createTextNode(this.arrivalTimeDateFinish);
                textNode6 = document.createTextNode((language === 'ru') ? this.arrivalStationRu : this.arrivalStation);
                textNode7 = document.createTextNode(this.tourTime);
                textNode8 = document.createTextNode(this.tourPriceEco);
                textNode9 = document.createTextNode(this.tourPriceBusiness);
                textNode10 = document.createTextNode(this.tourPriceComfort);


                var nod = document.createElement('a');
                var nodText = document.createTextNode(vocabulary[language]['buy']);

                nod.setAttribute('href', 'http://localhost:9999/html/ticket/BuyTicket.html?id=' +
                    this.routeId + "&dep_st_id=" + this.departureStationId + "&arr_st_id=" + this.arrivalStationId +
                    "&ecoPr=" + this.tourPriceEco + "&busPr=" + this.tourPriceBusiness + "&comPr=" + this.tourPriceComfort);
                nod.appendChild(nodText);

                cell1.appendChild(nod);
                cell2.appendChild(textNode2);
                cell3.appendChild(textNode3);
                cell4.appendChild(textNode4);
                cell5.appendChild(textNode5);
                cell6.appendChild(textNode6);
                cell7.appendChild(textNode7);
                cell8.appendChild(textNode8);
                cell9.appendChild(textNode9);
                cell10.appendChild(textNode10);

                row.appendChild(cell1);
                row.appendChild(cell2);
                row.appendChild(cell3);
                row.appendChild(cell4);
                row.appendChild(cell5);
                row.appendChild(cell6);
                row.appendChild(cell7);
                row.appendChild(cell8);
                row.appendChild(cell9);
                row.appendChild(cell10);

                new_tbody.appendChild(row);

                document.getElementsByTagName("tbody").item(0).parentNode.replaceChild(new_tbody, document.getElementsByTagName("tbody").item(0));

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
            'title': 'Маршруты',
            'email': 'Эл.почта',
            'fName': 'Имя',
            'train': 'Поезд',
            'arrival': 'Прибытие',
            'departure': 'Отправление',
            'station': 'Станция',
            'tourTime': 'Время в пути',
            'tourPrice': 'Ст-ть',
            'lName': 'Фамилия',
            'register': 'Зарегистрировать',
            'cancel': 'Отмена',
            'buy': 'Купить',
            'eco': 'Общ, грн.',
            'bus': 'Пл грн.',
            'com': 'Купе грн.',
            'pass': 'Пароль',
            'exists': 'Пользователь с таким адресом уже зарегистрирован.',
            'fillUp': 'Заполните все поля.'
        },
        en: {
            'title': 'Routes',
            'email': 'Email',
            'train': 'Train',
            'arrival': 'Arrival',
            'departure': 'Departure',
            'station': 'Station',
            'tourTime': 'Time in tour',
            'tourPrice': 'Price',
            'fName': 'First name',
            'lName': 'Last name',
            'eco': 'Eco hrn',
            'bus': 'Busn hrn',
            'com': 'Comf hrn',
            'buy': 'Buy',
            'register': 'Register',
            'cancel': 'Cancel',
            'pass': 'Password',
            'exists': 'User with this email already registered.',
            'fillUp': 'Fill up all the fields'
        }

    };
}