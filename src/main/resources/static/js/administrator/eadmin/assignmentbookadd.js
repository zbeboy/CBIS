/**
 * Created by lenovo on 2016-05-28.
 */

var fileIsRight = false;//文件类型正确

/**
 * 前端封装参数
 * @type {{}}
 */
var param = {
    'teachType':$('#teachType').val().trim(),
    'teachTaskTitle':$('#teachTaskTitle').val().trim(),
    'teachTaskTerm':$('#teachTaskTerm').val().trim(),
    'termStartTime':$('#termStartTime').val().trim(),
    'termEndTime':$('#termEndTime').val().trim(),
    'filePath':$('#filePath').val().trim(),
    'yearX':$('#yearX').val().trim(),
    'yearY':$('#yearY').val().trim(),
    'gradeX':$('#gradeX').val().trim(),
    'gradeY':$('#gradeY').val().trim(),
    'gradeNumX':$('#gradeNumX').val().trim(),
    'gradeNumY':$('#gradeNumY').val().trim()
}

/**
 * 初始化参数
 */
function initParam(){
    param.teachType = $('#teachType').val().trim();
    param.teachTaskTitle = $('#teachTaskTitle').val().trim();
    param.teachTaskTerm = $('#teachTaskTerm').val().trim();
    param.termStartTime = $('#termStartTime').val().trim();
    param.termEndTime = $('#termEndTime').val().trim();
    param.filePath = $('#filePath').val().trim();
    param.yearX = $('#yearX').val().trim();
    param.yearY = $('#yearY').val().trim();
    param.gradeX = $('#gradeX').val().trim();
    param.gradeY = $('#gradeY').val().trim();
    param.gradeNumX = $('#gradeNumX').val().trim();
    param.gradeNumY = $('#gradeNumY').val().trim();
}

$(function () {

    $('#submitData').attr('disabled',true);//未上传教学任务书前,不能提交数据.

    $('#fileupload').fileupload({
        url: web_path + '/student/uploadFile',
        formData: {pathname: 'assignmentbook'},
        dataType: 'json',
        done: function (e, data) {
            var regex = /(\.|\/)(xls|xlsx|XLS|XLSX)$/i;
            if (!regex.test(data.result.single.single.newName)) {
                layer.msg("请上传(xls|xlsx)类型的文件!");
                fileIsRight = false;
            } else {
                fileIsRight = true;
                $('#uploadFile').val(data.result.single.single.originalFilename + "." + data.result.single.single.ext);
                $('#filePath').val("/files/assignmentbook/"+data.result.single.single.newName);
                $('#submitData').attr('disabled',false);//上传任务书后可提交数据.
            }
        }
    });

    /* nice validator*/
    $('#assignmentBookAddForm').validator({
        stopOnError: false,
        timely: 'yellow_right_effect',
        ignore: ':hidden',
        validClass:'uk-form-success',
        invalidClass:'uk-form-danger',
        msgClass: 'n-bottom',
        fields: {
            'teachTaskTitle':'required;remote[/administrator/eadmin/validAddAssignmentBookTitle]',
            'termStartTime': '学年开始时间:required;date;',
            'termEndTime': '学年结束时间:required;date; match[gt, termStartTime, date]',
            'uploadFile': 'required;',
            'yearX':'required;integer[+];',
            'yearY':'required;integer[+];',
            'gradeX':'required;integer[+];',
            'gradeY':'required;integer[+];',
            'gradeNumX':'required;integer[+];',
            'gradeNumY':'required;integer[+];'
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
                url: web_path + "/administrator/eadmin/addAssignmentBook",
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
