/**
 * Created by lenovo on 2016-03-07.
 */

/**
 * 输出html
 * @param data
 */
function outputHtml(data) {
    $(target.bigTitle).attr('href', web_path + '/user/tie/tieArticleShow?id=' + target.id).text(data.articleInfo.bigTitle);
    $(target.realname).text(data.articleInfo.userRealName);
    $(target.date).text(data.articleInfo.date);
    $(target.imgurl).attr('href', web_path + '/user/tie/tieArticleShow?id=' + target.id);
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
    if (currentId == tieintroduceid) {
        target.id = currentId;
        target.bigTitle = "#tieintroducebigtitle";
        target.realname = "#tieintroducerealname";
        target.date = "#tieintroducedate";
        target.imgurl = "#tieintroduceimgurl";
        target.imgsrc = "#tieintroduceimgsrc";
        target.content = "#tieintroducecontent";
        target.sub = "#tieintroducesub";
    }

    if (currentId == tieheadid) {
        target.id = currentId;
        target.bigTitle = "#tieheadbigtitle";
        target.realname = "#tieheadrealname";
        target.date = "#tieheaddate";
        target.imgurl = "#tieheadimgurl";
        target.imgsrc = "#tieheadimgsrc";
        target.content = "#tieheadcontent";
        target.sub = "#tieheadsub";
    }

    if (currentId == tiegoalid) {
        target.id = currentId;
        target.bigTitle = "#tiegoalbigtitle";
        target.realname = "#tiegoalrealname";
        target.date = "#tiegoaldate";
        target.imgurl = "#tiegoalimgurl";
        target.imgsrc = "#tiegoalimgsrc";
        target.content = "#tiegoalcontent";
        target.sub = "#tiegoalsub";
    }

    if (currentId == tieitemid) {
        target.id = currentId;
        target.bigTitle = "#tieitembigtitle";
        target.realname = "#tieitemrealname";
        target.date = "#tieitemdate";
        target.imgurl = "#tieitemimgurl";
        target.imgsrc = "#tieitemimgsrc";
        target.content = "#tieitemcontent";
        target.sub = "#tieitemsub";
    }

    action();
}

/**
 * 封装参数id
 * @type {{id: string, bigTitle: string, realname: string, date: string, imgurl: string, imgsrc: string, content: string, sub: string}}
 */
var target = {
    'id': '',
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
        $.post(web_path + '/user/tie/tieArticleShowData', {
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
 * 系简介
 */
function toIntroduce() {
    currentId = tieintroduceid;
    initParam();
}

/**
 * 系主任
 */
function toHead() {
    currentId = tieheadid;
    initParam();
}

/**
 * 系培养目标
 */
function toGoal() {
    currentId = tiegoalid;
    initParam();
}

/**
 * 系特色
 */
function toItem() {
    currentId = tieitemid;
    initParam();
}