/**
 * Created by lenovo on 2016-06-01.
 */

var minX = 0;//最小行数
var maxX = 0;//最大行数
function outputHtml() {
    initParam();
    titleSort();//排序标题
    for (var i = 0; i < teachTaskTitles.length; i++) {
        //输出标题
        $('#titleData').append($('<th>').text(teachTaskTitles[i].title));
    }

    for(var i = minX;i<=maxX;i++){
        var arr = [];
        for(var j = 0;j<teachTaskContents.length;j++){
            if(teachTaskContents[j].contentX == i){
                arr.push(teachTaskContents[j]);
            }
        }

        var s = '';
        arr = contentSort(arr);
        for(var k = 0;k<arr.length;k++){
            s = s + '<td>'+arr[k].content+"</td>";
        }

        $('#contentData').append(
            $('<tr>').append(s)
        );
    }

}

function initParam() {
    if (teachTaskContents != null && teachTaskContents.length > 0) {
        minX = teachTaskContents[0].contentX;
        maxX = teachTaskContents[0].contentX;
        for (var i = 0; i < teachTaskContents.length; i++) {
            if (teachTaskContents[i].contentX < minX) {
                minX = teachTaskContents[i].contentX;
            }

            if (teachTaskContents[i].contentX > maxX) {
                maxX = teachTaskContents[i].contentX;
            }
        }
    }

    console.log(" minX :  " + minX);
    console.log(" maxX : " + maxX);
}

function titleSort(){
    for(var i = 0;i<teachTaskTitles.length;i++){
        for(var j = i+1;j<teachTaskTitles.length;j++){
            if(teachTaskTitles[i].titleY>teachTaskTitles[j].titleY){
                var temp = teachTaskTitles[j];
                teachTaskTitles[j] = teachTaskTitles[i];
                teachTaskTitles[i] = temp;
            }
        }
    }
}

function contentSort(arr){
    for(var i = 0;i<arr.length;i++){
        for(var j = i+1;j<arr.length;j++){
            if(arr[i].contentY>arr[j].contentY){
                var temp = arr[j];
                arr[j] = arr[i];
                arr[i] = temp;
            }
        }
    }
    return arr;
}

$(document).ready(function(){
   outputHtml();
});
