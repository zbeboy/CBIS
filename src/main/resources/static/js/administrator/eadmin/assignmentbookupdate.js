/**
 * Created by lenovo on 2016-06-01.
 */

/**
 * 前端封装参数
 * @type {{}}
 */
var param = {
    'id':$('#taskInfoId').val().trim(),
    'teachType':$('#teachType').val().trim(),
    'teachTaskTitle':$('#teachTaskTitle').val().trim(),
    'teachTaskTerm':$('#teachTaskTerm').val().trim(),
    'termStartTime':$('#termStartTime').val().trim(),
    'termEndTime':$('#termEndTime').val().trim()
}

/**
 * 初始化参数
 */
function initParam(){
    param.id = $('#taskInfoId').val().trim();
    param.teachType = $('#teachType').val().trim();
    param.teachTaskTitle = $('#teachTaskTitle').val().trim();
    param.teachTaskTerm = $('#teachTaskTerm').val().trim();
    param.termStartTime = $('#termStartTime').val().trim();
    param.termEndTime = $('#termEndTime').val().trim();
}

$(function () {
    /* nice validator*/
    $('#assignmentBookUpdateForm').validator({
        stopOnError: false,
        timely: 'yellow_right_effect',
        ignore: ':hidden',
        validClass:'uk-form-success',
        invalidClass:'uk-form-danger',
        fields: {
            'teachTaskTitle':'required;remote[/administrator/eadmin/validUpdateAssignmentBookTitle, id]',
            'termStartTime': '学年开始时间:required;date;',
            'termEndTime': '学年结束时间:required;date; match[gt, termStartTime, date]'
        },
        valid: function(form){
            var me = this;
            // 提交表单之前，hold住表单，并且在以后每次hold住时执行回调
            //loading层
            var index = layer.load(1, {
                shade: [0.1,'#fff'] //0.1透明度的白色背景
            });
            me.holdSubmit();
            initParam();
            $.ajax({
                url: web_path + "/administrator/eadmin/updateAssignmentBook",
                data: param,
                type: "POST",
                success: function(data){
                    // 提交表单成功后，释放hold，就可以再次提交
                    me.holdSubmit(false);
                    layer.close(index);
                    if(data.state){
                        layer.msg(data.msg, {icon: 1},function(){
                            window.location.href = web_path + '/administrator/eadmin/assignmentBookList';
                        });
                    } else {
                        layer.msg(data.msg);
                    }
                }
            });
        }
    });
});
