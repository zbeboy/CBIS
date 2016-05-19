/**
 * Created by lenovo on 2016-05-19.
 */
/**
 * 取消修改
 */
function cancelUpdate(){
    var modal = UIkit.modal("#update");

    if ( modal.isActive() ) {
        modal.hide();
    } else {
        modal.show();
    }
}

/**
 * 提交数据
 */
function submitData(){
    $('#updateForm').submit();
}

/**
 * 初始化验证
 */
function initForm(){
    $('#updateForm').validator({
        timely :0,
        fields: {
            mobile: {
                rule: "required; mobile;remote[/student/personal/validMobile]",
                msg: {required: "请填写手机号",email: "请检查手机号格式"}
            },
            code:{
                rule: "required;length[6];",
                msg: {required: "请填写验证码"}
            }
        },
        valid: function(form){
            var me = this;
            // 提交表单之前，hold住表单，防止重复提交
            me.holdSubmit();
            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $.ajax({
                url: web_path + "/student/checkMobile",
                data: $(form).serialize(),
                type: "POST",
                success: function(data){
                    layer.close(index);
                    if(data.state){
                        layer.msg(data.msg);
                        me.holdSubmit(false);
                        cancelUpdate();
                        setTimeout(refresh,5000);
                    } else {
                        $('#msg').text(data.msg);
                    }
                }
            });
        },
        validClass:"uk-form-success",
        invalidClass:"uk-form-danger",
        msgClass: "n-bottom"
    });

    $('#addForm').validator({
        timely :0,
        fields: {
            mobile: {
                rule: "required; mobile;remote[/student/personal/validMobile]",
                msg: {required: "请填写手机号",email: "请检查手机号格式"}
            },
            code:{
                rule: "required;length[6];",
                msg: {required: "请填写验证码"}
            }
        },
        valid: function(form){
            var me = this;
            // 提交表单之前，hold住表单，防止重复提交
            me.holdSubmit();
            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            $.ajax({
                url: web_path + "/student/checkMobile",
                data: $(form).serialize(),
                type: "POST",
                success: function(data){
                    layer.close(index);
                    if(data.state){
                        layer.msg(data.msg);
                        me.holdSubmit(false);
                        setTimeout(refresh,5000);
                    } else {
                        $('#addError').text(data.msg);
                    }

                }
            });
        },
        validClass:"uk-form-success",
        invalidClass:"uk-form-danger",
        msgClass: "n-bottom"
    });
}

/**
 * 刷新页面
 */
function refresh(){
    window.location.reload(true);
}

var InterValObj; //timer变量，控制时间
var count = 30; //间隔函数，1秒执行
var curCount;//当前剩余秒数
function sendMessage() {
    var mobile = $('#mobile').val().trim();
    var regex = /^1\d{10}$/;
    if(regex.test(mobile)){
        $('#mobile').removeClass('uk-form-danger');
        $('#mobileError').text('');
        $.post(web_path + '/student/personal/sendMobileKey',{
            'mobile':mobile
        },function(data){
            if(data.state){
                curCount = count;
                //设置button效果，开始计时
                $("#btnSendCode").attr("disabled", "true");
                $("#btnSendCode").val( + curCount + "秒再获取");
                InterValObj = window.setInterval(SetRemainTime, 1000); //启动计时器，1秒执行一次
                layer.msg(data.msg);
            } else {
                $('#addError').text(data.msg);
            }
        },'json')
    } else {
        $('#mobile').focus();
        $('#mobile').addClass('uk-form-danger');
        $('#mobileError').text('请正确输入手机号!');
    }

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

var updateInterValObj; //timer变量，控制时间
var updateCount = 30; //间隔函数，1秒执行
var updateCurCount;//当前剩余秒数
function sendUpdateMessage() {
    var mobile = $('#updateMobile').val().trim();
    var regex = /^1\d{10}$/;
    if(regex.test(mobile)){
        $('#updateMobile').removeClass('uk-form-danger');
        $('#updateMobileError').text('');
        $.post(web_path + '/student/personal/sendMobileKey',{
            'mobile':mobile
        },function(data){
            if(data.state){
                updateCurCount = updateCount;
                //设置button效果，开始计时
                $("#updateBtnSendCode").attr("disabled", "true");
                $("#updateBtnSendCode").val( + updateCurCount + "秒再获取");
                updateInterValObj = window.setInterval(SetUpdateRemainTime, 1000); //启动计时器，1秒执行一次
                layer.msg(data.msg);
            } else {
                $('#msg').text(data.msg);
            }
        },'json')
    } else {
        $('#updateMobile').focus();
        $('#updateMobile').addClass('uk-form-danger');
        $('#updateMobileError').text('请正确输入手机号!');
    }

}
//timer处理函数
function SetUpdateRemainTime() {
    if (updateCurCount == 0) {
        window.clearInterval(updateInterValObj);//停止计时器
        $("#updateBtnSendCode").removeAttr("disabled");//启用按钮
        $("#updateBtnSendCode").val("重新发送验证码");
        $('#updateCode').val('');
    }
    else {
        updateCurCount--;
        $("#updateBtnSendCode").val( + updateCurCount + "秒再获取");
    }
}

$(document).ready(function(){
    initForm();
});