/**
 * Created by lenovo on 2016-01-24.
 */
$(document).ready(function () {
    initArticleParam();
});

/**
 * 初始化参数
 */
function initArticleParam() {
    param.articleWordType = "系风采";
    param.checkArticleTitle = true;
    param.checkArticleContent = true;
    param.checkArticlePic = true;
    param.articleSaveOrUpdateUrl = web_path + "/maintainer/updateArticle";
    param.clickOkUrl = web_path + "/user/tie/tieElegantShow";
    param.clickNoUrl = web_path + "/maintainer/tie/tieElegant";
    param.uploadParamFileName = "tieelegant";
    param.id = $('#articleInfoId').text();
    param.cleanFromClient = false;
    param.cleanUrl = "";
    initUpload();
    initImage();
}