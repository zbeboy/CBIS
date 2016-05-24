/**
 * Created by lenovo on 2016-02-09.
 */

/**
 * 前端分页参数
 * @type {{majorName: (string|*|jQuery), bigTitle: (string|*|jQuery), realName: (string|*|jQuery), pageNum: number, pageSize: number}}
 */
var param = {
    'majorName':$('#majorName').val().trim(),
    'bigTitle':$('#bigTitle').val().trim(),
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
    var html = " <table class='tablesaw tablesaw-stack' data-tablesaw-mode='stack' id='mytable'>" +
        "<thead>" +
        "<tr>" +
        "<th scope='col' data-tablesaw-priority='persist'>专业</th>" +
        "<th scope='col'>标题</th>" +
        "<th scope='col'>作者</th>" +
        "<th scope='col'>时间</th>" +
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
                .append($('<td>').text(dealNull(list[i].majorName)))
                .append($('<td>').text(dealNull(list[i].bigTitle)))
                .append($('<td>').text(dealNull(list[i].realName)))
                .append($('<td>').text(dealNull(list[i].date)))
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
    window.location.href = web_path + '/maintainer/major/majorTraitUpdate?majorId=' + id;
}

/**
 * 重置搜索
 */
function refresh(){
    $('#majorName').val('');
    $('#bigTitle').val('');
    $('#realName').val('');
    $('#searchForm').submit();
}

function action(){
    $.post(web_path + '/maintainer/major/majorTraitData',param,
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