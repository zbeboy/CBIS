/**
 * Created by lenovo on 2016-02-17.
 */
$('.uk-pagination').on('select.uk.pagination', function (e, pageIndex) {
    var toTeacherName = '';
    var toTeacherJobNumber = '';
    if (param.teacherName != null && param.teacherName.trim().length > 0) {
        toTeacherName = param.teacherName;
    }

    if (param.teacherJobNumber != null && param.teacherJobNumber.trim().length > 0) {
        toTeacherJobNumber = param.teacherJobNumber;
    }
    window.location.href = "/maintainer/teacherManager?teacherName=" + toTeacherName + "&teacherJobNumber=" + toTeacherJobNumber + "&pageNum=" + (pageIndex + 1);
});

$(document).ready(function () {
    var pagination = UIkit.pagination('.uk-pagination', {
        items: param.totalData,
        itemsOnPage: param.pageSize,
        currentPage: param.pageNum - 1
    });
});
/**
 * 打开添加用户模态框
 * @param obj
 */
function openAddModal() {
    var modal = UIkit.modal('#addModal');
    if (!modal.isActive()) {
        modal.show();
    }
}

/**
 * 重置密码
 * @param obj
 */
function resetPassword(obj) {
    layer.confirm('您确定要重置该用户的密码吗？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        var teacherJobNumber = $(obj).prev().prev().text();
        $.post('/maintainer/resetPassword', {
            'username': teacherJobNumber
        }, function (data) {
            if (data.state) {
                layer.msg(data.msg, {icon: 1});
            } else {
                layer.msg(data.msg);
            }
        }, 'json');
    });
}

/**
 * 编辑用户信息，待定
 * @param obj
 */
function openEditModal(obj) {
    var modal = UIkit.modal('#editModal');
    if (!modal.isActive()) {
        modal.show();
    }
}

/**
 * 打开状态模态框
 * @param obj
 */
function openStateModal(obj) {
    var modal = UIkit.modal('#stateModal');
    var p = $(obj).parent();
    $('#stateNum').val($(p.children()[0]).text());
    if ($(obj).prev().text() === 'y') {
        $('#form-s-r').attr('checked', 'checked');
    } else if ($(obj).prev().text() === 'n') {
        $('#form-s-r1').attr('checked', 'checked');
    }
    if (!modal.isActive()) {
        modal.show();
    }
}

/**
 * 保存状态
 */
function state() {
    var n = $("input[type='radio']:checked").val();
    console.log($('#stateNum').val());
    console.log(n);
    $.post('/maintainer/resetEnable', {
        'username': $('#stateNum').val(),
        'enable': n
    }, function (data) {
        if (data.state) {
            window.location.reload(true);
        } else {
            layer.msg(data.msg);
        }
    }, 'json');
}

/**
 * 关闭模态框
 * @param obj
 */
function cancel(obj) {
    var modal = UIkit.modal(obj);
    if (modal.isActive()) {
        modal.hide();
    }
}

/**
 * 保存编辑，待定
 */
function edit() {


}