/**
 * Created by lenovo on 2016-05-19.
 */
/**
 * 更换验证码
 */
function newCaptcha(){
    $('#jcaptcha').attr('src',web_path + '/user/jcaptcha?d='+new Date().getTime());
}

/**
 * 验证 验证码
 */
$('input[name="j_captcha_response"]').on('invalid.rule', function(e, ruleName){
    //“remote”规则验证失败时
    console.log(ruleName);
    if (ruleName === 'remote') {
        //do something...
        newCaptcha();
    }
});