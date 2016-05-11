/**
 * Created by lenovo on 2016-05-07.
 */
/**
 * 计算全部
 */
function countAll() {
    var yearCount = 0;
    for (var i = 0; i < $('.yearCount').length; i++) {
        yearCount = yearCount + Number($($('.yearCount')[i]).text());
    }

    var autonomousPracticeCount = 0;
    for (var i = 0; i < $('.autonomousPracticeCount').length; i++) {
        autonomousPracticeCount = autonomousPracticeCount + Number($($('.autonomousPracticeCount')[i]).text());
    }

    var autonomousPracticeCountNo = 0;
    for (var i = 0; i < $('.autonomousPracticeCountNo').length; i++) {
        autonomousPracticeCountNo = autonomousPracticeCountNo + Number($($('.autonomousPracticeCountNo')[i]).text());
    }

    $('#yearCount').text(yearCount + '人');
    $('#autonomousPracticeCount').text(autonomousPracticeCount + '人');
    $('#autonomousPracticeCountNo').text(autonomousPracticeCountNo + '人');
}

/**
 * 初始化导航
 */
function initSubNav(){
    $('#subNavData').append(
        $('<li class="uk-active">').append($('<a>').attr('href',web_path+"/semi/autonomicpractice/dataAnalysis").text(autonomousPracticeParam.autonomousPracticeTitle))
    );
}

/**
 * 查看人员名单
 * @param obj
 */
function scanStudent(obj) {
    var year = $($(obj).parent().children()[0]).val();
    autonomousPracticeParam.year = year;
    autonomousPracticeParam.studentNumber = '';
    autonomousPracticeParam.type = 0;
    window.location.href = web_path + '/semi/autonomicpractice/autonomicPracticeStudentInfoInYear?autonomousPracticeParam=' + JSON.stringify(autonomousPracticeParam);
}

/**
 * 查看各专业情况
 * @param obj
 */
function scanMajor(obj){
    var year = $($(obj).parent().children()[0]).val();
    autonomousPracticeParam.year = year;
    window.location.href = web_path + '/semi/autonomicpractice/autonomicPracticeInMajorCount?autonomousPracticeParam=' + JSON.stringify(autonomousPracticeParam);
}

$(document).ready(function () {
    countAll();
    initSubNav();
});