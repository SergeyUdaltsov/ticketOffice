var order = new Object();
var vocabulary;
var language;
$(window).ready(function () {

    var password = readCookie('password');

    validateUser();

    vocabulary = getVocabulary();

    language = JSON.parse(window.localStorage.getItem('lang'));

    if (language !== null && language !== undefined) {
        translatePage(language);
    }

    var url = 'http://localhost:9999/railways/route/get/all';

    loadRoutes(url);


    $("#cancelButton").click(function () {

        $(location).attr('href', 'http://localhost:9999/html/admin/AdminStartPage.html');


    });

    $("#createButton").click(function () {

        $(location).attr('href', 'http://localhost:9999/html/route/CreateNewRoute.html');

    });

    $('.translate').click(function () {

        location.reload();
        var transLang = $(this).attr('id');

        translatePage(transLang);

        window.localStorage.setItem('lang', JSON.stringify(transLang));
    });

});

function validateUser() {
    var password = readCookie('password');
    var user = JSON.parse(window.localStorage.getItem('user'));

    if (password !== user.password ||
        JSON.parse(window.localStorage.getItem('status')) !== 'admin') {

        $(location).attr('href', 'http://localhost:9999/index.jsp');
    }
}

function loadRoutes(url) {
    $.ajax({
        url: url,
        type: 'get',
        dataType: 'json',
        data:{
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
                cell5 = document.createElement("td");
                cell6 = document.createElement("td");
                cell7 = document.createElement("td");


                textNode2 = document.createTextNode((language === 'ru') ? this.departureStationRu : this.departureStation);
                textNode3 = document.createTextNode(prepareDate(this.departureDate));
                textNode4 = document.createTextNode(prepareTime(this.departureTime));
                textNode5 = document.createTextNode((language === 'ru') ? this.arrivalStationRu : this.arrivalStation);
                textNode6 = document.createTextNode(prepareTime(this.arrivalTime));
                textNode7 = document.createTextNode(prepareDate(this.arrivalDate));

                var nod = document.createElement('a');
                var nodText = document.createTextNode(this.code);

                nod.setAttribute('href','http://localhost:9999/html/route/EditRoute.html?id=' + this.id);
                nod.appendChild(nodText);

                cell1.appendChild(nod);
                cell2.appendChild(textNode2);
                cell3.appendChild(textNode3);
                cell4.appendChild(textNode4);
                cell5.appendChild(textNode5);
                cell6.appendChild(textNode6);
                cell7.appendChild(textNode7);

                row.appendChild(cell1);
                row.appendChild(cell2);
                row.appendChild(cell3);
                row.appendChild(cell4);
                row.appendChild(cell5);
                row.appendChild(cell6);
                row.appendChild(cell7);

                new_tbody.appendChild(row);

                document.getElementsByTagName("tbody").item(0).parentNode.
                replaceChild(new_tbody, document.getElementsByTagName("tbody").item(0));

            });
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

function translatePage(transLang) {

    $('.lang').each(function (index, element) {
        $(this).text(vocabulary[transLang][$(this).attr('key')]);
    });

}

function getVocabulary() {
    return {
        ru: {
            'title': 'Маршруты',
            'create' : 'Создать маршрут',
            'cancel' :'Отмена',
            'code' : 'Код',
            'depDate' : 'Дата отпр.',
            'arrDate' : 'Дата приб.',
            'st_start' : 'Ст. отпр.',
            'depart_time': 'Время отпр',
            'st_finish': 'Ст приб.',
            'arr_time': 'Время приб.'

        },
        en: {
            'title': 'Routes',
            'create' : 'Create route',
            'cancel' :'Cancel',
            'code' : 'Code',
            'depDate' : 'Dep. date',
            'arrDate' : 'Arr. date',
            'st_start' : 'Dep st.',
            'depart_time': 'Dep. time',
            'st_finish': 'Arr. st.',
            'arr_time': 'Arr. time'
        }

    };
}

