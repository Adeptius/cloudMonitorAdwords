$(document).ready(function () {
    whenReady();
});

var registrationUrl = '/registration';

function whenReady() {
    showresult();
}

function showresult() {
    var key = getKeyFromUrl();
    $.post(registrationUrl + '/key',
        {key: key}, function (result) {
            processResult(result);
        });
}

function getKeyFromUrl() {
    var query = window.location.search;
    key = query.substring(query.indexOf('=') + 1);
    return key;
}

function processResult(result) {
    if (result.indexOf('token')>-1){ // если key есть
        var token = result.substring(result.indexOf('=') + 1);
        showThatAllOkAndRedirect(token)
    }else if (result.indexOf('WrongKey')>-1){
        showThatKeyIsWrongOrExpired();
    }else {
        $('.main-container').append('ошибка..');
    }
}


function showThatKeyIsWrongOrExpired() {
    var bigText = $('<h1><b>Регистрация:</b> <br/> <br/>Ссылка неправильная или прошло много времени</h1>');
    bigText.addClass('h1reg');
    $('.main-container').append(bigText);
}

function showThatAllOkAndRedirect(token) {
    localStorage.setItem('token', token);
    var bigText = $('<h1><b>Регистрация успешна!</b> <br/> <br/> Сейчас вы будете перемещены на главную страницу</h1>');
    bigText.addClass('h1reg');
    $('.main-container').append(bigText);
    setTimeout(function() {

        $('.main-container').find('*').slideUp();

        setTimeout(function() {
            window.location.href = '/';
        },800)

    }, 3000);

}