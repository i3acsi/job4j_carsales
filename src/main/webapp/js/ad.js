function loadAds(callback) {
    let markId = 0
    let modelId = 0
    let select = $('#mark option:selected').val()
    if (select &&
        (/^-?[1-9]\d{0,5}$/.test(select))) {
        markId = select
    }
    select = $('#model option:selected').val()
    if (select &&
        (/^-?[1-9]\d{0,5}$/.test(select))) {
        modelId = select
    }
    let freshAd = $('#freshAd').val()
    let withPhotos = $('#withPhotos').val()
    $.get({
        url: location.origin + '/auto/car',
        data: {
            'markId': markId,
            'modelId': modelId,
            'freshAd': freshAd,
            'withPhotos': withPhotos
        }
    }).done(function (json) {
        let html = loadAdSimple(json)
        $('#table').html(html)
        if (callback) {
            callback()
        }
    })
}

function loadAdSimple(json) {
    let table = ''
    for (let k in json) {
        let adId = json[k]['id']
        let model = json[k]['car']['model']
        let markName = model['mark']['name']
        let modelName = model['name']
        let created = json[k]['car']['created']
        let location = json[k]['location']
        let price = json[k]['price']
        let photo = json[k]['photos']
        if (photo.length > 0) {
            photo = photo[0]
        } else {
            photo = 'img/car.png'
        }
        table += '<tr class="pointer" onclick="watchAd(' + adId + ')">\n<th scope="row">' + k + '</th>\n' +
            '<td>' + markName + '</td>\n<td>' + modelName + '</td>\n<td>' + created + '</td>\n' +
            '<td>' + price + '</td>\n<td>\n<img src=\"' + photo + '\" class=\"img-fluid\" style=\"max-height: 100px; width: auto; display: inline-block\">'
            + '</td>\n<td>' + location + '</td>\n</tr>\n'
    }
    return table
}

function loadAdForMe(json) {
    let table = ''
    for (let k in json) {
        let adId = json[k]['id']
        let model = json[k]['car']['model']
        let modelName = model['name']
        let price = json[k]['price']
        let photo = json[k]['photos']
        let status = json[k]['active']
        let btn = (status) ?
            '<button class="btn btn-outline-success btn-small"  onclick="closeAd(' + adId + ')">Завершить</button>'
            : '<button class="btn btn-outline-success btn-small disabled" ">Завершено</button>'

        if (photo.length > 0) {
            photo = photo[0]
        } else {
            photo = 'img/car.png'
        }
        table += '<tr>\n<th scope="row"> ' + k + '</th>\n'
            + '<td class="pointer" onclick="watchAd(' + adId + ')">' + modelName + '</td>\n'
            + '<td class="pointer" onclick="watchAd(' + adId + ')">' + price + '</td>\n'
            + '<td>\n<img src="' + photo + '" class="img-fluid pointer" onclick="watchAd(' + adId + ')" '
            + 'style=\"max-height: 100px; width: auto; display: inline-block\">'
            + '</td>\n<td>' + btn + '</td>\n</tr>\n'
    }
    return table
}

function watchAd(adId) {
    document.location.href = location.origin + '/auto/car?id=' + adId
}

function closeAd(adId) {
    let data = new FormData()
    let json = JSON.stringify({
        "adId": adId
    })
    let url = location.origin + '/auto/car'
    data.append('closeAd', new Blob([json], {type: 'application/json'}))
    $.post({
        url: url,
        data: data,
        cache: false,
        processData: false,
        contentType: false,
        async: false
    }).done(function () {
        loadUserData()
    }).fail(function () {
        error()
    });

}

function freshAd() {
    let freshAd = $('#freshAd')
    if (freshAd.val() === 'true') {
        freshAd.val(false)
        freshAd.removeClass('active')
    } else {
        freshAd.val(true)
        freshAd.addClass('active')
    }
    loadAds()
}

function withPhotos() {
    let withPhotos = $('#withPhotos')
    if (withPhotos.val() === 'true') {
        withPhotos.val(false)
        withPhotos.removeClass('active')
    } else {
        withPhotos.val(true)
        withPhotos.addClass('active')
    }
    loadAds()
}