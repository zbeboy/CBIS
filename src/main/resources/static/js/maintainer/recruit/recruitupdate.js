/**
 * Created by lenovo on 2016-05-26.
 */
function initPage() {
    var c = $('#fitMajor').children();
    if (fitMajor != null && fitMajor.trim().length > 0) {
        var u = fitMajor.split(",");
        for (var i = 0; i < u.length; i++) {
            for (var j = 0; j < c.length; j++) {
                if (u[i] === $(c[j]).text()) {
                    $(c[j]).attr('selected', true);
                    break;
                }
            }
        }
    }

    $('#fitMajor').chosen({'max_selected_options': 3});
}

$(document).ready(function () {
    initPage();
});