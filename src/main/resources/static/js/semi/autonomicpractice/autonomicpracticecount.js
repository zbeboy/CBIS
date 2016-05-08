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
 * 查看人员名单
 * @param obj
 */
function scanStudent(obj) {
    var year = $($(obj).parent().children()[0]).val();
    var autonomousPracticeInfoId = $($(obj).parent().children()[1]).val();
    window.location.href = web_path + '/semi/autonomicpractice/autonomicPracticeStudentInfoInYear?id=' + autonomousPracticeInfoId + "&year=" + year;
}

/**
 * 查看各专业情况
 * @param obj
 */
function scanMajor(obj){
    var year = $($(obj).parent().children()[0]).val();
    var autonomousPracticeInfoId = $($(obj).parent().children()[1]).val();
    window.location.href = web_path + '/semi/autonomicpractice/autonomicPracticeInMajorCount?id=' + autonomousPracticeInfoId + "&year=" + year;
}

$(document).ready(function () {
    countAll();
});