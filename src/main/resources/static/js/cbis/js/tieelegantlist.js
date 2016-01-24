/**
 * Created by lenovo on 2016-01-18.
 */
function articleUpdate(obj){
    window.location.href="/admin/tieelegantupdate?id="+$(obj).prev().text();
}

function deleteTieElegant(obj) {
    UIkit.modal.confirm("你确定要删除该文章吗？", function () {
        $.post('/admin/deleteTieElegant', {
            'id': $(obj).prev().text()
        }, function (data, status) {
            if (status) {
                if (data.state) {
                    UKBlockUIWithUrl(data.msg, 1000, "/admin/tieelegant");
                } else {
                    UKBlockUI(data.msg, 1000);
                }
            }
        });
    });
}