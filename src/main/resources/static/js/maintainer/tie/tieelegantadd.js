/**
 * Created by lenovo on 2016-01-10.
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
    param.articleSaveOrUpdateUrl = web_path + "/maintainer/saveArticle";
    param.clickOkUrl = web_path + "/user/tie/tieElegantShow";
    param.clickNoUrl = web_path + "/maintainer/tie/tieElegant";
    param.uploadParamFileName = "tieelegant";
    param.id = 0;
    param.cleanFromClient = true;
    param.cleanUrl = web_path + "/maintainer/deleteFile";
    initUpload();
    initImage();
}
