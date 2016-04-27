/**
 * Created by lenovo on 2016-04-13.
 */

var currentHeadTypeIsRadioOrCheckbox = false;//当前选择的模板类型为单选或复选
var currentHeadTypeIsSwitch = false;//当前选择模板类型为开关
var addTemplate = null//全局保存初始状态下的添加模板
var sort = 0; //用于标题排序
var templateId = 0;//模板id

function headSelect(obj) {
    var isRadioOrCheckbox = false;
    var isSwitch = false;
    if (headType != null) {
        for (var i = 0; i < headType.length; i++) {
            if (Number($(obj).val()) == headType[i].id) {
                if (headType[i].typeName === '单选' || headType[i].typeName === '复选') {
                    isRadioOrCheckbox = true;
                    currentHeadTypeIsRadioOrCheckbox = true;
                } else {
                    currentHeadTypeIsRadioOrCheckbox = false;
                }
                if (headType[i].typeName === '开关') {
                    isSwitch = true;
                    currentHeadTypeIsSwitch = true;
                } else {
                    currentHeadTypeIsSwitch = false;
                }
                break;
            }
        }
        if (isRadioOrCheckbox) {
            $('#selectContent').removeClass('uk-text-muted');
            $('#selectContentButton').attr('disabled', false);
            $('#selectContent').find('p').remove();
            addSelectContent();
        } else if (isSwitch) {//是开关
            $('#selectContent').removeClass('uk-text-muted');
            $('#selectContentButton').attr('disabled', true);//开关不允许再添加内容

            //开关只保留两个内容选项
            $('#selectContent').find('p').remove();
            addSwitchContent();
        } else {
            $('#selectContent').addClass('uk-text-muted');
            $('#selectContentButton').attr('disabled', true);
            for (var i = 0; i < $('.selectContentInput').length; i++) {
                $($('.selectContentInput')[i]).attr('disabled', true);
            }

            for (var i = 0; i < $('.selectClose').length; i++) {
                $($('.selectClose')[i]).attr('disabled', true);
            }
        }
    }
}

/**
 * 保存模板名
 */
function saveTemplateInfo() {
    if ($('#autonomousPracticeTemplateTitle').val().trim().length <= 0) {
        layer.msg('请填写模板名!');
    } else {
        $.post(web_path + "/administrator/autonomicpractice/addAutonomicPracticeTemplate", {
            'templateName': $('#autonomousPracticeTemplateTitle').val().trim()
        }, function (data) {
            if (data.state) {
                $('#templateInfo').addClass('uk-hidden');
                $('#templdateData').removeClass('uk-hidden');
                templateId = data.obj;
            } else {
                layer.msg(data.msg);
            }
        });
    }
}

/**
 * 选择自定义标题 或 数据库标题
 * @param type
 */
function titleWay(type) {
    if (type === 'myTitle') {
        $('#headType').removeClass('uk-text-muted');
        $('#headTypeSelect').attr('disabled', false);
        if (currentHeadTypeIsRadioOrCheckbox) {
            $('#selectContent').removeClass('uk-text-muted');
            $('#selectContentButton').attr('disabled', false);
            for (var i = 0; i < $('.selectContentInput').length; i++) {
                $($('.selectContentInput')[i]).attr('disabled', false);
            }

            for (var i = 0; i < $('.selectClose').length; i++) {
                $($('.selectClose')[i]).attr('disabled', false);
            }
        } else if (currentHeadTypeIsSwitch) {
            $('#selectContent').removeClass('uk-text-muted');
            $('#selectContentButton').attr('disabled', true);//开关不允许再添加内容
            //开关只保留两个内容选项
            for (var i = 0; i < $('.selectContentInput').length; i++) {
                $($('.selectContentInput')[i]).attr('disabled', false);
            }
        } else {
            $('#selectContent').addClass('uk-text-muted');
            $('#selectContentButton').attr('disabled', true);
            for (var i = 0; i < $('.selectContentInput').length; i++) {
                $($('.selectContentInput')[i]).attr('disabled', true);
            }

            for (var i = 0; i < $('.selectClose').length; i++) {
                $($('.selectClose')[i]).attr('disabled', true);
            }
        }

        $('#databaseTable').addClass('uk-text-muted');
        $('#databaseTableSelect').attr('disabled', true);
        $('#databaseField').addClass('uk-text-muted');
        $('#databaseFieldSelect').attr('disabled', true);
    } else if (type === 'databaseTitle') {
        $('#headType').addClass('uk-text-muted');
        $('#headTypeSelect').attr('disabled', true);
        $('#selectContent').addClass('uk-text-muted');
        $('#selectContentButton').attr('disabled', true);

        for (var i = 0; i < $('.selectContentInput').length; i++) {
            $($('.selectContentInput')[i]).attr('disabled', true);
        }

        for (var i = 0; i < $('.selectClose').length; i++) {
            $($('.selectClose')[i]).attr('disabled', true);
        }

        $('#databaseTable').removeClass('uk-text-muted');
        $('#databaseTableSelect').attr('disabled', false);
        $('#databaseField').removeClass('uk-text-muted');
        $('#databaseFieldSelect').attr('disabled', false);
    }
}

