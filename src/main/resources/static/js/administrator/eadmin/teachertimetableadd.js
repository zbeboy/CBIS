/**
 * Created by lenovo on 2016-06-06.
 */

/**
 * 搜索教师
 */
function searchTeachers(){
    var index = layer.load(1, {
        shade: [0.1,'#fff'] //0.1透明度的白色背景
    });
    $.post(web_path + "/maintainer/users/teacherAllData", {
        'teacherName': $('#searchTeacher').val().trim()
    }, function (data) {
        layer.close(index);
        if (data.state) {
            var _ = DOMBuilder;
            $('#teacherData').empty();
            for (var i = 0; i < data.result.length; i++) {
                $('#teacherData').append(
                    $('<li>').append(
                        $('<label>').append(
                            $('<input type="radio" name="teacher" >').val(data.result[i].id)
                        ).append(
                            $('<span>').text(data.result[i].realName)
                        )
                    )
                );
            }
        } else {
            layer.msg(data.msg);
        }
    });
}

function refresh(){
    $('#searchTeacher').val('');
    searchTeachers();
}

$(document).ready(function(){
    searchTeachers();
});

/**
 * 前端封装参数
 * @type {{}}
 */
var param = {
    'teachType': $('#teachType').val().trim(),
    'timetableInfoFileName': $('#timetableInfoFileName').val().trim(),
    'teacherId': 0,
    'timetableInfoTerm':$('#timetableInfoTerm').val().trim(),
    'termStartTime': $('#termStartTime').val().trim(),
    'termEndTime': $('#termEndTime').val().trim(),
    'filePath': $('#filePath').val().trim()
}

/**
 * 初始化参数
 */
function initParam() {
    param.teachType = $('#teachType').val().trim();
    param.timetableInfoFileName = $('#timetableInfoFileName').val().trim();
    param.timetableInfoTerm = $('#timetableInfoTerm').val().trim();
    param.termStartTime = $('#termStartTime').val().trim();
    param.termEndTime = $('#termEndTime').val().trim();
    param.filePath = $('#filePath').val().trim();
    var n = $("input[name='teacher']:checked").val();
    param.teacherId = n;
}

$(function () {

    $('#submitData').attr('disabled', true);//未上传教学任务书前,不能提交数据.

    $('#fileupload').fileupload({
        url: web_path + '/student/uploadFile',
        formData: {pathname: 'teachertimetable'},
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
            $('#filePath').val("/files/teachertimetable/" + data.result.single.single.newName);
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
    $('#addForm').validator({
        stopOnError: false,
        timely: 'yellow_right_effect',
        ignore: ':hidden',
        validClass: 'uk-form-success',
        invalidClass: 'uk-form-danger',
        fields: {
            'timetableInfoFileName': 'required;length[1~30];',
            'timetableInfoTerm': 'required;checked;',
            'termStartTime': '学年开始时间:required;date;',
            'termEndTime': '学年结束时间:required;date; match[gt, termStartTime, date]',
            'uploadFile': 'required;'
        },
        valid: function (form) {
            initParam();
            if(param.teacherId == undefined || param.teacherId == 0 ){
                layer.msg('请选择一个教师!');
            } else {
                var me = this;
                // 提交表单之前，hold住表单，并且在以后每次hold住时执行回调
                //loading层
                var index = layer.load(1, {
                    shade: [0.1, '#fff'] //0.1透明度的白色背景
                });
                me.holdSubmit();
                $.ajax({
                    url: web_path + "/administrator/eadmin/addTeacherTimetable",
                    data: param,
                    type: "POST",
                    success: function (data) {
                        // 提交表单成功后，释放hold，就可以再次提交
                        me.holdSubmit(false);
                        layer.close(index);
                        if (data.state) {
                            layer.msg(data.msg, {icon: 1}, function () {
                                window.location.href = web_path + '/administrator/eadmin/teacherTimetableList?teachType='+teachType;
                            });
                        } else {
                            layer.msg(data.msg);
                        }
                    }
                });
            }

        }
    });
});