
function get(url, success){
    $.ajax({
        type: "get",
        url: url,
        async: true,
        dataType: 'json',
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
        success: success
    });
}


function addCart(id){
    let nums = $("#bnums-"+id).val();
    post("/api/cart/add",{
        id:id,
        nums:nums
    },function (data){
        if(data.code === 200)
            showNotify(data.message,"info");
        else
            alert(data.message);
    })
}

function delCart(id){
    post("/api/cart/del",{
        id:id,
    },function (data) {
        if (data.code === 200) {
            showNotify(data.message,"success",init);
        }
        else
            showNotify(data.message,"warning");
    })
}

function settleCart(id){
    post("/api/cart/settle",{
        id:id,
    },function (data) {
        if (data.code === 200) {
            showNotify(data.message,"success",init);
        }
        else
            showNotify(data.message,"warning");
    })
}

function init() {
    get("/api/cart",function (data){
        if(data.code === 200){
            flushList(data.data);
        }
    });
}

function flushList(bookList){
    $("#bookList").empty()
    for (let book of bookList) {
        $("#bookList").html($("#bookList").html()
            + '<tr>'
            + '<td>'
            +        '<div class="custom-control custom-checkbox">'
            +             '<input type="checkbox" class="custom-control-input ids" name="ids[]" value="'+book.id+'" id="ids-'+book.id+'">'
            +                 '<label class="custom-control-label" for="ids-'+book.id+'"></label>'
            +         '</div>'
            + '</td>'
            +  '<td>'+book.id+'</td>'
            +     '<td>《'+book.title+'》</td>'
            +     '<td>'+book.author+'</td>'
            +     '<td>'+book.price+'</td>'
            +     '<td>'+book.nums+'</td>'
            +     '<td>'
            +         '<div class="btn-group">'
            +             '<a class="btn btn-xs btn-default" href="javascript:void(0);" onclick="settleCart('+book.id+')" title="" data-toggle="tooltip"'
            +                'data-original-title="购买"><i class="mdi mdi-18px mdi-cart-plus"></i></a>'
            +             '<a class="btn btn-xs btn-default" href="javascript:void(0);" onclick="delCart('+book.id+')" title=""'
            +                'data-toggle="tooltip" data-original-title="移除"><i class="mdi mdi-18px mdi-cart-minus"></i></a>'
            +         '</div>'
            +     '</td>'
            + '</tr>')
    }
}

/*
 * 提取通用的通知消息方法
 * 这里只采用简单的用法，如果想要使用回调或者更多的用法，请查看lyear_js_notify.html页面
 * @param $msg 提示信息
 * @param $type 提示类型:'info', 'success', 'warning', 'danger'
 * @param $delay 毫秒数，例如：1000
 * @param $icon 图标，例如：'fa fa-user' 或 'glyphicon glyphicon-warning-sign'
 * @param $from 'top' 或 'bottom' 消息出现的位置
 * @param $align 'left', 'right', 'center' 消息出现的位置
 */
function showNotify($msg, $type,$onShow, $delay, $icon, $from, $align) {
    $type  = $type || 'info';
    $delay = $delay || 1000;
    $from  = $from || 'top';
    $align = $align || 'right';
    $enter = $type == 'danger' ? 'animated shake' : 'animated fadeInUp';

    jQuery.notify({
            icon: $icon,
            message: $msg
        },
        {
            element: 'body',
            type: $type,
            allow_dismiss: true,
            newest_on_top: true,
            showProgressbar: false,
            placement: {
                from: $from,
                align: $align
            },
            offset: 20,
            spacing: 10,
            z_index: 10800,
            delay: $delay,
            animate: {
                enter: $enter,
                exit: 'animated fadeOutDown'
            },
            onShow:$onShow
        });
}

/**
 * 登录  异步请求
 */
function login(data){
    $.post("/api/login",data,
        function(data){
            if(data.code === 200) {
                window.location.href = 'index.html'; // 页面跳转
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
 * 获取书籍列表    同步请求 ---初始化调用
 */
function getBookList(){
    let bookList = "";
    get("/api/books",function (data){
        if(data.code === 200){
            // alert(JSON.stringify(data))
            bookList = data.data;
        }
    });
    return bookList;
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






