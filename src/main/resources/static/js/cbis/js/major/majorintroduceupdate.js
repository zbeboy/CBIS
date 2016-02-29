/**
 * Created by lenovo on 2016-02-09.
 */
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

//图片已删除标志
var del_flag = false;

$(document).ready(function () {
    //初始化图片路径，显示图片
    if ($('#imgPath').text() != '') {
        var str = $('#imgPath').text().substring($('#imgPath').text().lastIndexOf('\\') + 1, $('#imgPath').text().length);
        $('#articleimg').attr('src', '/cbis/files/majorintroduce/' + str);
        $('#articleimg').data('path', $('#imgPath').text());
    } else {
        $('#upload-drop').removeClass("uk-hidden");
        $('#imgShow').children().addClass("uk-hidden");
    }

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
function updateArticle() {

    /*暂时先不替换html中特殊字符*/

    var title = repalceHtmlCode($('#title').val());
    /*大标题*/
    var summary = repalceHtmlCode($('#summary').val());
    /*文章概述*/
    if (del_flag) {
        imgPath = '';
    }
    var picPath = imgPath;
    /*图片绝对路径*/
    var subtitle = $('.mysubtitle');
    /*子标题数据*/

    var subData = new Array();

    subData.push(new articleData(title, summary, picPath, "", "", "专业简介", -1));
    /*可以没有子标题数据，只组装第一条*/
    for (var i = 0; i < subtitle.length; i++) {
        if ($($('.mysubtitle')[i]).val().trim().length > 0 || $($('.mysubpage')[i]).val().trim().length > 0) {/*当子标题与内容都为空时，不算入文章*/
            if ($($('.mysubtitle')[i]).val().trim().length <= 50) {
                if ($($('.mysubpage')[i]).val().trim().length <= 2000) {
                    subData.push(new articleData(title, summary, picPath, repalceHtmlCode($($('.mysubtitle')[i]).val()), repalceHtmlCode($($('.mysubpage')[i]).val()), "专业简介", i));
                } else {
                    $($('.summaryerror')[i]).text("文章简介长度应小于2000个字符！");
                }
            } else {
                $($('.titleerror')[i]).text("标题长度应小于50个字符！");
            }
        }
    }

    /*校验标题*/
    if ($('#title').val().trim().length <= 0 || $('#title').val().trim().length > 50) {
        $('#titileError').text('请添加1~50个字符长度的标题！');
        $('#title').removeClass('uk-form-success').addClass('uk-form-danger');
        $('#title').focus();
        return;
    } else {
        $('#titileError').text('');
        $('#title').removeClass('uk-form-danger').addClass('uk-form-success');
    }

    /*校验文章*/
    if ($('#summary').val().trim().length < 6 || $('#summary').val().trim().length > 2000) {
        $('#summaryError').text('请添加6~2000个字符长度的文章简介！');
        $('#summary').removeClass('uk-form-success').addClass('uk-form-danger');
        $('#summary').focus();
        return;
    } else {
        $('#summaryError').text('');
        $('#summary').removeClass('uk-form-danger').addClass('uk-form-success');
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

    var id = -1;

    if ($('#articleInfoId').text().trim() != '') {
        id = $('#articleInfoId').text();
    }

    $.post('/cbis/maintainer/updateMajorIntroduce', {
        'subData': subData,
        'id': id,
        'majorId':$('#majorId').text()
    }, function (data, status) {
        layer.close(index);
        if (status) {
            if (data.state) {
                layer.confirm(data.msg, {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    window.location.href = "/";
                }, function () {
                    window.location.href = "/maintainer/majorintroduce";
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

            action: '/cbis/maintainer/uploadpicture', // upload url

            allow: '*.(jpg|gif|png|JPEG|JPG|GIF|PNG)', // allow only images

            params: {'pathname': 'majorintroduce'},//json 数据 该文件服务器端相对 files下路径

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
                $('#articleimg').attr('src', '/cbis/files/majorintroduce/' + str);
                $('#articleimg').data('path', response);
                $('#articleimg').parent().parent().removeClass('uk-hidden');

                //若重新上传图片置已删除标志为假
                del_flag = false;

                //保存服务器端绝对路径
                imgPath = response;
            }
        };

    var select = UIkit.uploadSelect($("#upload-select"), settings),
        drop = UIkit.uploadDrop($("#upload-drop"), settings);
});

/**
 * 删除图片
 */
function cleanimg() {
    del_flag = true;
    $('#articleimg').data('path', '');
    $('#upload-drop').removeClass('uk-hidden');
    $('#articleimg').parent().parent().addClass('uk-hidden');
}

