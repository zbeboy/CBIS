/**
 * Created by lenovo on 2016-05-24.
 */
/**
 * 专业教师
 * @param data
 */
function outputTeacherHtml(data){
    $('#teacherData').empty();
    var list = data.single.majorTeacherVos;
    for(var i = 0;i<list.length;i++){
        $('#teacherData').append(
            $('<li>').append(
                $('<a class="uk-thumbnail uk-thumbnail-medium" onclick="toTeacher('+list[i].username+');" href="javascript:;">')
                    .append($('<img class="uk-thumbnail" alt="教师图片" src="'+web_path+list[i].headImg+'" />'))
                    .append($('<div class="uk-thumbnail-caption">').text(list[i].realName))
            )
        );
    }
}

function outputArticleSubHtml(data){
    $('#articleSubData').empty();
    var list = data.single.articleSub;
    for(var i = 0;i<list.length;i++){
        $('#articleSubData')
            .append($('<h2>').text(list[i].subTitle))
            .append($('<p>').text(list[i].subContent));
    }
}

function initPage(data){
    var userInfo = data.single.userInfo;
    var articleInfo = data.single.articleInfo;
    $('#headImg').attr('src',web_path+userInfo.headImg);
    $('#userRealName').text(userInfo.realName);
    $('#userIntroduction').text(userInfo.personaIntroduction);
    $('#bigTitleLink').attr('href', web_path + '/user/major/teachersResumeShow?username=' + param.username + "&majorId=" + param.majorId).text(articleInfo.bigTitle);
    $('#articleRealName').text(userInfo.realName);
    $('#articleDate').text(articleInfo.date);
    $('#articlePhotoUrlLink').attr('href', web_path + '/user/major/teachersResumeShow?username=' + param.username + "&majorId=" + param.majorId).text(articleInfo.bigTitle);
    $('#articleContent').text(articleInfo.articleContent);
}

var param = {
    'username':username,
    'majorId':majorId
}

function toTeacher(username){
    param.username = username;
    action();
}

/**
 * 执行入口
 */
function action() {
    if (username != null && majorId > 0) {
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.post(web_path + '/user/major/teachersResumeShowData',param,
            function (data) {
                layer.close(index);
                initPage(data);
                outputTeacherHtml(data);
                outputArticleSubHtml(data);
        }, 'json');
    }
}

$(document).ready(function(){
   action();
});