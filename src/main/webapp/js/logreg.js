function clearForm() {
    document.getElementById("email").value = "";
    document.getElementById("password").value = "";
    document.getElementById("newName").value = "";
    document.getElementById("newEmail").value = "";
    document.getElementById("newPhone").value = "";
    document.getElementById("newPWD").value = "";
    document.getElementById("newPWDConfirm").value = "";
}

function login() {
    const email = $('#email').val()
    const password = $('#password').val()
    if (validateLogin(email, password)) {
        $.post({
            url: getContextPath() +'/auth',
            data: {
                "action": 'login',
                "email": email,
                "password": password
            }
        }).done(function () {
            console.log("login - ok")
            document.location.href = getContextPath()
        }).fail(function () {
            console.log("login - fail")
            errorT("Неверный логин или пароль")
        });
    }
}

function reg() {
    const newName = $('#newName').val()
    const newEmail = $('#newEmail').val()
    const newPhone = $('#newPhone').val()
    const newPassword = $('#newPWD').val()
    const passwordConfirm = $('#newPWDConfirm').val()
    if (validateReg(newPhone, newEmail, newPhone, newPassword, passwordConfirm)) {
        $.post({
            url: getContextPath() +'/auth',
            data: {
                "action": 'reg',
                "name": newName,
                "email": newEmail,
                "phone": newPhone,
                "password": newPassword
            }
        }).done(function () {
            clearForm()
            okT("Пользователь успешно зарегистрирован: Name " + newName + ", Email:" + newEmail)
        }).fail(function () {
            errorT("Ошибка регистрации")
        });
    }
}