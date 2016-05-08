/**
 * Created by Administrator on 2016/5/5.
 */

var autonomicPracticeTeacherVos = null;
var studentIds = null;
var currentAuthorities = null;
var searchHeads =  null;

function outputHtml() {
    $('#tableData').empty();
    for (var i = 0; i < studentIds.length; i++) {
        var s = '';
        for (var j = 0; j < autonomicPracticeTeacherVos.length; j++) {
            if (studentIds[i] == autonomicPracticeTeacherVos[j].studentId && useTitle(autonomicPracticeTeacherVos[j].authority)) {
                s = s + '<li>' + autonomicPracticeTeacherVos[j].title + "</li>";
                if (autonomicPracticeTeacherVos[j].isDatabase == 1) {//是数据库字段
                    var headContent = autonomicPracticeTeacherVos[j].headContent;
                    var updateContent = autonomicPracticeTeacherVos[j].content==null?'':autonomicPracticeTeacherVos[j].content;

                    var temp = outputFieldHtml(autonomicPracticeTeacherVos[j].typeValue, autonomicPracticeTeacherVos[j].title, autonomicPracticeTeacherVos[j].titleVariable, headContent, updateContent, autonomicPracticeTeacherVos[j].databaseTableField);
                    s = s + '<li>' + temp + '</li>';
                } else {
                    var headContent = autonomicPracticeTeacherVos[j].headContent;
                    var updateContent = autonomicPracticeTeacherVos[j].content==null?'':autonomicPracticeTeacherVos[j].content;
                    var temp = outputFieldHtml(autonomicPracticeTeacherVos[j].typeValue, autonomicPracticeTeacherVos[j].title, autonomicPracticeTeacherVos[j].titleVariable, headContent, updateContent, '');
                    s = s + '<li>' + temp + '</li>';
                }
            }
        }
        s = s + "<li style='display: none' ><input value='"+autonomicPracticeTeacherListVo.autonomousPracticeInfoId+"' name='autonomousPracticeInfoId' /></li> ";
        s = s + "<li style='display: none' ><input value='"+studentIds[i]+"' name='studentId' /></li> ";
        s = '<div class="uk-panel uk-panel-divider">' +
            '<form class="uk-form" method="post" >' +
            '<ul class="uk-grid uk-grid-width-1-1 uk-grid-width-medium-1-2 uk-grid-width-large-1-4">' +
            s +
            '</ul>' +
            ' <div class="uk-text-center uk-margin-top">' +
            '<button type="button" onclick="saveStudent(this);" class="uk-button uk-button-primary">保存</button>' +
            '<span class="uk-float-right"><a href="'+web_path+'/teacher/autonomicpractice/autonomicPracticeTeacherSingle?autonomousPracticeInfoId='+autonomicPracticeTeacherListVo.autonomousPracticeInfoId+'&studentId='+studentIds[i]+'">编辑详情</a></span>' +
            '</div>' +
            '</form>' +
            '</div>';
        $('#tableData').append(s);
    }
}

/**
 * 是否有权限使用该标题
 * @param headsAuthority
 * @returns {boolean}
 */
function useTitle(headsAuthority) {
    var authorities = headsAuthority.split(",");
    var isRight = false;
    if (currentAuthorities != null) {
        for (var i = 0; i < currentAuthorities.length; i++) {
            for (var j = 0; j < authorities.length; j++) {
                if (currentAuthorities[i].authority.trim() === authorities[j].trim()) {
                    isRight = true;
                    break;
                }
            }
            if (isRight) {
                break;
            }
        }
    }
    return isRight;
}

