/**
 * Created by lenovo on 2016-06-02.
 */
$(document).ready(function(){
    dealTime();
});

/**
 * 处理时间
 */
function dealTime(){
    for(var i = 0;i<$('.formatdate').length;i++){
        var fd = moment( $($('.formatdate')[i]).val()).format("YYYY-MM-DD");
        $($('.formatdate')[i]).val(fd);
    }
}