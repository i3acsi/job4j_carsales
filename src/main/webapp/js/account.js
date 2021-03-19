// update account fields in Session if changed and call update userPic
// if someone of them is changed then call update acc in DB
function saveDataToAccount() {
    let id = document.getElementById("id").value;
    let name = document.getElementById("name").value;
    let oldName = document.getElementById("l_name").value;
    let email = document.getElementById("email").value;
    let oldEmail = document.getElementById("l_email").value;
    let phone = String(document.getElementById("phone").value);
    let oldPhone = String(document.getElementById("l_phone").value);
    let location = document.getElementById("location").value;
    let oldLocation = document.getElementById("l_location").value;
    let password = document.getElementById("pwd").value;
    let url = window.location.origin + '/auto/userInfo'

    let data = new FormData();
    let changed = false;
    if (validateUpdateAccount(name, oldName, email, oldEmail, phone, oldPhone, location, oldLocation, password)) {
        let json = JSON.stringify({
            "id": id,
            "name": name,
            "email": email,
            "phone": phone,
            "location": location,
            "password": password
        })

        data.append('blob', new Blob([json], {type: 'application/json'}))
        changed = true
    }

    let userPic = document.getElementById("photo").files[0];
    if (userPic) {
        data.append('datafile', userPic);
        changed = true
    }
    if (changed)
        $.post({
            url: url,
            data: data,
            cache: false,
            processData: false,
            contentType: false,
            async: false
        }).done(function () {
            ok()
            loadUserData()
        }).fail(function () {
            error()
        })

}


function readURL(input) {
    if (input.files && input.files[0]
    // && input.files[0].size < 3000000
    ) {
        let reader = new FileReader();
        reader.onload = function (e) {
            $('#userpic').attr('src', e.target.result);
        }
        reader.readAsDataURL(input.files[0]);
    } else {
        console.log("too big")
    }
}

$("#photo").change(function () {
    readURL(this);
});

// load userData from DB to fields on page
function loadUserData() {
    $.get({
        url: location.origin + '/auto/userInfo',
    }).done(function (list) {
        let json = list[1]
        let id = json['id']
        if (id != null) {
            document.getElementById("id").value = id;
        }
        let name = json['name']
        if (name != null) {
            document.getElementById("l_name").value = name;
            document.getElementById("name").value = name;
        }
        let email = json['email']
        if (email != null) {
            document.getElementById("l_email").value = email;
            document.getElementById("email").value = email;
        }
        let phone = json['telephone']
        if (phone != null) {
            document.getElementById("l_phone").value = phone;
            document.getElementById("phone").value = phone;
        }
        let userPic = json['userPic']
        if (userPic != null) {
            $('#userpic').attr('src', userPic);
        }
        let location = json['location']
        if (location != null) {
            document.getElementById("l_location").value = location;
            document.getElementById("location").value = location;
        }
        let adList = list[0];
        let htmlTable = loadAdForMe(adList)
        $('#myAdList').html(htmlTable)
    }).fail(function () {
        console.log('ошибка загрузки данных')
    });


}
