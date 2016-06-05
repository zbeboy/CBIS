/**
 * Created by lenovo on 2016-04-29.
 */
/**
 * 显示到table
 * @param data
 */
function outputToTable(data) {

    var headTypes = '';

    for (var i = 0; i < headType.length; i++) {
        if (headType[i].id == data.headTypeId) {
            headTypes = headType[i].typeName;
        }
    }
    $('#tableData').append(
        $('<div class="uk-panel uk-panel-box uk-width-medium-1-1">').append(
            $('<h3 class="uk-panel-title">').text(data.title)
            )
            .append(
                $('<ul class="uk-grid uk-grid-width-1-1 uk-grid-width-medium-1-3 uk-grid-width-large-1-4">').append($('<li class="uk-hidden">').text(data.id))
                    .append($('<li>').text('标题类型:' + headTypes))
                    .append($('<li>').text('数据库表:' + data.databaseTable))
                    .append($('<li>').text('数据库表字段:' + data.databaseTableField))
                    .append($('<li>').text('是否必填:' + (data.isRequired == 1 ? '是' : '否')))
            )
            .append(
                $('<p class="uk-text-break">').text('所需权限:' + data.authority)
            )
            .append(
                $('<p class="uk-text-break">').text('选择内容:' + data.content)
            )
            .append(
                $('<div class="uk-text-right">').append($('<button class="uk-button" type="button" onclick="editTitle(this);" >').text('编辑'))
                    .append($('<button class="uk-button" type="button" onclick="deleteTitle(this);" >').text('删除'))
            )
    );
}

/**
 * 输出到高效工作区
 * @param data
 */
function outputToPanel(data) {
    $('#panelData').append(
        $('<li onclick="highTitleSelect(this,' + data.id + ');" data="' + data.id + '" >').append(
            $('<div class="uk-panel uk-panel-box uk-panel-box-primary ">').text(data.title)
        )
    );
}

/**
 * 输出到高效工作区 不显示的标题
 * @param data
 */
function outputToPanelNoShow(data) {
    $('#panelData').append(
        $('<li onclick="highTitleSelect(this,' + data.id + ');" data="' + data.id + '" >').append(
            $('<div class="uk-panel uk-panel-box">').text(data.title)
        )
    );
}

/**
 * 移除panel中的title
 * @param data
 */
function removePanelTitle(data) {
    var p = $('#panelData').children();
    for (var i = 0; i < p.length; i++) {
        if (Number($(p[i]).attr('data')) == data.id) {
            $(p[i]).remove();
            break;
        }
    }
}

/**
 * 输出复选到高效工作区
 * @param data
 */
function outputToPanelForCheckbox(data) {
    $('#panelData').append(
        $('<li  data="' + data.id + '" >').append(
            $('<div class="uk-panel uk-panel-box uk-placeholder ">').text(data.title)
        )
    );
}

/**
 * 编辑时table
 * @param data
 */
function outputToEditTable(data) {

    var headTypes = '';

    for (var i = 0; i < headType.length; i++) {
        if (headType[i].id == data.headTypeId) {
            headTypes = headType[i].typeName;
        }
    }

    var p = $(editTitleObj).parent().parent().children();
    var u = $(p[1]).children();
    $(p[0]).text(data.title);
    $(u[0]).text(data.id);
    $(p[2]).text('所需权限:' + data.authority);
    $(u[1]).text('标题类型:' + headTypes);
    $(p[3]).text('选择内容:' + data.content);
    $(u[2]).text('数据库表:' + data.databaseTable);
    $(u[3]).text('数据库表字段:' + data.databaseTableField);
    $(u[4]).text('是否必填:' + (data.isRequired == 1 ? '是' : '否'));
}

var databaseTables = null;//数据库表下拉菜单数据
var headType = null;//标题类型数据
var tableFileds = null;//数据库表中字段数据
var currentHeadTypeIsRadioOrCheckbox = false;//当前选择的模板类型为单选或复选
var currentHeadTypeIsSwitch = false;//当前选择模板类型为开关
var addTemplate = null//全局保存初始状态下的添加模板
var sort = 0; //用于标题排序
var templateId = 0;//模板id
var globalAuthorities = null;//权限对象

