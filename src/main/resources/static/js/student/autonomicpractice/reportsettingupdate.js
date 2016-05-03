/**
 * Created by lenovo on 2016-04-29.
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
 * 处理时间
 */
function dealTime(){
    var st = moment($('#startTime').val()).format("YYYY-MM-DD");
    $('#startTime').val(st);
    var et = moment($('#endTime').val()).format("YYYY-MM-DD");
    $('#endTime').val(et);
}

$(document).ready(function(){
    dealTime();
});