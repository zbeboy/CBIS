/**
 * Created by lenovo on 2016-05-19.
 */
var InterValObj; //timer变量，控制时间
var count = 30; //间隔函数，1秒执行
var curCount;//当前剩余秒数
function sendMessage() {
    var username = $('#username').val().trim();
    var index = layer.load(1, {
        shade: [0.1,'#fff'] //0.1透明度的白色背景
    });
    $.post(web_path + '/user/mail/sendResetPasswordEmail',{
        'username':username
    },function(data){
        layer.close(index);
        if(data.state){
            curCount = count;
            //设置button效果，开始计时
            $("#btnSendCode").attr("disabled", "true");
            $("#btnSendCode").val( + curCount + "秒再获取");
            InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
            layer.msg(data.msg);
        } else {
            $('#msg').text(data.msg);
        }
    },'json')
}
//timer处理函数
function SetRemainTime() {
    if (curCount == 0) {
        window.clearInterval(InterValObj);//停止计时器
        $("#btnSendCode").removeAttr("disabled");//启用按钮
        $("#btnSendCode").val("重新发送验证码");
        $('#code').val(''); //清除验证码。如果不清除，过时间后，输入收到的验证码依然有效
    }
    else {
        curCount--;
        $("#btnSendCode").val( + curCount + "秒再获取");
    }
}

/**
 * 检验账号
 */
function validUsername(){
    var username = $('#username').val().trim();
    if(username.length>0){
        $('#username').removeClass('uk-form-danger');
        $('#msg').text('');
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.post(web_path + '/user/mail/validUsername',{
            'username':username
        },function(data){
            layer.close(index);
            console.log(data.ok == undefined);
            if(data.ok != undefined){
                sendMessage();
            } else {
                $('#username').focus();
                $('#username').addClass('uk-form-danger');
                $('#msg').text(data.error);
            }
        },'json');
    } else {
        $('#username').focus();
        $('#username').addClass('uk-form-danger');
        $('#msg').text('请输入账号!');
    }
}

$('#loginmodal').on({

    'show.uk.modal': function () {
        $('#username').focus();
    }
});