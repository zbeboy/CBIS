/**
 * Created by lenovo on 2016-02-28.
 */
//全局配置
var articleWordType = '';//文章类别
var checkArticleTitle = true;//开启标题检验
var checkArticleContent = true;//开启内容检验
var checkArticlePic = true;//开启封面检验
var articleSaveOrUpdateUrl = '';//文章保存或更新地址
var clickOkUrl = '';//文章保存或更新成功点击确定需要刷新的地址
var clickNoUrl = '';//文章保存或更新成功点击取消需要刷新的地址
var uploadParamFileName = '';//上传图片需要保存图片的文件夹名
var cleanFromClient = false;//删除图片时，服务器端也删除，默认只删除html dom
var cleanUrl = '';//若开启cleanFromClient,此项必填，删除请求地址

//初始化配置
function initArticleParam(initArticleWordType, initCheckArticleTitle, initCheckArticleContent, initCheckArticlePic, initArticleSaveOrUpdateUrl,
                          initClickOkUrl, initClickNoUrl, initUploadParamFileName, initCleanFromClient, initCleanUrl) {
    articleWordType = initArticleWordType;
    checkArticleTitle = initCheckArticleTitle;
    checkArticleContent = initCheckArticleContent;
    checkArticlePic = initCheckArticlePic;
    articleSaveOrUpdateUrl = initArticleSaveOrUpdateUrl;
    clickOkUrl = initClickOkUrl;
    clickNoUrl = initClickNoUrl;
    uploadParamFileName = initUploadParamFileName;
    cleanFromClient = initCleanFromClient;
    cleanUrl = initCleanUrl;
}


//全局变量 添加子标题和文章模板
var SUB_ARTICLE = "<div class='uk-alert' data-uk-alert=''>" +
    "<a href='' class='uk-alert-close uk-close'></a>" +
    "<div class='uk-form-row'>" +
    "<label class='uk-form-label' for='title'>标题</label>" +
    "<div class='uk-form-controls'>" +
    "<input type='text' class='uk-form-width-large mysubtitle' placeholder='标题' />" +
    "<p class='uk-text-danger titleerror'></p>" +
    "</div>" +
    "</div>" +
    "<div class='uk-form-row'>" +
    "<textarea cols='120' class='mysubpage' rows='5' placeholder='文章'></textarea>" +
    "<p class='uk-text-danger summaryerror'></p>" +
    "</div>" +
    "</div>";
//全局变量 保存图片显示模板代码
var IMG_SHOW = $('#imgShow').html();

//全局变量 保存服务器图片路径
var imgPath = "";

/**
 * 字数控制
 */
$(document).ready(function () {
    //default usage
    $("#title").charCount({
        allowed: 50,
        warning: 10,
        counterText: '剩余字数: '
    });
    //custom usage
    $("#summary").charCount({
        allowed: 2000,
        warning: 100,
        counterText: '剩余字数: '
    });
});

/**
 * 动态添加子标题文章
 */
function addsubarticle() {
    $('.uk-sortable').append(SUB_ARTICLE);
}

/**
 * 文章实体对象
 * @param title 大标题
 * @param summary 文章概述
 * @param picPath 图片服务端路径
 * @param subTitle 子标题
 * @param subPage 子内容
 */
function articleData(title, summary, picPath, subTitle, subPage, articleType, row) {
    this.title = title;
    this.summary = summary;
    this.picPath = picPath;
    this.subTitle = subTitle;
    this.subPage = subPage;
    this.articleType = articleType;
    this.row = row;
}

/**
 * 保存文章
 */
function saveArticle() {

    /*暂时先不替换html中特殊字符*/

    var title = repalceHtmlCode($('#title').val());
    /*大标题*/
    var summary = repalceHtmlCode($('#summary').val());
    /*文章概述*/
    var picPath = imgPath;
    /*图片绝对路径*/
    var subtitle = $('.mysubtitle');
    /*子标题数据*/

    var subData = new Array();

    subData.push(new articleData(title, summary, picPath, "", "", articleWordType, -1));
    /*可以没有子标题数据，只组装第一条*/
    for (var i = 0; i < subtitle.length; i++) {
        if ($($('.mysubtitle')[i]).val().trim().length > 0 || $($('.mysubpage')[i]).val().trim().length > 0) {/*当子标题与内容都为空时，不算入文章*/
            if ($($('.mysubtitle')[i]).val().trim().length <= 50) {
                if ($($('.mysubpage')[i]).val().trim().length <= 2000) {
                    subData.push(new articleData(title, summary, picPath, repalceHtmlCode($($('.mysubtitle')[i]).val()), repalceHtmlCode($($('.mysubpage')[i]).val()), articleWordType, i));
                } else {
                    $($('.summaryerror')[i]).text("文章简介长度应小于2000个字符！");
                }
            } else {
                $($('.titleerror')[i]).text("标题长度应小于50个字符！");
            }
        }
    }

    /*校验标题*/
    if (checkArticleTitle) {
        if ($('#title').val().trim().length <= 0 || $('#title').val().trim().length > 50) {
            $('#titileError').text('请添加1~50个字符长度的标题！');
            $('#title').removeClass('uk-form-success').addClass('uk-form-danger');
            $('#title').focus();
            return;
        } else {
            $('#titileError').text('');
            $('#title').removeClass('uk-form-danger').addClass('uk-form-success');
        }
    }

    /*校验文章*/
    if (checkArticleContent) {
        if ($('#summary').val().trim().length < 6 || $('#summary').val().trim().length > 2000) {
            $('#summaryError').text('请添加6~2000个字符长度的文章简介！');
            $('#summary').removeClass('uk-form-success').addClass('uk-form-danger');
            $('#summary').focus();
            return;
        } else {
            $('#summaryError').text('');
            $('#summary').removeClass('uk-form-danger').addClass('uk-form-success');
        }
    }

    /*校验是否上传了封面图片 */
    if (checkArticlePic) {
        if (picPath.trim().length <= 0) {
            layer.msg('请先上传一张封面图片！');
            return;
        }
    }

    /*发送数据到后台*/
    sendArtitle(JSON.stringify(subData));
}

