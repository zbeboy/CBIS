/**
 * Created by lenovo on 2016-02-09.
 */
$(document).ready(function () {
    initArticleParam();
});

/**
 * 初始化参数
 */
function initArticleParam() {
    var id = $('#articleInfoId').text();
    var url = '';
    var deletePicWay = false;//删除图片方式
    var deleteUrl = "";
    if (id.trim().length <= 0) {
        url = "/maintainer/saveArticle";
        deletePicWay = true;
        deleteUrl = "/maintainer/deleteFile";
        id = $('#majorId').text();
    } else {
        url = "/maintainer/updateArticle";
        deletePicWay = false;
    }
    param.articleWordType = "专业简介";
    param.checkArticleTitle = true;
    param.checkArticleContent = true;
    param.checkArticlePic = false;
    param.articleSaveOrUpdateUrl = url;
    param.clickOkUrl = "/user/majorArticleShow";
    param.clickNoUrl = "/maintainer/majorIntroduce";
    param.uploadParamFileName = "majorintroduce";
    param.id = id;
    param.cleanFromClient = deletePicWay;
    param.cleanUrl = deleteUrl;
    param.pluginClickOkUrlParam = "&majorId=" + $('#majorId').text();
    initUpload();
    initImage();
}

