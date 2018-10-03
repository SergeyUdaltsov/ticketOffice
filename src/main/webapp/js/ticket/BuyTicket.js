var vocabulary;
var language;
var request = new Object();
var economyCount;
var businessCount;
var comfortCount;

$(window).ready(function () {

    var password = readCookie('password');
    var user = JSON.parse(window.localStorage.getItem('user'));

    console.log(JSON.parse(window.localStorage.getItem('status')));
    if (password !== user.password ||
        JSON.parse(window.localStorage.getItem('status')) !== 'entered') {

        $(location).attr('href', 'http://localhost:9999/index.jsp');

    }



    var routeId = window.location.href.split("?")[1].split("=")[1].split("&")[0];
    var depStId = window.location.href.split("&")[1].split("=")[1];
    var arrStId = window.location.href.split("&")[2].split("=")[1];

    loadTicketsInfo(routeId, depStId, arrStId);

    loadTrip(routeId, depStId, arrStId);

    vocabulary = getVocabulary();

    language = JSON.parse(window.localStorage.getItem('lang'));

    if (language !== null && language !== undefined) {
        translatePage(language);
    }

    $("#buy").click(function () {

        window.localStorage.removeItem('status');

        request.ecoCountToBuy = ($("#ecoCount").val() === "") ? 0 : $("#ecoCount").val();
        request.busCountToBuy = ($("#busCount").val() === "") ? 0 : $("#busCount").val();
        request.comCountToBuy = ($("#comCount").val() === "") ? 0 : $("#comCount").val();
        request.routeId = routeId;
        request.depStId = depStId;
        request.arrStId = arrStId;

        (request.ecoCountToBuy === 0 && request.busCountToBuy === 0 && request.comCountToBuy === 0)
            ? alert(vocabulary[language]['fillUp'])
            : ((economyCount - request.ecoCountToBuy) < 0 || (businessCount - request.busCountToBuy) < 0 || (comfortCount - request.comCountToBuy) < 0)
            ? alert(vocabulary[language]['noTickets'])
            : buyTickets(JSON.stringify(request));

    });


    $("#cancelB").click(function () {

        window.history.back();

    });

    $('.translate').click(function () {
        var transLang = $(this).attr('id');

        translatePage(transLang);

        window.localStorage.setItem('lang', JSON.stringify(transLang));
    });
});

function buyTickets(request) {
    $.ajax({
        type: 'post',
        url: 'http://localhost:9999/railways/ticket/buy',
        dataType: 'JSON',
        data: {
            jsRequest: request
        },
        success: function (data) {

        },
        error: function (data) {
            if (data.status === 406) {
                alert(vocabulary[language]['noTickets']);
            }
        },
        complete: function (data) {
            if (data.status === 200) {
                window.history.back();

            }
        }
    });
}

function readCookie(name) {
    var i, c, ca, nameEQ = name + "=";
    ca = document.cookie.split(';');
    for (i = 0; i < ca.length; i++) {
        c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1, c.length);
        }
        if (c.indexOf(nameEQ) == 0) {
            return c.substring(nameEQ.length, c.length);
        }
    }
    return '';
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

            if (data.length === 0) {

                document.getElementsByTagName("tbody").item(0).parentNode.replaceChild(new_tbody, document.getElementsByTagName("tbody").item(0));

                return;
            }

            $.each(data, function () {

                var row = document.createElement("tr");
                cell1 = document.createElement("td");
                cell2 = document.createElement("td");
                cell3 = document.createElement("td");

                textNode1 = document.createTextNode(this.name);
                textNode2 = document.createTextNode(this.arrDateTimeString);
                textNode3 = document.createTextNode(this.depTimeString);

                cell1.appendChild(textNode1);
                cell2.appendChild(textNode2);
                cell3.appendChild(textNode3);

                row.appendChild(cell1);
                row.appendChild(cell2);
                row.appendChild(cell3);

                new_tbody.appendChild(row);

                document.getElementsByTagName("tbody").item(0).parentNode.replaceChild(new_tbody, document.getElementsByTagName("tbody").item(0));

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

            economyCount = data[0];
            businessCount = data[1];
            comfortCount = data[2];

            var econ = document.createTextNode(" " + economyCount);
            document.getElementById("ecoId").appendChild(econ);

            var bus = document.createTextNode(" " + businessCount);
            document.getElementById("busId").appendChild(bus);

            var comf = document.createTextNode(" " + comfortCount);
            document.getElementById("comId").appendChild(comf);


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
            'title': 'Купить билет',
            'create': 'Создать',
            'stName': 'Название',
            'economy': 'Общий:',
            'business': 'Плацкарт:',
            'comfort': 'Купе:',
            'name': 'Станция',
            'buy': 'Купить',
            'arrival': 'Прибытие',
            'departure': 'Отправление',
            'register': 'Зарегистрировать',
            'cancel': 'Отмена',
            'status': 'Время сессии истекло или пользователь не зарегистрирован',
            'noTickets': 'Недостаточно билетов',
            'pass': 'Пароль',
            'exists': 'Cтанция уже есть в базе.',
            'fillUp': 'Заполните все поля.'
        },
        en: {
            'title': 'Buy ticket',
            'create': 'Create',
            'name': 'Name',
            'buy': 'Buy',
            'arrival': 'Arrival',
            'departure': 'Departure',
            'economy': 'Count of economy:',
            'business': 'Count of business:',
            'comfort': 'Count of comfort:',
            'status': 'Session is over or user not validated',
            'stName': 'Station name',
            'lName': 'Last name',
            'register': 'Register',
            'noTickets': 'There are no tickets.',
            'cancel': 'Cancel',
            'pass': 'Password',
            'exists': 'Station already exists.',
            'fillUp': 'Fill up all the fields'
        }

    };
}
