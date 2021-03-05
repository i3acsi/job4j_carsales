function errorT(text) {
    document.getElementById("hintSuccess").textContent = ''
    document.getElementById("hint").textContent = text
}

function error() {
    document.getElementById("hintSuccess").textContent = ''
    document.getElementById("hint").textContent = 'Ошибка вставки данных'
}

function ok() {
    document.getElementById("hint").textContent = ''
    document.getElementById("hintSuccess").textContent = 'Данные добавлены'
}

function okT(text) {
    document.getElementById("hint").textContent = ''
    document.getElementById("hintSuccess").textContent = text
}

// export {errorT, error, ok, okT}