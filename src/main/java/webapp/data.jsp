<%@ page import="model.Order" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Оля
  Date: 23.07.2019
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Data</title>
    <link rel="stylesheet" href="design.css" />
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css" integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS" crossorigin="anonymous">
</head>
<body class="body">
<div class="container">
    <table class="table table-striped ">
        <thead>
        <tr class="h3">
            <th scope="col">Id</th>
            <th scope="col">name</th>
            <th scope="col">description</th>
            <th scope="col">moment</th>
            <th scope="col">sum</th>
            <th scope="col">counterparty_uuid</th>
        </tr>
        </thead>
        <tbody>
        <%
            ArrayList<Order> orders = (ArrayList<Order>) request.getAttribute("orders");
            for (Order order : orders) {
        %>
        <tr>
            <td><input class="form-control" type="text" name="Id" value="<%=order.getOrderId()%>" readonly >
            </td>
                <td><input class="form-control" type="text" name="name" value="<%=order.getName()%>"readonly >
                </td>
                <td><input class="form-control" type="text" name="description" value="<%=order.getDescription()%>"readonly>
                </td>
            <td><input class="form-control" type="text" name="moment" value="<%=order.getMoment()%>"readonly>
            </td>
            <td><input class="form-control" type="text" name="sum" value="<%=order.getSum()%>"readonly>
            </td>
            <td><input class="form-control" type="text" name="counterparty_uuid" value="<%=order.getCounterparty_uuid()%>"readonly>
            </td>
            </tr>
        <%} %>
        </tbody>
    </table>

</div>
<hr/>
<a href="/Pro"><button class="homeBtn">HOME</button> </a>

</body>
</html>
