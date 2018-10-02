var train = new Object();
var vocabulary;
var language;

$(window).ready(function () {

    if(JSON.parse(window.localStorage.getItem('status')) !== 'admin'){
        $(location).attr('href', 'http://localhost:9999/index.jsp');
    }

    var trainId = window.location.href.split("?")[1].split("=")[1];

    vocabulary = getVocabulary();

    language = JSON.parse(window.localStorage.getItem('lang'));

    if (language !== null && language !== undefined) {
        translatePage(language);
    }

    loadTrainInfo(trainId);


    $("#saveButton").click(function () {

        train.id = trainId;
        train.name = $("#trainName").val();
        train.nameRu = $("#trainNameRu").val();
        train.econCount = $("#econCount").val();
        train.busCount = $("#busCount").val();
        train.comfCount = $("#comfCount").val();

        var data = JSON.stringify(train);

        console.log(train);

        (train.name === "" || train.econCount === "" || train.busCount === "" || train.comfCount === "")
            ? alert(vocabulary[language]['fillUp'])
            : saveTrain(data);

    });

    $("#deleteButton").click(function () {

        var url = 'http://localhost:9999/railways/train/delete';

        deleteTrain(trainId);

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

function saveTrain(data) {
    $.ajax({
        type: 'post',
        url: 'http://localhost:9999/railways/train/update',
        dataType: 'JSON',
        data: {
            jsonTrain: data
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
                $(location).attr('href', 'http://localhost:9999/html/train/TrainsList.html');

            }
        }
    });
}

function loadTrainInfo(trainId) {

    $.ajax({
        type: 'get',
        url: 'http://localhost:9999/railways/train/get/by/id',
        dataType: 'JSON',
        data: {
            trainId: trainId
        },
        success: function (data) {

            document.getElementById("trainName").value = data.name;
            document.getElementById("trainNameRu").value = data.nameRu;
            document.getElementById("econCount").value = data.economyPlacesCount;
            document.getElementById("busCount").value = data.businessPlacesCount;
            document.getElementById("comfCount").value = data.comfortPlacesCount;

        },
        error: function (data) {
        }
    });
}


function deleteTrain(data) {
    $.ajax({
        type: 'get',
        url: 'http://localhost:9999/railways/train/delete',
        dataType: 'JSON',
        data: {
            trainId: data
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

                $(location).attr('href', 'http://localhost:9999/html/train/TrainsList.html');

            }
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
            'title': 'Изменить Поезд',
            'name': 'Название EN',
            'nameRu': 'Название RU',
            'cancel': 'Отмена',
            'save': 'Сохранить',
            'econ': 'Общие',
            'bus': 'Плацкарт',
            'comf': 'Купе',
            'delete': 'Удалить поезд',
            'fillUp': 'Заполните все поля.',
            'station added': 'Станция успешно добавлена',
            'exists': 'Поезд уже существует.'
        },
        en: {
            'title': 'Edit train',
            'save': 'Save',
            'name': 'Name EN',
            'nameRu': 'Name RU',
            'econ': 'Economy',
            'bus': 'Business',
            'comf': 'Comfort',
            'cancel': 'Cancel',
            'delete': 'Delete train',
            'fillUp': 'Please fill up all the fields.',
            'exists': 'Train already exists'
        }

    };
}