/**
 * Created by lenovo on 2016-06-07.
 */

/**
 * 前端分页参数
 * @type {{bigTitle: string, realName: string, pageNum: number, pageSize: number}}
 */
var param = {
    'teachTaskTitle': $('#teachTaskTitle').val().trim(),
    'teachTaskTerm': $('#teachTaskTerm').val().trim(),
    /* 'termStartTime':$('#termStartTime').val().trim(),
     'termEndTime':$('#termEndTime').val().trim(),*/
    'teachType':$('#teachType').val().trim(),
    'pageNum': 1,
    'pageSize': 20
}

/**
 * 创建分页
 * @param data
 */
function createPage(data) {
    var pagination = UIkit.pagination('.uk-pagination', {
        items: data.paginationData.totalDatas,
        itemsOnPage: data.paginationData.pageSize,
        currentPage: data.paginationData.pageNum - 1
    });
}

/**
 * 点击分页
 */
$('.uk-pagination').on('select.uk.pagination', function (e, pageIndex) {
    param.pageNum = pageIndex + 1;
    action();
});

/**
 * 输出表格
 */
function outputTable() {
    var html = "<table class='tablesaw tablesaw-stack' data-tablesaw-mode='stack' id='mytable'>" +
        "<thead>" +
        "<tr>" +
        "<th scope='col' data-tablesaw-priority='persist'>标题</th>" +
        "<th scope='col'>学期</th>" +
        "<th scope='col'>学年开始时间</th>" +
        "<th scope='col'>学年结束时间</th>" +
        "<th scope='col'>状态</th>" +
        "<th scope='col'>创建者</th>" +
        "<th scope='col'>操作</th>" +
        "</tr>" +
        "</thead>" +
        "<tbody id='tableData'></tbody>" +
        "</table>";
    $('#ajaxed').html(html);
}

/**
 * 输出数据
 * @param d
 */
function outputHtml(d) {
    outputTable();
    var list = d.result;
    for (var i = 0; i < list.length; i++) {

        var s = '';
        var use = 0;
        var ouse = '使用';
        if (list[i].isUse == 0) {
            s = '不可用';
            use = 0;
            //ouse = '<i class="uk-icon-genderless uk-text-success"></i>';
        } else {
            s = '可用';
            use = 1;
            //ouse = '<i class="uk-icon-ban uk-text-danger"></i>';
        }

        $('#tableData').append(
            $('<tr>')
                .append($('<td>').text(dealNull(list[i].teachTaskTitle)))
                .append($('<td>').text(dealNull(list[i].teachTaskTerm)))
                .append($('<td>').text(dealNull(list[i].termStartTime)))
                .append($('<td>').text(dealNull(list[i].termEndTime)))
                .append($('<td>').text(dealNull(s)))
                .append($('<td>').text(dealNull(list[i].realName)))
                .append(
                    $('<td>')
                        .append($('<a href="javascript:;" onclick="toUse(' + list[i].id + ',' + use + ');" >').html(ouse))
                )
        );
    }
    $('#mytable').table().data("table").refresh();
}

/**
 * 处理空值
 * @param obj
 */
function dealNull(obj) {
    if (obj == null) {
        return '';
    } else {
        return obj;
    }
}

/**
 * 是否使用
 * @param id
 * @param use
 */
function toUse(id, use) {
    if (Number(use) == 0) {
        layer.msg("该教学任务书状态不可用!");
    } else {
        window.location.href = web_path + '/administrator/eadmin/fourItemsLine?taskInfoId='+id+'&teachType='+param.teachType;
    }
}

/**
 * 重置搜索
 */
function refresh() {
    $('#teachTaskTitle').val('');
    $('#teachTaskTerm').val('');
    /* $('#termStartTime').val('');
     $('#termEndTime').val('');*/
    $('#searchForm').submit();
}


function action() {
    $.post(web_path + '/administrator/eadmin/assignmentBookData', param,
        function (data) {
            if (data.state) {
                if (data.result.length > 0) {
                    createPage(data);
                }
                outputHtml(data);
            }
        }, 'json')
}

$(document).ready(function () {
    action();
});