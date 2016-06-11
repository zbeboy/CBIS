/**
 * Created by lenovo on 2016-06-06.
 */

/**
 * 前端分页参数
 * @type {{bigTitle: string, realName: string, pageNum: number, pageSize: number}}
 */
var param = {
    'fileName': $('#fileName').val().trim(),
    'realName': $('#realName').val().trim(),
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
        "<th scope='col' data-tablesaw-priority='persist'>文件名</th>" +
        "<th scope='col'>备注</th>" +
        "<th scope='col'>上传者</th>" +
        "<th scope='col'>上传时间</th>" +
        "<th scope='col'>大小</th>" +
        "<th scope='col'>下载次数</th>" +
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

        $('#tableData').append(
            $('<tr>')
                .append($('<td>').text(dealNull(list[i].fileName)))
                .append($('<td>').text(dealNull(list[i].remark)))
                .append($('<td>').text(dealNull(list[i].realName)))
                .append($('<td>').text(dealNull(list[i].fileDate)))
                .append($('<td>').text(dealNull(list[i].fileSize)))
                .append($('<td>').text(dealNull(list[i].fileDownTimes)))
                .append(
                    $('<td>')
                        .append($('<a href="javascript:;" onclick="toDownload(' + list[i].id + ');" >').html('<i class="uk-icon-download"></i>'))
                        .append(' ')
                        .append($('<a href="javascript:;" onclick="toEdit(' + list[i].id + ');" >').html('<i class="uk-icon-pencil"></i>'))
                        .append(' ')
                        .append($('<a href="javascript:;" onclick="toDel(' + list[i].id + ');" >').html('<i class="uk-icon-trash uk-text-danger"></i>'))

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
 * 编辑
 * @param id
 */
function toEdit(id) {
    window.location.href = web_path + '/administrator/eadmin/relatedDownloadUpdate?id=' + id+'&teachType='+param.teachType;
}

/**
 * 删除
 * @param id
 */
function toDel(id) {
    layer.confirm("确定要删除该文件吗?", {
        btn: ['确定', '取消'] //按钮
    }, function () {
        var index = layer.load(1, {
            shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
        $.post(web_path + '/administrator/eadmin/deleteRelatedDownload', {
            'id': id
        }, function (data) {
            layer.close(index);
            if (data.state) {
                layer.msg(data.msg, {icon: 1}, function () {
                    window.location.reload(true);
                });
            } else {
                layer.msg(data.msg);
            }
        }, 'json');
    });
}

/**
 * 下载
 * @param id
 */
function toDownload(id) {
    window.location.href = web_path + '/administrator/eadmin/downloadRelatedDownload?id=' + id;
}

/**
 * 重置搜索
 */
function refresh() {
    $('#fileName').val('');
    $('#realName').val('');
    $('#searchForm').submit();
}


function action() {
    var index = layer.load(1, {
        shade: [0.1,'#fff'] //0.1透明度的白色背景
    });
    $.post(web_path + '/administrator/eadmin/relatedDownloadData', param,
        function (data) {
            layer.close(index);
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