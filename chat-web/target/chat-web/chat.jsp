<%--
  Created by IntelliJ IDEA.
  User: Huawei
  Date: 2021. 04. 02.
  Time: 2:09
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
        <th scope="col">Messages</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${sessionScope.messages}">
        <tr>
            <td>${item}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
