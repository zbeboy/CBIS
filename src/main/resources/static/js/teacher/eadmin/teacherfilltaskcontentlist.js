/**
 * Created by lenovo on 2016-06-03.
 */

function outputHtml() {
    var titleIds = [];//教学任务书标题id
    var firstId = 0;//取得第一个教学任务书id
    var ownTitleIds = [];//自定义标题id
    var c = true;
    if (taskTitle != null) {
        for (var i = 0; i < taskTitle.length; i++) {
            if (taskTitle[i].isAssignment == 1) {
                //输出教学任务书标题
                $('#titleData').append($('<th>').text(taskTitle[i].title));
                titleIds.push(taskTitle[i].id);
                if (c) {
                    firstId = taskTitle[i].id;
                    c = false;
                }
            }
        }

        for (var i = 0; i < taskTitle.length; i++) {
            if (taskTitle[i].isAssignment == 0) {
                //输出自定义标题
                $('#titleData').append($('<th>').text(taskTitle[i].title));
                //titleIds.push(taskTitle[i].id);
                ownTitleIds.push(taskTitle[i].id);
            }
        }

        //操作
        $('#titleData').append($('<th>').text('操作'));
    }

    if (taskContent != null && taskContent.length > 0) {
        if (firstId != 0) {
            var tempContent = [];
            for (var i = 0; i < taskContent.length; i++) {
                if (taskContent[i].teacherFillTaskHeadId == firstId) {
                    tempContent.push(taskContent[i]);
                }
            }

            tempContent = sortContent(tempContent);//进行一个行的排序

            var contentX = []; //每行的行序
            var cell = 0;
            //输出第一列
            for (var i = 0; i < tempContent.length; i++) {
                $('#contentData').append($('<tr>').append($('<td>').text(tempContent[i].content)));
                cell++;
                contentX.push(tempContent[i].contentX);
            }

            var cd = $('#contentData').children();//取得所有子节点，输出其它标题列
            for (var i = 0; i < titleIds.length; i++) {
                var tempId = 0;
                if (titleIds[i] != firstId) {

                    var tempContent = [];
                    for (var j = 0; j < taskContent.length; j++) {
                        if (taskContent[j].teacherFillTaskHeadId == titleIds[i]) {
                            tempContent.push(taskContent[j]);
                        }
                    }
                    tempContent = sortContent(tempContent);//进行一个行的排序

                    for (var k = 0; k < cell; k++) {
                        $(cd[k]).append($('<td>').text(tempContent[k].content));
                    }

                }
            }

            //输出自定义列
            for (var k = 0; k < ownTitleIds.length; k++) {
                for (var i = 0; i < cell; i++) {
                    var hasR = false;//是否有填写
                    for (var j = 0; j < taskContent.length; j++) {
                        if (taskContent[j].teacherFillTaskHeadId == ownTitleIds[k] && taskContent[j].contentX == contentX[i]) {//填写所在行
                            $(cd[i]).append($('<td>').text(taskContent[j].content));
                            hasR = true;
                            break;
                        }
                    }
                    if (!hasR) {
                        $(cd[i]).append($('<td>'));
                    }
                }
            }

            //输出自定义列
            //for (var i = 0; i < cell; i++) {
            //
            //    var hasR = false;//是否有填写
            //    for (var k = 0; k < ownTitleIds; k++) {
            //        for (var j = 0; j < taskContent.length; j++) {
            //            if (taskContent[j].teacherFillTaskHeadId == ownTitleIds[k] && taskContent[j].contentX == contentX[i]) {//填写所在行
            //                $(cd[i]).append($('<td>').text(taskContent[j].content));
            //                hasR = true;
            //                break;
            //            }
            //        }
            //    }
            //
            //    if(!hasR){
            //        $(cd[i]).append($('<td>'));
            //    }
            //}

            //输出操作列
            for (var i = 0; i < cell; i++) {
                var s = '<a href="javascript:;" onclick="toReport(' + contentX[i] + ',' + templateId + ','+teacherFillTaskInfo.id+');" >填报</a>';
                $(cd[i]).append($('<td>').html(s));
            }
        }

    }

}

function toReport(x, id,teacherTaskInfoId) {
    window.location.href = web_path + '/teacher/eadmin/teacherFillTaskContentAdd?x=' + x + "&templateId=" + id + "&teacherTaskInfoId="+teacherTaskInfoId;
}

function sortContent(arr) {
    for (var i = 0; i < arr.length; i++) {
        for (var j = i + 1; j < arr.length; j++) {
            if (arr[i].contentX > arr[j].contentX) {
                var temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
    }
    return arr;
}

$(document).ready(function () {
    outputHtml();
});