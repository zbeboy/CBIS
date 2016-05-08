/**
 * Created by lenovo on 2016-05-07.
 */
/**
 * 输出列表
 * @param data
 */
function outputHtml(data) {
    $('#dataShow').empty();
    for (var i = 0; i < data.result.length; i++) {
        $('#dataShow').append($('<div class="uk-panel uk-panel-box">').append(
            $('<h3 class="uk-panel-title">').text(data.result[i].autonomousPracticeTitle)
            )
            .append($('<ul class="uk-grid uk-grid-width-1-1 uk-grid-width-medium-1-2 uk-grid-width-large-1-2">')
                .append($('<li>').text('作者:' + data.result[i].realName))
                .append($('<li>').text('使用模板:' + data.result[i].autonomousPracticeTemplateTitle))
                .append($('<li>').text('开始时间:' + data.result[i].startTimeString))
                .append($('<li>').text('结束时间:' + data.result[i].endTimeString))
            )
            .append($('<p>').text('允许填报年级:' + data.result[i].gradeYear))
            .append($('<div class="uk-text-right uk-margin" >').append($('<button class="uk-button" data-id="' + data.result[i].id + '" type="button" onclick="startAP(this);" >').text('查看统计')))
        );
    }
}

//初始化参数
var param = {
    pageNum: 0,
    pageSize: 5
}

/**
 * 执行函数
 */
function action() {
    $.post(web_path + '/semi/autonomicpractice/dataAnalysisData', {
        'pageNum': param.pageNum,
        'pageSize': param.pageSize
    }, function (data) {
        if (data.state) {
            createPage(data);
            outputHtml(data);
        }
    })
}

/**
 * 分页
 * @param data
 */
function createPage(data) {
    UIkit.pagination('.uk-pagination', {
        items: data.paginationData.totalDatas,
        itemsOnPage: data.paginationData.pageSize
    });
}

/**
 * 分页回调
 */
$('.uk-pagination').on('select.uk.pagination', function (e, pageIndex) {
    param.pageNum = pageIndex + 1;
    action();
});

$(document).ready(function () {
    action();
});

/**
 * 开始填报
 * @param obj
 */
function startAP(obj) {
    var id = $(obj).attr('data-id');
    window.location.href = web_path + '/semi/autonomicpractice/autonomicPracticeCount?id=' + id;
}