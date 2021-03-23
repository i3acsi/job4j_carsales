<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login Register Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css"
          integrity="sha384-r4NyP46KrjDleawBgD5tp8Y7UzmLA05oM1iAEQ17CSuDqnUK2+k9luXQOfXJCJ4I" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

    <style>
        @import "style/mystyle.css";
    </style>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-fixed-top navbar-dark" style="background-color:  #30363d; alignment: center">
        <div class="container">
            <div class="col">
                <img src="img/brand4.png" alt="" class="img-fluid pointer w-100 "
                     onclick="document.location.href = getContextPath()">
            </div>
            <div class="col">
                <span class="text-danger " id="hint"></span>
                <span class="text-success " id="hintSuccess"></span>
            </div>
        </div>
    </nav>
    <div class="container mx-auto p-2 bg-secondary">
        <div class="row">
            <div class="col">
                <label for="loginInput" class="shadowtext p-2">Login</label>
                <div class="card bg-secondary box p-2" id="loginInput">
                    <div class="input-group mb-3">
                        <div class="input-group-text lbl border border-success">
                            <span class="text-success">Email</span>
                            <span class="text-danger mx-2">*</span>
                        </div>
                        <input type="email" class="form-control" id="email" required>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text lbl border border-success">
                            <span class="text-success">Password</span>
                            <span class="text-danger mx-2">*</span>
                        </div>
                        <input type="password" class="form-control" id="password" required>
                    </div>
                    <div class="input-group mb-3">
                        <button type="submit" class="btn btn-dark btn-outline-success" onclick="login()">Войти</button>
                    </div>
                </div>
            </div>
            <div class="col">
                <label for="regInput" class="shadowtext p-2">Register a new account</label>
                <div class="card bg-secondary box p-2" id="regInput">
                    <form>
                        <div class="input-group mb-3">
                            <div class="input-group-text lbl border border-success">
                                <span class="text-success">Имя пользователя</span>
                                <span class="text-danger mx-2">*</span>
                            </div>
                            <input type="text" class="form-control" id="newName" required>
                        </div>

                        <div class="input-group mb-3">
                            <div class="input-group-text lbl border border-success">
                                <span class="text-success">Email</span>
                                <span class="text-danger mx-2">*</span>
                            </div>
                            <input type="email" class="form-control" id="newEmail" required>
                        </div>

                        <div class="input-group mb-3">
                            <div class="input-group-text lbl border border-success">
                                <span class="text-success">Телефон</span>
                                <span class="text-danger mx-2">*</span>
                            </div>
                            <input type="text" class="form-control" id="newPhone" required>
                        </div>

                        <div class="input-group mb-3">
                            <div class="input-group-text lbl border border-success">
                                <span class="text-success">Пароль</span>
                                <span class="text-danger mx-2">*</span>
                            </div>
                            <input type="password" class="form-control" id="newPWD" required>
                        </div>

                        <div class="input-group mb-3">
                            <div class="input-group-text lbl border border-success">
                                <span class="text-success">Подтвердите пароль</span>
                                <span class="text-danger mx-2">*</span>
                            </div>
                            <input type="password" class="form-control" id="newPWDConfirm" required>
                        </div>
                        <button type="submit" class="btn btn-dark btn-outline-success" onclick="reg()">Зарегистрировать
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="js/validate.js"></script>
<script src="js/hint.js"></script>
<script src="js/logreg.js"></script>
</body>
</html>