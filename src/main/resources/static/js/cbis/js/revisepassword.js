/**
 * Created by lenovo on 2016-01-09.
 */
function validate(f){
    var oldPassword = f.oldPassword.value;
    var newPassword = f.newPassword.value;
    var okPassword = f.okPassword.value;

    var v = /^[\w]{6,20}$/;

    if(!v.test(oldPassword)){
        $('#errormsg').text('密码长度为6~20长度！');
        f.oldPassword.focus();
        $('#oldPassword').addClass('uk-form-danger');
        $('#newPassword').removeClass('uk-form-danger');
        $('#okPassword').removeClass('uk-form-danger');
        return false;
    }

    if(!v.test(newPassword)){
        $('#errormsg').text('密码长度为6~20长度！');
        f.newPassword.focus();
        $('#newPassword').addClass('uk-form-danger');
        $('#oldPassword').removeClass('uk-form-danger');
        $('#okPassword').removeClass('uk-form-danger');
        return false;
    }

    if(!v.test(okPassword)){
        $('#errormsg').text('密码长度为6~20长度！');
        f.okPassword.focus();
        $('#oldPassword').removeClass('uk-form-danger');
        $('#newPassword').removeClass('uk-form-danger');
        $('#okPassword').addClass('uk-form-danger');
        return false;
    }

    if(newPassword!=okPassword){
        $('#errormsg').text('密码不一致！');
        f.okPassword.focus();
        $('#oldPassword').removeClass('uk-form-danger');
        $('#newPassword').removeClass('uk-form-danger');
        $('#okPassword').addClass('uk-form-danger');
        return false;
    }
    $('#oldPassword').removeClass('uk-form-danger');
    $('#newPassword').removeClass('uk-form-danger');
    $('#okPassword').removeClass('uk-form-danger');
    $('#errormsg').text('');
    return true;
}

function validpassword(obj){
    var v = /^[\w]{6,20}$/;
    if(!v.test($('#oldPassword').val())){
        $('#ajaxerror').text('*密码长度为6~20长度！');
        $('#oldPassword').addClass('uk-form-danger');
        return;
    } else {
        var modal = UKLoad();
        $.post('/student/validpassword',{
            oldPassword:$('#oldPassword').val()
        },function(data,status){
            modal.hide();
            if(status){
                if(data.state){
                    $('#ajaxerror').text('');
                    $('#oldPassword').removeClass('uk-form-danger');
                } else {
                    $('#ajaxerror').text(data.msg);
                    $('#oldPassword').addClass('uk-form-danger');
                }
            } else {
                $('#errormsg').text('网络异常！')
            }
        },"json");
    }
}