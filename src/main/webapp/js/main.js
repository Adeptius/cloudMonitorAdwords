var token;
$(document).ready(function () {
    token = localStorage.getItem('token');
    if (token === null || token === '') {
        window.location.href = '../login';
    }else {
        $.post('/authorization/checkToken',
            {token: token}, function (result) {
                if (result != 'true'){
                    window.location.href = '../login';
                }
            });
    }
    start();
});

function start() {

}