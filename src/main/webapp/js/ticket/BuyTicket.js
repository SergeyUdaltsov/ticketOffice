var vocabulary;
var language;
var request = new Object();
$(window).ready(function () {

    var routeId = window.location.href.split("?")[1].split("=")[1].split("&")[0];
    var depStId = window.location.href.split("&")[1].split("=")[1];
    var arrStId = window.location.href.split("&")[2].split("=")[1];

    loadTicketsInfo(routeId, depStId, arrStId);

    // loadTrip(routeId, depStId, arrStId);

    vocabulary = getVocabulary();

    language = JSON.parse(window.localStorage.getItem('lang'));

    if (language !== null && language !== undefined) {
        translatePage(language);
    }

    $("#buy").click(function () {


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
            'register': 'Зарегистрировать',
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

function loadTrip(routeId, depSt, arrSt) {
    $.ajax({
        url: 'http://localhost:9999/railways/ticket/get/stations/by/trip',
        type: 'get',
        dataType: 'json',
        data: {
            routeId: routeId,
            depSt: depSt,
            arrSt: arrSt
        },

        success: function (data) {

            var new_tbody = document.createElement('tbody');

            if (data.length === 0){

                document.getElementsByTagName("tbody").item(0).parentNode.
                replaceChild(new_tbody, document.getElementsByTagName("tbody").item(0));

                return;
            }

            $.each(data, function () {

                var row = document.createElement("tr");
                cell1 = document.createElement("td");
                cell2 = document.createElement("td");
                cell3 = document.createElement("td");
                cell4 = document.createElement("td");

                textNode1 = document.createTextNode(this.arrStation);
                textNode2 = document.createTextNode(prepareDate(this.arrivalDate));
                textNode3 = document.createTextNode(prepareTime(this.arrivalTime));
                textNode4 = document.createTextNode(prepareTime(this.departureTime));

                cell1.appendChild(textNode1);
                cell2.appendChild(textNode2);
                cell3.appendChild(textNode3);
                cell4.appendChild(textNode4);

                row.appendChild(cell1);
                row.appendChild(cell2);
                row.appendChild(cell3);
                row.appendChild(cell4);

                new_tbody.appendChild(row);

                document.getElementsByTagName("tbody").item(0).parentNode.
                replaceChild(new_tbody, document.getElementsByTagName("tbody").item(0));

            });

        }

    });

}

function loadTicketsInfo(routeId, depSt, arrSt) {
    $.ajax({
        url: 'http://localhost:9999/railways/ticket/get/by/route',
        type: 'get',
        dataType: 'json',
        data: {
            routeId: routeId,
            depSt: depSt,
            arrSt: arrSt
        },

        success: function (data) {

            var econ = document.createTextNode(data[0]);
            document.getElementById("economy").appendChild(econ);

            var bus = document.createTextNode(data[1]);
            document.getElementById("business").appendChild(bus);

            var comf = document.createTextNode(data[2]);
            document.getElementById("comfort").appendChild(comf);


        }

    });
}

function prepareTime(time) {

    var hours = (Object.values(time)[0] < 10)? ("0" + Object.values(time)[0]) : (Object.values(time)[0]);
    var minutes = (Object.values(time)[1] < 10)? ("0" + Object.values(time)[1]) : (Object.values(time)[1]);
    var res =hours + ":" + minutes;
    return res;
}

function prepareDate(date) {
    var month = (Object.values(date)[1] < 10)? ("-0" + Object.values(date)[1]) : ("-" + Object.values(date)[1]);

    var day = (Object.values(date)[2] < 10)? ("-0" + Object.values(date)[2]) : ("-" + Object.values(date)[2]);

    var date = Object.values(date)[0] + month + day;
    return date;
}