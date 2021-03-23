function loadEngines(callback) {
    $.get({
        url: getContextPath() +'/engine',
    }).done(function (json) {
        // console.log(json)
        let link = parseJsonForEngine(json)
        $('#engine').html(link);
        if (callback){
            callback()
        }
    }).fail(function () {
        console.log('ошибка загрузки данных - двигатели')
    });
}

function addEngine() {
    let engineName = $('#engineName').val()
    let enginePower = $('#enginePower').val()
    let fuel = $('#fuel option:selected').val()
    let engineVolume = parseFloat($('#engineVolume').val())
    if (validateEngine(engineName, enginePower, fuel, engineVolume)
    ) {
        $.post({
            url: getContextPath() +'/engine',
            data: {
                'action': 'addEngine',
                'engineName': engineName,
                'enginePower': enginePower,
                'fuel': fuel,
                'engineVolume': engineVolume
            }
        }).done(function () {
            ok()
            $('#engineName').val('')
            $('#enginePower').val('')
            $('#fuel option:first').prop('selected', true);
            $('#engineVolume').val('')
            loadEngines()
        }).fail(function () {
            error()
        });
    }
}

function loadEnginesOfModel() {
    let modelId = $('#model option:selected').val()
    $.get({
        url: getContextPath() +'/engine',
        data: {
            'id': modelId
        }
    }).done(function (json) {
        // console.log("JSON for load engines of model: \n" + json )
        let link = parseJsonForEngine(json)
        // console.log(link)
        let select = document.getElementById('modelEngines')
        select.disabled = false;
        select.innerHTML = link

    }).fail(function () {
        console.log('ошибка загрузки данных - модель - двигатель')
    });

}

function parseJsonForEngine(json) {
    let link = '<option disabled selected></option>\n'
    for (let k in json) {
        let id = parseInt(json[k]['id'])
        let name = json[k]['name'] + "; "
            + json[k]['power'] + " л.с. ; "
            + json[k]['fuelType'] + "; "
            + json[k]['volume'] + " л."
        link += '<option value="' + id + '">' + name + '</option>\n'
    }
    return link
}

function addEngineToModel() {
    processEngineAndModel(false)
}

function delEngineFromModel() {
    processEngineAndModel(true)
}

function processEngineAndModel(del) {
    let engineId = ''
    let action = ''
    if (del){
        action =  'delEngineFromModel'
        engineId = $('#modelEngines option:selected').val()
    } else {
        action = 'addEngineToModel'
        engineId = $('#engine option:selected').val()
    }
    let modelId = $('#model option:selected').val()
    $.post({
        url: getContextPath() +'/mark',
        data: {
            'action': action,
            'engineId': engineId,
            'modelId': modelId
        }
    }).done(function () {
        ok()
        loadEnginesOfModel()
    }).fail(function () {
        error()
    });
}