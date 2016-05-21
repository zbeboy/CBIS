/**
 * Created by lenovo on 2016-01-24.
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
    param.articleSaveOrUpdateUrl = web_path + "/student/updateArticle";
    param.clickOkUrl = web_path + "/user/tie/tieElegantShow";
    param.clickNoUrl = web_path + "/maintainer/tie/tieElegant";
    param.uploadParamFileName = "tieelegant";
    param.articleId = $('#articleInfoId').text().trim();
    param.myParam = myParam;
    param.cleanFromClient = false;
    param.cleanUrl = "";
    initUpload();
    initImage();
}

/**
 * 删除系风采用
 */
function toDel(){
    layer.confirm("确定要删除该文章吗?", {
        btn: ['确定','取消'] //按钮
    }, function(){
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.post(web_path + '/maintainer/tie/deleteTieElegant',{
            'id':myParam.tieElegantId
        },function(data){
            layer.close(index);
            if(data.state){
                layer.msg(data.msg, {icon: 1},function(){
                    window.location.href = web_path + '/maintainer/tie/tieElegant';
                });
            } else {
                layer.msg(data.msg);
            }
        },'json');
    });
}