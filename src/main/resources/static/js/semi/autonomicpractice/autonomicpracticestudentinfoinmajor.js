/**
 * Created by lenovo on 2016-05-08.
 */

/*
 前端分页参数
 */
var pagingParam = {
    'havePayPageNum':1,
    'havePayPageSize':autonomousPracticeParam.havePayPageSize,
    'haveNoPayPageNum':1,
    'haveNoPayPageSize':autonomousPracticeParam.haveNoPayPageSize,
    'havePayStudent':[],//数据
    'haveNoPayStudent':[]//数据
}

/**
 * 输出已提交界面数据
 */
function outputHavePayHtml(){
    $('#tableHavePayData').empty();
    var dataPayLength = 0;

    if((pagingParam.havePayPageNum-1)*pagingParam.havePayPageSize+pagingParam.havePayPageSize>pagingParam.havePayStudent.length){
        dataPayLength = pagingParam.havePayStudent.length;
    } else {
        dataPayLength = (pagingParam.havePayPageNum-1)*pagingParam.havePayPageSize+pagingParam.havePayPageSize;
    }

    for (var i = (pagingParam.havePayPageNum-1)*pagingParam.havePayPageSize; i < dataPayLength; i++) {
        $('#tableHavePayData').append($('<div class="uk-panel uk-panel-divider">').append(
            $('<ul class="uk-grid uk-grid-width-1-1 uk-grid-width-medium-1-2 uk-grid-width-large-1-6">')
                .append($('<li>').text('学号:'))
                .append($('<li>').text(dealNull(pagingParam.havePayStudent[i].studentNumber)))
                .append($('<li>').text('姓名:'))
                .append($('<li>').text(dealNull(pagingParam.havePayStudent[i].studentName)))
                .append($('<li>').text('班级:'))
                .append($('<li>').text(dealNull(pagingParam.havePayStudent[i].gradeName)))
        ));
    }
}

/**
 * 处理空值
 * @param obj
 */
function dealNull(obj){
    if(obj == null){
        return '';
    } else {
        return obj;
    }
}

/**
 * 输出未提交界面数据
 */
function outputHaveNoPayHtml() {
    $('#tableHaveNoPayData').empty();
    var dataNoPayLength = 0
    if((pagingParam.haveNoPayPageNum - 1)*pagingParam.haveNoPayPageSize+pagingParam.haveNoPayPageSize>pagingParam.haveNoPayStudent.length){
        dataNoPayLength = pagingParam.haveNoPayStudent.length;
    } else {
        dataNoPayLength = (pagingParam.haveNoPayPageNum - 1)*pagingParam.haveNoPayPageSize+pagingParam.haveNoPayPageSize;
    }
    $('#tableHaveNoPayData').empty();
    for (var i = (pagingParam.haveNoPayPageNum - 1)*pagingParam.haveNoPayPageSize; i < dataNoPayLength; i++) {
        $('#tableHaveNoPayData').append($('<div class="uk-panel uk-panel-divider">').append(
            $('<ul class="uk-grid uk-grid-width-1-1 uk-grid-width-medium-1-2 uk-grid-width-large-1-6">')
                .append($('<li>').text('学号:'))
                .append($('<li>').text(dealNull(pagingParam.haveNoPayStudent[i].studentNumber)))
                .append($('<li>').text('姓名:'))
                .append($('<li>').text(dealNull(pagingParam.haveNoPayStudent[i].studentName)))
                .append($('<li>').text('班级:'))
                .append($('<li>').text(dealNull(pagingParam.haveNoPayStudent[i].gradeName)))
        ));
    }

}

/**
 * 初始化搜索
 */
function initSearch(){
    if(autonomousPracticeParam.type == 0){
        $('#searchHavePayStudentNumber').val(autonomousPracticeParam.studentNumber);
        $('#havePayBar').removeClass('uk-active').addClass('uk-active');
        $('#havePay').removeClass('uk-active').addClass('uk-active');
        $('#haveNoPayBar').removeClass('uk-active');
        $('#haveNoPay').removeClass('uk-active');
    } else if(autonomousPracticeParam.type == 1){
        $('#searchHaveNoPayContent').val(autonomousPracticeParam.studentNumber);
        $('#haveNoPayBar').removeClass('uk-active').addClass('uk-active');
        $('#haveNoPay').removeClass('uk-active').addClass('uk-active');
        $('#havePayBar').removeClass('uk-active');
        $('#havePay').removeClass('uk-active');
    }
}

/**
 * 已提交界面搜索
 */
