/**
 * Created by lenovo on 2016-02-14.
 */
var gradeModal = null;//添加模态框
var gradeName = null;//检验班级名
$(function () {

    var y  = new Date().getFullYear();
    for (var i = (y-30); i < (y+30); i++) //以今年为准，前30年，后30年
        document.gradeForm.year.options.add(new Option(" "+ i , i));

    UIkit.autocomplete("#gradeHeadAuto", {source: web_path + "/maintainer/grade/gradeHead", minLength: 2});
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
        deleteConfirm: function(item) {
            return "该班级 \"" + item.gradeName + "\" 会被移除. 你确定吗?";
        },
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
            {name: "gradeHeadID", title: "gradeHeadID", type: "text", visible: false},
            {
                name: "majorId",
                title: "专业",
                type: "select",
                items: db.majors,
                valueField: "id",
                textField: "majorName",
                width: 80
            },
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
            $('#gradeForm').attr('action', web_path + '/maintainer/grade/addGrade');
            $('#modalTitle').text("添加班级");
            $($('#majorName').children()[0]).attr("selected", "selected");
            $('#year').val('');
            $('#gradeName').val('');
            $('#gradeHeadID').val('');
            $('#gradeId').val(-1);
            gradeModal.show();
        } else if (dialogType === 'Edit') {
            $('#gradeForm').attr('action', web_path + '/maintainer/grade/updateGrade');
            $('#modalTitle').text("更新班级");
            for (var i = 0; i < $('#majorName').children().length; i++) {
                if (Number($($('#majorName').children()[i]).val()) === client.majorId) {
                    $($('#majorName').children()[i]).attr("selected", "selected");
                    break;
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