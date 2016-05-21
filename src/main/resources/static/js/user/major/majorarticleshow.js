/**
 * Created by lenovo on 2016-03-18.
 */
/**
 * 输出html
 * @param data
 */
function outputHtml(data) {
    $(target.bigTitle).attr('href', web_path + '/user/major/majorArticleShow?id=' + target.id + "&majorId=" + target.majorId).text(data.articleInfo.bigTitle);
    $(target.realname).text(data.articleInfo.realName);
    $(target.date).text(data.articleInfo.date);
    $(target.imgurl).attr('href', web_path + '/user/major/majorArticleShow?id=' + target.id + "&majorId=" + target.majorId);
    $(target.imgsrc).attr('src', data.articleInfo.articlePhotoUrl);
    $(target.content).text(data.articleInfo.articleContent);

    $(target.sub).empty();
    for (var i = 0; i < data.articleSub.length; i++) {
        if (data.articleSub[i].subTitle != '' && data.articleSub[i].subTitle != null) {
            $(target.sub).append($("<h2>").text(data.articleSub[i].subTitle));
        }
        if (data.articleSub[i].subContent != '' && data.articleSub[i].subContent != null) {
            $(target.sub).append($("<p>").text(data.articleSub[i].subContent));
        }

    }

}

/**
 * 初始化参数
 */
function initParam() {
    target.id = currentId;
    target.majorId = currentMajorId;
    target.navId = navId;

    if (currentId == majorintroduceid) {
        target.bigTitle = "#majorintroducebigtitle";
        target.realname = "#majorintroducerealname";
        target.date = "#majorintroducedate";
        target.imgurl = "#majorintroduceimgurl";
        target.imgsrc = "#majorintroduceimgsrc";
        target.content = "#majorintroducecontent";
        target.sub = "#majorintroducesub";
    }

    if (currentId == majorheadid) {
        target.bigTitle = "#majorheadbigtitle";
        target.realname = "#majorheadrealname";
        target.date = "#majorheaddate";
        target.imgurl = "#majorheadimgurl";
        target.imgsrc = "#majorheadimgsrc";
        target.content = "#majorheadcontent";
        target.sub = "#majorheadsub";
    }

    if (currentId == majortraingoalid) {
        target.bigTitle = "#majorgoalbigtitle";
        target.realname = "#majorgoalrealname";
        target.date = "#majorgoaldate";
        target.imgurl = "#majorgoalimgurl";
        target.imgsrc = "#majorgoalimgsrc";
        target.content = "#majorgoalcontent";
        target.sub = "#majorgoalsub";
    }

    if (currentId == majortraitid) {
        target.bigTitle = "#majoritembigtitle";
        target.realname = "#majoritemrealname";
        target.date = "#majoritemdate";
        target.imgurl = "#majoritemimgurl";
        target.imgsrc = "#majoritemimgsrc";
        target.content = "#majoritemcontent";
        target.sub = "#majoritemsub";
    }

    action();
}

/**
 * 封装参数
 * @type {{id: string, majorId: string, bigTitle: string, realname: string, date: string, imgurl: string, imgsrc: string, content: string, sub: string}}
 */
var target = {
    'id': '',
    'majorId': '',
    'navId': '',
    'bigTitle': '',
    'realname': '',
    'date': '',
    'imgurl': '',
    'imgsrc': '',
    'content': '',
    'sub': ''
};

/**
 * 执行入口
 */
function action() {
    if (target.id != null && target.id > 0) {
        $.post(web_path + '/user/major/majorArticleShowData', {
            'id': target.id
        }, function (data) {
            outputHtml(data);
        }, 'json');
    }
}

$(document).ready(function () {
    initParam();
});

/**
 * 专业简介
 */
function toIntroduce() {
    currentId = majorintroduceid;
    navId = "navmajorintroduce";
    initParam();
}

/**
 * 专业带头人
 */
function toHead() {
    currentId = majorheadid;
    navId = "navmajorhead";
    initParam();
}

/**
 * 专业培养目标
 */
function toGoal() {
    currentId = majortraingoalid;
    navId = "navmajortraingoal";
    initParam();
}

/**
 * 专业特色
 */
function toItem() {
    currentId = majortraitid;
    navId = "navmajortrait";
    initParam();
}

/**
 * 另一个专业
 * @param obj
 */
function toMajor(obj) {
    window.location.href = web_path + '/user/major/articleMajorData?navId=' + target.navId + "&majorId=" + $(obj).attr("data");
}

$('#loginmodal').on({

    'show.uk.modal': function () {
        $('#username').focus();
    }
});