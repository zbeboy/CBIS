/**
 * Created by lenovo on 2016-05-08.
 */

/**
 * 初始化导航
 */
function initSubNav() {
    $('#subNavData').append(
        $('<li>').append($('<a>').attr('href', web_path + "/semi/autonomicpractice/dataAnalysis").text(autonomousPracticeParam.autonomousPracticeTitle))
    );
    $('#subNavData').append(
        $('<li class="uk-active">').append($('<a>').attr('href', web_path + "/semi/autonomicpractice/autonomicPracticeCount?autonomousPracticeParam=" + JSON.stringify(autonomousPracticeParam) ).text(autonomousPracticeParam.year))
    );
}

/**
 * 查看人员名单
 * @param obj
 */
function scanStudent(obj) {
    var majorId = $($(obj).parent().children()[0]).val();
    var majorName = $($(obj).parent().children()[1]).val();
    autonomousPracticeParam.majorId = majorId;
    autonomousPracticeParam.majorName = majorName;
    autonomousPracticeParam.studentNumber = '';
    autonomousPracticeParam.type = 0;
    autonomousPracticeParam.havePayPageNum = 0;
    autonomousPracticeParam.havePayPageSize = 20;
    autonomousPracticeParam.havePayTotalData = 0;
    autonomousPracticeParam.haveNoPayPageNum = 0;
    autonomousPracticeParam.haveNoPayPageSize = 20;
    autonomousPracticeParam.haveNoPayTotalData = 0;
    window.location.href = web_path + '/semi/autonomicpractice/autonomicPracticeStudentInfoInMajor?autonomousPracticeParam=' + JSON.stringify(autonomousPracticeParam);
}

/**
 * 查看各班级情况
 * @param obj
 */
function scanGrade(obj){
    var majorId = $($(obj).parent().children()[0]).val();
    var majorName = $($(obj).parent().children()[1]).val();
    autonomousPracticeParam.majorId = majorId;
    autonomousPracticeParam.majorName = majorName;
    window.location.href = web_path + '/semi/autonomicpractice/autonomicPracticeInGradeCount?autonomousPracticeParam=' + JSON.stringify(autonomousPracticeParam);
}

$(document).ready(function(){
    initSubNav();
});