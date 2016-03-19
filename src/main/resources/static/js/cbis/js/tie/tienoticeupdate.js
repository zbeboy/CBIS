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
    param.openAffix = true;
    param.affixSaveFunc = "tieNoticeAffix"
    param.affixEndFunc = "endFunc()";
    param.cleanAffixFromClient = false;
    initUpload();
    initImage();
}

var tieNoticeAffixArr = new Array();
function tieNoticeAffixData(id,url,original_name){
    this.id = id;
    this.tieNoticeFileUrl = url;
    this.tieNoticeFileName = original_name;
}

function tieNoticeAffix(id,url,original_name){
    tieNoticeAffixArr.push(new tieNoticeAffixData(id,url,original_name));
}

function endFunc(){
    param.affixData = tieNoticeAffixArr;
}