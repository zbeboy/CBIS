/**
 * Created by lenovo on 2016-04-22.
 */
function outputHtml(data) {
    $('#dataShow').empty();
    for (var i = 0; i < data.result.length; i++) {
        if (data.result[i].ok) {
            $('#dataShow').append($('<div class="uk-panel uk-panel-box">').append(
                $('<h3 class="uk-panel-title">').text(data.result[i].autonomousPracticeTitle)
                )
                .append($('<input type="hidden" class="autonomousPracticeInfoId">').val(data.result[i].id))
                .append($('<ul class="uk-grid uk-grid-width-1-1 uk-grid-width-medium-1-2 uk-grid-width-large-1-2">')
                    .append($('<li>').text('作者:' + data.result[i].realName))
                    .append($('<li>').text('使用模板:' + data.result[i].autonomousPracticeTemplateTitle))
                    .append($('<li>').text('开始时间:' + data.result[i].startTimeString))
                    .append($('<li>').text('结束时间:' + data.result[i].endTimeString))
                )
                .append($('<p>').text('允许填报年级:' + data.result[i].gradeYear))
                .append($('<div class="uk-text-right uk-margin" >').append($('<button class="uk-button uk-button-primary" data-id="' + data.result[i].autonomousPracticeTemplateId + '" type="button" onclick="startAP(this);" >').text('进入填报')))
            );
        } else {
            $('#dataShow').append($('<div class="uk-panel uk-panel-box">').append(
                $('<h3 class="uk-panel-title">').text(data.result[i].autonomousPracticeTitle)
                )
                .append($('<input type="hidden" class="autonomousPracticeInfoId">').val(data.result[i].id))
                .append($('<ul class="uk-grid uk-grid-width-1-1 uk-grid-width-medium-1-2 uk-grid-width-large-1-2">')
                    .append($('<li>').text('作者:' + data.result[i].realName))
                    .append($('<li>').text('使用模板:' + data.result[i].autonomousPracticeTemplateTitle))
                    .append($('<li>').text('开始时间:' + data.result[i].startTimeString))
                    .append($('<li>').text('结束时间:' + data.result[i].endTimeString))
                )
                .append($('<p>').text('允许填报年级:' + data.result[i].gradeYear))
            );
        }

    }
}

var param = {
    pageNum: 0,
    pageSize: 5
}

function action() {
    $.post(web_path + '/student/autonomicpractice/autonomicPracticeData', {
        'pageNum': param.pageNum,
        'pageSize': param.pageSize
    }, function (data) {
        if (data.state) {
            createPage(data);
            outputHtml(data);
        }
    })
}

function createPage(data) {
    UIkit.pagination('.uk-pagination', {
        items: data.paginationData.totalDatas,
        itemsOnPage: data.paginationData.pageSize
    });
}

$('.uk-pagination').on('select.uk.pagination', function (e, pageIndex) {
    param.pageNum = pageIndex + 1;
    action();
});

$(document).ready(function () {
    action();
});

function startAP(obj) {
    var id = $(obj).attr('data-id');
    window.location.href = web_path + '/student/autonomicpractice/autonomicPracticeAdd?id=' + id;
}