/**
 * 在单选或复选下添加选项内容
 */
function addSelectContent() {
    $('#selectContent').append(
        $('<p>').append($('<input type="text" class="uk-form-width-medium selectContentInput" placeholder="选项"/>'))
            .append($('<button  class="selectClose uk-close uk-close-alt" onclick="selectClose(this);">'))
    );
}

/**
 * 开关选项
 */
function addSwitchContent() {
    $('#selectContent').append(
        $('<p>').append($('<input type="text" class="uk-form-width-medium selectContentInput" placeholder="开时内容"/>'))
        )
        .append($('<p>').append($('<input type="text" class="uk-form-width-medium selectContentInput" placeholder="关时内容"/>')));
}

/**
 * 删除选项
 * @param obj
 */
function selectClose(obj) {
    $(obj).parent().remove();
}

/**
 * 点击添加标题时 显示添加
 */
function addDataTitle() {
    $('#dataTitle').removeClass('uk-hidden');
    $('#title').focus();
}

/**
 * 显示到table
 * @param data
 */
function outputToTable(data) {

    var operator = "<a href='#'>编辑</a> &nbsp;&nbsp; <a href='#'>删除</a>";

    var headTypes = '';

    for (var i = 0; i < headType.length; i++) {
        if (headType[i].id == data.obj.headTypeId) {
            headTypes = headType[i].typeName;
        }
    }
    $('#tableData').append(
        $('<div class="uk-panel uk-panel-box uk-width-medium-1-1">').append(
            $('<h3 class="uk-panel-title">').text(data.obj.title)
            )
            .append(
                $('<ul class="uk-grid uk-grid-width-1-1 uk-grid-width-medium-1-2 uk-grid-width-large-1-3">').append($('<li class="uk-hidden">').text(data.obj.id))
                    .append($('<li>').text('标题类型:' + headTypes))
                    .append($('<li>').text('数据库表:' + data.obj.databaseTable))
                    .append($('<li>').text('数据库表字段:' + data.obj.databaseTableField))
            )
            .append(
                $('<p class="uk-text-break">').text('所需权限:' + data.obj.authority)
            )
            .append(
                $('<p class="uk-text-break">').text('选择内容:' + data.obj.content)
            )
            .append(
                $('<div class="uk-text-right">').append($('<button class="uk-button" type="button">').text('编辑'))
                    .append($('<button class="uk-button" type="button">').text('删除'))
            )
    );
}

/**
 * 输出到高效工作区
 * @param data
 */
function outputToPanel(data) {
    $('#panelData').append(
        $('<li onclick="highTitleSelect(this,' + data.obj.id + ');" data-id="' + data.obj.id + '" >').append(
            $('<div class="uk-panel uk-panel-box uk-panel-box-primary ">').text(data.obj.title)
        )
    );
}

/**
 * 输出复选到高效工作区
 * @param data
 */
