/**
 * Created by lenovo on 2016-06-07.
 */

/**
 * 前端封装参数
 * @type {{taskInfoId: (*|Document.taskInfoId), taskTitleId: (*|Document.taskTitleId), content: *, pageNum: number, pageSize: number}}
 */
var param = {
    'taskInfoId': fourItemsLineVo.taskInfoId,
    'taskTitleId':fourItemsLineVo.taskTitleId,
    'content':fourItemsLineVo.content,
    'pageNum': 1,
    'pageSize': 7
}

function titles(id,title){
    this.id = id;
    this.title = title;
}

/**
 * 初始化搜索参数
 * @param selects
 */
function initSearch(selects){
    $('#taskTitleId').empty();
    $('#taskTitleId').append($('<option value="0">').text('请选择标题'));
    for(var i = 0;i<selects.length;i++){
        if(fourItemsLineVo.taskTitleId == selects[i].id){
            $('#taskTitleId').append($('<option value="'+selects[i].id+'" selected="selected">').text(selects[i].title));
        } else {
            $('#taskTitleId').append($('<option value="'+selects[i].id+'">').text(selects[i].title));
        }

    }
    $('#content').val(fourItemsLineVo.content);
    $('#taskInfoId').val(fourItemsLineVo.taskInfoId);
}

/**
 * 输出数据
 * @param data
 */
function outputHtml(data) {
    $('#datas').empty();
    var result = data.single.result;
    var contentX = data.single.contentX;
    var fourItems = data.single.fourItems;

    //获取标题
    var selects = [];
    var count = true;

    for (var i = 0; i < contentX.length; i++) {
        var teachTaskTitle = '';
        var s = '<ul class="uk-grid uk-grid-width-1-1 uk-grid-width-medium-1-2 uk-grid-width-large-1-3">';
        for (var j = 0; j < result.length; j++) {
            if (contentX[i] == result[j].contentX) {
                s = s + '<li>' + result[j].title + " : " + result[j].content + '</li>';
                teachTaskTitle = result[j].teachTaskTitle;
                if(count){
                    selects.push(new titles(result[j].taskTitleId,result[j].title))
                }
            }
        }
        count = false;
        s = s + '</ul>';
        var fourItemsName = '';
        var fourItemId = 0;
        var fourItemUrl = '';
        for (var k = 0; k < fourItems.length; k++) {
            if (fourItems[k].contentX == contentX[i]) {
                fourItemsName = fourItems[k].fourItemsFileName + "." + fourItems[k].fileType;
                fourItemId = fourItems[k].id;
                fourItemUrl = fourItems[k].fourItemsFileUrl;
                break;
            }
        }

        var down = '';
        if(fourItemsName != ''){
             down = '<a href="'+web_path+'/administrator/eadmin/downloadFourItemsLine?id='+fourItemId+'" >'+fourItemsName+'</a>'+
                ' <a href="javascript:;" onclick="toDel('+fourItemId+');" >删除</a>';
        }
        $('#datas').append($('<div class="uk-panel uk-panel-box">')
            .append($('<h3 class="uk-panel-title">').text(teachTaskTitle))
            .append(s)
            //.append($('<p>').text(fourItemsName))
            .append($('<p>').html(down))
            .append($('<div class="uk-text-center">').append($('<button class="uk-button uk-button-primary" type="button" onclick="toUpload(' + contentX[i] + ',\'' + fourItemsName + '\',this,'+fourItemId+',\''+fourItemUrl+'\');" >').text('上传四大件')))
        );
    }

    if (contentX.length > 0) {
        createPage(data);
        initSearch(selects);
    }
}

/**
 * 删除四大件
 * @param id
 */
function toDel(id){
    layer.confirm("确定要删除该四大件吗?", {
        btn: ['确定','取消'] //按钮
    }, function(){
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.post(web_path + '/administrator/eadmin/deleteFourItemsLine',{
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

/**
 * 创建分页
 * @param data
 */
function createPage(data) {
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
 * 打开上传模态框
 * @param x
 * @param name
 * @param obj
 * @param fourItemId
 * @param fourItemUrl
 */
function toUpload(x, name,obj,fourItemId,fourItemUrl) {
    $('#fourItemId').val(fourItemId);
    $('#fourItemContentX').val(x);
    $('#uploadFile').val(name);
    $('#filePath').val(fourItemUrl);
    var modal = UIkit.modal("#upload");

    if ( modal.isActive() ) {
        modal.hide();
    } else {
        modal.show();
    }
}

/**
 * 关闭模态框
 * @param target
 */
function cancel(target){
    var modal = UIkit.modal(target);

    if ( modal.isActive() ) {
        modal.hide();
    } else {
        modal.show();
    }
}

/**
 * 获取数据
 */
function action() {
    $.post(web_path + '/administrator/eadmin/fourItemsLineData', param,
        function (data) {
            if (data.state) {
                outputHtml(data);
            }
        }, 'json')
}

/**
 * 重置搜索
 */
function refresh() {
    $('#taskTitleId').val(0);
    $('#content').val('');
    $('#searchForm').submit();
}

$(document).ready(function () {
    action();
});

$(function () {

    $('#submitData').attr('disabled', true);//未上传教学任务书前,不能提交数据.

    $('#fileupload').fileupload({
        url: web_path + '/student/uploadFile',
        formData: {pathname: 'fouritems'},
        dataType: 'json',
        send: function (e, data) {
            $('.uk-progress').removeClass('uk-hidden');
            $('.uk-progress-bar').css(
                'width',
                '0%'
            );
            return true;
        },
        done: function (e, data) {
            $('#uploadFile').val(data.result.single.single.originalFilename + "." + data.result.single.single.ext);
            $('#filePath').val("/files/fouritems/" + data.result.single.single.newName);
            $('#submitData').attr('disabled', false);//上传任务书后可提交数据.
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('.uk-progress-bar').css(
                'width',
                progress + '%'
            );
        }
    });

    /* nice validator*/
    $('#uploadForm').validator({
        stopOnError: false,
        timely: 'yellow_right_effect',
        ignore: ':hidden',
        validClass: 'uk-form-success',
        invalidClass: 'uk-form-danger',
        fields: {
            'uploadFile': 'required;'
        },
        valid: function (form) {
            var me = this;
            // 提交表单之前，hold住表单，并且在以后每次hold住时执行回调
            //loading层
            var index = layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
            me.holdSubmit();
            var url = '';
            if(Number($('#fourItemId').val()) <= 0 ){
                url = "/administrator/eadmin/addFourItemsLine";
            } else {
                url = "/administrator/eadmin/updateFourItemsLine";
            }
            $.ajax({
                url: web_path + url,
                data: {
                    'fourItemId':$('#fourItemId').val().trim(),
                    'fourItemContentX':$('#fourItemContentX').val().trim(),
                    'uploadFile':$('#uploadFile').val().trim(),
                    'filePath':$('#filePath').val().trim(),
                    'teachTaskInfoId':fourItemsLineVo.taskInfoId
                },
                type: "POST",
                success: function (data) {
                    // 提交表单成功后，释放hold，就可以再次提交
                    me.holdSubmit(false);
                    layer.close(index);
                    if (data.state) {
                        layer.msg(data.msg, {icon: 1}, function () {
                            window.location.reload(true);
                        });
                    } else {
                        layer.msg(data.msg);
                    }
                }
            });
        }
    });
});

/**
 * 提交上传文件
 */
function submitData(){
    $('#uploadForm').submit();
}
