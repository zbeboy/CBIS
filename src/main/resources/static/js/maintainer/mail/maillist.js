/**
 * Created by lenovo on 2016-05-19.
 */
/**
 * 前端参数
 * @type {{username: string, startDate: string, endDate: string, pageNum: number, pageSize: number}}
 */
var param = {
    'username':$('#username').val().trim(),
    'startDate':$('#startDate').val().trim(),
    'endDate':$('#endDate').val().trim(),
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
 * 输出html
 * @param data
 */
function outputHtml(data){

    $('#ajaxed').empty();

    var mydata = data.result;

    $( '#ajaxed' ).html( "<table class='tablesaw tablesaw-stack' data-tablesaw-mode='stack' id='mytable'><thead><tr><th scope='col' data-tablesaw-priority='persist'>接收账号</th><th scope='col'>标题</th><th scope='col'>接收邮箱</th><th scope='col'>时间</th></tr></thead><tbody id='place1'></tbody></table>" );

    var tbody = $( '#mytable tbody' ), props = ["acceptUser","subject","acceptEmail","sendTime"];

    $.each( mydata, function(i, value){
        var tr = $('<tr>');

        $.each(props, function(i, prop){
            $('<td>').html(value[prop]).appendTo(tr);
        });

        tbody.append(tr);
    });

    $('#mytable').table().data( "table" ).refresh();
}

/**
 * 初始化统计参数
 * @param data
 */
function initDaily(data){
    $('#currentDate').text(data.single.currentDate);
    $('#dailySendNum').text(data.single.dailySendNum);
    $('#dailyLimit').text(data.single.dailyLimit);
    $('#lessNum').text((data.single.dailyLimit - data.single.dailySendNum));
}

/**
 * 重置搜索
 */
function refresh(){
    $('#username').val('');
    $('#startDate').val('');
    $('#endDate').val('');
    $('#searchForm').submit();
}

/**
 * 执行入口
 */
function action(){
    var index = layer.load(1, {
        shade: [0.1,'#fff'] //0.1透明度的白色背景
    });
    $.post(web_path + '/maintainer/mail/mailListData',param,
    function(data){
        layer.close(index);
        outputHtml(data);
        if(data.result.length >0){
            createPage(data);
        }
        initDaily(data);
    },'json');
}

$( document ).ready(function() {
    action();
});