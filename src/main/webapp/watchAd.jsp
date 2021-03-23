<%@ page import="ru.job4j.carsales.dto.AnnouncementDto" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Calendar" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<%
    AnnouncementDto ad = (AnnouncementDto) request.getAttribute("ad");
    String vin = ad.getCar().getVin();
    String mark = ad.getCar().getModel().getMark().getName();
    String model = ad.getCar().getModel().getName();
    String engine = ad.getEngine().toString();
    String transmission = ad.getTransmission().toString();

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(ad.getCar().getCreated());
    String created = String.valueOf(calendar.get(Calendar.YEAR));

    String run = ad.getRun().toString();
    String color = ad.getColor();
    String desc = ad.getDescription();
    String price = ad.getPrice().toString();
    String location = ad.getLocation();
    List<String> photos = ad.getPhotos();
    Long owner = ad.getOwner();
%>
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
                            <% for (int i = 0; i < photos.size(); i++) { %>
                            <div class="col mb-3">
                                <div class="card shadow-sm border border-success" style="background-color: #30363d">
                                    <div class="input__wrapper">
                                        <img src="<%=photos.get(i)%>" class="img-fluid rounded"
                                             style="max-height: 130px"
                                             alt="">
                                        <br>
                                    </div>
                                </div>
                            </div>
                            <% } %>
                        </div>
                    </div>
                </div>
                <div class="input-group mb-3" style="height: 300px">
                    <textarea class="form-control" aria-label="With textarea" id="contacts" readonly hidden>
                        Контакты:
                    </textarea>
                </div>
            </div>
            <div class="form-group col-sm">
                <div class="input-group mb-3">
                    <div class="input-group-text lbl border border-success">
                        <span class="text-success">VIN или номер кузова</span>
                    </div>
                    <input type="text" class="form-control" id="vin" value="<%=vin%>" readonly>
                </div>

                <div class="input-group mb-3">
                    <div class="input-group-text lbl border border-success">
                        <span class="text-success">Марка</span>
                    </div>
                    <input type="text" class="form-control" id="mark" value="<%=mark%>" readonly>
                </div>

                <div class="input-group mb-3">
                    <div class="input-group-text lbl border border-success">
                        <span class="text-success">Модель</span>
                    </div>
                    <input type="text" class="form-control" id="model" value="<%=model%>" readonly>
                </div>

                <div class="input-group mb-3">
                    <div class="input-group-text lbl border border-success">
                        <span class="text-success">Двигатель</span>
                    </div>
                    <input type="text" class="form-control" id="engine" value="<%=engine%>" readonly>
                </div>

                <div class="input-group mb-3">
                    <div class="input-group-text lbl border border-success">
                        <span class="text-success">Трансмиссия</span>
                    </div>
                    <input type="text" class="form-control" id="transmissions" value="<%=transmission%>" readonly>
                </div>


                <div class="input-group mt-3 mb-3">
                    <div class="input-group-text lbl border border-success ">
                        <span class="text-success">Год выпуска</span>
                    </div>
                    <input type="text" class="form-control " id="created" value="<%=created%>" readonly>
                </div>

                <div class="input-group mt-3 mb-3">
                    <div class="input-group-text lbl border border-success ">
                        <span class="text-success">Пробег(км)</span>
                    </div>
                    <input type="text" class="form-control " id="run" value="<%=run%>" readonly>
                </div>

                <div class="input-group mt-3 mb-3">
                    <div class="input-group-text lbl border border-success ">
                        <span class="text-success">Цвет</span>
                    </div>
                    <input type="text" class="form-control " id="color" value="<%=color%>" readonly>
                </div>


                <div class="input-group mb-3" style="height: 300px">
        <span class="input-group-text lbl border border-success text-success"
              style="height: 50px">Описание</span>
                    <textarea class="form-control" aria-label="With textarea" id="desc" readonly>
                        <%=desc%>
                    </textarea>
                </div>

                <div class="input-group mb-3">
                    <div class="input-group-text lbl border border-success ">
                        <span class="text-success">Цена(руб.)</span>
                    </div>
                    <input type="text" class="form-control " id="price" value="<%=price%>" readonly>
                </div>

                <div class="input-group mb-3">
                    <div class="input-group-text lbl border border-success ">
                        <span class="text-success">Город продажи</span>
                    </div>
                    <input type="text" class="form-control " id="location" value="<%=location%>" readonly>
                </div>

                <div class="mb-3">
                    <button class="btn btn-dark btn-outline-success mt-2 pull-right" value="<%=owner%>"
                            onclick="watchContact(this.value)">
                        Посмотреть контакты
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="js/hint.js"></script>
<script src="js/validate.js"></script>

<script>

    function watchContact(id) {
        $.get({
            url: getContextPath() +'/userInfo',
            data: {'id': id},
            dataType: "json"
        }).done(function (json) {
            let phone = json[0]
            let email = json[1]
            $('#contacts').val('Телефон: ' + phone + '\n' + "Email: " + email)
        }).fail(function (err) {
            if (err && err['status'] === 403) {
                $('#contacts').val('Данные контакта доступны только зарегистрированным пользователям')
            } else {
                console.log('ошибка загрузки данных')
            }
        });

        $('#contacts').prop('hidden', false)
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