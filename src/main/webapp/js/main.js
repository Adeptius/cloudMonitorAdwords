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
    $('.logoutButton').on('click',function () {
        localStorage.setItem('token', '');
        window.location.href = '../login';
    });
    start();
});



function start() {
   showUserNameInHeader();
   addListenersToButtons();
    sitesButtonPressed();
}


function showUserNameInHeader() {
    $.ajax({
        url: '/user/getUser',
        type: 'post',
        headers: {
            Authorization: token
        },
        success: function (data) {
            var user = JSON.parse(data);
            var userName = user['login'];
            $('#userName').text(userName);
        }
    });
}

function addListenersToButtons() {
    $('#sitesButton').on('click', function () {
        sitesButtonPressed();
    });

    $('#settingsButton').on('click', function () {
        settingsButtonPressed();
    });
}

function sitesButtonPressed() {
    var data = null;

    $.ajax({
        url: '/sites/getSites',
        type: 'post',
        headers: {
            Authorization: token
        },
        async: false,
        success: function (receivedData) {
           data = receivedData;
        }
    });

    for (var i = 0; i < data.length; i++) {
        var obj = data[i];
        console.log('url ->' + obj.url);
        console.log('login ->' + obj.login);
        console.log('checkDelay ->' + obj.checkDelay);
        console.log('triesToEmail ->' + obj.triesToEmail);
        console.log('lookingWord ->' + obj.lookingWord);
    }
}


function settingsButtonPressed() {
    var data = null;

    $.ajax({
        url: '/sites/getSites',
        type: 'post',
        headers: {
            Authorization: token
        },
        async: false,
        success: function (receivedData) {
            data = receivedData;
        }
    });


    for (var i = 0; i < data.length; i++) {
        var obj = data[i];
        console.log('url ->' + obj.url);
        console.log('login ->' + obj.login);
        console.log('checkDelay ->' + obj.checkDelay);
        console.log('triesToEmail ->' + obj.triesToEmail);
        console.log('lookingWord ->' + obj.lookingWord);

        var html = $('#template').html();

        var content = tmpl(html, obj);

        $('.content').append(content);
    }






}