function initSearch(){
    $('#searchHead').empty();
    $('#searchHead').append($('<option value="0">').text('请选择标题'));
    for(var i = 0;i<searchHeads.length;i++){
        if(searchHeads[i].value == autonomicPracticeTeacherListVo.autonomousPracticeHeadId){
            $('#searchHead').append($('<option value="'+searchHeads[i].value+'" selected="selected" >').text(searchHeads[i].text));
        } else {
            $('#searchHead').append($('<option value="'+searchHeads[i].value+'" >').text(searchHeads[i].text));
        }
    }

    $('#searchContent').val((autonomicPracticeTeacherListVo.content==null?'':autonomicPracticeTeacherListVo.content));
    $('#searchAutonomousPracticeInfoId').val(autonomicPracticeTeacherListVo.autonomousPracticeInfoId);
}

function createPage(){
    var pagination = UIkit.pagination('.uk-pagination', {
        items: param.totalData,
        itemsOnPage: param.pageSize,
        currentPage: param.pageNum,
        edges:2
    });
}

var param = {
    'autonomousPracticeInfoId':autonomicPracticeTeacherListVo.autonomousPracticeInfoId,
    'pageNum':autonomicPracticeTeacherListVo.pageNum,
    'pageSize':autonomicPracticeTeacherListVo.pageSize,
    'totalData':autonomicPracticeTeacherListVo.totalData,
    'autonomousPracticeHeadId':autonomicPracticeTeacherListVo.autonomousPracticeHeadId,
    'content':autonomicPracticeTeacherListVo.content
}

function action(){
    $.post(web_path + '/teacher/autonomicpractice/autonomicPracticeTeacherData',param,function(data){
       if(data.state){
           autonomicPracticeTeacherVos = data.single.autonomicPracticeTeacherVos;
           studentIds = data.single.studentIds;
           currentAuthorities = data.single.currentAuthorities;
           searchHeads = data.single.searchHeads;
           param = data.single.autonomicPracticeTeacherListVo;
           outputHtml();
           initSearch();
           if(autonomicPracticeTeacherVos.length>0){
               createPage();
           }
       } else {
           layer.msg(data.msg);
       }
    },'json');
}

/**
 * 点击分页
 */
$('.uk-pagination').on('select.uk.pagination', function (e, pageIndex) {
    param.pageNum = pageIndex + 1;
    action();
});

$(document).ready(function () {
    action();
});

/**
 * 保存学生信息
 * @param obj
 */
function saveStudent(obj){
    var p = $(obj).parent().parent();
    $.post(web_path + '/teacher/autonomicpractice/addAutonomicPracticeTeacherList',p.serialize(),
    function(data){
       layer.msg(data.msg);
    },'json');
    console.log(p.serialize());
}


/**
 * 添加文本
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeText(title, title_variable, content) {
    var HEADTYPE_TEXT = "<input type='text' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' value='" + content + "' />";
    return HEADTYPE_TEXT;
}

/**
 * 添加多文本
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeTextarea(title, title_variable, content) {


    var HEADTYPE_TEXTAREA = "<textarea id='" + title_variable + "' name='" + title_variable + "' cols='78' rows='5' placeholder='" + title + "'>" + content + "</textarea>";
    return HEADTYPE_TEXTAREA;


}

/**
 * 添加select
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeRadio(title, title_variable, headContent, updateContent) {

    var c = headContent.split(",");
    var s = '<option value="" >请选择</option>';
    for (var i = 0; i < c.length; i++) {
        if (c[i] === updateContent) {
            s = s + "<option value='" + c[i] + "' selected='selected' >" + c[i] + "</option>";
        } else {
            s = s + "<option value='" + c[i] + "' >" + c[i] + "</option>";
        }

    }

    var HEADTYPE_RADIO =
        "<select id='" + title_variable + "' name='" + title_variable + "' >" +
        s +
        "</select>";
    return HEADTYPE_RADIO;

}

/**
 * 添加多选
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeCheckbox(title, title_variable, headContent, updateContent) {

    var c = headContent.split(",");
    var d = updateContent.split(",");
    var s = '';
    for (var i = 0; i < c.length; i++) {
        var isChecked = false;
        for (var j = 0; j < d.length; j++) {
            if (c[i] === d[j]) {
                s = s + "<label>" +
                    "<input type='checkbox' value='" + d[j] + "' checked='checked' name='" + title_variable + "' />" +
                    c[i] +
                    "</label><br/>";
                isChecked = true;
                break;
            }
        }

        if (!isChecked) {
            s = s + "<label>" +
                "<input type='checkbox' value='" + c[i] + "' name='" + title_variable + "' />" +
                c[i] +
                "</label><br/>";
        }
    }

    var HEADTYPE_CHECKBOX = s;
    return HEADTYPE_CHECKBOX;

}

/**
 * 添加开关
 * @param title
 * @param title_variable
 * @param headContent
 * @param updateContent
 * @returns {string}
 */
