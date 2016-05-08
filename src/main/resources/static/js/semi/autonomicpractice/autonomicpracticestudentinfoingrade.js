/**
 * Created by lenovo on 2016-05-08.
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

var param = {
    'id': autonomicPracticeStudentInfoInGradeVo.id,
    'gradeId': autonomicPracticeStudentInfoInGradeVo.gradeId,
    'year': autonomicPracticeStudentInfoInGradeVo.year,
    'type': autonomicPracticeStudentInfoInGradeVo.type,
    'studentNumber': autonomicPracticeStudentInfoInGradeVo.studentNumber,
    'havePayPageNum': 0,
    'havePayPageSize': 20,
    'havePayTotalData': 0,
    'haveNoPayPageNum': 0,
    'haveNoPayPageSize': 20,
    'haveNoPayTotalData': 0
}

function initSearch() {
    if (param.type == 0) {
        $('#searchHavePayStudentNumber').val(param.studentNumber);
        $('#havePayBar').removeClass('uk-active').addClass('uk-active');
        $('#havePay').removeClass('uk-active').addClass('uk-active');
        $('#haveNoPayBar').removeClass('uk-active');
        $('#haveNoPay').removeClass('uk-active');
    } else if (param.type == 1) {
        $('#searchHaveNoPayContent').val(param.studentNumber);
        $('#haveNoPayBar').removeClass('uk-active').addClass('uk-active');
        $('#haveNoPay').removeClass('uk-active').addClass('uk-active');
        $('#havePayBar').removeClass('uk-active');
        $('#havePay').removeClass('uk-active');
    }
}

function action() {
    $.post(web_path + '/semi/autonomicpractice/autonomicPracticeStudentInfoInGradeData', param,
        function (data) {
            outputHtml(data);
            param = data.single.autonomicPracticeStudentInfoInGradeVo;
            createPage(data);
            initSearch();
        }, 'json');
}

function createPage(data) {
    if (data.single.havePayStudent.length > 0) {
        UIkit.pagination('#havePayPagination', {
            items: param.havePayTotalData,
            itemsOnPage: param.havePayPageSize,
            currentPage: param.havePayPageNum,
            edges: 2
        });
    }

    if (data.single.haveNoPayStudent.length > 0) {
        UIkit.pagination('#haveNoPayPagination', {
            items: param.haveNoPayTotalData,
            itemsOnPage: param.haveNoPayPageSize,
            currentPage: param.haveNoPayPageNum,
            edges: 2
        });
    }

}

/**
 * 点击分页
 */
$('#havePayPagination').on('select.uk.pagination', function (e, pageIndex) {
    param.havePayPageNum = pageIndex + 1;
    action();
});

/**
 * 点击分页
 */
$('#haveNoPayPagination').on('select.uk.pagination', function (e, pageIndex) {
    param.haveNoPayPageNum = pageIndex + 1;
    action();
});

$(document).ready(function () {
    action();
});