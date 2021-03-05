function loadMarks(callback) {
    let link = '<option disabled selected>Марка</option>\n'
    $.get({
        url: location.origin + '/auto/mark',
    }).done(function (json) {
        for (let k in json) {
            let id = parseInt(json[k]['id'])
            let name = json[k]['name']
            link += '<option value="' + id + '">' + name + '</option>'
        }
        $('#mark').html(link);
        if(callback)
            callback();
    }).fail(function () {
        console.log('ошибка загрузки данных - марки')
    });
}

function addMark() {
    let markName = $('#markName').val()
    if (markName.length > 0) {
        $.post({
            url: location.origin + '/auto/mark',
            data: {
                'action': 'addMark',
                'markName': markName
            }
        }).done(function () {
            ok()
            $('#markName').val('')
            loadMarks()
        }).fail(function () {
            error()
        });
    } else {
        errorT('Название марки должно быть длиннее')
    }
}
