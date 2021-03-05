function loadTms() {
    $.get({
        url: location.origin + '/auto/transmission',
    }).done(function (json) {
        // console.log(json)
        let link = parseJsonForTm(json)
        $('#transmission').html(link);
    }).fail(function () {
        console.log('ошибка загрузки данных - двигатели')
    });
}

function addTm() {
    let tmType = $('#tmType option:selected').val()
    let driveType = $('#driveType option:selected').val()
    console.log(tmType+ " " + driveType)
    $.post({
        url: location.origin + '/auto/transmission',
        data: {
            'action' : 'addTm',
            'tmType': tmType,
            'driveType': driveType,
        }
    }).done(function () {
        ok()
        $('#tmType option:first').prop('selected', true);
        $('#driveType option:first').prop('selected', true);
        loadTms()
    }).fail(function () {
        error()
    });
}

function parseJsonForTm(json) {
    let link = '<option disabled selected></option>\n'
    for (let k in json) {
        console.log(json[k])
        let id = parseInt(json[k]['id'])
        let automatic = (json[k]['automatic'])? 'AT' : 'MT'
        let driveType = json[k]['driveType']
        link += '<option value="' + id + '">' + automatic + ", " + driveType + '</option>\n'
    }
    return link
}