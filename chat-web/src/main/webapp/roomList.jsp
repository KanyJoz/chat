<%--
  Created by IntelliJ IDEA.
  User: Huawei
  Date: 2021. 04. 02.
  Time: 0:24
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table class="table">
    <thead class="thead-dark">
    <tr>
        <th scope="col">Username</th>
        <th scope="col">Email</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${sessionScope.rooms}">
        <tr>
            <td>${item.name}</td>
            <td><a href="roomname-servlet?roomId=${item.id}">People of Room</a></td>
            <td><a href="roomtype-servlet?roomId=${item.id}">Join</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
