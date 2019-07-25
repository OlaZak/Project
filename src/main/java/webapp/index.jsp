<%--
  Created by IntelliJ IDEA.
  User: Оля
  Date: 04.02.2019
  Time: 23:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
<title>Hello</title>
<link rel="stylesheet" href="design.css"/>
</head>
<body>
<div class="container" style="margin-top: 15px">
    <form action="/Pro/main" method="post" class="form">
        <div class="login">
            <div class="label">Login</div>
            <input name="login" type="login" placeholder="user123"/>
        </div>

        <div class="password">
            <div class="label">Password:</div>
            <input name="pass" type="password" placeholder="qwerty"/>
        </div>
        <button class="btn">submit</button>

    </form>
</div>
</body>
</html>
