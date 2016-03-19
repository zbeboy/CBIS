/**
 * Created by lenovo on 2016-02-01.
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
        id = 0;
    } else {
        url = "/maintainer/updateArticle";
        deletePicWay = false;
    }
    param.articleWordType = "系培养目标";
    param.checkArticleTitle = true;
    param.checkArticleContent = true;
    param.checkArticlePic = false;
    param.articleSaveOrUpdateUrl = url;
    param.clickOkUrl = "/user/tieArticleShow";
    param.clickNoUrl = "/maintainer/tieTrainGoalUpdate";
    param.uploadParamFileName = "tietraingoal";
    param.id = id;
    param.cleanFromClient = deletePicWay;
    param.cleanUrl = deleteUrl;
    initUpload();
    initImage();
}
