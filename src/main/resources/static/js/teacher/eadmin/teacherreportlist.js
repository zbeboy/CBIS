/**
 * Created by lenovo on 2016-06-03.
 */
/**
 * 输出列表
 * @param data
 */
function outputHtml(data) {
    $('#dataShow').empty();
    for (var i = 0; i < data.result.length; i++) {
        if (data.result[i].ok) {
            $('#dataShow').append($('<div class="uk-panel uk-panel-box">').append(
                $('<h3 class="uk-panel-title">').text(data.result[i].title)
                )
                .append($('<ul class="uk-grid uk-grid-width-1-1 uk-grid-width-medium-1-2 uk-grid-width-large-1-2">')
                    .append($('<li>').text('作者:' + data.result[i].realName))
                    .append($('<li>').text('使用模板:' + data.result[i].templateTitle))
                    .append($('<li>').text('开始时间:' + data.result[i].startTimeString))
                    .append($('<li>').text('结束时间:' + data.result[i].endTimeString))
                )
                .append($('<p>').text('教学任务书:' + data.result[i].teachTaskTitle))
                .append($('<div class="uk-text-right uk-margin" >').append($('<button class="uk-button uk-button-primary" data-id="' + data.result[i].id + '" data-templateid="' + data.result[i].templateId + '" type="button" onclick="startAP(this);" >').text('进入填报')))
            );
        } else {
            $('#dataShow').append($('<div class="uk-panel uk-panel-box">').append(
                $('<h3 class="uk-panel-title">').text(data.result[i].title)
                )
                .append($('<ul class="uk-grid uk-grid-width-1-1 uk-grid-width-medium-1-2 uk-grid-width-large-1-2">')
                    .append($('<li>').text('作者:' + data.result[i].realName))
                    .append($('<li>').text('使用模板:' + data.result[i].templateTitle))
                    .append($('<li>').text('开始时间:' + data.result[i].startTimeString))
                    .append($('<li>').text('结束时间:' + data.result[i].endTimeString))
                )
                .append($('<p>').text('教学任务书:' + data.result[i].teachTaskTitle))
            );
        }

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
    var index = layer.load(1, {
        shade: [0.1,'#fff'] //0.1透明度的白色背景
    });
    $.post(web_path + '/teacher/eadmin/teacherReportData', {
        'pageNum': param.pageNum,
        'pageSize': param.pageSize,
        'teachType':teachType
    }, function (data) {
        layer.close(index);
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
    var templateId = $(obj).attr('data-templateid');
    var teacherTaskInfoId = $(obj).attr('data-id');
    window.location.href = web_path + '/teacher/eadmin/teacherFillTaskContentList?templateId=' + templateId + "&teacherTaskInfoId="+teacherTaskInfoId+"&teachType="+teachType;
}