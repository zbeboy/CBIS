/**
 * Created by lenovo on 2016-06-04.
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
        "<th scope='col'>时间</th>" +
        "<th scope='col'>教学任务书</th>" +
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
                .append($('<td>').text(dealNull(list[i].createTime)))
                .append($('<td>').text(dealNull(list[i].teachTaskTitle)))
                .append(
                    $('<td>')
                        .append($('<a href="javascript:;" onclick="toEdit('+list[i].id+',\''+dealNull(list[i].title)+'\');" >').html('<i class="uk-icon-pencil"></i>'))
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

function toEdit(id,title){
    $('#templateId').val(id);
    $('#templateTitle').val(title);
    var modal = UIkit.modal("#use");

    if ( modal.isActive() ) {
        modal.hide();
    } else {
        modal.show();
    }
}

function cancel(target){
    var modal = UIkit.modal(target);

    if ( modal.isActive() ) {
        modal.hide();
    } else {
        modal.show();
    }
}

function submitData(){
    $('#useForm').submit();
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
    var index = layer.load(1, {
        shade: [0.1,'#fff'] //0.1透明度的白色背景
    });
    $.post(web_path + '/administrator/eadmin/teachingMaterialTemplateData',param,
        function(data){
            layer.close(index);
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