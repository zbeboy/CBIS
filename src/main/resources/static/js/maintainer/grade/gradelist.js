/**
 * Created by lenovo on 2016-02-14.
 */

var gradeModal = null;//添加模态框
var gradeName = null;//检验班级名
function initPage(){
    var y = new Date().getFullYear();
    for (var i = (y - 30); i < (y + 30); i++) //以今年为准，前30年，后30年
        document.gradeForm.year.options.add(new Option(" " + i, i));

    UIkit.autocomplete("#gradeHeadAuto", {source: web_path + "/maintainer/grade/gradeHead", minLength: 2});
    gradeModal = UIkit.modal("#gradeModal");
}

function addData(){
    $('#gradeForm').attr('action', web_path + '/maintainer/grade/addGrade');
    $('#modalTitle').text("添加班级");
    $($('#majorName').children()[0]).attr("selected", "selected");
    $('#year').val('');
    $('#gradeName').val('');
    $('#gradeHeadID').val('');
    $('#gradeId').val(-1);
    gradeModal.show();
}

function cancel() {
    window.location.reload(true);
}

/**
 * 前端分页参数
 * @type {{bigTitle: string, realName: string, pageNum: number, pageSize: number}}
 */
var param = {
    'majorName':$('#majorSearchName').val().trim(),
    'gradeName':$('#gradeSearchName').val().trim(),
    'gradeHead':$('#gradeHead').val().trim(),
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
        "<th scope='col' data-tablesaw-priority='persist'>专业</th>" +
        "<th scope='col'>年级</th>" +
        "<th scope='col'>班级</th>" +
        "<th scope='col'>班主任</th>" +
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
                .append($('<td>').text(list[i].majorName))
                .append($('<td>').text(list[i].year))
                .append($('<td>').text(list[i].gradeName))
                .append($('<td>').text(list[i].gradeHead))
                .append(
                    $('<td>')
                        .append($('<a href="javascript:;" onclick="toEdit('+list[i].id+');" >').html('<i class="uk-icon-pencil"></i>'))
                        .append(' ')
                        .append($('<a href="javascript:;" onclick="toDel('+list[i].id+');" >').html('<i class="uk-icon-trash uk-text-danger"></i>'))

                )
        );
    }
    $('#mytable').table().data( "table" ).refresh();
}

function toDel(id){
    layer.confirm("确定要删除该文章吗?", {
        btn: ['确定','取消'] //按钮
    }, function(){
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.post(web_path + '/maintainer/grade/deleteGrade',{
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

function toEdit(id){
    $.post(web_path + '/maintainer/grade/gradeUpdate',{
        'id':id
    },function(data){
        if(data.state){
            $('#gradeForm').attr('action', web_path + '/maintainer/grade/updateGrade');
            $('#modalTitle').text("更新班级");
            for (var i = 0; i < $('#majorName').children().length; i++) {
                if (Number($($('#majorName').children()[i]).val()) === data.obj.majorId) {
                    $($('#majorName').children()[i]).attr("selected", "selected");
                    break;
                }
            }
            $('#year').val(data.obj.year);
            $('#gradeName').val(data.obj.gradeName);
            gradeName = data.obj.gradeName;
            $('#gradeHeadID').val(data.obj.gradeHeadID);
            $('#gradeId').val(data.obj.id);
            gradeModal.show();
        } else {
            layer.msg(data.msg);
        }

    },'json');
}

/**
 * 重置搜索
 */
function refresh(){
    $('#majorSearchName').val('');
    $('#gradeSearchName').val('');
    $('#gradeHead').val('');
    $('#searchForm').submit();
}

function action(){
    $.post(web_path + '/maintainer/grade/gradeData',param,
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
    initPage();
    action();
});