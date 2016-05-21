/**
 * Created by lenovo on 2016-02-07.
 */

/**
 * 前端分页参数
 * @type {{majorName: (string|*|jQuery), pageNum: number, pageSize: number}}
 */
var param = {
    'majorName':$('#majorName').val().trim(),
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
        "<th scope='col' data-tablesaw-priority='persist'>#</th>" +
        "<th scope='col'>专业</th>" +
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
    var j = 1;//序号
    for(var i = 0;i< list.length;i++){
        $('#tableData').append(
            $('<tr>')
                .append($('<td>').text(j))
                .append($('<td>').text(list[i].majorName))
                .append(
                    $('<td>')
                        .append($('<a href="javascript:;" onclick="toEdit('+list[i].id+',\''+list[i].majorName+'\');" >').text('编辑'))
                        .append(' ')
                        .append($('<a href="javascript:;" onclick="toDel('+list[i].id+');" >').text('删除'))

                )
        );
        j++;
    }
    $('#mytable').table().data( "table" ).refresh();
}

function toDel(id){
    layer.confirm("确定要删除该专业吗?", {
        btn: ['确定','取消'] //按钮
    }, function(){
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.post(web_path + '/maintainer/major/deleteMajor',{
            'id':id
        },function(data){
            layer.close(index);
            if(data.state){
                layer.msg(data.msg, {icon: 1},function(){
                    window.location.reload(true);
                });
            } else {
                layer.msg(data.msg);
            }
        },'json');
    });
}

function toEdit(id,majorName){
    $('#majorId').val(id);
    $('#update-majorname').val(majorName);
    openModal('#updateModal');
}

/**
 * 重置搜索
 */
function refresh(){
    $('#majorName').val('');
    $('#searchForm').submit();
}

/**
 * 打开模态框
 * @param target
 */
function openModal(target){
    var modal = UIkit.modal(target);
    if (!modal.isActive()) {
        modal.show();
    }
}

/**
 * 关闭模态框
 * @param target
 */
function cancel(target){
    var modal = UIkit.modal(target);
    if (modal.isActive()) {
        modal.hide();
    }
}

function action(){
    $.post(web_path + '/maintainer/major/majorData',param,
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