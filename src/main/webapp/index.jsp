<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title class="lang" key="title">Ukrainian railways</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
    <script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" href="../../css/Style.css">
</head>
<body>
<div id="login">
    <div class="container">
        <div class="row">
            <div class="col-md-10" id="title">
                <h3 class="lang" key="title">Ukrainian Railways</h3>
            </div>
            <div class="col-md-2" id="flags">

                <button class="translate" id="ru"><img src="../img/Ru.jpg" alt="Русский"></button>
                <button class="translate" id="en"><img src="../img/eng.png" alt="English"></button>
            </div>
        </div>
        <div id="login-row" class="row justify-content-center align-items-center">
            <div id="login-column" class="col-md-6">
                <div id="login-box" class="col-md-12">
                    <div class="form-group">
                        <label for="username" class="lang" key="email">Email:</label><br>
                        <input type="email" id="username" class="form-control">
                    </div>
                    <div class="form-group">
                        <label for="password" class="lang" key="pass">Password:</label><br>
                        <input type="password" id="password" class="form-control">
                    </div>
                    <div class="form-group">
                        <button class="lang" key="log" id="enter">Log in</button>
                    </div>
                    <div id="register">
                        <a href="http://localhost:9999/html/user/RegisterUser.html" class="lang" key="reg" id="reg">Register</a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="js/Index.js"></script>
</body>
<footer>
    <p class="copyright"> &#169; Copyright Made for Epam Systems by Sergey Udaltsov email: sergii.udaltsov@gmail.com,
        skype: afranij31</p>
</footer>
</html>
