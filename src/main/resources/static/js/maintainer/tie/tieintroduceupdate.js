/**
 * Created by lenovo on 2016-01-31.
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
        url = web_path + "/maintainer/saveArticle";
        deletePicWay = true;
        deleteUrl = web_path + "/maintainer/deleteFile";
        id = 0;
    } else {
        url = web_path + "/maintainer/updateArticle";
        deletePicWay = false;
    }
    param.articleWordType = "系简介";
    param.checkArticleTitle = true;
    param.checkArticleContent = true;
    param.checkArticlePic = false;
    param.articleSaveOrUpdateUrl = url;
    param.clickOkUrl = web_path + "/user/tie/tieArticleShow";
    param.clickNoUrl = web_path + "/maintainer/tie/tieIntroduceUpdate";
    param.uploadParamFileName = "tieintroduce";
    param.id = id;
    param.cleanFromClient = deletePicWay;
    param.cleanUrl = deleteUrl;
    initUpload();
    initImage();
}
