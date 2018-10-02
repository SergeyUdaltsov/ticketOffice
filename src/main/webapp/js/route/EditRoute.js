var station = new Object();
var vocabulary;
var language;
var arrDate;
$(window).ready(function () {

    if(JSON.parse(window.localStorage.getItem('status')) !== 'admin'){
        $(location).attr('href', 'http://localhost:9999/index.jsp');
    }
    var routeId = window.location.href.split("?")[1].split("=")[1];

    vocabulary = getVocabulary();

    language = JSON.parse(window.localStorage.getItem('lang'));

    if (language !== null && language !== undefined) {
        translatePage(language);
    }

    var select = $("#startStSelect");
    var url = 'http://localhost:9999/railways/station/get/all';

    loadStations(select, url);

    select = $("#trainSelect");
    url = 'http://localhost:9999/railways/train/get/all';

    loadStations(select, url);

    loadRouteInfo(routeId);

    loadIntermediateStations(routeId);


    $("#addStationButton").click(function () {

        station.routeId = routeId;
        station.id = $("#startStSelect").val();
        station.arrTime = $("#arrTime").val();
        station.depTime = $("#depTime").val();
        station.arrDate = $("#arrDate").val();

        var data = JSON.stringify(station);

        url = 'http://localhost:9999/railways/route/intermediate/add';

        (station.arrTime === "" || station.depTime === "" || station.arrDate === "")
            ? alert(vocabulary[language]['fillUp'])
            : (station.arrTime > station.depTime)
            ? alert(vocabulary[language]['timeError'])
            : addItem(data, url);

    });

    $("#deleteButton").click(function () {

        var url = 'http://localhost:9999/railways/route/delete';

        deleteItem(routeId, url);

        $(location).attr('href', 'http://localhost:9999/html/route/RoutesList.html');

    });

    $("#setTrainButton").click(function () {

        var trainId = $("#trainSelect").val();

        url = 'http://localhost:9999/railways/route/train/set';

        setTrain(url, trainId, routeId);

    });

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


function translatePage(transLang) {

    $('.lang').each(function (index, element) {
        $(this).text(vocabulary[transLang][$(this).attr('key')]);
    });

}

function loadRouteInfo(routeId) {

    $.ajax({
        type: 'get',
        url: 'http://localhost:9999/railways/route/get/by/id',
        dataType: 'JSON',
        data: {
            routeId: routeId
        },
        success: function (data) {

            var stFrom = document.createTextNode(" " + data.departureStation + " ");
            var startTime = document.createTextNode(data.depTimeString);
            var startDate = document.createTextNode(data.depDateString);

            var stTo = document.createTextNode(data.arrivalStation);
            var finishTime = document.createTextNode(data.arrTimeString);
            var finishDate = document.createTextNode(data.arrDateString);

            document.getElementById("depSt").appendChild(stFrom);
            document.getElementById("startTime").appendChild(startTime);
            document.getElementById("startDate").appendChild(startDate);

            document.getElementById("arrSt").appendChild(stTo);
            document.getElementById("finTime").appendChild(finishTime);
            document.getElementById("finDate").appendChild(finishDate);

        },
        error: function (data) {
        }
    });
}



function loadStations(select, url) {
    $.ajax({
        url: url,
        type: 'get',
        dataType: 'json',

        success: function (data) {
            $.each(data, function () {

                var opt = $("<option value='" + this.id + "'></option>").text((language === 'ru') ? this.nameRu : this.name);
                select.append(opt);
            });
        }
    });
}

function addItem(data, url) {
    $.ajax({
        type: 'post',
        url: url,
        dataType: 'JSON',
        data: {
            jsonStation: data
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
                location.reload();
            }
        }
    });
}

function setTrain(url, trainId, routeId) {
    $.ajax({
        type: 'post',
        url: url,
        dataType: 'JSON',
        data: {
            trainId: trainId,
            routeId: routeId
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
                location.reload();
            }
        }
    });
}

function loadIntermediateStations(routeId) {
    $.ajax({
        url: 'http://localhost:9999/railways/route/intermediate/get/all/by/route',
        type: 'get',
        dataType: 'json',
        data: {
            routeId: routeId
        },

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
                cell5 = document.createElement("td");


                textNode1 = document.createTextNode(this.intermediateId);
                textNode2 = document.createTextNode((language === 'ru') ? this.nameRu : this.name);
                textNode3 = document.createTextNode(this.arrDateTimeString);
                textNode4 = document.createTextNode(this.stopping);
                textNode5 = document.createTextNode(this.depTimeString);

                var url = 'http://localhost:9999/railways/route/intermediate/delete';

                cell1.innerHTML = '<input type="button" value="del" onclick="deleteItem(' + this.intermediateId + ')">';
                cell2.appendChild(textNode2);
                cell3.appendChild(textNode3);
                cell4.appendChild(textNode4);
                cell5.appendChild(textNode5);

                row.appendChild(cell1);
                row.appendChild(cell2);
                row.appendChild(cell3);
                row.appendChild(cell4);
                row.appendChild(cell5);

                new_tbody.appendChild(row);

                document.getElementsByTagName("tbody").item(0).parentNode.replaceChild(new_tbody, document.getElementsByTagName("tbody").item(0));

            });
        }

    });
}


function deleteItem(data, url) {
    $.ajax({
        type: 'get',
        url: (url === undefined) ? 'http://localhost:9999/railways/route/intermediate/delete' : url,
        dataType: 'JSON',
        data: {
            stationId: data
        },
        success: function (data) {

        },
        error: function (data) {
            if (data.status === 406) {
                alert(vocabulary[language]['cDelete'])
            }
        },
        complete: function (data) {
            if (data.status === 200) {

                location.reload();
            }
        }
    });
}


function getVocabulary() {
    return {
        ru: {
            'title': 'Изменить маршрут',
            'interSt': 'Добавить промежуточную станцию',
            'depTime': 'Отправление',
            'chStationArr': 'Конечная станция :',
            'arrTime': 'Прибытие',
            'arrDate': 'Дата прибытия',
            'timeError': 'Время отправления больше времени прибытия',
            'dateError': 'Неверная дата',
            'add': 'Добавить станцию',
            'id': '№',
            'name': 'Станция',
            'cancel': 'Отмена',
            'stFrom': 'Станция отпр: ',
            'stTo': 'Станция приб: ',
            'stopping': 'Стоянка',
            'train': 'Поезд: ',
            'time': 'Время: ',
            'date': 'Дата: ',
            'create': 'Создать',
            'delete': 'Удалить маршрут',
            'fillUp': 'Заполните все поля.',
            'setTrain': 'Назначить поезд',
            'station added': 'Станция успешно добавлена',
            'exists': 'Маршрут уже существует.'
        },
        en: {
            'title': 'Edit route',
            'interSt': 'Intermediate station :',
            'depTime': 'Departure',
            'chStationArr': 'Finish station :',
            'arrTime': 'Arrival',
            'timeError': 'Arrival time is more than departure time',
            'dateError': 'Wrong date',
            'add': 'Add station',
            'stFrom': 'Station from: ',
            'time': 'Time: ',
            'arrDate': 'Arrival date',
            'stopping': 'Stopping',
            'date': 'Date: ',
            'id': 'Id',
            'name': 'Station',
            'train': 'Train: ',
            'stTo': 'Station to',
            'cancel': 'Cancel',
            'setTrain': 'Set train',
            'delete': 'Delete route',
            'create': 'Create',
            'station added': 'Station successfully added.',
            'fillUp': 'Please fill up all the fields.',
            'exists': 'Route already exists'
        }

    };
}