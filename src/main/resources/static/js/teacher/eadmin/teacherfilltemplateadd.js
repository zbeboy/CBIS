/**
 * Created by lenovo on 2016-05-29.
 */

var addTemplate = null//全局保存初始状态下的添加模板
var assignmentBookId = 0;//模板id
/**
 * 点击添加标题时 显示添加
 */
function addDataTitle() {
    $('#dataTitle').removeClass('uk-hidden');
    $('#dataTitle').empty();
    $('#dataTitle').append(addTemplate);
    isEditTitle = false;
}

/**
 * 执行
 */
$(document).ready(function () {
    addTemplate = $('#dataTitle').html();//html数据
});

/**
 * 选择自定义标题 或 数据库标题
 * @param type
 */
function titleWay(type) {
    if (type === 'myTitle') {
        $('#headType').removeClass('uk-text-muted');
        $('#title').attr('disabled', false);

        $('#assignmentBookField').addClass('uk-text-muted');
        $('#assignmentBookFieldSelect').attr('disabled', true);

    } else if (type === 'assignmentBookTitle') {
        $('#headType').addClass('uk-text-muted');
        $('#title').attr('disabled', true);

        $('#assignmentBookField').removeClass('uk-text-muted');
        $('#assignmentBookFieldSelect').attr('disabled', false);
    }
}


/**
 * 显示到table
 * @param data
 */
function outputToTable(data) {
    $('#tableData').append(
        $('<div class="uk-panel uk-panel-box uk-width-medium-1-1">').append(
            $('<h3 class="uk-panel-title">').text(data.title)
            )
            .append(
                $('<ul class="uk-grid uk-grid-width-1-1 uk-grid-width-medium-1-3 uk-grid-width-large-1-4">').append($('<li class="uk-hidden">').text(data.id))
                    .append($('<li>').text('标题类型:' + headTypes))/*是教学任务书标题,还是自定义标题*/
            )
            .append(
                $('<div class="uk-text-right">').append($('<button class="uk-button" type="button" onclick="editTitle(this);" >').text('编辑'))
                    .append($('<button class="uk-button" type="button" onclick="deleteTitle(this);" >').text('删除'))
            )
    );
}

/**
 * 编辑时table
 * @param data
 */
function outputToEditTable(data) {
    var p = $(editTitleObj).parent().parent().children();
    var u = $(p[1]).children();
    $(p[0]).text(data.title);
    $(u[0]).text(data.id);

    $(u[1]).text('标题类型:' + headTypes);/*是教学任务书标题,还是自定义标题*/
}

/**
 * 保存添加
 */
function saveAddTitle() {

    var isAssignmentBook = 0;
    for (var i = 0; i < $("input[name='isAssignmentBook']:checked").length; i++) {
        isAssignmentBook = $($("input[name='isAssignmentBook']:checked")[i]).val().trim();
    }

    isAssignmentBook = Number(isAssignmentBook);

    var title = '';
    var assignmentBookFieldSelect = '';

    if (isAssignmentBook == 0) {
        if ($('#title').val().trim().length <= 0) {
            layer.msg('请填写标题!');
            return;
        } else {
            title = $('#title').val().trim();
        }
    } else {
        if ($('#assignmentBookFieldSelect').val().trim().length <= 0) {
            layer.msg('请选择教学任务书标题!');
            return;
        } else {
            assignmentBookFieldSelect = $('#assignmentBookFieldSelect').val().trim();
        }
    }

    var url = '/teacher/eadmin/addTeacherFillTemplate';
    var id = assignmentBookId;
    if (isEditTitle) {
        url = '/teacher/eadmin/updateTeacherFillTemplate';
        id = editTitleId;
    }

    $.post(web_path + url, {
        'id': id,
        'title': title,
        'assignmentBookFieldSelect': assignmentBookFieldSelect
    }, function (data) {
        if (data.state) {
            $('#dataTitle').addClass('uk-hidden');
            $('#dataTitle').empty();
            $('#dataTitle').append(addTemplate);
            if (!isEditTitle) {
                outputToTable(data.obj);
            } else {
                outputToEditTable(data.obj);
            }
        } else {
            layer.msg(data.msg);
        }
    });
}

/**
 * 编辑标题
 * @type {null}
 */
var editTitleObj = null;//编辑标题对象
var editTitleId = 0;//编辑的标题id
function editTitle(obj) {
    isEditTitle = true;
    editTitleObj = obj;
    $('#dataTitle').removeClass('uk-hidden');
    $('#dataTitle').empty();
    $('#dataTitle').append(addTemplate);

    var p = $(obj).parent().parent().children();
    var u = $(p[1]).children();
    var title = $(p[0]).text();
    editTitleId = $(u[0]).text();

    var headType = $(u[1]).text();
    if(headType === '教学任务书标题'){
        $('#myTitle').attr('checked', false);
        $('#assignmentBookTitle').attr('checked', true);
        titleWay('assignmentBookTitle');
    } else if(headType === '自定义标题'){
        titleWay('myTitle');
    }
}

/**
 * 删除标题
 * @param obj
 */
function deleteTitle(obj) {
    layer.confirm('您确定要删除该标题吗?', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        var p = $(obj).parent().parent().children();
        var u = $(p[1]).children();
        var id = $(u[0]).text();
        $.post(web_path + '/teacher/eadmin/deleteTeacherFillTemplate', {
            'id': id
        }, function (data) {
            if (data.state) {
                $(obj).parent().parent().remove();
            }
            layer.msg(data.msg);
        });
    });

}

/**
 * 取消添加
 */
function cancelAddTitle() {
    $('#dataTitle').addClass('uk-hidden');
    $('#dataTitle').empty();
    $('#dataTitle').append(addTemplate);
}

/**
 * 返回
 */
function toBack(){

}
