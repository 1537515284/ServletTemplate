
$(document).ready(function(){
	let nickname = null;
	let userList = null;
	let userPortrait = null;

	init();

	function init(){
		// 获取用户昵称 和 在线用户列表
		nickname = getUserNickName();
		userList = getOnlineList();

		// 获取用户头像信息
		userPortrait =  $.cookie("userPortrait");

		// 初始化用户昵称
		$("#nickname").text(nickname);

		flush_userList(userList);



		getMessage();
		getSysMsg();
	}

	/**
	 * 接收用户消息
	 */
	function getMessage(){
		get("/message",function (data){
			if(data.code === 200){
				let msgList = data.data;
				for (let msg of msgList) {
					flush_message(msg.sender_nickname, userPortrait, msg.content,msg.time);
				}
			}
			setTimeout(getMessage,2000);
		},true)
	}

	/**
	 * 接收系统消息
	 */
	function getSysMsg(){
		get("/systemMessage",function (data){
			if(data.code === 200){
				let msgList = data.data;
				for (let msg of msgList) {
					sys_message(msg.nickname);
				}
				flush_userList(msgList);
			}
			setTimeout(getSysMsg,2000);
		},true)
	}


	// 登出按钮
	$('#logout').click(function(event) {
		logout();
	});






// --------------------聊天室内页面----------------------------------------------------

	// 发送图片

	$('.imgFileBtn').change(function(event) {


		var str = '<img src="images/chatimg/' + '1/201503/agafsdfeaef.jpg' +'" />'

		let time = curTime();
		sends_message(nickname, userPortrait, str,curTime()); // sends_message(昵称,头像id,聊天内容);


		// 滚动条滚到最下面
		$('.scrollbar-macosx.scroll-content.scroll-scrolly_visible').animate({
			scrollTop: $('.scrollbar-macosx.scroll-content.scroll-scrolly_visible').prop('scrollHeight')
		}, 500);
	});

	// 发送消息

	$('.text input').focus();
	$('#subxx').click(function(event) {
		var str = $('.text input').val(); // 获取聊天内容
		str = str.replace(/\</g,'&lt;');
		str = str.replace(/\>/g,'&gt;');
		str = str.replace(/\n/g,'<br/>');
		str = str.replace(/\[em_([0-9]*)\]/g,'<img src="images/face/$1.gif" alt="" />');
		if(str!='') {

			// 异步发送消息
			let time = curTime();
			sendMessage(str,time);
			sends_message(nickname, userPortrait, str,time); // sends_message(昵称,头像id,聊天内容);

			// 滚动条滚到最下面
			$('.scrollbar-macosx.scroll-content.scroll-scrolly_visible').animate({
				scrollTop: $('.scrollbar-macosx.scroll-content.scroll-scrolly_visible').prop('scrollHeight')
			}, 500);

		}
		$('.text input').val(''); // 清空输入框
		$('.text input').focus(); // 输入框获取焦点
	});




// -----下边的代码不用管---------------------------------------



	jQuery('.scrollbar-macosx').scrollbar();
	$('.topnavlist li a').click(function(event) {
		$('.topnavlist .popover').not($(this).next('.popover')).removeClass('show');
		$(this).next('.popover').toggleClass('show');
		if($(this).next('.popover').attr('class')!='popover fade bottom in') {
			$('.clapboard').removeClass('hidden');
		}else{
			$('.clapboard').click();
		}
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
	$('.face_btn,.faces').hover(function() {
		$('.faces').addClass('show');
	}, function() {
		$('.faces').removeClass('show');
	});
	$('.faces img').click(function(event) {
		if($(this).attr('alt')!='') {
			$('.text input').val($('.text input').val() + '[em_' + $(this).attr('alt') + ']');
		}
		$('.faces').removeClass('show');
		$('.text input').focus();
	});
	$('.imgFileico').click(function(event) {
		$('.imgFileBtn').click();
	});

	$('.text input').keypress(function(e) { 
		if (e.which == 13){
			$('#subxx').click();
		}
	});

	function sends_message (userName, userPortrait, message,time) {

		if(message!='') {
			$('.main .chat_info').html($('.main .chat_info').html() + '<li class="right"><img src="images/user/' + userPortrait + '.png" alt=""><b>' + userName + '</b><i>'+ time +'</i><div class="aaa">' + message  +'</div></li>');
		}
	}

	function flush_message (userName, userPortrait, message,time) {
		if(message!='') {
			$('.main .chat_info').html($('.main .chat_info').html() + '<li class="left"><img src="images/user/' + userPortrait + '.png" alt=""><b>' + userName + '</b><i>'+ time +'</i><div>' + message  +'</div></li>');
		}
	}

	function sys_message (nickname) {
		if(nickname!='') {
			$('.main .chat_info').html($('.main .chat_info').html() + '<li class="systeminfo">' +
				'<span>【<span class="text-success" style="display: inline">'+ nickname +'</span> 】 加入了房间</span> </li>');
		}
	}


	function flush_userList(userList){
		// 初始化在线用户列表
		let curNum = Number($("#userCount").text());
		if(isNaN(curNum))
			curNum = 0;
		let count =  curNum + userList.length;
		$("#userCount").text(count);
		for (let userListElement of userList) {
			$("#userList").html($("#userList").html()
				+ "<li> <img src='images/user/12.png' alt='portrait_1'> <b>" +userListElement.nickname+"</b> </li>");
		}
	}


	function curTime(){
		let myDate = new Date;
		let h = myDate.getHours();//获取当前小时数(0-23)
		let m = myDate.getMinutes();//获取当前分钟数(0-59)
		let s = myDate.getSeconds();//获取当前秒
		let time = h+':'+m+':'+s;
		return time;
	}



});
