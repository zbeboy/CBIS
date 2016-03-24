/**
 * Created by lenovo on 2016-01-13.
 */
/**
 * 用于jquery json csrf全局设置
 */
$(function () {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    $(document).ajaxSend(function (e, xhr, options) {
        xhr.setRequestHeader(header, token);
    });
});
function getToken() {
    var token = $("meta[name='_csrf']").attr("content");
    return token;
}

function getHeader() {
    var header = $("meta[name='_csrf_header']").attr("content");
    return header;
}
