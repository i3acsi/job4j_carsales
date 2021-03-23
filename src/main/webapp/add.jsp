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
    <head>
        <meta charset="UTF-8">
        <title>New Announcement</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css"
              integrity="sha384-r4NyP46KrjDleawBgD5tp8Y7UzmLA05oM1iAEQ17CSuDqnUK2+k9luXQOfXJCJ4I"
              crossorigin="anonymous">
        <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
        <style>
            @import "style/mystyle.css";
        </style>
    </head>
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

    <div class="container mx-auto p-2 bg-secondary">
        <div class="row">
            <div class="form-group col-sm">
                <div class="album">
                    <div class="container">
                        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-3 g-3">
                            <div class="col mb-3" id="photos">
                                <div class="card shadow-sm border border-success" style="background-color: #30363d">
                                    <div class="input__wrapper">
                                        <input name="file" type="file" id="photo" class="input input__file"
                                               accept="image/jpeg,image/png,image/gif" multiple>
                                        <label for="photo">
                                            <img src="img/download.png" id="pic" class="img-fluid rounded pointer"
                                                 style="max-height: 130px"
                                                 alt="">
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-group col-sm">
                <div class="input-group mb-3">
                    <div class="input-group-text lbl border border-success">
                        <span class="text-success">VIN или номер кузова</span>
                        <span class="text-danger mx-2">*</span>
                    </div>
                    <input type="text" class="form-control" id="vin">
                </div>

                <div class="input-group mb-3">
                    <div class="input-group-text lbl border border-success">
                        <span class="text-success">Марка</span>
                        <span class="text-danger mx-2">*</span>
                    </div>
                    <select class="form-control input-sm myselect" id="mark" onchange="loadModels()">

                    </select>
                </div>

                <div class="input-group mb-3">
                    <div class="input-group-text lbl border border-success">
                        <span class="text-success">Модель</span>
                        <span class="text-danger mx-2">*</span>
                    </div>
                    <select class="form-control input-sm myselect" id="model" onchange="loadEnginesOfModel()" disabled>
                        <option disabled selected>Модель</option>

                    </select>
                </div>


                <%--Форма для работы с двигателями--%>
                <div class="input-group mb-0" id="engines">
                    <div class="input-group-text lbl border border-success">
                        <span class="text-success">Двигатель</span>
                        <span class="text-danger mx-2">*</span>
                    </div>
                    <select class="form-control input-sm myselect" id="modelEngines" disabled>
                        <option disabled selected></option>
                    </select>
                </div>
                <div class="input-group btn btn-dark btn-outline-success dropdown-toggle mt-0"
                     data-toggle="dropdown" title="Добавить двигатель" style="height: 25px; margin-top: 0px"
                     id="downBtn1"
                     onclick="$('#addEngine').prop('hidden', false);
                this.hidden=true">
                </div>

                <div class="input-group mt-0 mb-3 dropup" id="addEngine" hidden>
                    <div class="container box p-2" id="engineInput">
                        <div class="input-group mb-3">
                            <div class="input-group-text lbl border border-success">
                                <span class="text-success">Двигатели</span>
                            </div>
                            <select class="form-control myselect" id="engine">
                                <%--                            <option disabled selected></option>--%>
                            </select>
                            <button class="btn btn-dark btn-outline-success mx-2" onclick="addEngine()">Добавить
                            </button>
                            <div class="input-group-text lbl border border-success">
                                <span class="text-success">Название</span>
                                <span class="text-danger mx-2">*</span>
                            </div>
                            <input type="text" class="form-control" id="engineName">
                        </div>

                        <div class="input-group mb-3 ">
                            <button class="btn btn-dark btn-outline-success pull-left"
                                    title="Добавить выбранный двигатель выбранной модели" onclick="addEngineToModel()">
                                Добавить двигатель модели
                            </button>

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

                    <div class="input-group btn btn-dark btn-outline-success dropdown-toggle mt-0"
                         data-toggle="dropdown" title="Скрыть форму" style="height: 1%"
                         onclick="$('#addEngine').prop('hidden', true) ;
                            $('#downBtn1').prop('hidden', false)">
                    </div>
                </div>
                <%--Форма для работы с двигателями--%>


                <%--Форма для работы с трансмиссией--%>
                <div class="input-group mb-0 mt-3" id="transmissions">
                    <div class="input-group-text lbl border border-success">
                        <span class="text-success">Трансмиссия</span>
                        <span class="text-danger mx-2">*</span>
                    </div>
                    <select class="form-control input-sm myselect" id="transmission">
                        <option disabled selected></option>
                    </select>
                </div>
                <div class="input-group btn btn-dark btn-outline-success dropdown-toggle mt-0"
                     data-toggle="dropdown" title="Добавить трансмиссию" style="height: 25px; margin-top: 0px"
                     id="downBtn2"
                     onclick="$('#addTm').prop('hidden', false);
                this.hidden=true">
                </div>

                <div class="input-group mt-0 mb-3 dropup" id="addTm" hidden>
                    <div class="container box p-2" id="tmInput">
                        <div class="input-group mb-3">
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
                        <div class="input-group mb-3" style="width: 80%; margin-left: auto; margin-right: 0em;">
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

                    <div class="input-group btn btn-dark btn-outline-success dropdown-toggle mt-0"
                         data-toggle="dropdown" title="Скрыть форму" style="height: 1%"
                         onclick="$('#addTm').prop('hidden', true) ;
                            $('#downBtn2').prop('hidden', false)">
                    </div>
                </div>
                <%--Форма для работы с Трансмиссиями--%>

                <div class="input-group mt-3 mb-3">
                    <div class="input-group-text lbl border border-success ">
                        <span class="text-success">Год выпуска</span>
                        <span class="text-danger mx-2">*</span>
                    </div>
                    <input type="text" class="form-control " id="created">
                </div>

                <div class="input-group mt-3 mb-3">
                    <div class="input-group-text lbl border border-success ">
                        <span class="text-success">Пробег (км)</span>
                        <span class="text-danger mx-2">*</span>
                    </div>
                    <input type="text" class="form-control " id="run">
                </div>

                <div class="input-group mb-3 ">
                    <div>
                        <div class="input-group-text lbl border border-success">
                            <span class="text-success">Цвет</span>
                            <span class="text-danger mx-2">*</span>
                        </div>
                        <input type="text" id="color" hidden class="mx-2">
                    </div>
                    <div class="btn-group" role="group" style="width: 80%">
                        <button type="button" class="btn-circle border border-success" title="серый"
                                style="background-color: darkgrey" onclick="setColor('gray')"></button>
                        <button type="button" class="btn-circle border border-success" title="серебристый"
                                style="background-color: silver" onclick="setColor('silver')"></button>
                        <button type="button" class="btn-circle border border-success" title="белый"
                                style="background-color: white" onclick="setColor('white')"></button>
                        <button type="button" class="btn-circle border border-success" title="синий"
                                style="background-color: blue" onclick="setColor('blue')"></button>
                        <button type="button" class="btn-circle border border-success" title="голубой"
                                style="background-color: deepskyblue" onclick="setColor('sky blue')"></button>
                        <button type="button" class="btn-circle border border-success" title="зеленый"
                                style="background-color: green" onclick="setColor('green')"></button>
                        <button type="button" class="btn-circle border border-success" title="красный"
                                style="background-color: red" onclick="setColor('red')"></button>
                        <button type="button" class="btn-circle border border-success" title="бордовый"
                                style="background-color: #9b2d30" onclick="setColor('burgundy')"></button>
                        <button type="button" class="btn-circle border border-success" title="розовый"
                                style="background-color: pink" onclick="setColor('pink')"></button>
                        <button type="button" class="btn-circle border border-success" title="бежевый"
                                style="background-color: #f5f5dc" onclick="setColor('beige')"></button>
                        <button type="button" class="btn-circle border border-success" title="желтый"
                                style="background-color: yellow" onclick="setColor('yellow')"></button>
                        <button type="button" class="btn-circle border border-success" title="золотистый"
                                style="background-color: gold" onclick="setColor('gold')"></button>
                        <button type="button" class="btn-circle border border-success" title="коричневый"
                                style="background-color: saddlebrown" onclick="setColor('brown')"></button>
                        <button type="button" class="btn-circle border border-success" title="фиолетовый"
                                style="background-color: blueviolet" onclick="setColor('violet')"></button>
                    </div>
                </div>

                <div class="input-group mb-3" style="height: 300px">
                    <span class="input-group-text lbl border border-success text-success"
                          style="height: 50px">Описание</span>
                    <textarea class="form-control" aria-label="With textarea" id="desc"></textarea>
                </div>

                <div class="input-group mb-3">
                    <div class="input-group-text lbl border border-success ">
                        <span class="text-success">Цена (руб.)</span>
                        <span class="text-danger mx-2">*</span>
                    </div>
                    <input type="text" class="form-control " id="price">
                </div>

                <div class="input-group mb-3">
                    <div class="input-group-text lbl border border-success ">
                        <span class="text-success">Город продажи</span>
                        <span class="text-danger mx-2">*</span>
                    </div>
                    <input type="text" class="form-control " id="location">
                </div>

                <div class="mb-3">
                    <button class="btn btn-dark btn-outline-success mt-2 pull-right" onclick="resumeAdd()">
                        Разместить объявление
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="js/hint.js"></script>
<script src="js/validate.js"></script>
<script src="js/mark.js"></script>
<script src="js/model.js"></script>
<script src="js/engine.js"></script>
<script src="js/transmission.js"></script>
<script>
    let url = getContextPath() +'/car'

    function resumeAdd() {
        let vin = $('#vin').val()
        let modelId = $('#model option:selected').val()
        let engineId = $('#modelEngines option:selected').val()
        let transmissionId = $('#transmission option:selected').val()
        let created = $('#created').val()
        let run = $('#run').val()
        let color = $('#color').val()
        let desc = $('#desc').val()
        let price = $('#price').val()
        let location = $('#location').val()
        let pics = document.getElementById("photo").files;

        let data = new FormData();
        let changed = false;

        if (validateAdd(vin, modelId, engineId, transmissionId, created, run, color, price, location)) {
            let json = JSON.stringify({
                "vin": vin,
                "modelId": modelId,
                "engineId": engineId,
                "transmissionId": transmissionId,
                "created": created,
                "run": run,
                "color": color,
                "desc": desc,
                "price": price,
                "location": location,
            })
            data.append('blob', new Blob([json], {type: 'application/json'}))
            changed = true
        }

        if (pics) {
            for (let k in pics) {
                data.append('datafile' + k, pics[k]);
                changed = true
            }
        }

        if (changed)
            $.post({
                url: url,
                data: data,
                cache: false,
                processData: false,
                contentType: false,
                async: false
            }).done(function () {
                clearForm()
                ok()
            }).fail(function () {
                error()
            })
    }

    $(document).ready(function () {
        loadMarks(loadEngines(loadTms()))
    })

    function addFrame(id, img) {
        return '                                <div class="col mb-3">\n' +
            '                                    <div class="card shadow-sm border border-success" style="background-color: #30363d">\n' +
            '                                        <div class="input__wrapper">\n' +
            '                                            <img src="' + img + '" id="carpic_' + id + ' " class="img-fluid rounded" style="max-height: 130px"\n' +
            '                                                 alt="">\n' +
            '                                            <br>\n' +
            '                                        </div>\n' +
            '                                    </div>\n' +
            '                                </div>\n'
    }

    function readURL(input) {
        if (input.files && input.files.length > 0
        ) {
            for (let i = 0; i < input.files.length; i++) {
                let reader = new FileReader();
                reader.onload = function (e) {
                    let img = e.target.result
                    let frame = addFrame(i, img)
                    $('#photos').before(frame);
                }
                reader.readAsDataURL(input.files[i]);
            }

        }
    }

    $("#photo").change(function () {
        console.log("change")
        readURL(this);
    });

    function setColor(color) {
        document.getElementById("color").value = color
    }

    function clearForm() {
        document.getElementById("hintSuccess").value = ''
        document.getElementById("hint").value = ''
    }

    function logout() {
        $.get({
            url: getContextPath() +'/auth'
        })
        location.href = getContextPath()
    }
</script>
</body>
</html>