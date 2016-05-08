/**
 * Created by lenovo on 2016-05-08.
 */
/**
 * 查看人员名单
 * @param obj
 */
function scanStudent(obj) {
    var gradeId = $($(obj).parent().children()[0]).val();
    var autonomousPracticeInfoId = $($(obj).parent().children()[1]).val();
    var year = $($(obj).parent().children()[2]).val();
    window.location.href = web_path + '/semi/autonomicpractice/autonomicPracticeStudentInfoInGrade?id=' + autonomousPracticeInfoId + "&gradeId=" + gradeId+"&year="+year;
}