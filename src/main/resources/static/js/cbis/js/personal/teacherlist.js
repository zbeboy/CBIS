/**
 * Created by lenovo on 2016-02-17.
 */
function resetPassword(obj){
    layer.confirm('您确定要重置该用户的密码吗？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        var teacherJobNumber = $(obj).prev().prev().text();
        $.post('/cbis/maintainer/resetpassword',{
            'username':teacherJobNumber
        },function(data){
            if(data.state){
                layer.msg(data.msg, {icon: 1});
            } else {
                layer.msg(data.msg);
            }
        },'json');
    });
}

var old_username = null;

function openEditModal(obj){
    var modal = UIkit.modal('#editModal');
    var username = $(obj).prev().text();
    $('#edit-username').val(username);
    old_username = username;
    if ( !modal.isActive() ) {
        modal.show();
    }
}

function openStateModal(obj){
    var modal = UIkit.modal('#stateModal');
    if($(obj).prev().text() === 'y'){
        $('#form-s-r').attr('checked','checked');
    } else if($(obj).prev().text() === 'n'){
        $('#form-s-r1').attr('checked','checked');
    }
    if ( !modal.isActive() ) {
        modal.show();
    }
}

function cancel(obj){
    var modal = UIkit.modal(obj);
    if ( modal.isActive() ) {
        modal.hide();
    }
}

function edit(){


}

function state(){
    var n = $("input[type='radio']:checked").val();
}