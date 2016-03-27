/**
 * Created by lenovo on 2016-03-09.
 */
$('[data-uk-switcher]').on('show.uk.switcher', function (event, area) {
    for (var i = 0; i < $('#time-id').children().length; i++) {
        if ($($('#time-id').children()[i]).attr('data') === $(area).attr('data')) {
            outputHtml(id = $(area).attr('data'), i);
        }
    }

});


/**
 * 输出数据
 * @param data
 */
function outputHtml(id, elementIndex) {
    $.post(web_path + '/user/tie/tieNoticeTimeDropData', {
            'id': id,
            'bigTitle': bigTitle,
            '_csrf': $("meta[name='_csrf']").attr("content")
        },
        function (data) {
            $($('#time-id').children()[elementIndex]).empty();
            if (data.state) {
                $($('#time-id').children()[elementIndex]).append($('<ul class="uk-list uk-list-space uk-list-line" >'));
                for (var i = 0; i < data.result.length; i++) {
                    $($('#time-id').children()[elementIndex]).children().append(
                        $('<li>').append(
                            $('<div class="uk-grid">').append(
                                $('<div class="uk-width-4-5 uk-text-truncate" >').append(
                                    $('<a>').attr('href', web_path + '/user/tie/tieNoticeShow?id=' + data.result[i].id).text(data.result[i].bigTitle)
                                )
                            ).append(
                                $('<div class="uk-width-1-5 uk-text-muted uk-text-right">').text(data.result[i].date)
                            )
                        )
                    );
                }
            } else {
                layer.msg(data.msg);
            }
        }, 'json');
}

$('#loginmodal').on({

    'show.uk.modal': function () {
        $('#username').focus();
    }
});