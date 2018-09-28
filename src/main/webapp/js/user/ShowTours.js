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

    loadTrains(trip);

    window.localStorage.setItem('order', JSON.stringify(order));


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
            'buy': 'Купить',
            'pass': 'Пароль',
            'exists': 'Пользователь с таким адресом уже зарегистрирован.',
            'fillUp': 'Заполните все поля.'
        },
        en: {
            'title': 'Registration',
            'email': 'Email',
            'fName': 'First name',
            'lName': 'Last name',
            'buy': 'Buy',
            'register': 'Register',
            'cancel': 'Cancel',
            'pass': 'Password',
            'exists': 'User with this email already registered.',
            'fillUp': 'Fill up all the fields'
        }

    };
}

function loadTrains(data) {
    $.ajax({
        url: 'http://localhost:9999/railways/train/show',
        type: 'get',
        dataType: 'json',
        data: {
            jsonTrip: data
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

                textNode2 = document.createTextNode(prepareDate(this.departureDate));
                textNode3 = document.createTextNode(prepareTime(this.departureTime));
                textNode4 = document.createTextNode(this.depStation);
                textNode5 = document.createTextNode(prepareDate(this.arrivalDate));
                textNode6 = document.createTextNode(prepareTime(this.arrivalTime));
                textNode7 = document.createTextNode(this.arrStation);
                textNode8 = document.createTextNode(this.tourTime);
                textNode9 = document.createTextNode(this.tourPrice);


                var nod = document.createElement('a');
                var nodText = document.createTextNode(vocabulary[language]['buy']);

                nod.setAttribute('href', 'http://localhost:9999/html/ticket/BuyTicket.html?id=' +
                    this.routeId + "&dep_st_id=" + this.startStationId + "&arr_st_id=" + this.finishStationId);
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

                row.appendChild(cell1);
                row.appendChild(cell2);
                row.appendChild(cell3);
                row.appendChild(cell4);
                row.appendChild(cell5);
                row.appendChild(cell6);
                row.appendChild(cell7);
                row.appendChild(cell8);
                row.appendChild(cell9);

                new_tbody.appendChild(row);

                document.getElementsByTagName("tbody").item(0).parentNode.replaceChild(new_tbody, document.getElementsByTagName("tbody").item(0));

            });
        }

    });
}

function prepareTime(time) {

    var hours = (Object.values(time)[0] < 10) ? ("0" + Object.values(time)[0]) : (Object.values(time)[0]);
    var minutes = (Object.values(time)[1] < 10) ? ("0" + Object.values(time)[1]) : (Object.values(time)[1]);
    var res = hours + ":" + minutes;
    return res;
}

function prepareDate(date) {
    var month = (Object.values(date)[1] < 10) ? ("-0" + Object.values(date)[1]) : ("-" + Object.values(date)[1]);

    var day = (Object.values(date)[2] < 10) ? ("-0" + Object.values(date)[2]) : ("-" + Object.values(date)[2]);

    var date = Object.values(date)[0] + month + day;
    return date;
}