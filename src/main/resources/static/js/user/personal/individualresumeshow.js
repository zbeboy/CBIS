/**
 * Created by lenovo on 2016-05-16.
 */
/**
 * 初始化页面链接
 */
function initParam(){
    if(userInfo.headImg != null){
        $('#headImg').attr('src',web_path + userInfo.headImg);
    }

    if(articleInfo.articlePhotoUrl != null){
        $('#articlePhotoUrl').attr('src',web_path + articleInfo.articlePhotoUrl);
    }

    $('#bigTitleLink').attr('href',web_path + '/student/personal/individualResumeShow?id='+articleInfo.id + "&username="+userInfo.username);
    $('#articlePhotoUrlLink').attr('href',web_path + '/student/personal/individualResumeShow?id='+articleInfo.id + "&username="+userInfo.username);
}

/**
 * 返回
 */
function back(){
    window.location.href = web_path + '/student/personal/individualResume';
}

$(document).ready(function(){
    initParam();
});