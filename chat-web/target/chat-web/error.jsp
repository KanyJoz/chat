<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <link rel="stylesheet" href="resources/css/error.css">
    <title>Chat App - KJ</title>
</head>
<body>
    <ul class="nav nav-pills justify-content-end">
        <c:choose>
            <c:when test="${sessionScope.user != null}">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="index.jsp">Main Page</a>
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

    <div class="card" style="width: 40rem;">
        <svg xmlns="http://www.w3.org/2000/svg" width="75" height="75" fill="currentColor" class="bi bi-cloud-sleet-fill card-img-top" viewBox="0 0 16 16">
            <path d="M2.375 13.5a.25.25 0 0 1 .25.25v.57l.501-.287a.25.25 0 0 1 .248.434l-.495.283.495.283a.25.25 0 1 1-.248.434l-.501-.286v.569a.25.25 0 1 1-.5 0v-.57l-.501.287a.25.25 0 1 1-.248-.434l.495-.283-.495-.283a.25.25 0 1 1 .248-.434l.501.286v-.569a.25.25 0 0 1 .25-.25zm1.849-2.447a.5.5 0 0 1 .223.67l-.5 1a.5.5 0 0 1-.894-.447l.5-1a.5.5 0 0 1 .67-.223zM6.375 13.5a.25.25 0 0 1 .25.25v.57l.5-.287a.25.25 0 0 1 .249.434l-.495.283.495.283a.25.25 0 1 1-.248.434l-.501-.286v.569a.25.25 0 1 1-.5 0v-.57l-.501.287a.25.25 0 1 1-.248-.434l.495-.283-.495-.283a.25.25 0 1 1 .248-.434l.501.286v-.569a.25.25 0 0 1 .25-.25zm1.849-2.447a.5.5 0 0 1 .223.67l-.5 1a.5.5 0 0 1-.894-.447l.5-1a.5.5 0 0 1 .67-.223zm2.151 2.447a.25.25 0 0 1 .25.25v.57l.5-.287a.25.25 0 0 1 .249.434l-.495.283.495.283a.25.25 0 1 1-.248.434l-.501-.286v.569a.25.25 0 0 1-.5 0v-.57l-.501.287a.25.25 0 1 1-.248-.434l.495-.283-.495-.283a.25.25 0 1 1 .248-.434l.501.286v-.569a.25.25 0 0 1 .25-.25zm1.849-2.447a.5.5 0 0 1 .223.67l-.5 1a.5.5 0 1 1-.894-.447l.5-1a.5.5 0 0 1 .67-.223zm1.181-7.026a5.001 5.001 0 0 0-9.499-1.004A3.5 3.5 0 1 0 3.5 10H13a3 3 0 0 0 .405-5.973z"/>
        </svg>
        <div class="card-body">
            <h5 class="card-title">Something went wrong</h5>
            <p class="card-text">${sessionScope.error}!</p>
            <a href="index.jsp" class="btn btn-primary">Go Back</a>
        </div>
    </div>
</body>
</html>
