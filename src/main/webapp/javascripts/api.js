
function get(url, success,async){
    $.ajax({
        type: "get",
        url: url,
        async: async,
        dataType: 'json',
        // xhrFields: {
        //     withCredentials: true
        // },
        success: success
    });
}



function post(url, data, success,async){
    $.ajax({
        type: "post",
        url: url,
        async: async,
        data: data,
        dataType: 'json',
        // xhrFields: {
        //     withCredentials: true
        // },
        success: success
    });
}

/**
 * 登录  同步请求
 */
function login(username,password,userPortrait){
    $.post("/login",
        {"username":username,"password":password,"userPortrait":userPortrait},
        function(data){
            if(data.code === 200) {
                window.location.href = 'room.html'; // 页面跳转
            }else{
                alert(data.message);
            }
        },
        false
    );
}

/**
 * 登出  同步请求
 */
function logout(){
    $.get("/logout",
        function(data){
            if(data.code === 200) {
                window.location.href = 'login.html'; // 页面跳转
            }else{
                alert(data.message);
            }
        },
        false
    );
}


/**
 * 获取用户昵称   同步请求
 */
function getUserNickName(){
    let nickname ="";
    get("/user-nickName",function (data){
        if(data.code === 200){
            // alert(data.data)
            nickname = data.data; // 用户昵称
        }else {
            window.location = "login.html"
        }
    },false);
    return nickname;
}

/**
 * 获取在线用户列表    同步请求 ---初始化调用
 */
function getInitOnlineList(){
    let userList = "";
    get("/user-onlineList",function (data){
        if(data.code === 200){
            // alert(JSON.stringify(data))
            userList = data.data;
        }else {
            window.location = "login.html"
        }
    },false);
    return userList;
}

/**
 * 发送消息
 * @param message   消息内容
 */
function sendMessage(message,time){
    post("/message",{'message':message,'time':time},function (data){
        if(data.code === 200){

            // alert(JSON.stringify(data))
        }else {
            window.location = "login.html"
        }
    },true)
}

/**
 * 接收消息
 */
// function getMessage(){
//     get("/message",function (data){
//         if(data.code === 200){
//             alert(data.data)
//         }else {
//             window.location = "login.html"
//         }
//     },false)
// }






