/**
 * Created by lenovo on 2016-01-31.
 */
$(function(){

    //先选出 textarea 和 统计字数 dom 节点
    var wordCount = $("#wordCount"),
        textArea = wordCount.find("textarea"),
        word = wordCount.find(".word");
    //调用
    statInputNum(textArea,word);

});
/*
 * 剩余字数统计
 * 注意 最大字数只需要在放数字的节点哪里直接写好即可 如：<var class="word">200</var>
 */
function statInputNum(textArea,numItem) {
    var max = numItem.text(),
        curLength;
    textArea[0].setAttribute("maxlength", max);
    curLength = textArea.val().length;
    numItem.text(max - curLength);
    textArea.on('input propertychange', function () {
        numItem.text(max - $(this).val().length);
    });
}