<%--
  Created by IntelliJ IDEA.
  User: LS
  Date: 2022/5/5
  Time: 20:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>登录</title>
    <script>
        window.onload=function (){
            var img = document.getElementById("image");
            img.onclick = function () {
                img.src="/test/checkcode?time"+new Date().getTime();
            }
        }
    </script>
</head>
<body>
<form action="/test/checkLogin">
    用户名<input type="text" name="username"><br>
    密码  <input type="password" name="pwd"><br>
    验证码<input type="text" name="checkcode"><img id="image" src="/test/checkcode"><br>
    <input type="submit" value="登录">
    <h3>${msg}</h3>
</form>
</body>
</html>
