/**
 * Created by lenovo on 2016-05-07.
 */

var tieId = 0;//系id 为后面查询使用

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
            .append($('<div class="uk-text-right uk-margin" >').append($('<button class="uk-button" data-year="'+data.result[i].gradeYear+'" data-title="'+data.result[i].autonomousPracticeTitle+'"  data-id="' + data.result[i].id + '" type="button" onclick="startAP(this);" >').text('查看统计')))
        );
    }
    tieId = data.obj;
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
    $.post(web_path + '/semi/autonomicpractice/dataAnalysisData', {
        'pageNum': param.pageNum,
        'pageSize': param.pageSize
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
 * 层级参数 全局配置
 * @type {{autonomousPracticeInfoId: number, autonomousPracticeTitle: string, year: string, majorId: number, majorName: string, gradeId: number, gradeName: number}}
 */
var autonomousPracticeParam = {
    'autonomousPracticeInfoId':0,//自主实习信息表id
    'autonomousPracticeTitle':'',//自主实习信息表标题
    'tieId':0,//系id
    'year':'',//年级
    'majorId':0,//专业id
    'majorName':'',//专业名
    'gradeId':0,//班级id
    'gradeName':0,//班级名
    'gradeYear':'',//允许填报的年级
    'type':0,//0是已提交 1未提交
    'studentNumber':'',//搜索用
    'havePayPageNum':0,//提交列表中
    'havePayPageSize':20,//提交列表中
    'havePayTotalData':0,//提交列表中
    'haveNoPayPageNum':0,//未提交列表中
    'haveNoPayPageSize':20,//未提交列表中
    'haveNoPayTotalData':0//未提交列表中
}

/**
 * 开始
 * @param obj
 */
function startAP(obj) {
    var id = $(obj).attr('data-id');
    var title = $(obj).attr('data-title');
    var gradeYear = $(obj).attr('data-year');
    autonomousPracticeParam.autonomousPracticeInfoId = id;
    autonomousPracticeParam.tieId = tieId;
    autonomousPracticeParam.autonomousPracticeTitle = title;
    autonomousPracticeParam.gradeYear = gradeYear;
    window.location.href = web_path + '/semi/autonomicpractice/autonomicPracticeCount?autonomousPracticeParam=' + JSON.stringify(autonomousPracticeParam);
}