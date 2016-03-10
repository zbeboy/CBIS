/**
 * Created by lenovo on 2016-03-09.
 */
$(document).ready(function () {
    initArticleParam();
});
/**
 * 初始化参数
 */
function initArticleParam() {
    param.articleWordType = "系公告";
    param.checkArticleTitle = true;
    param.checkArticleContent = true;
    param.checkArticlePic = false;
    param.articleSaveOrUpdateUrl = "/maintainer/updateArticle";
    param.clickOkUrl = "/user/tieNoticeShow";
    param.clickNoUrl = "/maintainer/tieNotice";
    param.uploadParamFileName = "tienotice";
    param.id = $('#articleInfoId').text();
    param.cleanFromClient = false;
    param.cleanUrl = "";
    initUpload();
    initImage();
}