/**
 * Created by lenovo on 2016-05-26.
 */
/**
 * 前端分页参数
 * @type {{bigTitle: string, realName: string, pageNum: number, pageSize: number}}
 */
var param = {
    'username':$('#username').val().trim(),
    'operationBehavior':$('#operationBehavior').val().trim(),
    'startTime':$('#startTime').val().trim(),
    'endTime':$('#endTime').val().trim(),
    'pageNum':1,
    'pageSize':20
}

/**
 * 创建分页
 * @param data
 */
function createPage(data){
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
function outputTable(){
    var html = "<table class='tablesaw tablesaw-stack' data-tablesaw-mode='stack' id='mytable'>" +
        "<thead>" +
        "<tr>" +
        "<th scope='col' data-tablesaw-priority='persist'>账号</th>" +
        "<th scope='col'>操作行为</th>" +
        "<th scope='col'>时间</th>" +
        "</tr>" +
        "</thead>" +
        "<tbody id='tableData'></tbody>" +
        "</table>";
    $( '#ajaxed').html(html);
}

/**
 * 输出数据
 * @param d
 */
function outputHtml(d){
    outputTable();
    var list = d.result;
    for(var i = 0;i< list.length;i++){
        $('#tableData').append(
            $('<tr>')
                .append($('<td>').text(dealNull(list[i].username)))
                .append($('<td>').text(dealNull(list[i].operationBehavior)))
                .append($('<td>').text(dealNull(list[i].createTime)))
        );
    }
    $('#mytable').table().data( "table" ).refresh();
}

/**
 * 处理空值
 * @param obj
 */
function dealNull(obj){
    if(obj == null){
        return '';
    } else {
        return obj;
    }
}

/**
 * 重置搜索
 */
function refresh(){
    $('#username').val('');
    $('#operationBehavior').val('');
    $('#startTime').val('');
    $('#endTime').val('');
    $('#searchForm').submit();
}

function action(){
    $.post(web_path + '/maintainer/system/systemLogData',param,
        function(data){
            if(data.state){
                if(data.result.length>0){
                    createPage(data);
                }
                outputHtml(data);
            }
        },'json')
}

$( document ).ready(function() {
    action();
});