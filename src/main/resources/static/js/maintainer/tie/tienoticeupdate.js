/**
 * Created by lenovo on 2016-03-09.
 */
var isShow = $('#isShow').val();
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
    'articleId':$('#articleInfoId').text().trim(),//文章id
    'tieNoticeId':$('#tieNoticeId').text().trim(),
    'isShow':isShow//是否首页显示
}
/**
 * 初始化参数
 */
function initArticleParam() {
    param.articleWordType = "系公告";
    param.checkArticleTitle = true;
    param.checkArticleContent = true;
    param.checkArticlePic = false;
    param.articleSaveOrUpdateUrl = web_path + "/student/updateArticle";
    param.clickOkUrl = web_path + "/user/tie/tieNoticeShow";
    param.clickNoUrl = web_path + "/maintainer/tie/tieNotice";
    param.uploadParamFileName = "tienotice";
    param.myParam = myParam;
    param.articleId = $('#articleInfoId').text().trim();
    param.cleanFromClient = false;
    param.openAffix = true;
    param.affixSaveFunc = "tieNoticeAffix"
    param.affixEndFunc = "endFunc()";
    param.cleanAffixFromClient = false;
    initUpload();
    initImage();
}

var tieNoticeAffixArr = new Array();
function tieNoticeAffixData(id, url, original_name) {
    this.id = id;
    this.tieNoticeFileUrl = url;
    this.tieNoticeFileName = original_name;
}

function tieNoticeAffix(id, url, original_name) {
    tieNoticeAffixArr.push(new tieNoticeAffixData(id, url, original_name));
}

function endFunc() {
    param.affixData = tieNoticeAffixArr;
}

/**
 *删除公告文章
 */
function toDel(){
    layer.confirm("确定要删除该文章吗?", {
        btn: ['确定','取消'] //按钮
    }, function(){
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.post(web_path + '/maintainer/tie/deleteTieNotice',{
            'id':myParam.tieNoticeId
        },function(data){
            layer.close(index);
            if(data.state){
                layer.msg(data.msg, {icon: 1},function(){
                    window.location.href = web_path + '/maintainer/tie/tieNotice';
                });
            } else {
                layer.msg(data.msg);
            }
        },'json');
    });
}