function havePaySearch(){
    autonomousPracticeParam.type = 0;
    autonomousPracticeParam.studentNumber = $('#searchHavePayStudentNumber').val().trim();
    window.location.href = web_path + '/semi/autonomicpractice/autonomicPracticeStudentInfoInMajor?autonomousPracticeParam='+JSON.stringify(autonomousPracticeParam);
}

/**
 * 重置已提交界面搜索
 */
function refreshHavePaySearch(){
    autonomousPracticeParam.type = 0;
    autonomousPracticeParam.studentNumber = '';
    window.location.href = web_path + '/semi/autonomicpractice/autonomicPracticeStudentInfoInMajor?autonomousPracticeParam='+JSON.stringify(autonomousPracticeParam);
}

/**
 * 未提交界面搜索
 */
function haveNoPaySearch(){
    autonomousPracticeParam.type = 1;
    autonomousPracticeParam.studentNumber = $('#searchHaveNoPayContent').val().trim();
    window.location.href = web_path + '/semi/autonomicpractice/autonomicPracticeStudentInfoInMajor?autonomousPracticeParam='+JSON.stringify(autonomousPracticeParam);
}

/**
 * 重置未提交界面搜索
 */
function refreshHaveNoPaySearch(){
    autonomousPracticeParam.type = 1;
    autonomousPracticeParam.studentNumber = '';
    window.location.href = web_path + '/semi/autonomicpractice/autonomicPracticeStudentInfoInMajor?autonomousPracticeParam='+JSON.stringify(autonomousPracticeParam);
}

/**
 * 执行入口
 */
function action() {
    $.post(web_path + '/semi/autonomicpractice/autonomicPracticeStudentInfoInMajorData', autonomousPracticeParam,
        function (data) {
            pagingParam.havePayStudent = data.single.havePayStudent;
            pagingParam.haveNoPayStudent = data.single.haveNoPayStudent;
            outputHavePayHtml();
            outputHaveNoPayHtml();
            autonomousPracticeParam = data.single.autonomousPracticeParam;
            createPage(data);
            initSearch();
        }, 'json');
}

/**
 * 导出数据
 */
function exportData(obj){
    var exportType = $(obj).prev().val();
    var jsonData = $(obj).prev().prev();
    var exportForm = $(obj).parent().parent().parent();
    if(Number(exportType) == 0){
        jsonData.val(JSON.stringify(pagingParam.havePayStudent));
    } else if(Number(exportType) == 1) {
        jsonData.val(JSON.stringify(pagingParam.haveNoPayStudent));
    }
    exportForm.submit();
}

/**
 * 创建分页
 * @param data
 */
function createPage(data){
    if(data.single.havePayStudent.length>0){
        UIkit.pagination('#havePayPagination', {
            items: autonomousPracticeParam.havePayTotalData,
            itemsOnPage: autonomousPracticeParam.havePayPageSize,
            currentPage: autonomousPracticeParam.havePayPageNum,
            edges:2
        });
    }

    if(data.single.haveNoPayStudent.length>0){
        UIkit.pagination('#haveNoPayPagination', {
            items: autonomousPracticeParam.haveNoPayTotalData,
            itemsOnPage: autonomousPracticeParam.haveNoPayPageSize,
            currentPage: autonomousPracticeParam.haveNoPayPageNum,
            edges:2
        });
    }
}

/**
 * 点击分页
 */
$('#havePayPagination').on('select.uk.pagination', function (e, pageIndex) {
    pagingParam.havePayPageNum = pageIndex + 1;
    outputHavePayHtml();
});

/**
 * 点击分页
 */
$('#haveNoPayPagination').on('select.uk.pagination', function (e, pageIndex) {
    pagingParam.haveNoPayPageNum = pageIndex + 1;
    outputHaveNoPayHtml();
});

/**
 * 初始化导航
 */
function initSubNav() {
    $('#subNavData').append(
        $('<li>').append($('<a>').attr('href', web_path + "/semi/autonomicpractice/dataAnalysis").text(autonomousPracticeParam.autonomousPracticeTitle))
    );

    $('#subNavData').append(
        $('<li>').append($('<a>').attr('href', web_path + "/semi/autonomicpractice/autonomicPracticeCount?autonomousPracticeParam="+JSON.stringify(autonomousPracticeParam)).text(autonomousPracticeParam.year))
    );

    $('#subNavData').append(
        $('<li class="uk-active">').append($('<a>').attr('href', web_path + "/semi/autonomicpractice/autonomicPracticeInMajorCount?autonomousPracticeParam=" + JSON.stringify(autonomousPracticeParam) ).text(autonomousPracticeParam.majorName))
    );
}

$(document).ready(function(){
    action();
    initSubNav();
});