/**
 * 初始化模板数据
 */
function initTemplateData() {
    $.get(web_path + '/administrator/autonomicpractice/initTemplateData', function (data) {
        globalAuthorities = JSON.parse(data.single.roleList);
        headType = data.single.headType;
        databaseTables = data.single.databaseTables;
        tableFileds = data.single.tableFileds;
        initParam();
    });
}

/**
 * 初始化全局参数
 */
function initParam() {
    initAuthority();//输出权限到下拉菜单
    initHeadTypeSelect();//输出标题类型
    initDatabaseTableSelect();//输出数据库表
}

/**
 * 输出权限到下拉菜单
 */
function initAuthority() {
    for (var i = 0; i < globalAuthorities.length; i++) {
        $('#authority').append(
            $('<li>').append(
                $('<label>').append($('<input  type="checkbox" class="authority" name="authority" value="' + globalAuthorities[i].authority + '" />'))
                    .append(globalAuthorities[i].role)
            )
        );
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
 * 初始化数据库表
 */
function initDatabaseTableSelect() {
    if (databaseTables != null) {
        $('#databaseTableSelect').empty();
        $('#databaseTableSelect').append($('<option value="" >').text('请选择'));
        for (var i = 0; i < databaseTables.length; i++) {
            $('#databaseTableSelect').append($('<option>').val(databaseTables[i].id).text(databaseTables[i].text));
        }
    }
}

/**
 * 执行
 */
$(document).ready(function () {
    initTemplateData();//初始化模板数据
    addTemplate = $('#dataTitle').html();
});


/**
 * 检验模板名
 * @param id
 * @param templateName
 */
function validateUpdateAutonomicPracticeTemplateTitle(id, templateName) {
    if (templateName.length <= 0) {
        layer.msg('请填写模板名!');
    } else {
        $.post(web_path + "/administrator/autonomicpractice/validateUpdateAutonomicPracticeTemplateTitle", {
            'id': id,
            'templateName': templateName
        }, function (data) {
            if (data.state) {
                sendTemplateInfo(id, templateName);
            } else {
                layer.msg(data.msg);
            }
        });
    }
}

/**
 * 发送模板名
 * @param templateName
 */
function sendTemplateInfo(id, templateName) {
    $.post(web_path + "/administrator/autonomicpractice/updateAutonomicPracticeTemplate", {
        'id': id,
        'templateName': templateName
    }, function (data) {
        if (data.state) {
            $('#templateInfo').addClass('uk-hidden');
            $('#templdateData').removeClass('uk-hidden');
            templateId = data.obj;
            for (var i = 0; i < data.result.length; i++) {
                outputToTable(data.result[i]);

                if (data.result[i].typeValue != 'checkbox' && data.result[i].typeValue != 'textarea' && data.result[i].typeValue != 'password') {
                    if (data.result[i].isShowHighlyActive == 1) {
                        outputToPanel(data.result[i]);
                    } else {
                        outputToPanelNoShow(data.result[i]);
                    }
                } else {
                    outputToPanelForCheckbox(data.result[i]);
                }

            }
        } else {
            layer.msg(data.msg);
        }
    });
}

/**
 * 保存模板名
 */
function saveTemplateInfo() {
    validateUpdateAutonomicPracticeTemplateTitle($('#templateId').val(), $('#autonomousPracticeTemplateTitle').val().trim());
}

/**
 * 点击添加标题时 显示添加
 */
function addDataTitle() {
    $('#dataTitle').removeClass('uk-hidden');
    $('#dataTitle').empty();
    $('#dataTitle').append(addTemplate);
    initParam();
    $('#title').focus();
    isEditTitle = false;
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
 * 选择标题类型
 * @param obj
 */
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
 * 保存添加
 */
function saveAddTitle() {
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
    var isRequired = 1;
    var selectContentInput = new Array();
    var databaseTableSelect = 0;
    var databaseFieldSelect = '';

    for (var i = 0; i < headType.length; i++) {
        if (Number($('#headTypeSelect').val()) == headType[i].id) {
            if (headType[i].typeName === '复选' || headType[i].typeName === '多文本' || headType[i].typeName === '密码') {
                isShowHighlyActive = 0;
                break;
            }
        }
    }

    if ($("input[name='isRequired']:checked").val() === 'on') {//是否必填
        isRequired = 1;
    } else {
        isRequired = 0;
    }

    if (isDatabase == 0) {
        if ($('#headTypeSelect').val().trim().length <= 0) {
            layer.msg('请选择标题类型!');
            return;
        } else {
            if (currentHeadTypeIsRadioOrCheckbox) {
                if ($('.selectContentInput').length <= 0 || $($('.selectContentInput')[0]).val().trim().length <= 0) {
                    layer.msg('请为单选或多选添加选项!');
                    return;
                } else {
                    headTypeSelect = $('#headTypeSelect').val().trim();
                    for (var i = 0; i < $('.selectContentInput').length; i++) {
                        if ($($('.selectContentInput')[i]).val().trim().length > 0) {
                            var temp = $($('.selectContentInput')[i]).val().trim();
                            temp = temp.replace(/(,)/g, " ");
                            selectContentInput.push(temp);
                        }
                    }
                }
            } else if (currentHeadTypeIsSwitch) {
                if ($('.selectContentInput').length <= 0 || $($('.selectContentInput')[0]).val().trim().length <= 0 || $($('.selectContentInput')[1]).val().trim().length <= 0) {
                    layer.msg('请为开关添加选项!');
                    return;
                } else {
                    headTypeSelect = $('#headTypeSelect').val().trim();
                    for (var i = 0; i < $('.selectContentInput').length; i++) {
                        if ($($('.selectContentInput')[i]).val().trim().length > 0) {
                            var temp = $($('.selectContentInput')[i]).val().trim();
                            temp = temp.replace(/(,)/g, " ");
                            selectContentInput.push(temp);
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

    var url = '/administrator/autonomicpractice/addAutonomicPracticeHead';
    var id = templateId;
    if (isEditTitle) {
        url = '/administrator/autonomicpractice/updateAutonomicPracticeHead';
        id = editTitleId;
    }

    $.post(web_path + url, {
        'id': id,
        'title': title,
        'authority': authority.join(","),
        'isDatabase': isDatabase,
        'headTypeSelect': headTypeSelect,
        'selectContentInput': selectContentInput.join(","),
        'databaseTableSelect': databaseTableSelect,
        'databaseFieldSelect': databaseFieldSelect,
        'isShowHighlyActive': isShowHighlyActive,
        'isRequired': isRequired,
        'sort': sort

    }, function (data) {
        if (data.state) {
            $('#dataTitle').addClass('uk-hidden');
            $('#dataTitle').empty();
            $('#dataTitle').append(addTemplate);
            initParam();
            if (!isEditTitle) {
                sort++;
                outputToTable(data.obj);

            } else {
                outputToEditTable(data.obj);
                removePanelTitle(data.obj);
            }
            if (isShowHighlyActive == 1) {
                outputToPanel(data.obj);
            } else {
                outputToPanelForCheckbox(data.obj);
            }
        } else {
            layer.msg(data.msg);
        }
    });
}

/**
 * 取消添加
 */
function cancelAddTitle() {
    $('#dataTitle').addClass('uk-hidden');
    $('#dataTitle').empty();
    $('#dataTitle').append(addTemplate);
    initParam();
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
 * 保存标题顺序
 */
function saveHighlyTitleSort() {
    var sorts = new Array();
    for (var i = 0; i < $('#panelData').children().length; i++) {
        sorts.push($($('#panelData').children()[i]).attr('data'));
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
    window.location.href = web_path + '/administrator/autonomicpractice/templateList';
}

/**
 * 编辑时的权限
 * @param authorities
 */
function editTitleInitAuthorities(authorities) {
    for (var i = 0; i < authorities.length; i++) {
        for (var j = 0; j < $('.authority').length; j++) {
            if (authorities[i] == $($('.authority')[j]).val()) {
                $($('.authority')[j]).attr('checked', true);
                break;
            }
        }
    }
}

/**
 * 编辑时数据库字段
 */
function editDatabaseSelect(id, dfs) {
    if (tableFileds != null) {
        $('#databaseFieldSelect').empty();
        $('#databaseFieldSelect').append($(' <option value="" >').text('请选择'));
        for (var i = 0; i < tableFileds.length; i++) {
            if (id == tableFileds[i].id) {
                $('#databaseFieldSelect').append($(' <option>').val(tableFileds[i].value).text(tableFileds[i].text));
            }
        }

        var dfsp = $('#databaseFieldSelect').children();
        for (var i = 0; i < dfsp.length; i++) {
            if ($(dfsp[i]).val() == dfs) {
                $(dfsp[i]).attr('selected', true);
                break;
            }
        }
    }
}

/**
 * 编辑标题
 */
var isEditTitle = false;//是否在编辑标题
var editTitleObj = null;//编辑标题对象
var editTitleId = 0;//编辑的标题id
function editTitle(obj) {
    isEditTitle = true;
    editTitleObj = obj;
    $('#dataTitle').removeClass('uk-hidden');
    $('#dataTitle').empty();
    $('#dataTitle').append(addTemplate);
    initParam();

    var p = $(obj).parent().parent().children();
    var u = $(p[1]).children();
    var title = $(p[0]).text();
    editTitleId = $(u[0]).text();
    var authority = $(p[2]).text();
    var headTypeSelect = $(u[1]).text();
    var selectContentInput = $(p[3]).text();
    var databaseTableSelect = $(u[2]).text();
    var databaseFieldSelect = $(u[3]).text();
    var isRequired = $(u[4]).text();

    $('#title').val(title);// 标题

    var authorities = authority.split(":")[1].split(",");// 权限
    editTitleInitAuthorities(authorities);

    var hts = headTypeSelect.split(":")[1];//标题类型
    var htsp = $('#headTypeSelect').children();
    for (var i = 0; i < htsp.length; i++) {
        if ($(htsp[i]).text() == hts) {
            $(htsp[i]).attr('selected', true);
            break;
        }
    }

    if (hts === '单选' || hts === '复选') {
        isRadioOrCheckbox = true;
        currentHeadTypeIsRadioOrCheckbox = true;
    } else {
        currentHeadTypeIsRadioOrCheckbox = false;
    }
    if (hts === '开关') {
        isSwitch = true;
        currentHeadTypeIsSwitch = true;
    } else {
        currentHeadTypeIsSwitch = false;
    }

    var selectContentInputs = selectContentInput.split(":")[1].split(",");//选项内容
    if (selectContentInputs.length > 0) {
        $('#selectContent').removeClass('uk-text-muted');
        $('#selectContentButton').attr('disabled', false);
        $('#selectContent').find('p').remove();
    }
    for (var i = 0; i < selectContentInputs.length; i++) {
        addSelectContent();
        var sp = $('#selectContent').children();
        var sps = $(sp[sp.length - 1]).children();
        $(sps[0]).val(selectContentInputs[i]);
    }

    if (hts === '数据库字段') {
        $('#myTitle').attr('checked', false);
        $('#databaseTitle').attr('checked', true);
        titleWay('databaseTitle');
    } else {
        titleWay('myTitle');
    }

    var dts = databaseTableSelect.split(":")[1];//数据库表
    var dtsId = 0;
    if (dts == 'student') {
        dts = '学生表';
    }
    var dtsp = $('#databaseTableSelect').children();
    for (var i = 0; i < dtsp.length; i++) {
        if ($(dtsp[i]).text() == dts) {
            $(dtsp[i]).attr('selected', true);
            dtsId = Number($(dtsp[i]).val());
            break;
        }
    }

    var dfs = databaseFieldSelect.split(":")[1];//数据库字段
    editDatabaseSelect(dtsId, dfs);

    var ir = isRequired.split(":")[1];
    if (ir === '是') {//是否必填
        $('#isRequired').attr('checked', true);
    } else {
        $('#isRequired').attr('checked', false);
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
        $.post(web_path + '/administrator/autonomicpractice/deleteAutonomicPracticeHead', {
            'id': id
        }, function (data) {
            if (data.state) {
                var d = $('#panelData').children();
                for (var i = 0; i < d.length; i++) {
                    if (Number($(d[i]).attr('data')) == data.obj) {
                        $(d[i]).remove();
                        break;
                    }
                }
                $(obj).parent().parent().remove();
            }
            layer.msg(data.msg);
        });
    });

}