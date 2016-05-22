/**
 * Created by lenovo on 2016-05-16.
 */
$(document).ready(function () {
    initArticleParam();
});

/*
 自定义参数
 */
var myParam = {
    'articleId': 0,//文章id
    'username': 0//用户 username
}

/**
 * 初始化参数
 */
function initArticleParam() {
    var url = '';
    var deletePicWay = false;//删除图片方式
    var deleteUrl = "";
    if (articleInfoId == null) {
        url = web_path + "/student/saveArticle";
        deletePicWay = true;
        deleteUrl = web_path + "/student/deleteFile";
        $('#scan').addClass('uk-hidden');
    } else {
        url = web_path + "/student/updateArticle";
        deletePicWay = false;
        $('#scan').removeClass('uk-hidden');
    }
    myParam.articleId = articleInfoId;
    myParam.username = username;
    param.articleWordType = "用户简介";
    param.checkArticleTitle = true;
    param.checkArticleContent = true;
    param.checkArticlePic = false;
    param.articleSaveOrUpdateUrl = url;
    param.clickOkUrl = web_path + "/student/personal/individualResumeShow";
    param.clickNoUrl = web_path + "/student/personal/individualResume";
    param.uploadParamFileName = "individualresume";
    param.myParam = myParam;
    param.cleanFromClient = deletePicWay;
    param.cleanUrl = deleteUrl;
    param.pluginClickOkUrlParam = "&username=" + username;
    initUpload();
    initImage();
}

/**
 * 查看文章
 */
function scanIntroduce() {
    window.location.href = web_path + '/user/personal/individualResumeShow?username=' + username;
}