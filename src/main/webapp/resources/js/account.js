/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var acnt = {};
acnt.warnmsg = function (msg, alert) {
    return '<div class="alert alert-' + alert + '"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>' + msg + '</div>';
};

acnt.setMenu = function (type, id) {
    if (type === "visitor") {
        $('.vis_menu').show();
        $('.com_menu').hide();
        $('.cand_menu').hide();
    } else if (type === "company") {
        $('.vis_menu').hide();
        $('.com_menu').show();
        $('.cand_menu').hide();
        $('#my_com').html('<a href="/employer/' + id + '">My Company</a>');
    } else {
        $('.vis_menu').hide();
        $('.com_menu').hide();
        $('.cand_menu').show();
        $('#my_rsm').html('<a href="/resume/' + id + '">My Resume</a>');
    }

};

acnt.register = function (formId, path, confirmId, warningBox) {
    var formObj = acnt.getFormObj($(formId));
    if (formObj['pass'] !== $(confirmId).val()) {
        $(warningBox).html(acnt.warnmsg('Password and repeat password mismatch', 'warning'));
        return false;
    }
    var attr;
    for (attr in formObj) {
        if (formObj[attr] === '') {
            $(warningBox).html(acnt.warnmsg('Please enter all the attrubutes', 'info'));
            return false;
        }
    }
    $.post(path, formObj, function (data) {

        if (data === "success") {
            $('#register').modal('hide');
            $("#login").modal('show');
            $('#login-warning').html(acnt.warnmsg('Congratulation! You have registered successfully, please login', 'success'));
        } else {
            $(warningBox).html(acnt.warnmsg('Sorry, your email are already being used', 'danger'));
        }
    });
};

acnt.getFormObj = function (f) {
    var o = {};
    var a = f.serializeArray();
    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

acnt.closeModel = function () {
    $('#login').modal('hide');
};

$('#login-btn').click(function (e) {
    e.preventDefault();
    var email = $('#login-email').val();
    var pass = $('#login-password').val();
    if (email === "" || pass === "") {
        $('#login-warning').html(acnt.warnmsg('Please enter your Email and password!', 'warning'));
        return false;
    }
    $.post("/login", {"email": email, "pass": pass}, function (data) {
        var res = JSON.parse(data);
        if (res['state'] === "success") {
            acnt.closeModel();
            acnt.setMenu(res['type'], res['id']);
            acnt.getComUrl();
        } else {
            $('#login-warning').html(acnt.warnmsg('Email or password incorrect!', 'danger'));
        }
    });
});

$.post("/info", {}, function (data) {
    var res = JSON.parse(data);
    acnt.setMenu(res['type'], res['id']);
});

$('.logout').click(function (e) {
    $.get("/logout", function () {
    });
});

$('#register-per-btn').click(function (e) {
    e.preventDefault();
    acnt.register('#register-per', "/reg/person", '#register-per-password2', '#reg-personal-warning');
});

$('#register-com-btn').click(function (e) {
    e.preventDefault();
    acnt.register('#register-com', "/reg/company", '#register-com-password2', '#reg-company-warning');
});


acnt.emDelJob = function (id) {
    $.post("/employee/deletejob", {"id": id}, function (data) {
        $('#job' + id).remove();
    });
};
acnt.comDelJob = function (id) {
    $.post("/employer/deletejob", {"id": id}, function (data) {
        $('#jobtitle' + id).addClass("closed-job");
        $('#jobdel' + id).remove();
    });
};



$('#sub-com-info').click(function () {
    $('#com-i').submit();
});

$("#job-search-apply").click(function () {
    console.log("apply");
    $("#cmin").val($("#salary-field-lower").html().replace("$", "").replace(".", ""));
    $("#cmax").val($("#salary-field-upper").html().replace("$", "").replace(".", ""));
    $('#sjform').submit();
});

$("#cand-search-apply").click(function () {
    $('#scandform').submit();
});

var lsearch = {};
lsearch.bindAll = function (all, target) {
    $(all).change(function () {
        var c = this.checked;
        $(target).prop("checked", c);
    });
    $(target).change(function () {
        if (this.checked) {

        } else {
            $(all).prop("checked", false);
        }
    });
};
lsearch.bindAll('#job-type-all', '.job-type');
lsearch.bindAll('#job-ind-all', '.job-ind');
lsearch.bindAll('#job-loc-all', '.job-loc');

lsearch.bindAll('#cand-ind-all', '.cand-ind');
lsearch.bindAll('#cand-loc-all', '.cand-loc');