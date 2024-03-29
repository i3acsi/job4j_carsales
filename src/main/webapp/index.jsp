<%@ page import="ru.job4j.carsales.service.AuthService" %>
<%@ page import="ru.job4j.carsales.model.Account" %>
<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Car Sales Platform</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/5.0.0-alpha1/css/bootstrap.min.css"
          integrity="sha384-r4NyP46KrjDleawBgD5tp8Y7UzmLA05oM1iAEQ17CSuDqnUK2+k9luXQOfXJCJ4I" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <style>
        @import "style/mystyle.css";
    </style>
</head>
<body>
<%
    Account account = AuthService.getLoggedInAccount(request);
%>
<script>
    let url = getContextPath();
</script>
<div class="container ">
    <nav class="navbar navbar-fixed-top navbar-dark boarded" style="background-color:  #30363d; alignment: center">
        <div class="container">
            <div class="col">
                <img src="img/brand4.png" alt="" class="img-fluid pointer w-100 "
                     onclick="document.location.href = getContextPath()">
            </div>
            <% if (account == null) {%>
            <div class="col" align="right">
                <a href='logreg.jsp' class="btn btn-outline-success pull-right mt-5" type="submit">Login or
                    Registration</a>
            </div>
            <% } else if (AuthService.isAdmin(account)) {%>
            <div class="col" align="right">
                <button class="btn btn-outline-success pull-right mt-5" onclick=
                        "document.location.href = getContextPath() +'/editDB.jsp'">Manage
                </button>
                <button class="btn btn-outline-success pull-right mt-5" onclick=
                        "document.location.href = getContextPath() +'/account.jsp'"><%=account.getName()%>
                </button>
                <button class="btn btn-outline-success pull-right mt-5" onclick="logout()">Logout</button>
            </div>
            <% } else { %>
            <div class="col" align="right">
                <button class="btn btn-outline-success pull-right mt-5" onclick=
                        "document.location.href = getContextPath() +'/add.jsp'">Добавить объявление
                </button>
                <button class="btn btn-outline-success pull-right mt-5" onclick=
                        "document.location.href = getContextPath() +'/account.jsp'"><%=account.getName()%>
                </button>
                <button class="btn btn-outline-success pull-right mt-5" onclick="logout()">Logout</button>
            </div>
            <% } %>
        </div>
    </nav>

    <div class="container mx-auto p-2 bg-secondary">
        <div class="form-group row">
            <div class="col">
                <select class="form-control input-sm myselect" id="mark" onchange="
                $('#model').val('')
                loadModels(loadAds())">
                </select>
            </div>
            <div class="col">
                <select class="form-control input-sm myselect" id="model" disabled onchange="loadAds()">
                    <option disabled selected>Модель</option>
                </select>
            </div>
            <div class="col">
                <button class="btn btn-dark btn-outline-success" value="false" onclick="freshAd()" id="freshAd">Показать
                    свежие объявления
                </button>
            </div>
            <div class="col">
                <button class="btn btn-dark btn-outline-success active" value="true" onclick="withPhotos()"
                        id="withPhotos">Показать объявления с фото
                </button>
            </div>
        </div>
        <div class="row mt-2">
            <div class="col">
                <table class="table table-dark">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Марка</th>
                        <th scope="col">Модель</th>
                        <th scope="col">Год выпуска</th>
                        <th scope="col">Цена</th>
                        <th scope="col">Фото</th>
                        <th scope="col">Город</th>
                    </tr>
                    </thead>
                    <tbody id="table">

                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<script src="js/validate.js"></script>
<script src="js/hint.js"></script>
<script src="js/mark.js"></script>
<script src="js/model.js"></script>
<script src="js/ad.js"></script>
<script>
    $(document).ready(function () {
        loadAds(loadMarks())
    })

    function logout() {
        $.get({
            url: getContextPath() + '/auth'
        })
        location.href = getContextPath()
    }
</script>
</body>
</html>