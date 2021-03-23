<%@ page contentType="text/html; charset=UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>New Announcement</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css"
          integrity="sha384-r4NyP46KrjDleawBgD5tp8Y7UzmLA05oM1iAEQ17CSuDqnUK2+k9luXQOfXJCJ4I" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <style>
        @import "style/mystyle.css";
    </style>
<body>
<div class="container ">
    <nav class="navbar navbar-fixed-top navbar-dark boarded" style="background-color:  #30363d; alignment: center">
        <div class="container">
            <div class="col-lg">
                <img src="img/brand4.png" alt="" class="img-fluid pointer w-100 "
                     onclick="document.location.href = getContextPath()">
            </div>
            <div class="col" align="right">
                <span class="text-danger " id="hint"></span>
                <span class="text-success " id="hintSuccess"></span>
                <button class="btn btn-outline-success pull-right mt-5" onclick="logout()">Logout</button>
            </div>
        </div>
    </nav>
    <div class="container-sm p-2 bg-secondary">
        <div class="row">
            <div class="form-group col-sm ">
                <label for="markModelInput" class="shadowtext" style="margin-top: 20px">Марки и Модели</label>
                <div class="container box p-2" id="markModelInput">
                    <div class="input-group mb-3">
                        <div class="input-group-text lbl border border-success">
                            <span class="text-success">Марка</span>
                        </div>
                        <select class="form-control input-sm myselect" id="mark" onchange="loadModels()">
                            <option disabled selected>Марка</option>
                        </select>
                        <div class="input-group-text btn btn-dark btn-outline-success ml-2" onclick="addMark()">
                            Добавить
                        </div>
                        <input type="text" class="form-control" id="markName">
                    </div>

                    <div class="input-group mb-3">
                        <div class="input-group-text lbl border border-success">
                            <span class="text-success">Модель</span>
                        </div>
                        <select class="form-control input-sm myselect" id="model" disabled  onchange="loadEnginesOfModel()">
                            <option disabled selected>Модель</option>
                        </select>
                        <div class="input-group-text btn btn-dark btn-outline-success ml-2" onclick="addModel()">
                            Добавить
                        </div>
                        <input type="text" class="form-control" id="modelName">
                    </div>
                    <div class="input-group mb-3" style="width: 60%; margin-left: auto; margin-right: 0em;">
                        <button class="btn btn-dark btn-outline-success mr-2" title="Удалить выбранный двигатель у выбранной модели" onclick="delEngineFromModel()">Удалить</button>
                        <div class="input-group-text lbl border border-success">
                            <span class="text-success">Двигатели модели</span>
                        </div>
                        <select class="form-control input-sm myselect" id="modelEngines" disabled>
                            <option disabled selected></option>
                        </select>
                    </div>

                </div>
                <label for="engineInput" class="shadowtext" style="margin-top: 20px">Двигатели</label>
                <div class="container box p-2" id="engineInput">
                    <div class="input-group mb-3">
                        <div class="input-group-text lbl border border-success">
                            <span class="text-success">Двигатель</span>
                        </div>
                        <select class="form-control myselect" id="engine" >
<%--                            <option disabled selected></option>--%>
                        </select>
                        <button class="btn btn-dark btn-outline-success mx-2" onclick="addEngine()">Добавить</button>
                        <div class="input-group-text lbl border border-success">
                            <span class="text-success">Название</span>
                            <span class="text-danger mx-2">*</span>
                        </div>
                        <input type="text" class="form-control" id="engineName">
                    </div>

                    <div class="input-group mb-3 " >
                        <button class="btn btn-dark btn-outline-success pull-left" title="Добавить выбранный двигатель выбранной модели" onclick="addEngineToModel()">Добавить двигатель модели</button>

                        <div class="input-group " style="width: 50%; margin-left: auto; margin-right: 0.5em;">
                            <div class="input-group-text lbl border border-success ml-2">
                                <span class="text-success">Мощность (л.с.)</span>
                                <span class="text-danger mx-2">*</span>
                            </div>
                            <input type="text" class="form-control" id="enginePower">
                        </div>
                    </div>

                    <div class="input-group mb-3 " style="width: 50%; margin-left: auto; margin-right: 0.5em;">
                        <div class="input-group-text lbl border border-success ml-2">
                            <span class="text-success">Топливо</span>
                            <span class="text-danger mx-2">*</span>
                        </div>
                        <select class="form-control input-sm myselect" id="fuel">
                            <option value="" disabled selected></option>
                            <option value="Бензин">Бензин</option>
                            <option value="Дизель">Дизель</option>
                            <option value="Газ">Газ</option>
                        </select>
                    </div>

                    <div class="input-group mb-3 " style="width: 50%; margin-left: auto; margin-right: 0.5em;">
                        <div class="input-group-text lbl border border-success ml-2">
                            <span class="text-success">Объем (л)</span>
                            <span class="text-danger mx-2">*</span>
                        </div>
                        <input type="number" step="0.1" class="form-control" id="engineVolume">
                    </div>
                </div>

                <label for="transmissionInput" class="shadowtext" style="margin-top: 20px">Трансмиссии </label>
                <div class="container box p-2" id="transmissionInput">
                    <div class="input-group mb-3">
                        <div class="input-group-text lbl border border-success">
                            <span class="text-success">Трансмиссия</span>
                        </div>
                        <select class="form-control input-sm myselect" id="transmission">
                            <option disabled selected></option>
                        </select>
                        <button class="btn btn-dark btn-outline-success mx-2" onclick="addTm()">Добавить</button>
                        <div class="input-group-text lbl border border-success">
                            <span class="text-success">Тип</span>
                            <span class="text-danger mx-2">*</span>
                        </div>
                        <select class="form-control input-sm myselect" id="tmType">
                            <option disabled selected></option>
                            <option value="AT">Автомат</option>
                            <option value="MT">Механика</option>
                        </select>
                    </div>
                    <div class="input-group mb-3" style="width: 50%; margin-left: auto; margin-right: 0em;">
                        <div class="input-group-text lbl border border-success">
                            <span class="text-success">Вид привода</span>
                            <span class="text-danger mx-2">*</span>
                        </div>
                        <select class="form-control input-sm myselect" id="driveType">
                            <option disabled selected></option>
                            <option value="FWD">Передний</option>
                            <option value="RWD">Задний</option>
                            <option value="4WD">Полный</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="js/validate.js"></script>
<script src="js/hint.js"></script>
<script src="js/mark.js"></script>
<script src="js/model.js"></script>
<script src="js/engine.js"></script>
<script src="js/transmission.js"></script>
<script>
    $(document).ready(function () {
        loadMarks(loadEngines(loadTms()))
    })

    function logout() {
        $.get({
            url: getContextPath() +'/auth'
        })
        location.href = getContextPath()
    }
</script>
</body>
</html>