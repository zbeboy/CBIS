/**
 * Created by lenovo on 2016-01-13.
 */
/**
 * 替换网页文本中html特殊字符
 * @param s 待处理文本
 * @returns {string}
 */
function repalceHtmlCode(s){

    var rs1 = "";

    var pattern = new RegExp("[&]");
    for (var i = 0; i < s.length; i++) {
        rs1 = rs1+s.substr(i, 1).replace(pattern, '&amp;');
    }

    var rs2 = "";
    var pattern = new RegExp("[<]");
    for (var i = 0; i < rs1.length; i++) {
        rs2 = rs2+rs1.substr(i, 1).replace(pattern, '&gt;');
    }

    var rs3 = "";
    var pattern = new RegExp("[>]");
    for (var i = 0; i < rs2.length; i++) {
        rs3 = rs3+rs2.substr(i, 1).replace(pattern, '&frasl;');
    }

    var rs4 = "";
    var pattern = new RegExp("[/]");
    for (var i = 0; i < rs3.length; i++) {
        rs4 = rs4+rs3.substr(i, 1).replace(pattern, '&quot;');
    }

    var rs5 = "";
    var pattern = new RegExp("[\"]");
    for (var i = 0; i < rs4.length; i++) {
        rs5 = rs5+rs4.substr(i, 1).replace(pattern, '&quot;');
    }

    return rs5;
}