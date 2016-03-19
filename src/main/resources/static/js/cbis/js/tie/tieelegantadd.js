/**
 * Created by lenovo on 2016-01-10.
 */
$(document).ready(function(){
    initArticleParam();
});

/**
 * 初始化参数
 */
function initArticleParam(){
    param.articleWordType = "系风采";
    param.checkArticleTitle = true;
    param.checkArticleContent = true;
    param.checkArticlePic = true;
    param.articleSaveOrUpdateUrl = "/maintainer/saveArticle";
    param.clickOkUrl = "/user/tieElegantShow";
    param.clickNoUrl = "/maintainer/tieElegant";
    param.uploadParamFileName = "tieelegant";
    param.id = 0;
    param.cleanFromClient = true;
    param.cleanUrl = "/maintainer/deleteFile";
    initUpload();
    initImage();
}
