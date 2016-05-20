/**
 * Created by lenovo on 2016-03-09.
 */

/**
 * 前端分页参数
 * @type {{bigTitle: string, realName: string, pageNum: number, pageSize: number}}
 */
var param = {
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

function outputHtml(d){
    $('#tableData').empty();
    var list = d.result;
    for(var i = 0;i< list.length;i++){
        var s = '';
        var show = 0;
        var oshow = '';
        if(list[i].isShow == 0){
            s = '不显示';
            show = 1;
            oshow = '显示';
        } else {
            s = '显示';
            show = 0;
            oshow = '不显示';
        }
        $('#tableData').append(
            $('<tr>')
                .append($('<td>').text(list[i].bigTitle))
                .append($('<td>').text(list[i].realName))
                .append($('<td>').text(list[i].date))
                .append($('<td>').text(s))
                .append(
                    $('<td>')
                        .append($('<a href="javascript:;" onclick="toShow('+list[i].id+','+show+');" >').text(oshow))
                        .append(' ')
                        .append($('<a href="javascript:;" onclick="toEdit('+list[i].id+');" >').text('编辑'))
                        .append(' ')
                        .append($('<a href="javascript:;" onclick="toDel('+list[i].id+');" >').text('删除'))

                )
        );
    }
}

function toShow(id,show){

}

function toDel(id){

}

function toEdit(id){
    window.location.href = web_path + '/maintainer/tie/tieNoticeUpdate?id=' + id;
}

/**
 * 重置搜索
 */
function refresh(){
    $('#bigTitle').val('');
    $('#realName').val('');
    $('#searchForm').submit();
}

function action(){
    $.post(web_path + '/maintainer/tie/tieNoticeData',param,
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