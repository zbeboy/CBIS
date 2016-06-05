/**
 * Created by lenovo on 2016-06-05.
 */

/**
 * 前端封装参数
 * @type {{}}
 */
var param = {
    'teachType': $('#teachType').val().trim(),
    'teachCourseInfoFileName': $('#teachCourseInfoFileName').val().trim(),
    'teachCourseInfoTerm': $('#teachCourseInfoTerm').val().trim(),
    'termStartTime': $('#termStartTime').val().trim(),
    'termEndTime': $('#termEndTime').val().trim(),
    'filePath': $('#filePath').val().trim()
}

/**
 * 初始化参数
 */
function initParam() {
    param.teachType = $('#teachType').val().trim();
    param.teachCourseInfoFileName = $('#teachCourseInfoFileName').val().trim();
    param.teachCourseInfoTerm = $('#teachCourseInfoTerm').val().trim();
    param.termStartTime = $('#termStartTime').val().trim();
    param.termEndTime = $('#termEndTime').val().trim();
    param.filePath = $('#filePath').val().trim();
}

$(function () {

    $('#submitData').attr('disabled', true);//未上传教学任务书前,不能提交数据.

    $('#fileupload').fileupload({
        url: web_path + '/student/uploadFile',
        formData: {pathname: 'teachingprocess'},
        dataType: 'json',
        send: function (e, data) {
            var regex = /(\.|\/)(xls|xlsx|doc|docx|ppt|pptx|XLS|XLSX|DOC|DOCX|PPT|PPTX)$/i;
            if (regex.test(data.files[0].name)) {
                $('.uk-progress').removeClass('uk-hidden');
                $('.uk-progress-bar').css(
                    'width',
                    '0%'
                );
                return true;
            } else {
                layer.msg("请上传(xls|xlsx|doc|docx|ppt|pptx)类型的文件!");
                return false;
            }
            return false;
        },
        done: function (e, data) {
            $('#uploadFile').val(data.result.single.single.originalFilename + "." + data.result.single.single.ext);
            $('#filePath').val("/files/teachingprocess/" + data.result.single.single.newName);
            $('#submitData').attr('disabled', false);//上传任务书后可提交数据.
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('.uk-progress-bar').css(
                'width',
                progress + '%'
            );
        }
    });

    /* nice validator*/
    $('#addTeachingProcessForm').validator({
        stopOnError: false,
        timely: 'yellow_right_effect',
        ignore: ':hidden',
        validClass: 'uk-form-success',
        invalidClass: 'uk-form-danger',
        fields: {
            'teachCourseInfoFileName': 'required;length[1~30];',
            'teachCourseInfoTerm': 'required;checked;',
            'termStartTime': '学年开始时间:required;date;',
            'termEndTime': '学年结束时间:required;date; match[gt, termStartTime, date]',
            'uploadFile': 'required;'
        },
        valid: function (form) {
            var me = this;
            // 提交表单之前，hold住表单，并且在以后每次hold住时执行回调
            //loading层
            var index = layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
            me.holdSubmit();
            initParam();
            $.ajax({
                url: web_path + "/administrator/eadmin/addTeachingProcess",
                data: param,
                type: "POST",
                success: function (data) {
                    // 提交表单成功后，释放hold，就可以再次提交
                    me.holdSubmit(false);
                    layer.close(index);
                    if (data.state) {
                        layer.msg(data.msg, {icon: 1}, function () {
                            window.location.href = web_path + '/administrator/eadmin/teachingProcessList';
                        });
                    } else {
                        layer.msg(data.msg);
                    }
                }
            });
        }
    });
});