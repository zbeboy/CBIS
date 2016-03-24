/**
 * Created by lenovo on 2016-03-19.
 */
/**
 * 点击专业，去专业文章
 * @param obj
 */
function toMajor(obj) {
    var p = $(obj).parent().parent().parent();

    if ($(p.children()[0]).text().trim().length > 0 && $(p.children()[1]).text().trim().length > 0) {
        window.location.href = web_path + "/user/major/majorArticleShow?majorId=" + $(p.children()[0]).text() + "&id=" + $(p.children()[1]).text();
    }
}

/**
 * 点击专业更多
 * @param obj
 */
function moreMajor(obj) {
    var p = $(obj).prev();
    var majorId = $($(p.children()[0]).children()[0]).text();
    var articleInfoId = $($(p.children()[0]).children()[1]).text();
    if (majorId.trim().length > 0 && articleInfoId.trim().length > 0) {
        window.location.href = web_path + "/user/major/majorArticleShow?majorId=" + majorId + "&id=" + articleInfoId;
    }
}

$('#loginmodal').on({

    'show.uk.modal': function () {
        $('#username').focus();
    }
});