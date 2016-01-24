/**
 * Created by lenovo on 2016-01-13.
 */
function UKalert(msg){
    UIkit.modal.alert(msg);
}

function UKBlockUI(msg,time){
    modal = UIkit.modal.blockUI(msg);
    setTimeout(function(){ modal.hide() }, time);
}

function UKBlockUIWithUrl(msg,time,url){
    modal = UIkit.modal.blockUI(msg);
    setTimeout(function(){ modal.hide(), window.location.href=url}, time)
}

function UKLoad(){
    modal = UIkit.modal.blockUI("<div class='uk-modal-spinner'></div>");
    return modal;
}

function UKConfirm(msg,url){
    UIkit.modal.confirm(msg, function(){ window.location.href=url});
}
