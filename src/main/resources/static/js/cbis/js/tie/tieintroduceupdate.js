/**
 * Created by lenovo on 2016-01-31.
 */
$(document).ready(function () {
    var id = $('#articleInfoId').text();
    var url = '';
    var deletePicWay = false;//删除图片方式
    var deleteUrl = "";
    if (id.trim().length <= 0) {
        url = "/maintainer/saveArticle";
        deletePicWay = true;
        deleteUrl = "/maintainer/deletePictue";
        id = 0;
    } else {
        url = "/maintainer/updateArticle";
        deletePicWay = false;
    }

    initArticleParam("系简介", true, true, false, url, "/user/tieArticleShow", "/maintainer/tieIntroduceUpdate", "tieintroduce", id, deletePicWay, "");

});