function addHeadTypeSwitch(title, title_variable, headContent, updateContent) {
    var c = headContent.split(",");
    var d = updateContent.split(",");
    var s = '';
    if (c[0] === d[0]) {
        s = s + "<label>" +
            "<input type='checkbox' checked='checked' name='" + title_variable + "' />" +
            c[0] +
            "</label>";
    } else {
        s = s + "<label>" +
            "<input type='checkbox' name='" + title_variable + "' />" +
            c[0] +
            "</label>";
    }
    var HEADTYPE_CHECKBOX = s;
    return HEADTYPE_CHECKBOX;
}

/**
 * 添加密码
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypePassword(title, title_variable, content) {
    var HEADTYPE_PASSWORD =
        "<input type='password' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' value='" + content + "' />";
    return HEADTYPE_PASSWORD;
}

/**
 * 添加日期
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeDate(title, title_variable, content) {
    var HEADTYPE_DATE =
        "<input type='text' readonly='readonly' value='" + content + "' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' data-uk-datepicker='{format:\"YYYY-MM-DD\"}' />";
    return HEADTYPE_DATE;
}

/**
 * 添加时间
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeTime(title, title_variable, content) {
    var HEADTYPE_TIME =
        "<input type='text' readonly='readonly' value='" + content + "' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' data-uk-timepicker='' />";
    return HEADTYPE_TIME;
}

/**
 * 添加邮箱
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeEmail(title, title_variable, content) {
    var HEADTYPE_EMAIL =
        "<input type='email' value='" + content + "' pattern='^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' />"
    return HEADTYPE_EMAIL;

}

/**
 * 添加数字
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeNumber(title, title_variable, content) {

    var HEADTYPE_NUMBER =
        "<input type='number' value='" + content + "' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' />";
    return HEADTYPE_NUMBER;

}

/**
 * 添加手机号
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeMobile(title, title_variable, content) {
    var HEADTYPE_MOBILE =
        "<input type='number' value='" + content + "' pattern='^1\d{10}$' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' />";
    return HEADTYPE_MOBILE;

}

/**
 * 添加电话
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeTelephone(title, title_variable, content) {

    var HEADTYPE_TEL =
        "<input type='text' value='" + content + "' pattern='^0\d{2,3}-?\d{7,8}$' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' />";
    return HEADTYPE_TEL;


}

/**
 * 添加邮编
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypePostcode(title, title_variable, content) {

    var HEADTYPE_POSTCODE =
        "<input type='number' value='" + content + "' pattern='^[1-9][0-9]{5}$' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' />";
    return HEADTYPE_POSTCODE;

}

/**
 * 添加qq
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeQq(title, title_variable, content) {

    var HEADTYPE_QQ =
        "<input type='number' value='" + content + "' pattern='^[1-9]\d{4,15}$' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' />";
    return HEADTYPE_QQ;
}

/**
 * 添加身份证
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeIDCard(title, title_variable, content) {
    var HEADTYPE_ID_card =
        "<input type='text' value='" + content + "' pattern='(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' />";
    return HEADTYPE_ID_card;
}

/**
 * 添加数据库表student性别
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addDatabaseStudentSex(title, title_variable, updateContent) {
    var s = '<option value="" >请选择</option>';
    if (updateContent === '男') {
        s = s + "<option value='" + updateContent + "' selected='selected' >" + updateContent + "</option>";
        s = s + "<option value='女'>女</option>";
    } else if (updateContent === '女') {
        s = s + "<option value='" + updateContent + "' selected='selected' >" + updateContent + "</option>";
        s = s + "<option value='男'>男</option>";
    } else {
        s = s + "<option value='女' >女</option>";
        s = s + "<option value='男'>男</option>";
    }

    var HEADTYPE_RADIO =
        "<select id='" + title_variable + "' name='" + title_variable + "' >" +
        s +
        "</select>";
    return HEADTYPE_RADIO;
}

/**
 * 添加只读文本
 * @param title
 * @param title_variable
 * @param headContent
 * @param updateContent
 * @returns {string}
 */
