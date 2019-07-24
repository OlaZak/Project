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
        <div class="row">
            <div class="col-1">Login</div>
            <div class="col-4"><input name="login" class="padding-left" type="login" placeholder="login"/></div>
        </div>
        <div class="row">
            <div class="col-1">Password:</div>
            <div class="col-11"><input name="pass" class="padding-left" type="password" placeholder="password"/></div>
        </div>
        <div class="row">
            <div class="col-1">Press:</div>
            <div class="col-4"><input type="submit" value="Submit"></div>
        </div>
    </form>
</div>
</body>
</html>
