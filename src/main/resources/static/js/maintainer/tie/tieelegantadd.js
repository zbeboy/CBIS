/**
 * Created by lenovo on 2016-01-10.
 */
var isShow = 0;
$(document).ready(function () {
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
    'isShow':isShow//是否首页显示
}

/**
 * 初始化参数
 */
function initArticleParam() {
    param.articleWordType = "系风采";
    param.checkArticleTitle = true;
    param.checkArticleContent = true;
    param.checkArticlePic = true;
    param.articleSaveOrUpdateUrl = web_path + "/student/saveArticle";
    param.clickOkUrl = web_path + "/user/tie/tieElegantShow";
    param.clickNoUrl = web_path + "/maintainer/tie/tieElegant";
    param.uploadParamFileName = "tieelegant";
    param.myParam = myParam;
    param.cleanFromClient = true;
    param.cleanUrl = web_path + "/student/deleteFile";
    initUpload();
    initImage();
}
