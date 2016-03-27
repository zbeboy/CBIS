/**
 * Created by lenovo on 2016-01-24.
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
    'articleId':$('#articleInfoId').text().trim(),//文章id
    'tieElegantId':$('#tieElegantId').text().trim(),
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
    param.articleSaveOrUpdateUrl = web_path + "/maintainer/updateArticle";
    param.clickOkUrl = web_path + "/user/tie/tieElegantShow";
    param.clickNoUrl = web_path + "/maintainer/tie/tieElegant";
    param.uploadParamFileName = "tieelegant";
    param.id = $('#articleInfoId').text();
    param.myParam = myParam;
    param.cleanFromClient = false;
    param.cleanUrl = "";
    initUpload();
    initImage();
}