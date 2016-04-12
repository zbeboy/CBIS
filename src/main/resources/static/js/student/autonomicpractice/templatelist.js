/**
 * Created by lenovo on 2016-04-12.
 */
$(function () {

    $("#jsGrid").jsGrid({
        width: "100%",
        editing: false,
        inserting: false,
        filtering: true,
        sorting: true,
        paging: true,
        autoload: true,
        pageSize: 10,
        pageButtonCount: 5,
        pageLoading: true,
        deleteConfirm: function(item) {
            return "该模板 \"" + item.bigTitle + "\" 会被移除. 你确定吗?";
        },
        rowClick: function (args) {
            showDetailsDialog('Edit', args.item);
        },
        pagePrevText: "上一页",
        pageNextText: "下一页",
        pageFirstText: "首页",
        pageLastText: "尾页",
        controller: db,
        fields: [
            {name: "id", title: "id", type: "number", visible: false},
            {name: "autonomousPracticeTemplateTitle", title: "标题", type: "text", width: 80},
            {name: "createTime", title: "创建时间", type: "text", width: 80},
            {name: "username", title: "创建者", type: "text",width: 80},
            {
                type: "control",
                modeSwitchButton: false,
                editButton: false,
                headerTemplate: function () {
                    return $("<button>").attr("type", "button").addClass("uk-button").text("添加")
                        .on("click", function () {
                            showDetailsDialog('Add', {});
                        });
                }
            }
        ]
    });

});

function showDetailsDialog(type, client) {
    if (type === 'Edit') {
        layer.confirm('去编辑?', {
            btn: ['确定', '取消'] //按钮
        }, function () {
            window.location.href = web_path + '/administrator/autonomicpractice/templateUpdate?id=' + client.id;
        });
    } else if (type === 'Add') {
        window.location.href = web_path + '/administrator/autonomicpractice/templateAdd';
    }
}