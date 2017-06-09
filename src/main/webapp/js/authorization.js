$(document).ready(function () {
    whenReady();
});

function whenReady() {
    $('#loginButton').on('click', function () {
        tryLogin();
    })
}

function tryLogin() {
    // searching all fields
    var loginField = $('#loginField');
    var passwordField = $('#passwordField');

    var login = loginField.val();
    var password = passwordField.val();

    $.post('/authorization/checkAuthorization',
        {login: login, password: password}, function (result) {
            processResult(result);
        });
}

function processResult(result) {
    if (result === 'false') {
        showLoginAndPassIncorrect();
    } else if (result.indexOf('token') > -1) {
        var token = result.substring(result.indexOf('=') + 1);
        localStorage.setItem('token', token);
        window.location.href = '../';
    }
}


function showLoginAndPassIncorrect() {

    var loginDiv = $('#inputLoginDiv');
    loginDiv.removeClass('has-success');
    loginDiv.removeClass('has-warning');
    loginDiv.removeClass('has-error');
    loginDiv.addClass('has-error');

    var passwordDiv = $('#inputPasswordDiv');
    passwordDiv.removeClass('has-success');
    passwordDiv.removeClass('has-warning');
    passwordDiv.removeClass('has-error');
    passwordDiv.addClass('has-error');
    passwordDiv.find('span').html('Неправильный логин или пароль');
}