function addHeadTypeReadonlyText(title, title_variable, updateContent) {

    var HEADTYPE_TEXT =
        "<input type='text' readonly='readonly' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' value='" + updateContent + "' />";
    return HEADTYPE_TEXT;
}

/**
 * 输出标题html
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function outputFieldHtml(headType, title, title_variable, headContent, updateContent, databaseTableField) {
    if (headType === 'text') {
        return addHeadTypeText(title, title_variable, updateContent);
    }

    if (headType === 'textarea') {
        return addHeadTypeTextarea(title, title_variable, updateContent);
    }

    if (headType === 'select') {
        return addHeadTypeRadio(title, title_variable, headContent, updateContent);
    }

    if (headType === 'checkbox') {
        return addHeadTypeCheckbox(title, title_variable, headContent, updateContent);
    }

    if (headType === 'switch') {
        return addHeadTypeSwitch(title, title_variable, headContent, updateContent);
    }

    if (headType === 'password') {
        return addHeadTypePassword(title, title_variable, updateContent);
    }

    if (headType === 'date') {
        return addHeadTypeDate(title, title_variable, updateContent);
    }

    if (headType === 'time') {
        return addHeadTypeTime(title, title_variable, updateContent);
    }

    if (headType === 'email') {
        return addHeadTypeEmail(title, title_variable, updateContent);
    }

    if (headType === 'number') {
        return addHeadTypeNumber(title, title_variable, updateContent);
    }

    if (headType === 'mobile') {
        return addHeadTypeMobile(title, title_variable, updateContent);
    }

    if (headType === 'telephone') {
        return addHeadTypeTelephone(title, title_variable, updateContent);
    }

    if (headType === 'postcode') {
        return addHeadTypePostcode(title, title_variable, updateContent);
    }

    if (headType === 'qq') {
        return addHeadTypeQq(title, title_variable, updateContent);
    }

    if (headType === 'ID_card') {
        return addHeadTypeIDCard(title, title_variable, updateContent);
    }

    if (headType === 'database') {
        if (databaseTableField === 'student_number') {
            return addHeadTypeReadonlyText(title, title_variable, updateContent);
        }

        if (databaseTableField === 'student_name') {
            return addHeadTypeText(title, title_variable, updateContent);
        }

        if (databaseTableField === 'grade_name') {
            return addHeadTypeReadonlyText(title, title_variable, updateContent);
        }

        if (databaseTableField === 'student_phone') {
            return addHeadTypeMobile(title, title_variable, updateContent);
        }

        if (databaseTableField === 'student_email') {
            return addHeadTypeEmail(title, title_variable, updateContent);
        }

        if (databaseTableField === 'student_birthday') {
            return addHeadTypeDate(title, title_variable, updateContent);
        }

        if (databaseTableField === 'student_sex') {
            return addDatabaseStudentSex(title, title_variable, updateContent);
        }

        if (databaseTableField === 'student_identity_card') {
            return addHeadTypeIDCard(title, title_variable, updateContent);
        }

        if (databaseTableField === 'student_address') {
            return addHeadTypeText(title, title_variable, updateContent);
        }
    }
}