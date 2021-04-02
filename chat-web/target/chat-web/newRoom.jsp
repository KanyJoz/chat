<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="resources/css/index.css">
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
                    <a class="nav-link active" href="newRoom.jsp">Create Room</a>
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

    <div class="container">
        <p class="h1">Chat Application Room Creation</p>
        <form action="newroom-servlet" method="post">
            <div class="mb-3">
                <label for="roomname" class="form-label">Name of the Room</label>
                <input maxlength="64" minlength="8" type="text" class="form-control" id="roomname" name="roomname" required>
            </div>
            <div class="mb-3">
                <label for="rules" class="form-label">Rules separated with new lines</label>
                <textarea class="form-control" id="rules" name="rules" rows="3" required></textarea>
            </div>
            <div class="mb-3">
                <select required class="form-select" name="roomtype">
                    <option value="0">Friends</option>
                    <option value="1">Work</option>
                    <option value="2">Sport</option>
                    <option value="3">Love</option>
                    <option value="4">Education</option>
                    <option value="5">Health</option>
                    <option value="6">Politics</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Create Room</button>
        </form>
    </div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</body>
</html>
