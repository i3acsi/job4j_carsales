function loadModels(callback) {
    let select = document.getElementById('mark')
    let markId = select.options[select.selectedIndex].value;
    let link = '<option disabled selected>Модель</option>\n'
    $.get({
        url: location.origin + '/auto/mark',
        data: {
            'id': markId
        }
    }).done(function (json) {
        for (let k in json) {
            let id = parseInt(json[k]['id'])
            let name = json[k]['name']
            link += '<option value="' + id + '">' + name + '</option>'
        }
        let elem = document.getElementById('model');
        elem.disabled = false;
        $('#model').html(link);
        if (callback){
            callback()
        }
    }).fail(function () {
        $('#model').html(link);
        console.log('ошибка загрузки данных - модель')
    });
}

function addModel() {
    let modelName = $('#modelName').val()
    let select = document.getElementById('mark')
    let markId = select.options[select.selectedIndex].value;
    if (modelName.length > 0) {
        $.post({
            url: location.origin + '/auto/mark',
            data: {
                'action': 'addModel',
                'modelName': modelName,
                'markId': markId
            }
        }).done(function () {
            ok()
            $('#modelName').val('')
            loadModels(markId)
        }).fail(function () {
            error()
        });
    } else {
        errorT('Название модели должно быть длиннее')
    }
}