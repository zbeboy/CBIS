/**
 * Created by lenovo on 2016-05-26.
 */
/**
 * 前端分页参数
 * @type {{bigTitle: string, realName: string, pageNum: number, pageSize: number}}
 */
var param = {
    'recruitTitle': $('#recruitTitle').val().trim(),
    'fitMajor': $('#fitMajor').val().trim(),
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
                .append($('<h3 class="uk-panel-title">').text(dealNull(list[i].recruitTitle)))
                .append($('<p>').text('时间: ' + dealNull(list[i].recruitTime)))
                .append($('<p>').text('适合专业: ' + dealNull(list[i].fitMajor)))
                .append($("<p>").text('地点: ' + dealNull(list[i].recruitAddress)))
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
    window.location.href = web_path + '/user/recruit/recruitShowDetailed?id='+id;
}

/**
 * 重置搜索
 */
function refresh() {
    $('#recruitTitle').val('');
    $('#fitMajor').val('');
    $('#searchForm').submit();
}

function action() {
    var index = layer.load(1, {
        shade: [0.1,'#fff'] //0.1透明度的白色背景
    });
    $.post(web_path + '/user/recruit/recruitShowData', param,
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