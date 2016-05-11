/**
 * Created by lenovo on 2016-05-07.
 */
function outputHtml(data) {

    var havePayStudent = data.single.havePayStudent;
    $('#tableHavePayData').empty();
    for (var i = 0; i < havePayStudent.length; i++) {
        $('#tableHavePayData').append($('<div class="uk-panel uk-panel-divider">').append(
            $('<ul class="uk-grid uk-grid-width-1-1 uk-grid-width-medium-1-2 uk-grid-width-large-1-6">')
                .append($('<li>').text('学号:'))
                .append($('<li>').text(havePayStudent[i].studentNumber))
                .append($('<li>').text('姓名:'))
                .append($('<li>').text(havePayStudent[i].studentName))
                .append($('<li>').text('班级:'))
                .append($('<li>').text(havePayStudent[i].gradeName))
        ));
    }

    var haveNoPayStudent = data.single.haveNoPayStudent;
    $('#tableHaveNoPayData').empty();
    for (var i = 0; i < haveNoPayStudent.length; i++) {
        $('#tableHaveNoPayData').append($('<div class="uk-panel uk-panel-divider">').append(
            $('<ul class="uk-grid uk-grid-width-1-1 uk-grid-width-medium-1-2 uk-grid-width-large-1-6">')
                .append($('<li>').text('学号:'))
                .append($('<li>').text(haveNoPayStudent[i].studentNumber))
                .append($('<li>').text('姓名:'))
                .append($('<li>').text(haveNoPayStudent[i].studentName))
                .append($('<li>').text('班级:'))
                .append($('<li>').text(haveNoPayStudent[i].gradeName))
        ));
    }

}

function havePaySearch(){
    autonomousPracticeParam.type = 0;
    autonomousPracticeParam.studentNumber = $('#searchHavePayStudentNumber').val().trim();
    window.location.href = web_path + '/semi/autonomicpractice/autonomicPracticeStudentInfoInYear?autonomousPracticeParam='+JSON.stringify(autonomousPracticeParam);
}

function haveNoPaySearch(){
    autonomousPracticeParam.type = 1;
    autonomousPracticeParam.studentNumber = $('#searchHaveNoPayContent').val().trim();
    window.location.href = web_path + '/semi/autonomicpractice/autonomicPracticeStudentInfoInYear?autonomousPracticeParam='+JSON.stringify(autonomousPracticeParam);
}

function initSearch() {
    if (autonomousPracticeParam.type == 0) {
        $('#searchHavePayStudentNumber').val(autonomousPracticeParam.studentNumber);
        $('#havePayBar').removeClass('uk-active').addClass('uk-active');
        $('#havePay').removeClass('uk-active').addClass('uk-active');
        $('#haveNoPayBar').removeClass('uk-active');
        $('#haveNoPay').removeClass('uk-active');
    } else if (autonomousPracticeParam.type == 1) {
        $('#searchHaveNoPayContent').val(autonomousPracticeParam.studentNumber);
        $('#haveNoPayBar').removeClass('uk-active').addClass('uk-active');
        $('#haveNoPay').removeClass('uk-active').addClass('uk-active');
        $('#havePayBar').removeClass('uk-active');
        $('#havePay').removeClass('uk-active');
    }
}

function action() {
    $.post(web_path + '/semi/autonomicpractice/autonomicPracticeStudentInfoInYearData', autonomousPracticeParam,
        function (data) {
            outputHtml(data);
            autonomousPracticeParam = data.single.autonomousPracticeParam;
            createPage(data);
            initSearch();
        }, 'json');
}

function createPage(data) {

    if (data.single.havePayStudent.length > 0) {
        UIkit.pagination('#havePayPagination', {
            items: autonomousPracticeParam.havePayTotalData,
            itemsOnPage: autonomousPracticeParam.havePayPageSize,
            currentPage: autonomousPracticeParam.havePayPageNum,
            edges: 2
        });
    }

    if (data.single.haveNoPayStudent.length > 0) {
        UIkit.pagination('#haveNoPayPagination', {
            items: autonomousPracticeParam.haveNoPayTotalData,
            itemsOnPage: autonomousPracticeParam.haveNoPayPageSize,
            currentPage: autonomousPracticeParam.haveNoPayPageNum,
            edges: 2
        });
    }
}

/**
 * 点击分页
 */
$('#havePayPagination').on('select.uk.pagination', function (e, pageIndex) {
    autonomousPracticeParam.havePayPageNum = pageIndex + 1;
    action();
});

/**
 * 点击分页
 */
$('#haveNoPayPagination').on('select.uk.pagination', function (e, pageIndex) {
    autonomousPracticeParam.haveNoPayPageNum = pageIndex + 1;
    action();
});

/**
 * 初始化导航
 */
function initSubNav() {
    $('#subNavData').append(
        $('<li>').append($('<a>').attr('href', web_path + "/semi/autonomicpractice/dataAnalysis").text(autonomousPracticeParam.autonomousPracticeTitle))
    );

    $('#subNavData').append(
        $('<li class="uk-active">').append($('<a>').attr('href', web_path + "/semi/autonomicpractice/autonomicPracticeCount?autonomousPracticeParam=" + JSON.stringify(autonomousPracticeParam)).text(autonomousPracticeParam.year))
    );
}

$(document).ready(function () {
    action();
    initSubNav();
});