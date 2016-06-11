/**
 * Created by lenovo on 2016-06-04.
 */

var addTemplate = null//全局保存初始状态下的添加模板
/**
 * 点击添加标题时 显示添加
 */
function addDataTitle() {
    $('#dataTitle').removeClass('uk-hidden');
    $('#dataTitle').empty();
    $('#dataTitle').append(addTemplate);
    isEditTitle = false;
    initJrange();
}

/**
 * 初始化范围插件
 */
function initJrange() {
    $('.slider-input').jRange({
        from: 0,
        to: 100,
        step: 1,
        scale: [0, 25, 50, 75, 100],
        format: '%s',
        width: '70%',
        showLabels: true,
        isRange: false
    });
}

/**
 * 输出初始化数据
 * @param data
 */
function outputData(data){
    var list = data.result;
    for(var i = 0;i<list.length;i++){
        if(list[i].isAssignment == 1){
            list[i].title = list[i].teachTaskTitle;
        }
        outputToTable(list[i]);
    }
}

/**
 * 请求数据
 */
function action(){
    var index = layer.load(1, {
        shade: [0.1,'#fff'] //0.1透明度的白色背景
    });
    $.post(web_path + '/administrator/eadmin/teachingMaterialTemplateTitleUpdateData',{
        'templateId':assignmentBookId
    },function(data){
        layer.close(index);
        if(data.state){
            outputData(data);
        }
    },'json');
}

/**
 * 执行
 */
$(document).ready(function () {
    addTemplate = $('#dataTitle').html();//html数据
    action();
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

    var s = '';
    if (data.isAssignment == 0) {
        s = '自定义标题';
    } else {
        s = '教学任务书标题';
    }

    $('#tableData').append(
        $('<div class="uk-panel uk-panel-box uk-width-medium-1-1">').append(
            $('<h3 class="uk-panel-title">').text(data.title)
            )
            .append(
                $('<ul class="uk-grid uk-grid-width-1-1 uk-grid-width-medium-1-3 uk-grid-width-large-1-4">').append($('<li class="uk-hidden">').text(data.id))
                    .append($('<li>').text('标题类型:' + s))/*是教学任务书标题,还是自定义标题*/
                    .append($('<li>').text('序号:' + data.sort))
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

    var s = '';
    if (data.isAssignment == 0) {
        s = '自定义标题';
    } else {
        s = '教学任务书标题';
    }

    $(u[1]).text('标题类型:' + s);
    /*是教学任务书标题,还是自定义标题*/
    $(u[2]).text('序号:' + data.sort);
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
    var assignmentBookFieldSelect = 0;

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
            var c = $('#assignmentBookFieldSelect').children();
            for(var i = 0;i< c.length;i++){
                if($(c[i]).val() === assignmentBookFieldSelect){
                    title = $(c[i]).text();
                    break;
                }
            }
        }
    }

    var param = {};
    var url = '/administrator/eadmin/addTeachingMaterialTemplateTitle';
    var id = assignmentBookId;
    if (isEditTitle) {
        url = '/administrator/eadmin/updateTeachingMaterialTemplateTitle';
        id = editTitleId;
        param ={
            'id': id,
            'title': title,
            'teachTaskTitleId': assignmentBookFieldSelect,
            'isAssignment': isAssignmentBook,
            'sort': $('#sort').val().trim()
        };
    } else {
        param ={
            'teachingMaterialTemplateId': id,
            'title': title,
            'teachTaskTitleId': assignmentBookFieldSelect,
            'isAssignment': isAssignmentBook,
            'sort': $('#sort').val().trim()
        };
    }

    var index = layer.load(1, {
        shade: [0.1,'#fff'] //0.1透明度的白色背景
    });
    $.post(web_path + url, param, function (data) {
        if (data.state) {
            layer.close(index);
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
    },'json');
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

    var headType = $(u[1]).text().split(":")[1];
    if (headType === '教学任务书标题') {
        $('#myTitle').attr('checked', false);
        $('#assignmentBookTitle').attr('checked', true);
        titleWay('assignmentBookTitle');

        for(var i = 0;i<$("#assignmentBookFieldSelect").children().length;i++){
            if(title === $($("#assignmentBookFieldSelect").children()[i]).text()){
                $($("#assignmentBookFieldSelect").children()[i]).attr('selected',true);
                break;
            }
        }

    } else if (headType === '自定义标题') {
        titleWay('myTitle');
        $('#title').val(title);
    }

    var sort = $(u[2]).text().split(":")[1];
    $('.slider-input').val(sort);
    initJrange();
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
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.post(web_path + '/administrator/eadmin/deleteTeachingMaterialTemplateTitle', {
            'id': id
        }, function (data) {
            layer.close(index);
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
function toBack() {
    window.location.href = web_path + '/administrator/eadmin/teachingMaterialTemplateList';
}
