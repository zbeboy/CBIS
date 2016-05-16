/**
 * Created by lenovo on 2016-02-17.
 */

/**
 * 输出html
 * @param data
 */
function outputHtml(data) {
    $('#studentData').empty();

    var _ = DOMBuilder;
    for (var i = 0; i < data.result.length; i++) {
        var authorities = new Array();
        for (var j = 0; j < data.result[i].authorities.length; j++) {
            authorities.push(data.result[i].authorities[j]);
        }

        $('#studentData').append(_.DOM(
            _('li')._(
                _('div.uk-panel.uk-panel-space.uk-panel-box.uk-panel-box-secondary')._([
                    _('h3.uk-panel-title').H('姓名:' + (data.result[i].studentName == null ? '' : data.result[i].studentName)),
                    _('ul.uk-list.uk-list-space')._([
                        _('li').H('账号:' + data.result[i].studentNumber),
                        _('li').H('班级:' + (data.result[i].gradeName == null ? '' : data.result[i].gradeName)),
                        _('li').H('角色:' + (authorities.join(","))),
                        _('li' + (data.result[i].enabled ? '' : '.uk-text-danger')).H('状态:' + (data.result[i].enabled ? '正常' : '注销')),
                        _('li.uk-clearfix')._([
                            _('p.uk-hidden').H(data.result[i].studentNumber),
                            _('button.uk-button.uk-button-primary.uk-float-right[type=button][onclick=openEditModal(this);]').H('编辑'),
                            _('button.uk-button.uk-float-left[type=button][onclick=resetPassword(this);]').H('重置')
                        ]),
                        _('li.uk-clearfix')._([
                            _('p.uk-hidden').H(data.result[i].studentNumber),
                            _('p.uk-hidden').H(authorities.join(",")),
                            _('button.uk-button.uk-float-right[type=button][onclick=openAuthoritiesModal(this);]').H('权限'),
                            _('p.uk-hidden').H(data.result[i].enabled ? 'y' : 'n'),
                            _('button.uk-button.uk-button-danger.uk-float-left[onclick=openStateModal(this);][type=button]').H('状态')
                        ])
                    ])
                ])
            )
        ));
    }
}

/**
 * 执行入口
 */
function action() {
    $.post(web_path + '/maintainer/users/studentManagerData', {
        'param': JSON.stringify(param)
    }, function (data) {
        outputHtml(data);
        if(data.result.length>0){
            initPage(data);
        }
    });
}

/**
 * 分页
 * @param data
 */
function initPage(data) {
    var pagination = UIkit.pagination('.uk-pagination', {
        items: data.single.totalData,
        itemsOnPage: data.single.pageSize,
        currentPage: data.single.pageNum - 1
    });
}

/**
 * 封装参数
 * @type {{studentName: (string|*|jQuery), studentNumber: (string|*|jQuery), pageNum: number, pageSize: number, totalData: number}}
 */
var param = {
    'studentName': $('#studentName').val().trim(),
    'studentNumber': $('#studentNumber').val().trim(),
    'pageNum': 1,
    'pageSize': 6,
    'totalData': 1
}

/**
 * 点击分页
 */
$('.uk-pagination').on('select.uk.pagination', function (e, pageIndex) {
    param.pageNum = pageIndex + 1;
    action();
});

/**
 * 权限封装对象
 * @param id
 * @param value
 * @param text
 */
function authoritiesObj(id, value, text) {
    this.id = id;
    this.value = value;
    this.text = text;
}

/**
 * 权限数组
 * @type {Array}
 */
var authoritiesParam = new Array();

/**
 * 初始化权限
 */
function initAuthorities() {

    $.get(web_path + '/maintainer/users/getAuthorities', function (data) {
        var d = JSON.parse(data.single.roleList);
        for (var i = 0; i < d.length; i++) {
            authoritiesParam.push(new authoritiesObj("form-a-r" + i, d[i].authority, d[i].role));
            $('#authorities').append(
                $('<input type="checkbox" id="form-a-r' + i + '" name="authoritiesCheck" value="' + d[i].authority + '" />')
            ).append(
                $('<label for="form-a-r' + i + '">'+d[i].role+'</label>')
            ).append(
                $('<br/>')
            );
        }
    });

}

/**
 * 执行
 */
$(document).ready(function () {
    action();
    initAuthorities();
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
 * 选择年份
 * @param obj
 */
function selectYear(obj) {
    if ($(obj).val().trim().length > 0) {
        $.post(web_path + "/maintainer/users/gradeData", {
            'year': $(obj).val().trim()
        }, function (data) {
            if (data.state) {
                var _ = DOMBuilder;
                $('#gradeData').empty();
                for (var i = 0; i < data.result.length; i++) {
                    $('#gradeData').append(
                        $('<li>').append(
                            $('<label>').append(
                                $('<input type="radio" name="grade" >').val(data.result[i].id)
                            ).append(
                                $('<span>').text(data.result[i].gradeName)
                            )
                        )
                    );
                }
            } else {
                layer.msg(data.msg);
            }
        });
    } else {
        $('#gradeData').empty();
    }
}

/**
 * 检验是否选择了班级
 */
function submitAddData() {
    var n = $("input[name='grade']:checked").val();
    if (n == undefined) {
        layer.msg('请选择一个班级!');
    } else {
        $('#addStudentForm').submit();
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
        $.post(web_path + '/maintainer/users/resetPassword', {
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
    $.post(web_path + '/maintainer/users/resetEnable', {
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

    var checkBox = document.getElementsByName('authoritiesCheck');
    for (var i = 0; i < checkBox.length; i++) {
        checkBox[i].checked = false;
    }

    var authorities = $(obj).prev().text().trim().split(",");

    for (var i = 0; i < authorities.length; i++) {
        for(var j = 0;j<authoritiesParam.length;j++){
            if(authorities[i] === authoritiesParam[j].text){
                document.getElementById(authoritiesParam[j].id).checked = true;
                break;
            }
        }
    }

    if (!modal.isActive()) {
        modal.show();
    }
}

/**
 * 权限保存
 */
function authorities() {
    var n = new Array();
    for (var i = 0; i < $("input[name='authoritiesCheck']:checked").length; i++) {
        n.push($($("input[name='authoritiesCheck']:checked")[i]).val().trim());
    }
    $.post(web_path + '/maintainer/users/resetAuthority', {
        'username': $('#authoritiesNum').val(),
        'authority': n.join(",")
    }, function (data) {
        if (data.state) {
            window.location.reload(true);
        } else {
            layer.msg(data.msg);
        }
    }, 'json');
}

/**
 * 搜索重置
 */
function refresh(){
    $('#studentName').val('');
    $('#studentNumber').val('');
    $('#searchForm').submit();
}
