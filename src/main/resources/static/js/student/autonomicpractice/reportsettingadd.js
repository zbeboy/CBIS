/**
 * Created by lenovo on 2016-04-12.
 */
function submitAutonomicPracticeData(){
    var n = $("input[name='gradeYear']:checked").val();
    if (n == undefined) {
        layer.msg('请选择一个年级!');
    } else {
        $('#addAutonomicPracticeForm').submit();
    }
}

/**
 * 返回
 */
function back(){
    window.location.href = web_path + '/administrator/autonomicpractice/reportsettingList';
}