
$(document).ready(function(){

// -------------------------登录页面---------------------------------------------------

    // 登录按钮

    $('#login').click(function(event) {

        let username = $('#username').val(); // 用户昵称
        let password = $('#password').val(); // 用户密码
        let userPortrait = $('.login img').attr('portrait_id'); // 用户头像id

        // cookie存放用户头像信息
        $.cookie("userPortrait",userPortrait,{expires:1});

        if(username=='' || password == '') {
            alert("用户名或密码不能为空!");
            return;
        }
        login(username,password);
    });



    $('.clapboard').click(function(event) {
        $('.topnavlist .popover').removeClass('show');
        $(this).addClass('hidden');
        $('.user_portrait img').attr('portrait_id', $('.user_portrait img').attr('ptimg'));
        $('.user_portrait img').attr('src', 'images/user/' + $('.user_portrait img').attr('ptimg') + '.png');
        $('.select_portrait img').removeClass('t');
        $('.select_portrait img').eq($('.user_portrait img').attr('ptimg')-1).addClass('t');
        $('.rooms .user_name input').val('');
    });
    $('.select_portrait img').hover(function() {
        var portrait_id = $(this).attr('portrait_id');
        $('.user_portrait img').attr('src', 'images/user/' + portrait_id + '.png');
    }, function() {
        var t_id = $('.user_portrait img').attr('portrait_id');
        $('.user_portrait img').attr('src', 'images/user/' + t_id + '.png');
    });
    $('.select_portrait img').click(function(event) {
        var portrait_id = $(this).attr('portrait_id');
        $('.user_portrait img').attr('portrait_id', portrait_id);
        $('.select_portrait img').removeClass('t');
        $(this).addClass('t');
    });

});
