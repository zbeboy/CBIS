/**
 * Created by lenovo on 2016-04-04.
 */
var tableLine = null;

$(document).ready(function () {
    tableLine = $($('#titleData').children()[0]);
});

/**
 * 添加行
 */
function addTitle() {
    $('#titleData').append("<li>" + tableLine.html() + "</li>");
}

/**
 * 删除行
 * @param obj
 */
function deleteLine(obj) {
    $(obj).parent().parent().parent().parent().remove();
}

var title = '';//标题内容

function inputTitle(obj) {
    title = $(obj).val();
}

var typeTarget = '';//类型选中

var isSingle = false;//选中的单选或复选
var typePluginTarget = '';//类型扩展选中
var contentTarget = '';//标题内容

function selectType(obj) {
    var id = $(obj).val();
    typeTarget = id;
    if (head_type_plugins != null) {
        $($($(obj).parent().parent().next().children()[1]).children()[0]).empty();
        $($($(obj).parent().parent().next().children()[1]).children()[0]).append($('<option value="">').text('请选择'));
        for (var i = 0; i < head_type_plugins.length; i++) {
            if (head_type_plugins[i].headTypeId == id) {
                $($($(obj).parent().parent().next().children()[1]).children()[0]).append($('<option value="' + head_type_plugins[i].id + '">').text(head_type_plugins[i].type));
            }
        }
        $($($(obj).parent().parent().next().next().children()[1]).children()[0]).val('');
        typePluginTarget = '';
        isSingle = false;
    }
}



function selectTypePlugin(obj) {
    var id = $(obj).val();
    typePluginTarget = id;
    if (head_type_plugins != null) {
        $($($(obj).parent().parent().next().children()[1]).children()[0]).val('');
        contentTarget = $($($(obj).parent().parent().next().children()[1]).children()[0]).val();
        for (var i = 0; i < head_type_plugins.length; i++) {
            if (head_type_plugins[i].id == id) {
                $($($(obj).parent().parent().next().children()[1]).children()[0]).val(head_type_plugins[i].content);
                if (head_type_plugins[i].type === '单选' || head_type_plugins[i].type === '多选') {
                    isSingle = true;
                    $($($(obj).parent().parent().next().children()[1]).children()[0]).attr('disabled', false);
                } else {
                    $($($(obj).parent().parent().next().children()[1]).children()[0]).attr('disabled', true);
                }
            }
        }
    }
}

var tableTarget = '';//表
var fieldTarget = '';//表字段

function selectTable(obj) {
    var id = $(obj).val();
    tableTarget = id;
    if (database_fields != null) {
        $($($(obj).parent().parent().next().children()[1]).children()[0]).empty();
        $($($(obj).parent().parent().next().children()[1]).children()[0]).append($('<option value="">').text('请选择'));
        for (var i = 0; i < database_fields.length; i++) {
            if (database_fields[i].id === Number(id)) {
                $($($(obj).parent().parent().next().children()[1]).children()[0]).append($('<option value="' + database_fields[i].value + '">').text(database_fields[i].text));
            }
        }
    }
}

function selectField(obj) {
    fieldTarget = $(obj).val();
}

function inputTitleContent(obj){
    contentTarget = $(obj).val();
}

function submitData(obj) {

    if (title != '') {//标题不能为空

        if (tableTarget === '') {//是否选了对应数据库

            if(typeTarget != ''){
                if (typePluginTarget != '') {//没选对应数据库，就必须勾选标题类型

                    if (isSingle) {//选了单选或多选，就必须输入标题内容
                        if (contentTarget != '') {//是否输入了标题内容
                            console.log('我选了单选或多选后,submit!');
                            var s = $(obj).parent().parent().serialize();

                            if(s.indexOf('authority')!=-1){
                                $.ajax({
                                    type: 'post',
                                    url: web_path + '/administrator/autonomicpractice/saveAutonomicPracticeTitle',
                                    data: s,
                                    success: function(data) {
                                        if(!data.state){
                                            layer.msg(data.msg);
                                        }
                                    }
                                });
                            } else {
                             layer.msg('请至少选择一个权限!');
                            }

                        } else {
                            layer.msg("请输入标题内容!")
                        }
                    } else {
                        console.log('我没选单选或多选后,submit!');
                        var s = $(obj).parent().parent().serialize();
                        if(s.indexOf('authority')!=-1){
                            $.ajax({
                                type: 'post',
                                url: web_path + '/administrator/autonomicpractice/saveAutonomicPracticeTitle',
                                data: s,
                                success: function(data) {
                                    if(!data.state){
                                        layer.msg(data.msg);
                                    }
                                }
                            });
                        } else {
                            layer.msg('请至少选择一个权限!');
                        }
                    }

                } else {
                    layer.msg("请选择标题扩展!");
                }
            } else {
                layer.msg("请选择标题类型!");
            }

        } else {
            if (fieldTarget != '') {//在选了对应数据库的情况下，必须选字段
                console.log('我选了对应数据库字段后,submit!')
                var s = $(obj).parent().parent().serialize();
                if(s.indexOf('authority')!=-1){
                    console.log(s);
                    $.ajax({
                        type: 'post',
                        url: web_path + '/administrator/autonomicpractice/saveAutonomicPracticeTitle',
                        data: s,
                        success: function(data) {
                            if(!data.state){
                                layer.msg(data.msg);
                            }
                        }
                    });
                } else {
                    layer.msg('请至少选择一个权限!');
                }
            } else {
                layer.msg('请选择数据库字段!');
            }
        }

    } else {
        layer.msg("请输入标题!");
    }
}

