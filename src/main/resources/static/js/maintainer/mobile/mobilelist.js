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

    $( '#ajaxed' ).html( "<table class='tablesaw tablesaw-stack' data-tablesaw-mode='stack' id='mytable'><thead><tr><th scope='col' data-tablesaw-priority='persist'>接收账号</th><th scope='col'>内容</th><th scope='col'>接收手机</th><th scope='col'>时间</th></tr></thead><tbody id='place1'></tbody></table>" );

    var tbody = $( '#mytable tbody' ), props = ["acceptUser","content","acceptMobile","sendTime"];

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
    $.post(web_path + '/maintainer/mobile/mobileListData',param,
        function(data){
            outputHtml(data);
            if(data.result.length >0){
                createPage(data);
            }
        },'json');
}

$( document ).ready(function() {
    action();
});