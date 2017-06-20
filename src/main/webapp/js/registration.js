$(document).ready(function () {
    whenReady();
});

var registrationUrl = '/registration';

function whenReady() {
    // searching all fields
    var loginField = $('#loginField');
    var emailField = $('#emailField');
    var passwordField = $('#passwordField');
    var repeatPasswordField = $('#repeatPasswordField');

    // Cleaning all fields if autoinject
    loginField.val('');
    emailField.val('');
    passwordField.val('');
    repeatPasswordField.val('');

    //Login checker
    loginField.bind('input', function () {
        $.post(registrationUrl + '/checkNewLogin',
            {login: $(this).val()}, function (result) {
                checkLogin(result);
                disableOrEnableButton();
            });
    });

    //EmailChecker
    emailField.bind('input', function () {
        $.post(registrationUrl + '/checkEmail',
            {email: $(this).val()}, function (result) {
                checkEmail(result);
                disableOrEnableButton();
            });
    });

    //PasswordChecker
    passwordField.bind('input', function () {
        $.post(registrationUrl + '/checkPassword',
            {password: $(this).val()}, function (result) {
                checkPassword(result);
                checkRepeatPassword();
                disableOrEnableButton();
            });
    });

    //RepeatPasswordChecker
    repeatPasswordField.bind('input', function () {
        checkRepeatPassword();
        disableOrEnableButton();
    });

    $('#registerButton').on('click', function () {
        // keySended();
        register();
    })
}

function checkLogin(result) {
    var loginDiv = $('#inputLoginDiv');
    loginDiv.removeClass('has-success');
    loginDiv.removeClass('has-warning');
    loginDiv.removeClass('has-error');

    if (result == 'good') {
        loginDiv.addClass('has-success');
        loginDiv.find('span').html('Логин свободен');
        loginOk = true;
    } else if (result == 'error') {
        loginDiv.addClass('has-error');
        loginDiv.find('span').html('Логин должен состоять из 4 и более букв и/или цифр');
        loginOk = false;
    } else if (result == 'busy') {
        loginDiv.addClass('has-error');
        loginDiv.find('span').html('Логин занят');
        loginOk = false;
    } else if (result == 'awaiting'){
        loginDiv.addClass('has-error');
        loginDiv.find('span').html('Пользователю уже было отправлено письмо. Проверьте почту');
        loginOk = false;
    }
}

function checkEmail(result) {
    var emailDiv = $('#inputEmailDiv');
    emailDiv.removeClass('has-success');
    emailDiv.removeClass('has-warning');
    emailDiv.removeClass('has-error');

    if (result == 'good') {
        emailDiv.addClass('has-success');
        emailDiv.find('span').html('');
        emailOk = true;
    } else if (result == 'error') {
        emailDiv.addClass('has-error');
        emailDiv.find('span').html('Неправильный Email');
        emailOk = false;
    } else if (result == 'busy') {
        emailDiv.addClass('has-error');
        emailDiv.find('span').html('Этот Email уже зарегистрирован');
        emailOk = false;
    } else if (result == 'awaiting'){
        emailDiv.addClass('has-error');
        emailDiv.find('span').html('На этот Email было отправлено письмо. Проверьте почту.');
        emailOk = false;
    }
}

function checkPassword(result) {
    var passwordDiv = $('#inputPasswordDiv');
    passwordDiv.removeClass('has-success');
    passwordDiv.removeClass('has-warning');
    passwordDiv.removeClass('has-error');

    if (result == 'good') {
        passwordDiv.addClass('has-success');
        passwordDiv.find('span').html('');
        passwordOk = true;
    } else if (result == 'error') {
        passwordDiv.addClass('has-error');
        passwordDiv.find('span').html('Пароль должен состоять из 6 и более букв и/или цифр');
        passwordOk = false;
    }
}

function checkRepeatPassword() {
    var pass = $('#passwordField').val();
    var repeatPass = $('#repeatPasswordField').val();

    var repeatePasswordDiv = $('#inputRepeatPasswordDiv');
    repeatePasswordDiv.removeClass('has-success');
    repeatePasswordDiv.removeClass('has-warning');
    repeatePasswordDiv.removeClass('has-error');

    if (pass == repeatPass) {
        repeatePasswordDiv.addClass('has-success');
        repeatePasswordDiv.find('span').html('');
        repeatePasswordOk = true;
    } else {
        repeatePasswordDiv.addClass('has-error');
        repeatePasswordDiv.find('span').html('Пароли не совпадают');
        repeatePasswordOk = false;
    }
}

var loginOk = false;
var emailOk = false;
var passwordOk = false;
var repeatePasswordOk = false;

function disableOrEnableButton() {
    var button = $('#registerButton');
    if (loginOk && emailOk && passwordOk && repeatePasswordOk){
        button.removeAttr("disabled");
    }else {
        button.attr('disabled', 'disabled');
    }
}

function register() {
    var login = $('#loginField').val();
    var email = $('#emailField').val();
    var password = $('#passwordField').val();
    logged = login;

    $.post(registrationUrl + '/register',
        {login: login, password: password, email: email}, function (result) {
            if (result.indexOf('key sended to ')>-1){
                var email = result.substring(14, result.length);
                keySended(email);
            }
        });
}

var logged;

function keySended(email) {

    var loginContainer = $('.login-container');
    loginContainer.find('*').slideUp();

    var bigText = $('<h1>Почти готово!</h1>');
    bigText.addClass('h1reg');
    loginContainer.append(bigText);

    bigText.slideDown();

    var smallText = $('<h4>'+logged+', мы отправили Вам письмо на '+ email +' о подтверждении регистрации</h4>')
    loginContainer.append(smallText);
}