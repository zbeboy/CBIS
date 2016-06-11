/**
 * Created by lenovo on 2016-02-07.
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
        "<th scope='col'>首页显示</th>" +
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
        var s = '';
        var show = 0;
        var oshow = '';
        if(list[i].isShow == 1){
            s = '显示';
            show = 0;
            oshow = '<i class="uk-icon-eye-slash"></i>';
        } else {
            s = '不显示';
            show = 1;
            oshow = '<i class="uk-icon-eye"></i>';
        }

        $('#tableData').append(
            $('<tr>')
                .append($('<td>').text(dealNull(list[i].majorName)))
                .append($('<td>').text(dealNull(list[i].bigTitle)))
                .append($('<td>').text(dealNull(list[i].realName)))
                .append($('<td>').text(dealNull(list[i].date)))
                .append($('<td>').text(s))
                .append(
                    $('<td>')
                        .append($('<a href="javascript:;" onclick="toShow('+list[i].id+','+show+');" >').html(oshow))
                        .append(' ')
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

/**
 * 首页显示
 * @param id
 * @param show
 */
function toShow(id,show){
    var s = '';
    if(Number(show) == 0){
        s = "确定不在首页显示该专业吗?";
    } else {
        s = "确定在首页显示该专业吗?"
    }
    layer.confirm(s, {
        btn: ['确定','取消'] //按钮
    }, function(){
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.post(web_path + '/maintainer/major/updateMajorIntroduceShow',{
            'id':id,
            'isShow':show
        },function(data){
            layer.close(index);
            if(data.state){
                layer.msg(data.msg, {icon: 1});
                action();
            } else {
                layer.msg(data.msg);
            }
        },'json');
    });
}

function toEdit(id){
    window.location.href = web_path + '/maintainer/major/majorIntroduceUpdate?majorId=' + id;
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
    var index = layer.load(1, {
        shade: [0.1,'#fff'] //0.1透明度的白色背景
    });
    $.post(web_path + '/maintainer/major/majorIntroduceData',param,
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