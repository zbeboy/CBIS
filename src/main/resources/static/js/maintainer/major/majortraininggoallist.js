/**
 * Created by lenovo on 2016-02-09.
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
            showDetailsDialog(args.item);
        },
        pagePrevText: "上一页",
        pageNextText: "下一页",
        pageFirstText: "首页",
        pageLastText: "尾页",
        controller: db,
        fields: [
            {
                name: "id",
                title: "专业",
                type: "select",
                items: db.majors,
                valueField: "id",
                textField: "majorName",
                width: 80
            },
            {name: "bigTitle", title: "标题", type: "text", width: 100},
            {name: "username", title: "作者", type: "text", width: 50},
            {name: "date", title: "时间", type: "text", width: 70},
            {
                type: "control",
                modeSwitchButton: true,
                editButton: false,
                deleteButton: false
            }
        ]
    });

});

function showDetailsDialog(client) {
    layer.confirm('去编辑？', {
        btn: ['确定', '取消'] //按钮
    }, function () {
        window.location.href = web_path + '/maintainer/major/majorTrainingGoalUpdate?majorId=' + client.id;
    });
}