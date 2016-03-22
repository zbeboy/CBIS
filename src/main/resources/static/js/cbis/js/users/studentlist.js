/**
 * Created by lenovo on 2016-02-17.
 */
$('.uk-pagination').on('select.uk.pagination', function (e, pageIndex) {
    var toStudentName = '';
    var toStudentNumber = '';
    if (param.studentName != null && param.studentName.trim().length > 0) {
        toStudentName = param.studentName;
    }

    if (param.studentNumber != null && param.studentNumber.trim().length > 0) {
        toStudentNumber = param.studentNumber;
    }
    window.location.href = "/maintainer/studentManager?studentName=" + toStudentName + "&studentNumber=" + toStudentNumber + "&pageNum=" + (pageIndex + 1);
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
        var studentNumber = $(obj).prev().prev().text();
        $.post('/maintainer/resetPassword', {
            'username': studentNumber
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

    var radio = document.getElementsByName('stateRadio');
    for (var i = 0; i < radio.length; i++) {
        radio[i].checked = false;
    }

    if ($(obj).prev().text() === 'y') {
        document.getElementById('form-s-r').checked = true;
    } else if ($(obj).prev().text() === 'n') {
        document.getElementById('form-s-r1').checked = true;
    }
    if (!modal.isActive()) {
        modal.show();
    }
}

/**
 * 保存状态
 */
function state() {
    var n = $("input[name='stateRadio']:checked").val();
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

/**
 * 权限窗口
 * @param obj
 */
function openAuthoritiesModal(obj) {
    var modal = UIkit.modal('#authoritiesModal');
    var p = $(obj).parent();
    $('#authoritiesNum').val($(p.children()[0]).text());

    var radio = document.getElementsByName('authoritiesRadio');
    for (var i = 0; i < radio.length; i++) {
        radio[i].checked = false;
    }

    if ($(obj).prev().text().trim() === '超级管理员') {
        document.getElementById('form-a-r').checked = true;
    } else if ($(obj).prev().text().trim() === '管理员') {
        document.getElementById('form-a-r1').checked = true;
    } else if ($(obj).prev().text().trim() === '教师') {
        document.getElementById('form-a-r2').checked = true;
    } else if ($(obj).prev().text().trim() === '学生') {
        document.getElementById('form-a-r3').checked = true;
    }

    if (!modal.isActive()) {
        modal.show();
    }
}

/**
 * 权限保存
 */
function authorities() {
    var n = $("input[name='authoritiesRadio']:checked").val();
    console.log(n);
    console.log($('#authoritiesNum').val());
    $.post('/maintainer/resetAuthority', {
        'username': $('#authoritiesNum').val(),
        'authority': n
    }, function (data) {
        if (data.state) {
            window.location.reload(true);
        } else {
            layer.msg(data.msg);
        }
    }, 'json');
}