/**
 * ajax 发送文章
 * @param subData json 字符串数据
 */
function sendArtitle(subData) {

    var index = layer.load(1, {shade: false});

    $.post(articleSaveOrUpdateUrl, {
        'subData': subData
    }, function (data, status) {
        layer.close(index);
        if (status) {
            if (data.state) {
                layer.confirm(data.msg, {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    window.location.href = clickOkUrl;
                }, function () {
                    window.location.href = clickNoUrl;
                });
            } else {
                layer.msg(data.msg);
            }
        } else {
            layer.msg("网络异常，请稍后重试！");
        }
    }, 'json');
}

/**
 * 校验标题
 */
function writerTitle() {
    if ($('#title').val().trim().length <= 0 || $('#title').val().trim().length > 50) {
        $('#titileError').text('请添加1~50个字符长度的标题！');
        $('#title').removeClass('uk-form-success').addClass('uk-form-danger');
    } else {
        $('#titileError').text('');
        $('#title').removeClass('uk-form-danger').addClass('uk-form-success');
    }
}

/**
 * 校验概述
 */
function writerSummary() {
    if ($('#summary').val().trim().length < 6 || $('#summary').val().trim().length > 2000) {
        $('#summaryError').text('请添加6~2000个字符长度的文章简介！');
        $('#summary').removeClass('uk-form-success').addClass('uk-form-danger');
    } else {
        $('#summaryError').text('');
        $('#summary').removeClass('uk-form-danger').addClass('uk-form-success');
    }
}

/**
 * 上传图片到服务器端
 */
$(function () {
    var progressbar = $("#progressbar"),
        bar = progressbar.find('.uk-progress-bar'),
        settings = {

            action: '/maintainer/uploadPicture', // upload url

            allow: '*.(jpeg|jpg|gif|png|JPEG|JPG|GIF|PNG)', // allow only images

            params: {'pathname': uploadParamFileName},//json 数据 该文件服务器端相对 files下路径

            loadstart: function () {
                bar.css("width", "0%").text("0%");
                progressbar.removeClass("uk-hidden");
            },

            progress: function (percent) {
                percent = Math.ceil(percent);
                bar.css("width", percent + "%").text(percent + "%");
            },

            allcomplete: function (response) {

                bar.css("width", "100%").text("100%");

                setTimeout(function () {
                    progressbar.addClass("uk-hidden");
                }, 250);

                //隐藏上传组件
                $('#upload-drop').addClass('uk-hidden');
                //获取服务器端图片相对路径
                var str = response.substring(response.lastIndexOf('\\') + 1, response.length);

                //若图片组件已被删除，需要重新添加图片显示组件
                if ($('#imgShow').html().trim().length <= 0) {
                    $('#imgShow').append(IMG_SHOW);
                }

                //显示图片
                $('#articleimg').attr('src', '/files/' + uploadParamFileName + '/' + str);
                $('#articleimg').data('path', response);
                $('#articleimg').parent().parent().removeClass('uk-hidden');

                //保存服务器端绝对路径
                imgPath = response;
            }
        };

    var select = UIkit.uploadSelect($("#upload-select"), settings),
        drop = UIkit.uploadDrop($("#upload-drop"), settings);
});


/**
 * 删除图片 包括服务器端图片路径
 */
function cleanimg() {

    if (cleanFromClient) {
        var path = $('#articleimg').data('path');
        if (path != null) {
            $.post(cleanUrl, {
                'path': path
            }, function (data, status) {

                if (status) {/*网络正常*/
                    if (data.state) {
                        /*删除成功 重新显示上传组件*/
                        $('#upload-drop').removeClass('uk-hidden');
                        $('#articleimg').parent().parent().addClass('uk-hidden');
                    }
                    layer.msg(data.msg);
                } else {
                    layer.msg("网络异常，请稍后重试！");
                }
            });
        } else {
            $('#upload-drop').removeClass('uk-hidden');
            $('#articleimg').parent().parent().addClass('uk-hidden');
        }
    } else {
        $('#upload-drop').removeClass('uk-hidden');
        $('#articleimg').parent().parent().addClass('uk-hidden');
    }


}

