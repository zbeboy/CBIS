/**
 * Created by lenovo on 2016-02-14.
 */
var gradeModal = null;//添加模态框
var gradeName = null;//检验班级名
$(function () {
    UIkit.autocomplete("#gradeHeadAuto", {source: "/maintainer/gradehead"});
    gradeModal = UIkit.modal("#gradeModal");
    $("#jsGrid").jsGrid({
        width: "100%",
        editing: true,
        filtering: true,
        sorting: true,
        paging: true,
        autoload: true,
        pageSize: 10,
        pageButtonCount: 5,
        pageLoading: true,
        deleteConfirm: "你确定要删除该班级吗？",
        rowClick: function (args) {
            showDetailsDialog("Edit", args.item);
        },
        pagePrevText: "上一页",
        pageNextText: "下一页",
        pageFirstText: "首页",
        pageLastText: "尾页",
        controller: db,
        fields: [
            {name: "id", title: "id", type: "number", visible: false},
            {name: "teacherId", title: "teacherId", type: "number", visible: false},
            {name: "gradeHeadID", title: "gradeHeadID", type: "text", visible: false},
            {name: "majorName", title: "专业", type: "text", width: 80},
            {name: "year", title: "年级", type: "text", width: 60},
            {name: "gradeName", title: "班级", type: "text", width: 70},
            {name: "gradeHead", title: "班主任", type: "text", width: 50},
            {
                type: "control",
                modeSwitchButton: false,
                editButton: false,
                headerTemplate: function () {
                    return $("<button class='uk-button'>").attr("type", "button").text("添加")
                        .on("click", function () {
                            showDetailsDialog("Add", {});
                        });
                }
            }
        ]
    });

    var showDetailsDialog = function (dialogType, client) {
        if (dialogType === 'Add') {
            $('#modalTitle').text("添加班级");
            $($('#majorName').children()[0]).attr("selected", "selected");
            $('#year').val('');
            $('#gradeName').val('');
            $('#gradeHeadID').val('');
            $('#gradeId').val(-1);
            gradeModal.show();
        } else if (dialogType === 'Edit') {
            $('#modalTitle').text("更新班级");
            for (var i = 0; i < $('#majorName').children().length; i++) {
                if ($($('#majorName').children()[i]).text() === client.majorName) {
                    $($('#majorName').children()[i]).attr("selected", "selected");
                }
            }
            $('#year').val(client.year);
            $('#gradeName').val(client.gradeName);
            gradeName = client.gradeName;
            $('#gradeHeadID').val(client.gradeHeadID);
            $('#gradeId').val(client.id);
            gradeModal.show();
        }
    };

});

function cancel() {
    window.location.reload(true);
}

$('form[name="gradeForm"]').validator({
    ignore: ':hidden',
    stopOnError: false,
    timely: false,
    rules:{
        year:[/^\d{4}$/,'请输入4位数字']
    },
    fields: {
        'majorName': {
            rule: "required;",
            msg: {required: "请选择专业"},
            tip: "选择所属专业",
            ok: "您稍后可再更改",
            target: "#majorNameError"
        },
        'year': {
            rule: "required;year",
            msg: {required: "请填写年级"},
            tip: "年级",
            target: "#yearError"
        },
        'gradeName': {
            rule: "required;length[1~70]",
            msg: {required: "请填写班级名"},
            tip: "班级名",
            target: "#gradeNameError"
        },
        'gradeHeadID': {
            rule: "required;length[1~25]",
            msg: {checked: "请填写班主任"}
        }
    },
    validClass: "uk-form-success",
    invalidClass: "uk-form-danger",
    msgClass: "n-right",
    valid: function (form) {
        var me = this;
        $.post("/cbis/maintainer/checktearchernum", {
            'tearcherJobNum': $('#gradeHeadID').val()
        }, function (data) {
            if (data.state) {
                $('#gradeHeadError').removeClass('uk-text-danger');
                $('#gradeHeadError').text('');

                var url = '';
                var checkGradeName = false;

                if (Number($('#gradeId').val()) == -1) {

                    $.post("/cbis/maintainer/checkgradename",{
                        'gradeName':$('#gradeName').val()
                    },function(data){
                        if(data.state){
                            me.holdSubmit();
                            $.ajax({
                                url: "/cbis/maintainer/addgrade",
                                data: $(form).serialize(),
                                type: 'post',
                                success: function (d) {
                                    if (d.state) {
                                        window.location.reload(true);
                                    } else {
                                        layer.msg(d.msg);
                                    }
                                    me.holdSubmit(false);
                                }
                            });
                        } else {
                            $('#gradeName').removeClass('uk-form-success').addClass('uk-form-danger');
                            layer.msg(data.msg);
                        }
                    },'json');
                } else if (Number($('#gradeId').val()) > 0) {

                    if(gradeName === $('#gradeName').val()){
                        me.holdSubmit();
                        $.ajax({
                            url: "/cbis/maintainer/updategrade",
                            data: $(form).serialize(),
                            type: 'post',
                            success: function (d) {
                                if (d.state) {
                                    window.location.reload(true);
                                } else {
                                    layer.msg(d.msg);
                                }
                                me.holdSubmit(false);
                            }
                        });
                    } else {
                        $.post("/cbis/maintainer/checkgradename",{
                            'gradeName':$('#gradeName').val()
                        },function(data){
                            if(data.state){
                                me.holdSubmit();
                                $.ajax({
                                    url: "/cbis/maintainer/updategrade",
                                    data: $(form).serialize(),
                                    type: 'post',
                                    success: function (d) {
                                        if (d.state) {
                                            window.location.reload(true);
                                        } else {
                                            layer.msg(d.msg);
                                        }
                                        me.holdSubmit(false);
                                    }
                                });
                            } else {
                                $('#gradeName').removeClass('uk-form-success').addClass('uk-form-danger');
                                layer.msg(data.msg);
                            }
                        },'json');
                    }
                }

            } else {
                $('#gradeHeadError').removeClass('uk-text-danger').addClass('uk-text-danger');
                $('#gradeHeadError').text(data.msg);
            }
        }, 'json');
    }
});