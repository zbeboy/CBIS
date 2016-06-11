/**
 * Created by lenovo on 2016-05-16.
 */
/**
 * 上传图片到服务器端
 */
function initUpload() {
    var progressbar = $("#progressbar"),
        bar = progressbar.find('.uk-progress-bar'),
        settings = {

            action: web_path + '/student/uploadFile', // upload url

            allow: '*.(jpeg|jpg|gif|png|JPEG|JPG|GIF|PNG)', // allow only images

            params: {'pathname': 'headImg'},//json 数据 该文件服务器端相对 files下路径

            loadstart: function () {
                bar.css("width", "0%").text("0%");
                progressbar.removeClass("uk-hidden");
            },

            progress: function (percent) {
                percent = Math.ceil(percent);
                bar.css("width", percent + "%").text(percent + "%");
            },

            allcomplete: function (response) {

                bar.css("width", "100%").text("100%");

                setTimeout(function () {
                    progressbar.addClass("uk-hidden");
                }, 250);

                //获取服务器端图片相对路径
                var json = JSON.parse(response);
                var str = json.msg.substring(json.msg.lastIndexOf('/') + 1, json.msg.length);

                //显示图片
                $('#showHeadImg').attr('src', web_path + '/files/headImg/' + str);
                $('#headImg').val('/files/headImg/' + str);
            }
        };

    var select = UIkit.uploadSelect($("#upload-select"), settings),
        drop = UIkit.uploadDrop($("#upload-drop"), settings);

}

/**
 * 全局参数
 * @type {{realName: string, sex: string, birthday: string, nation: string, post: string, politicalLandscape: string, religiousBelief: string, headImg: string, identityCard: string, familyResidence: string, parentName: string, parentContactPhone: string, dormitoryNumber: string, placeOrigin: string, problemSituation: string, personaIntroduction: string}}
 */
var param = {
    'username':'',
    'realName': '',
    'sex': '男',
    'birthday': '',
    'nation': '',
    'post': '',
    'politicalLandscape': '',
    'religiousBelief': '',
    'headImg': '',
    'identityCard': '',
    'familyResidence': '',
    'parentName': '',
    'parentContactPhone': '',
    'dormitoryNumber': '',
    'placeOrigin': '',
    'problemSituation': '',
    'personaIntroduction': ''
}

function initParam() {
    param.username = $('#username').val().trim();
    param.realName = $('#realName').val().trim();
    param.sex = $("input[name='sex']:checked").val().trim();
    param.birthday = $('#birthday').val().trim();
    param.nation = $('#nation').val().trim();
    param.post = $('#post').val().trim();
    param.politicalLandscape = $('#politicalLandscape').val().trim();
    param.religiousBelief = $('#religiousBelief').val().trim();
    param.headImg = $('#headImg').val().trim();
    param.identityCard = $('#identityCard').val().trim();
    param.familyResidence = $('#familyResidence').val().trim();
    param.personaIntroduction = $('#personaIntroduction').val().trim();
}

function initHeadImg(){
    var headImg =   $('#headImg').val().trim();
    if(headImg.length>0){
        $('#showHeadImg').attr('src', web_path + headImg);
    }
}

function validateAll() {
    var identityCard = param.identityCard;
    if (identityCard.length > 0) {
        var regex = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        if (!regex.test(identityCard)) {
            $('#identityCard').removeClass('uk-form-success').addClass('uk-form-danger');
            $('#identityCardError').text('请输入正确的身份证号!');
            $('#identityCard').focus();
            return false;
        } else {
            $('#identityCard').removeClass('uk-form-danger').addClass('uk-form-success');
            $('#identityCardError').text('');
        }
    }

    $('#realName').removeClass('uk-form-danger').addClass('uk-form-success');
    $('#birthday').removeClass('uk-form-danger').addClass('uk-form-success');
    $('#nation').removeClass('uk-form-danger').addClass('uk-form-success');
    $('#post').removeClass('uk-form-danger').addClass('uk-form-success');
    $('#politicalLandscape').removeClass('uk-form-danger').addClass('uk-form-success');
    $('#religiousBelief').removeClass('uk-form-danger').addClass('uk-form-success');
    $('#familyResidence').removeClass('uk-form-danger').addClass('uk-form-success');
    $('#personaIntroduction').removeClass('uk-form-danger').addClass('uk-form-success');
    return true;
}

function submitData(){
    initParam();
    if(validateAll()){
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.post(web_path + '/teacher/personal/updateTeacherModifyData',param,
            function(data){
                layer.close(index);
                if(data.state){
                    window.location.reload(true);
                } else {
                    layer.msg(data.msg);
                }
            },'json');
    }
}

$(document).ready(function () {
    initUpload();
    initHeadImg();
});