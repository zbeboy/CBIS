/**
 * Created by lenovo on 2016-05-25.
 */
function initPage(data) {
    var metrics = JSON.parse(data.single.metrics);
    $('#sysUptime').text(metrics['uptime']);
    $('#sysInstanceUptime').text(metrics['instance.uptime']);
    var memFree = Number(metrics['mem.free']) / Number(metrics['mem']);
    console.log(Number(metrics['mem.free']));
    console.log(Number(metrics['mem']));
    console.log(memFree);
    if (memFree > 80) {
        $('#sysMemFree').parent().addClass('uk-progress-danger');
    } else if (memFree <= 80 && memFree > 50) {
        $('#sysMemFree').parent().removeClass('uk-progress-danger').addClass('uk-progress-warning');
    } else {
        $('#sysMemFree').parent().removeClass('uk-progress-danger').removeClass('uk-progress-warning');
    }
    $('#sysMemFree').css('width', (memFree * 100) + "%");
    var classesLoaded = Number(metrics['classes.loaded']) / Number(metrics['classes']);
    $('#sysClassesLoaded').css('width', (classesLoaded * 100) + "%");
    $('#sysDatasourceActive').text(metrics['datasource.primary.active']);
    $('#sysDatasourceUsage').text(metrics['datasource.primary.usage']);
}

function action() {
    var index = layer.load(1, {
        shade: [0.1,'#fff'] //0.1透明度的白色背景
    });
    $.get(web_path + '/maintainer/system/systemMetricsData', function (data) {
        layer.close(index);
        if (data.state) {
            //console.log(JSON.parse(data.single.metrics));
            initPage(data);
        }
    }, 'json');
}

$(document).ready(function () {
    var int = self.setInterval("action()", 5000);
});