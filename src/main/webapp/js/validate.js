function validateEngine(name, power, fuel, volume) {
    return notEmptyName(name, 'Имя')
        && isInteger(power, 'Мощность')
        && notEmptyName(fuel, 'Название топлива')
        && isFloat(volume, 'Объем')
}


function validateLogin(email, password) {
    return isEmail(email) && isPwd(password)
}

function validateReg(name, email, phone, pwd, pwdConfirm) {
    return isName(name) && isEmail(email) && isPhone(phone) && isPwd(pwd) && passwordMatching(pwd, pwdConfirm)
}

function confirmChanged(name, oldName, email, oldEmail, phone, oldPhone, location, oldLocation) {
    // console.log(name !== oldName)
    // console.log(email !== oldEmail)
    // console.log(phone !== oldPhone)
    // console.log(location !== oldLocation)
    // let result = false
    // result =
    return (name !== oldName)
        || (email !== oldEmail)
        || (phone !== oldPhone)
        || (location !== oldLocation)

}

function validateUpdateAccount(name, oldName, email, oldEmail, phone, oldPhone, location, oldLocation, pwd) {
    let result = false
    if (confirmChanged(name, oldName, email, oldEmail, phone, oldPhone, location, oldLocation)) {
        result = isName(name) && isEmail(email) && isPhone(phone) && isLocation(location) && isPwd(pwd)
    }
    return result
}

function validateAdd(vin, modelId, engineId, transmissionId, created, run, color, price, location) {
    return notEmptyName(vin, 'VIN номер ')
        && isSelected(modelId, 'Модель автомобиля')
        && isSelected(transmissionId, 'Трансмиссия')
        && isYear(created)
        && isInteger(run, "Пробег")
        && notEmptyName(color, "Поле  \"цвет\"")
        && isInteger(price, "Цена")
        && isLocation(location)
}

function isYear(value) {
    if ( /\d{4}/.test(value)){
        return true
    } else {
        errorT("не верно указан год")
        return false
    }
}

function isSelected(value, name) {
    if (value > 0) {
        return true
    } else {
        errorT(name + " не выбрана")
        return false
    }
}

function isInteger(value, name) {
    if (/^-?[1-9]\d{0,5}$/.test(value)) {
        return true
    } else {
        errorT(name + " -  целое число.")
        return false
    }
}

function isFloat(value, name) {
    if (/^(0|[1-9]\d*)(\.[0-9])?$/.test(value)) {
        return true
    } else {
        errorT(name + " - число формата x.x")
        return false
    }
}



function isPhone(value) {
    if (/^((8|\+7)[\- ]?)?(\(?\d{3}\)?[\- ]?)?[\d\- ]{7,10}$/.test(value.replace(/\D/g, ""))) {
        return true
    } else {
        errorT("Некорректный телефон.")
        return false
    }
}

function isEmail(value) {
    if (/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(String(value).toLowerCase())) {
        return true
    } else {
        errorT("Некорректный email.")
        return false
    }
}

function isPwd(value) {
    console.log(value + " " + value.length + " " + value.length > 5)
    if (value.length > 5) {
        return true
    } else {
        errorT("Пароль дожен быть более 5и символов.")
        return false
    }
}

function passwordMatching(pwd1, pwd2) {
    if (pwd1 === pwd2) {
        return true
    } else {
        errorT("Пароли должены совпадать.")
        return false
    }
}

function isName(value) {
    if (value.length > 3) {
        return true
    } else {
        errorT("Имя должно быть длиннее 3 символов.")
        return false
    }
}

function notEmptyName(value, name) {
    if (value.length > 0) {
        return true
    } else {
        errorT(name + " не должно быть пустым.")
        return false
    }
}

function isLocation(value) {
    if (value.length > 3) {
        return true
    } else {
        errorT("Название города должно быть длиннее 3 символов.")
        return false
    }
}