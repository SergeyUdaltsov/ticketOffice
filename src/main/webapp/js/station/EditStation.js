var station = new Object();
var vocabulary;
var language;
$(window).ready(function () {

    if(JSON.parse(window.localStorage.getItem('status')) !== 'admin'){
        $(location).attr('href', 'http://localhost:9999/index.jsp');
    }

    vocabulary = getVocabulary();

    language = JSON.parse(window.localStorage.getItem('lang'));

    if (language !== null && language !== undefined) {
        translatePage(language);
    }

    var stationId = window.location.href.split("?")[1].split("=")[1];
    console.log(stationId);

    loadStationInfo(stationId);

    $("#updateButton").click(function () {

        station.id = stationId;
        station.name = $("#nStation").val();

        (station.name === "")
            ? alert('fill up')
            : updateStation(JSON.stringify(station));

    });

    $("#deleteButton").click(function () {

        deleteStation(stationId);
    });

    $("#cancelButton").click(function () {

        $(location).attr('href', 'http://localhost:9999/html/station/StationsList.html');

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
            'title': 'Станция',
            'create': 'Создать',
            'name': 'Название',
            'update': 'Сохранить',
            'delete': 'Удалить',
            'cancel': 'Отмена',
            'cDelete': 'Невозможно удалить станцию.',
            'exists': 'Cтанция уже есть в базе.',
            'fillUp': 'Заполните все поля.'
        },
        en: {
            'title': 'Station',
            'create': 'Create',
            'name': 'Station name',
            'cDelete': 'Could not delete',
            'update': 'Save',
            'cancel': 'Cancel',
            'delete': 'Delete',
            'exists': 'Station already exists.',
            'fillUp': 'Fill up all the fields'
        }

    };
}

function deleteStation(data) {
    $.ajax({
        type: 'get',
        url: 'http://localhost:9999/railways/station/delete',
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
                $(location).attr('href', 'http://localhost:9999/html/station/StationsList.html');

            }
        }
    });
}

function updateStation(data) {
    $.ajax({
        type: 'post',
        url: 'http://localhost:9999/railways/station/update',
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
                $(location).attr('href', 'http://localhost:9999/html/station/StationsList.html');

            }
        }
    });
}

function loadStationInfo(stationId) {

    $.ajax({
        type: 'get',
        url: 'http://localhost:9999/railways/station/get/by/id',
        dataType: 'JSON',
        data: {
            stationId: stationId
        },
        success: function (data) {

            console.log(data.name);
            document.getElementById("nStation").text = data.name;
        },
        error: function (data) {
        }
    });
}

