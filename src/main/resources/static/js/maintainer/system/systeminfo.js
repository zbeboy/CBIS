/**
 * Created by lenovo on 2016-05-25.
 */

/**
 * 初始化页面数据
 * @param data
 */
function initPage(data){
    //health
    var health = JSON.parse(data.single.health);
    if(health.status === 'UP'){
        $('#sysStatus').text('正常');
    } else {
        $('#sysStatus').addClass('uk-badge-danger');
        $('#sysStatus').text('异常');
    }

    if(health.diskSpace.status === 'UP'){
        $('#sysDiskStatus').text('正常');
    } else {
        $('#sysDiskStatus').addClass('uk-badge-danger');
        $('#sysDiskStatus').text('异常');
    }

    $('#sysDiskTotal').text(health.diskSpace.total + "B")
    $('#sysDiskFree').text(health.diskSpace.free + "B");

    if(health.db.status === 'UP'){
        $('#sysDbStatus').text('正常');
    } else {
        $('#sysDbStatus').addClass('uk-badge-danger');
        $('#sysDbStatus').text('异常');
    }
    $('#sysDb').text(health.db.database);

    //env
    var env = JSON.parse(data.single.env);
    $('#sysProfiles').text(env.profiles.join(","));
    $('#sysJavaRunTime').text(env.systemProperties['java.runtime.name']);
    $('#sysJavaRunTimeVersion').text(env.systemProperties['java.runtime.version']);
    $('#sysOsName').text(env.systemProperties['os.name']);
    $('#sysOsVersion').text(env.systemProperties['os.version']);
    $('#sysUser').text(env.systemProperties['user.name']);
    $('#sysCPU').text(env.systemProperties['os.arch']);
    $('#sysUserLanguage').text(env.systemProperties['user.language']);
    $('#sysFileEncode').text(env.systemProperties['file.encoding']);
    $('#sysJavaHome').text(env.systemProperties['java.home']);
    $('#sysJavaVm').text(env.systemProperties['java.vm.name']);

    //project
    var info = JSON.parse(data.single.info);
    $('#sysProjectName').text(info.build.artifactId);
    $('#sysProjectVersion').text(info.build.version);

    //email
    var email = data.single.email;
    if(email.switch){
        $('#sysEmailSwitch').text('开启');
    } else {
        $('#sysEmailSwitch').text('关闭');
    }
    $('#sysEmailUsername').text(email.username);
    $('#sysEmailPassword').text(email.password);
    $('#sysEmailHost').text(email.host);
    $('#sysEmailPort').text(email.port);

    //mobile
    var mobile = data.single.mobile;
    if(mobile.switch){
        $('#sysMobileSwitch').text('开启');
    } else {
        $('#sysMobileSwitch').text('关闭');
    }
    $('#sysMobileApiKey').text(mobile.apikey);
}

/**
 * 执行入口
 */
function action(){
    $.get(web_path + '/maintainer/system/systemManagerData',function(data){
        if(data.state){
            //console.log(JSON.parse(data.single.health));
            //console.log(JSON.parse(data.single.env));
            //console.log(JSON.parse(data.single.info));
            //console.log(data.single.email);
            //console.log(data.single.mobile);
            initPage(data);
        }
    },'json');
}

$(document).ready(function(){
    action();
});