/**
 * Created by lenovo on 2016-02-07.
 */
$(function () {

    $("#jsGrid").jsGrid({
        width: "100%",
        editing: true,
        inserting: true,
        filtering: true,
        sorting: true,
        paging: true,
        autoload: true,
        pageSize: 10,
        pageButtonCount: 5,
        pageLoading: true,
        deleteConfirm: function(item) {
            return "该专业 \"" + item.majorName + "\" 会被移除. 你确定吗?";
        },
        pagePrevText: "上一页",
        pageNextText: "下一页",
        pageFirstText: "首页",
        pageLastText: "尾页",
        controller: db,
        fields: [
            {name: "id", title: "id", type: "number", visible: false},
            {name: "majorName", title: "专业", type: "text", width: 110},
            {type: "control"}
        ]
    });

});