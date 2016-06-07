/**
 * Created by lenovo on 2016-06-06.
 */

/**
 * 前端封装参数
 * @type {{}}
 */
var param = {
    'teachType': $('#teachType').val().trim(),
    'fileName': $('#fileName').val().trim(),
    'remark': $('#remark').val().trim(),
    'filePath': $('#filePath').val().trim()
}

/**
 * 初始化参数
 */
function initParam() {
    param.teachType = $('#teachType').val().trim();
    param.fileName = $('#fileName').val().trim();
    param.remark = $('#remark').val().trim();
    param.filePath = $('#filePath').val().trim();
}

$(function () {

    $('#submitData').attr('disabled', true);//未上传教学任务书前,不能提交数据.

    $('#fileupload').fileupload({
        url: web_path + '/student/uploadFile',
        formData: {pathname: 'relateddownload'},
        dataType: 'json',
        send: function (e, data) {
            $('.uk-progress').removeClass('uk-hidden');
            $('.uk-progress-bar').css(
                'width',
                '0%'
            );
            return true;
        },
        done: function (e, data) {
            $('#uploadFile').val(data.result.single.single.originalFilename + "." + data.result.single.single.ext);
            $('#filePath').val("/files/relateddownload/" + data.result.single.single.newName);
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
            'fileName': 'required;length[1~30];',
            'remark': 'length[0~100];',
            'uploadFile': 'required;'
        },
        valid: function (form) {
            initParam();
            var me = this;
            // 提交表单之前，hold住表单，并且在以后每次hold住时执行回调
            //loading层
            var index = layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
            me.holdSubmit();
            $.ajax({
                url: web_path + "/administrator/eadmin/addRelatedDownload",
                data: param,
                type: "POST",
                success: function (data) {
                    // 提交表单成功后，释放hold，就可以再次提交
                    me.holdSubmit(false);
                    layer.close(index);
                    if (data.state) {
                        layer.msg(data.msg, {icon: 1}, function () {
                            window.location.href = web_path + '/administrator/eadmin/relatedDownloadList?teachType='+teachType;
                        });
                    } else {
                        layer.msg(data.msg);
                    }
                }
            });
        }
    });
});