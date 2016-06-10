/**
 * Created by guipeng on 2016/6/8.
 */
/**
 * 前端分页参数
 * @type {{examTitle: string, majorName: string, pageNum: number, pageSize: number}}
 */
var param = {
    'examTitle': $('#examTitle').val().trim(),
    'majorName': $('#majorName').val().trim(),
    'pageNum': 1,
    'pageSize': 10
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
 * 输出数据
 * @param d
 */
function outputHtml(d) {
    $('#tableData').empty();
    var list = d.result;
    for (var i = 0; i < list.length; i++) {
        $('#tableData').append(
            $('<div class="uk-panel uk-panel-divider">')
                .append($('<h3 class="uk-panel-title">').text(dealNull(list[i].examTitle)))
                .append($('<p>').text('时间: ' + dealNull(list[i].examTime)))
                .append($('<p>').text('专业: ' + dealNull(list[i].majorName)))
                .append($("<p>").text('地点: ' + dealNull(list[i].examAddress)))
                .append($('<div class="uk-text-right">')
                    .append($('<button class="uk-button uk-button-primary" type="button" onclick="toLook(\'' + list[i].id + '\');">').text('查看详情'))
                )
        );
    }
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

function toLook(id){
    window.location.href = web_path + '/user/exam/examShowDetailed?id='+id;
}

/**
 * 重置搜索
 */
function refresh() {
    $('#examTitle').val('');
    $('#majorName').val('');
    $('#searchForm').submit();
}

function action() {
    $.post(web_path + '/user/exam/examShowData', param,
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