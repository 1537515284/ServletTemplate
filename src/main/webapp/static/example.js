function askVerifyCode(){
    get("http://localhost:8080/api/auth/verify-code",{
        email:$("#input-email").val()
    },function (data){
        if(data.code === 200)
            window.location.href="login.html"
        else
            alert(data.message)
    })
}

function login(){
    post("http://localhost:8080/api/auth/login",{
        username: $("#username").val(),
        password: $("#password").val()
    },function (data){
        if(data.code === 200){
            window.location = "index.html"
        }else {
            alert(data.message)
        }
    })
}

function logout(){
    get("http://localhost:8080/api/auth/logout",function (data){
        if(data.code === 200){
            window.location = "login.html"
        }else {
            alert(data.message)
        }
    })
}

function initUserInfo(){
    get("http://localhost:8080/api/user/info",function (data){
        if(data.code === 200){
            $("#profile-name").text(data.data.username)
        }else {
            window.location = "login.html"
        }
    })
}

function get(url, success){
    $.ajax({
        type: "get",
        url: url,
        async: true,
        dataType: 'json',
        // 跨域
        xhrFields: {
            withCredentials: true
        },
        success: success
    });
}

function post(url, data, success){
    $.ajax({
        type: "post",
        url: url,
        async: true,
        data: data,
        dataType: 'json',
        xhrFields: {
            withCredentials: true
        },
        success: success
    });
}