/**
 * Created by lenovo on 2016-02-09.
 */
$(document).ready(function () {
    initArticleParam();
});

/*
自定义参数
 */
var myParam = {
    'articleId':0,//文章id
    'majorId':0//专业id
}

/**
 * 初始化参数
 */
function initArticleParam() {
    var url = '';
    var deletePicWay = false;//删除图片方式
    var deleteUrl = "";
    if ($('#articleInfoId').text().trim().length <= 0) {
        url = web_path + "/student/saveArticle";
        deletePicWay = true;
        deleteUrl = web_path + "/student/deleteFile";
    } else {
        url = web_path + "/student/updateArticle";
        deletePicWay = false;
    }
    myParam.articleId = $('#articleInfoId').text().trim();
    myParam.majorId = $('#majorId').text().trim();
    param.articleWordType = "专业培养目标";
    param.checkArticleTitle = true;
    param.checkArticleContent = true;
    param.checkArticlePic = false;
    param.articleSaveOrUpdateUrl = url;
    param.clickOkUrl = web_path + "/user/major/majorArticleShow";
    param.clickNoUrl = web_path + "/maintainer/major/majorTrainingGoal";
    param.uploadParamFileName = "majortraininggoal";
    param.myParam = myParam;
    param.articleId = $('#articleInfoId').text().trim();
    param.cleanFromClient = deletePicWay;
    param.cleanUrl = deleteUrl;
    param.pluginClickOkUrlParam = "&majorId=" + $('#majorId').text();
    initUpload();
    initImage();
}

