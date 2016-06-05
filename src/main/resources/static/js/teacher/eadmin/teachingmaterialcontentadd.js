/**
 * Created by lenovo on 2016-06-05.
 */

/**
 * 只读文件
 * @param title
 * @param title_v
 * @param content
 * @returns {string}
 */
function readOnlyHtml(title,title_v,content){
    var s = ' <div class="uk-form-row">' +
        '<label class="uk-form-label" for="'+title_v+'">'+title+'</label>' +
        '<div class="uk-form-controls">' +
        '<input readonly="readonly" type="text" id="'+title_v+'" name="'+title_v+'" placeholder="'+title+'" value="'+content+'" />' +
        '</div>' +
        '</div>';
    return s;
}

/**
 * 普通文本
 * @param title
 * @param title_v
 * @param content
 * @returns {string}
 */
function html(title,title_v,content){
    var s = ' <div class="uk-form-row">' +
        '<label class="uk-form-label" for="'+title_v+'">'+title+'</label>' +
        '<div class="uk-form-controls">' +
        '<input type="text" id="'+title_v+'" placeholder="'+title+'" name="'+title_v+'" value="'+content+'" />' +
        '</div>' +
        '</div>';
    return s;
}

/**
 * 空白html
 * @param title
 * @param title_v
 * @returns {string}
 */
function blankHtml(title,title_v){
    var s = ' <div class="uk-form-row">' +
        '<label class="uk-form-label" for="'+title_v+'">'+title+'</label>' +
        '<div class="uk-form-controls">' +
        '<input type="text" id="'+title_v+'" name="'+title_v+'" placeholder="'+title+'" />' +
        '</div>' +
        '</div>';
    return s;
}

function outputHtml(){

    var hasIds = [];//已经填写的id

    for(var i = 0;i<taskContent.length;i++){
        for(var j = 0;j<taskTitle.length;j++){
            if(taskContent[i].teachingMaterialHeadId == taskTitle[j].id && taskTitle[j].isAssignment == 1){
                $('#taskTitle').append(readOnlyHtml(taskTitle[j].title,taskTitle[j].titleVariable,taskContent[i].content));
                hasIds.push(taskTitle[j].id);
                isRight = true;
            } else if(taskContent[i].teachingMaterialHeadId == taskTitle[j].id && taskTitle[j].isAssignment == 0){
                $('#myTitle').append(html(taskTitle[j].title,taskTitle[j].titleVariable,taskContent[i].content));
                hasIds.push(taskTitle[j].id);
                isRight = true;
            }
        }
    }

    for(var j = 0;j<taskTitle.length;j++){
        var has = false;
        for(var i = 0;i<hasIds.length;i++){
            if(taskTitle[j].id == hasIds[i]){
                has = true;
                break;
            }
        }
        if(!has){
            $('#myTitle').append(blankHtml(taskTitle[j].title,taskTitle[j].titleVariable));
        }
    }

}

function submitData(){
    $('#myTitle').submit();
}

function toBack(){
    window.location.href = web_path + "/teacher/eadmin/teachingMaterialContentList?templateId=" + templateId + "&teachingMaterialInfoId="+teachingMaterialInfo.id;
}

$(document).ready(function(){
    outputHtml();
});