function outputToPanelForCheckbox(data){
    $('#panelData').append(
        $('<li  data-id="' + data.obj.id + '" >').append(
            $('<div class="uk-panel uk-panel-box uk-placeholder ">').text(data.obj.title)
        )
    );
}


/**
 * 保存添加
 */
function saveAddTitle() {
    //$.ajax({
    //    type: "POST",
    //    url: web_path + "/administrator/autonomicpractice/addAutonomicPracticeHead",
    //    data: $('#autonomousPracticeHeadForm').serialize(),
    //    success: function(data){
    //        console.log(data.state);
    //    }
    //});

    var title = $('#title').val().trim();
    if (title.length <= 0) {
        layer.msg('请填写标题名!');
        return;
    }

    var authority = new Array();
    for (var i = 0; i < $("input[name='authority']:checked").length; i++) {
        authority.push($($("input[name='authority']:checked")[i]).val().trim());
    }

    if (authority.length <= 0) {
        layer.msg('请至少选择一个权限!');
        return;
    }

    var isDatabase = 0;
    for (var i = 0; i < $("input[name='isDatabase']:checked").length; i++) {
        isDatabase = $($("input[name='isDatabase']:checked")[i]).val().trim();
    }

    isDatabase = Number(isDatabase);

    var headTypeSelect = Number($('#databaseHeadType').val().trim());
    var isShowHighlyActive = 1;
    var selectContentInput = new Array();
    var databaseTableSelect = 0;
    var databaseFieldSelect = '';

    for(var i = 0;i<headType.length;i++){
        if(Number($('#headTypeSelect').val()) == headType[i].id){
            if(headType[i].typeName === '复选' || headType[i].typeName === '多文本' || headType[i].typeName === '密码'){
                isShowHighlyActive = 0;
                break;
            }
        }
    }

    if (isDatabase == 0) {
        if ($('#headTypeSelect').val().trim().length <= 0) {
            layer.msg('请选择标题类型!');
            return;
        } else {
            if (currentHeadTypeIsRadioOrCheckbox) {
                if ($($('.selectContentInput')[0]).val().trim().length <= 0) {
                    layer.msg('请为单选或多选添加选项!');
                    return;
                } else {
                    headTypeSelect = $('#headTypeSelect').val().trim();
                    for (var i = 0; i < $('.selectContentInput').length; i++) {
                        if ($($('.selectContentInput')[i]).val().trim().length > 0) {
                            selectContentInput.push($($('.selectContentInput')[i]).val().trim());
                        }
                    }
                }
            } else if (currentHeadTypeIsSwitch) {
                if ($($('.selectContentInput')[0]).val().trim().length <= 0 || $($('.selectContentInput')[1]).val().trim().length <= 0) {
                    layer.msg('请为开关添加选项!');
                    return;
                } else {
                    headTypeSelect = $('#headTypeSelect').val().trim();
                    for (var i = 0; i < $('.selectContentInput').length; i++) {
                        if ($($('.selectContentInput')[i]).val().trim().length > 0) {
                            selectContentInput.push($($('.selectContentInput')[i]).val().trim());
                        }
                    }
                }
            } else {
                headTypeSelect = $('#headTypeSelect').val().trim();
            }
        }
    } else {
        if ($('#databaseTableSelect').val().trim().length <= 0) {
            layer.msg('请选择数据库表!');
            return;
        } else {
            if ($('#databaseFieldSelect').val().trim().length <= 0) {
                layer.msg('请选择数据库表对应字段!');
                return;
            } else {
                databaseTableSelect = $('#databaseTableSelect').val().trim();
                databaseFieldSelect = $('#databaseFieldSelect').val().trim();
            }
        }
    }


    console.log('authority : ' + authority.join(","));


    console.log('isDatabase : ' + isDatabase);

    console.log('headTypeSelect : ' + $('#headTypeSelect').val());


    console.log('selectContentInput : ' + selectContentInput.join(","));
    console.log('databaseTableSelect : ' + $('#databaseTableSelect').val());
    console.log('databaseFieldSelect : ' + $('#databaseFieldSelect').val());

    $.post(web_path + "/administrator/autonomicpractice/addAutonomicPracticeHead", {
        'templateId': templateId,
        'title': title,
        'authority': authority.join(","),
        'isDatabase': isDatabase,
        'headTypeSelect': headTypeSelect,
        'selectContentInput': selectContentInput.join(","),
        'databaseTableSelect': databaseTableSelect,
        'databaseFieldSelect': databaseFieldSelect,
        'isShowHighlyActive':isShowHighlyActive,
        'sort': sort

    }, function (data) {
        if (data.state) {
            $('#dataTitle').addClass('uk-hidden');
            $('#dataTitle').empty();
            $('#dataTitle').append(addTemplate);
            initAuthorities();
            sort++;
            outputToTable(data);
            if(isShowHighlyActive == 1){
                outputToPanel(data);
            } else {
                outputToPanelForCheckbox(data);
            }
        } else {
            layer.msg(data.msg);
        }
        console.log(data.state);
    });
}

