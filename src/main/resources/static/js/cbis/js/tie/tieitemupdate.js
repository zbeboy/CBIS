/**
 * Created by lenovo on 2016-02-01.
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

    initArticleParam("系特色", true, true, false, url, "/user/tieArticleShow", "/maintainer/tieItemUpdate", "tieitem", id, deletePicWay, "");

});

