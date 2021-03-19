<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Account profile</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css"
          integrity="sha384-r4NyP46KrjDleawBgD5tp8Y7UzmLA05oM1iAEQ17CSuDqnUK2+k9luXQOfXJCJ4I" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <style>
        @import "style/mystyle.css";
    </style>
</head>
<body>
<div class="container ">
    <nav class="navbar navbar-fixed-top navbar-dark" style="background-color:  #30363d; alignment: center">
        <div class="container">
            <div class="col-lg">
                <img src="img/brand4.png" alt="" class="img-fluid pointer w-100 "
                     onclick="document.location.href = location.origin + '/auto'">
            </div>
            <div class="col" align="right">
                <span class="text-danger" id="hint"></span>
                <span class="text-success" id="hintSuccess"></span>
                <button class="btn btn-outline-success pull-right mt-5" onclick="logout()">Logout</button>
            </div>
        </div>
    </nav>

    <div class="container mx-auto p-2 bg-secondary">
        <div class="row">
            <div class="form-group col">
                <div class="input__wrapper">
                    <img src="img/userpic.png" id="userpic" class="img-fluid rounded" width="200" height="200"
                         alt="img/userpic.png">
                    <br>
                    <input name="file" type="file" id="photo" class="input input__file"
                           accept="image/jpeg,image/png,image/gif">
                    <label for="photo" class="btn btn-dark btn-outline-success mt-2">Выберете фото профиля
                    </label>
                </div>
                <h5 class="shadowtext">Мои объявления</h5>
                <table class="table table-dark">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Модель</th>
                        <th scope="col">Цена</th>
                        <th scope="col">Фото</th>
                        <th scope="col">Статус</th>
                    </tr>
                    </thead>
                    <tbody id="myAdList">

                    </tbody>
                </table>
            </div>
            <div class="col-4">
                <form>
                    <input type="text" id="id" hidden>
                    <div class="input-group mb-3">
                        <div class="input-group-text lbl border border-success">
                            <span class="text-success" id="l_name">Имя</span>
                            <span class="text-danger mx-2">*</span>
                        </div>
                        <input type="text" class="form-control" id="name" required>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text lbl border border-success">
                            <span class="text-success" id="l_email">Email</span>
                            <span class="text-danger mx-2">*</span>
                        </div>
                        <input type="email" class="form-control" id="email" required>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text lbl border border-success">
                            <span class="text-success" id="l_phone">Телефон</span>
                            <span class="text-danger mx-2">*</span>
                        </div>
                        <input type="text" class="form-control" id="phone" required>
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text lbl border border-success">
                            <span class="text-success" id="l_location">Город</span>
                            <span class="text-danger mx-2">*</span>
                        </div>
                        <input type="text" class="form-control" id="location">
                    </div>
                    <div class="input-group mb-3">
                        <div class="input-group-text lbl border border-success">
                            <span class="text-success">Подтвердите пароль</span>
                            <span class="text-danger mx-2">*</span>
                        </div>
                        <input type="password" class="form-control" id="pwd">
                    </div>
                    <div class="mb-3">
                        <button class="btn btn-dark btn-outline-success mt-2 pull-right" onclick="saveDataToAccount(true)">Сохранить
                            данные
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
<script src="js/hint.js"></script>
<script src="js/validate.js"></script>
<script src="js/ad.js"></script>
<script src="js/account.js"></script>
<script>
    $(document).ready(function () {
        loadUserData()
    })


    function clearForm() {
        document.getElementById("pwd").value = ''
        document.getElementById("hintSuccess").value = ''
        document.getElementById("hint").value = ''
    }

    function logout() {
        $.get({
            url: location.origin + '/auto/auth'
        })
        location.href = location.origin + '/auto'
    }
</script>
</body>
</html>