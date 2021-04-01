<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-eOJMYsd53ii+scO/bJGFsiCZc+5NDVN2yr8+0RDqr0Ql0h+rP48ckxlpbzKgwra6" crossorigin="anonymous">
    <title>Chat App - KJ</title>
</head>
<body>

    <div class="container-md">
        <form action="username-servlet" method="get">
            <div class="mb-3">
                <label for="username" class="form-label">Username</label>
                <input type="text" class="form-control" id="username" name="username" required>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>

        <form action="hobby-servlet" method="get">
            <div class="mb-3">
                <label for="hobby" class="form-label">Username</label>
                <input type="text" class="form-control" id="hobby" name="hobby" required>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>

        <form action="roomname-servlet" method="get">
            <div class="mb-3">
                <label for="roomname" class="form-label">Username</label>
                <input type="text" class="form-control" id="roomname" name="roomname" required>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>

        <form action="roomtype-servlet" method="get">
            <div class="mb-3">
                <select class="form-select" name="roomtype">
                    <option value="1">Friends</option>
                    <option value="2">Work</option>
                    <option value="3">Sport</option>
                    <option value="4">Love</option>
                    <option value="5">Education</option>
                    <option value="6">Health</option>
                    <option value="7">Politics</option>
                </select>
            </div>
            <button type="submit" class="btn btn-primary">Submit</button>
        </form>

        <a href="login.jsp">Login</a>
        <a href="registration.jsp">Registration</a>
    </div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta3/dist/js/bootstrap.bundle.min.js" integrity="sha384-JEW9xMcG8R+pH31jmWH6WWP0WintQrMb4s7ZOdauHnUtxwoG2vI5DkLtS3qm9Ekf" crossorigin="anonymous"></script>
</body>
</html>
