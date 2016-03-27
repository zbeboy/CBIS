/**
 * Created by lenovo on 2016-02-09.
 */

var isShow = 0;
$(document).ready(function () {
    isShow = $('#isShow').val();
    initArticleParam();
    $('#isShow').click(function(){
        if (isShow == 0) {
            isShow = 1;
        } else {
            isShow = 0;
        }
        myParam.isShow = isShow;
    });
});

/*
自定义参数
 */
var myParam = {
    'articleId':0,//文章id
    'majorId':0,//专业id
    'isShow':isShow//是否首页显示
}

/**
 * 初始化参数
 */
function initArticleParam() {
    var url = '';
    var deletePicWay = false;//删除图片方式
    var deleteUrl = "";
    if ($('#articleInfoId').text().trim().length <= 0) {
        url = web_path + "/maintainer/saveArticle";
        deletePicWay = true;
        deleteUrl = web_path + "/maintainer/deleteFile";
    } else {
        url = web_path + "/maintainer/updateArticle";
        deletePicWay = false;
    }
    myParam.articleId = $('#articleInfoId').text().trim();
    myParam.majorId = $('#majorId').text().trim();
    param.articleWordType = "专业简介";
    param.checkArticleTitle = true;
    param.checkArticleContent = true;
    param.checkArticlePic = false;
    param.articleSaveOrUpdateUrl = url;
    param.clickOkUrl = web_path + "/user/major/majorArticleShow";
    param.clickNoUrl = web_path + "/maintainer/major/majorIntroduce";
    param.uploadParamFileName = "majorintroduce";
    param.myParam = myParam;
    param.cleanFromClient = deletePicWay;
    param.cleanUrl = deleteUrl;
    param.pluginClickOkUrlParam = "&majorId=" + $('#majorId').text();
    initUpload();
    initImage();
}

