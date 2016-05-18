/**
 * Created by lenovo on 2016-05-17.
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
        fields: {
            email: {
                rule: "required; email;remote[/student/personal/validEmail]",
                msg: {required: "请填写邮箱",email: "请检查邮箱格式"}
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
                url: web_path + "/student/personal/updateEmail",
                data: $(form).serialize(),
                type: "POST",
                success: function(data){
                    layer.close(index);
                    cancelUpdate();
                    layer.msg(data.msg);
                    me.holdSubmit(false);
                }
            });
        },
        validClass:"uk-form-success",
        invalidClass:"uk-form-danger",
        msgClass: "n-bottom"
    });

    $('#addForm').validator({
        fields: {
            email: {
                rule: "required; email;remote[/student/personal/validEmail]",
                msg: {required: "请填写邮箱",email: "请检查邮箱格式"}
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
                url: web_path + "/student/personal/updateEmail",
                data: $(form).serialize(),
                type: "POST",
                success: function(data){
                    layer.close(index);
                    cancelUpdate();
                    layer.msg(data.msg);
                    me.holdSubmit(false);
                }
            });
        },
        validClass:"uk-form-success",
        invalidClass:"uk-form-danger",
        msgClass: "n-bottom"
    });
}

$(document).ready(function(){
   initForm();
});