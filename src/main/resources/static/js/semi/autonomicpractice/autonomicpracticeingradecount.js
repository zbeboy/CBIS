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
        $('<li>').append($('<a>').attr('href', web_path + "/semi/autonomicpractice/autonomicPracticeCount?autonomousPracticeParam="+JSON.stringify(autonomousPracticeParam)).text(autonomousPracticeParam.year))
    );

    $('#subNavData').append(
        $('<li class="uk-active">').append($('<a>').attr('href', web_path + "/semi/autonomicpractice/autonomicPracticeInMajorCount?autonomousPracticeParam=" + JSON.stringify(autonomousPracticeParam) ).text(autonomousPracticeParam.majorName))
    );
}

/**
 * 查看人员名单
 * @param obj
 */
function scanStudent(obj) {
    var gradeId = $($(obj).parent().children()[0]).val();
    var gradeName = $($(obj).parent().children()[1]).val();
    autonomousPracticeParam.gradeId = gradeId;
    autonomousPracticeParam.gradeName = gradeName;
    autonomousPracticeParam.studentNumber = '';
    autonomousPracticeParam.type = 0;
    autonomousPracticeParam.havePayPageNum = 0;
    autonomousPracticeParam.havePayPageSize = 1;
    autonomousPracticeParam.havePayTotalData = 0;
    autonomousPracticeParam.haveNoPayPageNum = 0;
    autonomousPracticeParam.haveNoPayPageSize = 1;
    autonomousPracticeParam.haveNoPayTotalData = 0;
    window.location.href = web_path + '/semi/autonomicpractice/autonomicPracticeStudentInfoInGrade?autonomousPracticeParam=' + JSON.stringify(autonomousPracticeParam);
}

$(document).ready(function(){
    initSubNav();
});