/**
 * 保存标题顺序
 */
function saveHighlyTitleSort() {
    var sorts = new Array();
    for (var i = 0; i < $('#panelData').children().length; i++) {
        sorts.push($($('#panelData').children()[i]).attr('data-id'));
    }

    $.post(web_path + '/administrator/autonomicpractice/updateAutonomicPracticeHeadHighlySort', {
        'sort': sorts.join(",")
    }, function (data) {
        window.location.href = web_path + '/administrator/autonomicpractice/templateList';
    });
}

/**
 * 取消全部
 */
function cancelAll() {
    $.post(web_path + '/administrator/autonomicpractice/deleteTemplate', {
        'id': templateId
    }, function (data) {
        window.location.href = web_path + '/administrator/autonomicpractice/templateList';
    });
}

/**
 * 取消添加
 */
function cancelAddTitle() {
    $('#dataTitle').addClass('uk-hidden');
    $('#dataTitle').empty();
    $('#dataTitle').append(addTemplate);
    initAuthorities();
}

/**
 * 选择显示哪些标题
 * @param obj
 */
function highTitleSelect(obj, id) {
    $.post(web_path + '/administrator/autonomicpractice/updateAutonomicPracticeHeadHighlyShow', {
        'id': id
    }, function (data) {
        if (data.state) {
            for (var i = 0; i < $(obj).children().length; i++) {
                if ($($(obj).children()[i]).hasClass('uk-panel-box-primary')) {
                    $($(obj).children()[i]).removeClass('uk-panel-box-primary');
                } else {
                    $($(obj).children()[i]).addClass('uk-panel-box-primary');
                }
            }
        }

    });
}

/**
 * 初始化权限
 */
function initAuthorities() {

    $.get(web_path + '/maintainer/users/getAuthorities', function (data) {
        var d = JSON.parse(data.single.roleList);
        for (var i = 0; i < d.length; i++) {
            $('#authority').append(
                $('<li>').append(
                    $('<label>').append($('<input  type="checkbox" name="authority" value="' + d[i].authority + '" />'))
                        .append(d[i].role)
                )
            );
        }
    });

}

/**
 * 选择数据库表
 * @param obj
 */
function databaseSelect(obj) {
    if (tableFileds != null) {
        $('#databaseFieldSelect').empty();
        $('#databaseFieldSelect').append($(' <option value="" >').text('请选择'));
        for (var i = 0; i < tableFileds.length; i++) {
            if (Number($(obj).val()) == tableFileds[i].id) {
                $('#databaseFieldSelect').append($(' <option>').val(tableFileds[i].value).text(tableFileds[i].text));
            }
        }
    }
}

/**
 * 初始化标题类型
 */
function initHeadTypeSelect() {

    if (headType != null) {
        for (var i = 0; i < headType.length; i++) {
            if (headType[i].typeValue != 'database') {
                $('#headTypeSelect').append($('<option>').val(headType[i].id).text(headType[i].typeName));
            } else {
                $('#databaseHeadType').val(headType[i].id);
            }
        }

    }
}

/**
 * 执行
 */
$(document).ready(function () {
    initAuthorities();
    initHeadTypeSelect();
    addTemplate = $('#dataTitle').html();
});

