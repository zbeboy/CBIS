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

/**
 * 点击可见
 * @param obj
 */
function isVisible(obj) {
    if (obj.checked) {
        $(obj).parent().next().next().children().attr('disabled', false);
        $(obj).parent().next().next().next().children().attr('disabled', false);
    } else {
        $(obj).parent().next().next().children().attr('checked', false);
        $(obj).parent().next().next().children().attr('disabled', true);
        $(obj).parent().next().next().next().children().attr('checked', false);
        $(obj).parent().next().next().next().children().attr('disabled', true);
    }
    console.log(obj.checked);
}

/**
 * 选择数据库表
 * @param obj
 */
function selectTable(obj) {
    console.log($(obj).val());
    if ($(obj).val().trim().length > 0) {
        $(obj).parent().next().children().attr('disabled', false);
    } else {
        $(obj).parent().next().children().attr('disabled', true);
    }
}

/**
 * 选择标题类型
 * @param obj
 */
function selectType(obj) {
    console.log($(obj).val());
    if ($(obj).val().trim().length > 0) {
        $(obj).parent().next().children().attr('disabled', false);
        $(obj).parent().next().next().children().attr('disabled', false);
    } else {
        $(obj).parent().next().children().attr('disabled', true);
        $(obj).parent().next().next().children().attr('disabled', true);
        $(obj).parent().next().next().children().val('');
    }
}