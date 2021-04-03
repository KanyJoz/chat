<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="resources/css/chat.css">
    <title>Chat App - KJ</title>
</head>
<body>
    <ul class="nav nav-pills justify-content-end">
        <c:choose>
            <c:when test="${sessionScope.user != null}">
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="index.jsp">Main Page</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="user-servlet">Your Page</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="newRoom.jsp">Create Room</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="logout-servlet">Logout</a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="index.jsp">Main Page</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" aria-current="page" href="login.jsp">Login</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="registration.jsp">Registration</a>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>

        <table class="table">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Sender</th>
                <th scope="col">Message</th>
            </tr>
            </thead>
            <tbody>
            <c:set var="counter" scope="session" value="${1}"/>
            <c:forEach var="item" items="${sessionScope.messages}">
                <tr>
                    <th scope="row">${sessionScope.counter}</th>
                    <td>${item.sender}</td>
                    <td>${item.message}</td>
                </tr>
                <c:set var="counter" scope="session" value="${sessionScope.counter + 1}"/>
            </c:forEach>
            </tbody>
        </table>

        <div class="container-sm">
            <form class="my-form" action="chat-servlet" method="post">
                <div class="mb-3">
                    <label for="message" class="form-label">Send Message</label>
                    <input type="text" class="form-control" id="message" name="message" required>
                </div>
                <button type="submit" class="btn btn-primary">Send</button>
            </form>
            <form class="my-form" action="refresh-servlet" method="get">
                <label for="refresh" class="form-label">Refresh Feed</label>
                <button id="refresh" type="submit" class="btn btn-primary">Refresh</button>
            </form>
            <form class="my-form" action="refresh-servlet" enctype = "multipart/form-data" method="post">
                <label for="img" class="form-label">Send Image</label>
                <input type="file" id="img" name="img" accept="image/*">
                <button type="submit" class="btn btn-primary">Send</button>
            </form>
        </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</body>
</html>
