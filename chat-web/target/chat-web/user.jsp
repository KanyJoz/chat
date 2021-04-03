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
                <a class="nav-link active" aria-current="page" href="user-servlet">Your Page</a>
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

<div class="container">
    <p class="h1">User Data And Removal</p>
    <form action="user-servlet" method="post">
        <div class="mb-3">
            <label for="username" class="form-label">Username</label>
            <input disabled value="${sessionScope.user.username}" maxlength="64" minlength="8" type="text" class="form-control" id="username" name="username" required>
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Email address</label>
            <input disabled value="${sessionScope.user.email}" type="email" class="form-control" id="email" name="email" required>
        </div>
        <div class="mb-3">
            <label for="age" class="form-label">Age</label>
            <input disabled value="${sessionScope.user.age}" type="number" class="form-control" id="age" name="age" required min="12" max="150">
        </div>
        <c:choose>
            <c:when test="${sessionScope.user.sex.equals('male')}">
                <div class="form-check">
                    <input disabled class="form-check-input" type="radio" value="male" name="sex" id="male" checked>
                    <label class="form-check-label" for="male">
                        Male
                    </label>
                </div>
                <div class="form-check">
                    <input disabled class="form-check-input" type="radio" value="female" name="sex" id="female">
                    <label class="form-check-label" for="female">
                        Female
                    </label>
                </div>
            </c:when>
            <c:otherwise>
                <div class="form-check">
                    <input disabled class="form-check-input" type="radio" value="male" name="sex" id="male2">
                    <label class="form-check-label" for="male2">
                        Male
                    </label>
                </div>
                <div class="form-check">
                    <input disabled class="form-check-input" type="radio" value="female" name="sex" id="female2" checked>
                    <label class="form-check-label" for="female2">
                        Female
                    </label>
                </div>
            </c:otherwise>
        </c:choose>

        <div class="mb-3">
            <label for="hobbies" class="form-label">Hobbies separated with new lines</label>
            <textarea disabled class="form-control" id="hobbies" name="hobbies" rows="3" required>${sessionScope.tmpHobbies}</textarea>
        </div>
        <button type="submit" class="btn btn-danger">DELETE YOURSELF</button>
    </form>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</body>
</html>
