/**
 * Created by lenovo on 2016-06-05.
 */

/**
 * 前端分页参数
 * @type {{bigTitle: string, realName: string, pageNum: number, pageSize: number}}
 */
var param = {
    'title':$('#title').val().trim(),
    'realName':$('#realName').val().trim(),
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
        "<th scope='col' data-tablesaw-priority='persist'>标题</th>" +
        "<th scope='col'>作者</th>" +
        "<th scope='col'>模板</th>" +
        "<th scope='col'>开始时间</th>" +
        "<th scope='col'>结束时间</th>" +
        "<th scope='col'>创建时间</th>" +
        "<th scope='col'>操作</th>" +
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
                .append($('<td>').text(dealNull(list[i].title)))
                .append($('<td>').text(dealNull(list[i].realName)))
                .append($('<td>').text(dealNull(list[i].templateTitle)))
                .append($('<td>').text(dealNull(list[i].startTime)))
                .append($('<td>').text(dealNull(list[i].endTime)))
                .append($('<td>').text(dealNull(list[i].createTime)))
                .append(
                    $('<td>')
                        .append($('<a href="javascript:;" onclick="toEdit('+list[i].id+');" >').html('<i class="uk-icon-pencil"></i>'))
                )
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

function toEdit(id){
    window.location.href = web_path + '/administrator/eadmin/teachingMaterialInfoUpdate?id='+id;
}

/**
 * 重置搜索
 */
function refresh(){
    $('#title').val('');
    $('#realName').val('');
    $('#searchForm').submit();
}

function action(){
    $.post(web_path + '/administrator/eadmin/teachingMaterialInfoData',param,
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