/**
 * Created by lenovo on 2016-03-09.
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
            return "该文章 \"" + item.bigTitle + "\" 会被移除. 你确定吗?";
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
            {name: "bigTitle", title: "标题", type: "text", width: 110},
            {name: "username", title: "作者", type: "text", width: 60},
            {name: "date", title: "时间", type: "text", width: 80},
            {name: "show", title: "显示", type: "checkbox",sorting: false, width: 40},
            {name: "isShow", title: "isShow", type: "number", visible: false},
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
            window.location.href = web_path + '/maintainer/tie/tieNoticeUpdate?id=' + client.id;
        });
    } else if (type === 'Add') {
        window.location.href = web_path + '/maintainer/tie/tieNoticeAdd';
    }
}