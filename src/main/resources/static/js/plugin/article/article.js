/**
 * Created by lenovo on 2016-02-28.
 */

/**
 * 问题记录：参数需要重新封装，文章id和专业Id分开，使用fastjson
 * @type {{articleWordType: string, checkArticleTitle: boolean, checkArticleContent: boolean, checkArticlePic: boolean, articleSaveOrUpdateUrl: string, clickOkUrl: string, clickNoUrl: string, uploadParamFileName: string, articleId: number, tieId: number, majorId: number, gradeId: number, teacherId: number, studentId: number, cleanFromClient: boolean, cleanUrl: string, openAffix: boolean, affixSaveFunc: string, affixEndFunc: string, affixData: string, cleanAffixFromClient: boolean, pluginClickOkUrlParam: string, isShow: number}}
 */
//全局配置
var param = {
    'articleWordType': '',//文章类别
    'checkArticleTitle': true,//开启标题检验
    'checkArticleContent': true,//开启内容检验
    'checkArticlePic': true,//开启封面检验
    'articleSaveOrUpdateUrl': '',//文章保存或更新地址
    'clickOkUrl': '',//文章保存或更新成功点击确定需要刷新的地址
    'clickNoUrl': '',//文章保存或更新成功点击取消需要刷新的地址
    'uploadParamFileName': '',//上传图片需要保存图片的文件夹名
    'articleId': 0,//用于文章id
    'myParam': {},//自定义参数
    'cleanFromClient': false,//删除图片时，服务器端也删除，默认只删除html dom
    'cleanUrl': '',//若开启cleanFromClient,此项必填，删除请求地址
    'openAffix': false,//开启附件功能
    'affixSaveFunc': '',//数据存储要调用的函数，调用时，会传输附件id,url,original_name
    'affixEndFunc': '',//数据存储完毕要调用的函数，调用该函数时，请封装param.affixData过来，系统会调用该参数
    'affixData': '',//附件数据
    'cleanAffixFromClient': false,//删除附件时，服务器端也删除，默认只删除html dom
    'pluginClickOkUrlParam': ''//文章保存或更新成功点击确定需要刷新的地址 的扩展参数
};

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

//全局变量，是否已删除图片标题，用于修复更新时图片未删除bug
var is_del = false;

function initImage() {
    if (imgPath != '' && imgPath != null) {
        var str = imgPath.substring(imgPath.lastIndexOf('/') + 1, imgPath.length);
        $('#articleimg').attr('src', web_path + '/files/' + param.uploadParamFileName + '/' + str);
    } else {
        $('#upload-drop').removeClass("uk-hidden");
        $('#imgShow').children().addClass("uk-hidden");
    }
}

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
function articleData(title, summary, picPath, subTitle, subPage, articleType) {
    this.bigTitle = title;
    this.articleContent = summary;
    this.articlePhotoUrl = picPath;
    this.subTitle = subTitle;
    this.subContent = subPage;
    this.articleType = articleType;
}

/**
 * 保存文章
 */
function saveArticle() {
    /*大标题*/
    var title = repalceHtmlCode($('#title').val());
    /*文章概述*/
    var summary = repalceHtmlCode($('#summary').val());

    if (is_del) {//更新时图片已删除
        imgPath = '';
    }
    /*图片绝对路径*/
    var picPath = imgPath;
    /*子标题数据*/
    var subtitle = $('.mysubtitle');


    var subData = new Array();

    subData.push(new articleData(title, summary, picPath, "", "", param.articleWordType));
    /*可以没有子标题数据，只组装第一条*/
    for (var i = 0; i < subtitle.length; i++) {
        if ($($('.mysubtitle')[i]).val().trim().length > 0 || $($('.mysubpage')[i]).val().trim().length > 0) {/*当子标题与内容都为空时，不算入文章*/
            if ($($('.mysubtitle')[i]).val().trim().length <= 50) {
                if ($($('.mysubpage')[i]).val().trim().length <= 2000) {
                    subData.push(new articleData(title, summary, picPath, repalceHtmlCode($($('.mysubtitle')[i]).val()), repalceHtmlCode($($('.mysubpage')[i]).val()), param.articleWordType));
                } else {
                    $($('.summaryerror')[i]).text("文章简介长度应小于2000个字符！");
                }
            } else {
                $($('.titleerror')[i]).text("标题长度应小于50个字符！");
            }
        }
    }

    //附件数据
    if (param.openAffix) {
        for (var i = 0; i < $('.affixdata').length; i++) {
            var temp = $($('.affixdata')[i]).children();
            var url = $(temp[0]).text();
            var originalName = $(temp[1]).text();
            eval(param.affixSaveFunc + "('" + 0 + "','" + url + "','" + originalName + "')");
        }
        eval(param.affixEndFunc);
    }


    /*校验标题*/
    if (param.checkArticleTitle) {
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
    if (param.checkArticleContent) {
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
    if (param.checkArticlePic) {
        if (picPath.trim().length <= 0) {
            layer.msg('请先上传一张封面图片！');
            return;
        }
    }

    /*发送数据到后台*/
    sendArtitle(subData);
}

/**
 * ajax 发送文章
 * @param subData json 字符串数据
 */
function sendArtitle(subData) {

    var lastParam = {
        'articleData': subData,
        'myParam': param.myParam,
        'openAffix': param.openAffix,
        'affixData': param.affixData
    };

    var index = layer.load(1, {shade: false});
    $.post(param.articleSaveOrUpdateUrl, {
        'lastParam': JSON.stringify(lastParam)
    }, function (data, status) {
        layer.close(index);
        if (status) {
            if (data.state) {
                layer.confirm(data.msg, {
                    btn: ['确定', '取消'] //按钮
                }, function () {
                    window.location.href = param.clickOkUrl + "?id=" + data.single.single + param.pluginClickOkUrlParam;
                }, function () {
                    window.location.href = param.clickNoUrl;
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

function initUpload() {
    var progressbar = $("#progressbar"),
        bar = progressbar.find('.uk-progress-bar'),
        settings = {

            action: web_path + '/student/uploadFile', // upload url

            allow: '*.(jpeg|jpg|gif|png|JPEG|JPG|GIF|PNG)', // allow only images

            params: {'pathname': param.uploadParamFileName},//json 数据 该文件服务器端相对 files下路径

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
                var json = JSON.parse(response);
                var str = json.msg.substring(json.msg.lastIndexOf('/') + 1, json.msg.length);

                //若图片组件已被删除，需要重新添加图片显示组件
                if ($('#imgShow').html().trim().length <= 0) {
                    $('#imgShow').append(IMG_SHOW);
                }

                //显示图片
                $('#articleimg').attr('src', web_path + '/files/' + param.uploadParamFileName + '/' + str);
                $('#articleimg').parent().parent().removeClass('uk-hidden');

                //保存服务器端绝对路径
                imgPath = '/files/' + param.uploadParamFileName + '/' + str;

                //删除图片标记
                is_del = false;
            }
        };

    var select = UIkit.uploadSelect($("#upload-select"), settings),
        drop = UIkit.uploadDrop($("#upload-drop"), settings);

    //初始化附件
    if (param.openAffix) {
        var affixSettings = {

            action: web_path + '/student/uploadFile', // upload url

            allow: '*.(txt|doc|docx|xls|xlsx|ppt|pptx|zip|7z|rar|iso|gzip|png|jpeg|jpg|gif|tar|pdf)', // allow only images

            params: {'pathname': param.uploadParamFileName},//json 数据 该文件服务器端相对 files下路径


            allcomplete: function (response) {
                var json = JSON.parse(response);
                outputAffixHtml(json.single.single);
            }
        };
        var affixSelect = UIkit.uploadSelect($("#affixFile"), affixSettings)
    }

}


/**
 * 删除图片 包括服务器端图片路径
 */
function cleanimg() {

    if (param.cleanFromClient) {
        var path = imgPath;
        if (path != null) {
            var index = layer.load(1, {shade: false});
            $.post(param.cleanUrl, {
                'path': path
            }, function (data, status) {
                layer.close(index);
                if (status) {/*网络正常*/
                    /*删除成功 重新显示上传组件*/
                    $('#upload-drop').removeClass('uk-hidden');
                    $('#articleimg').parent().parent().addClass('uk-hidden');
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
        //图片已删除标记，用于更新保存用
        is_del = true;
    }
}

/**
 * 删除文章
 */
function deleteArticle() {

    layer.confirm("确定要删除该文章吗?", {
        btn: ['确定','取消'] //按钮
    }, function(){
        var index = layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
        $.post(web_path + '/maintainer/deleteArticle', {
            'id': param.articleId
        }, function (data, status) {
            layer.close(index);
            if (status) {
                if (data.state) {
                    window.location.href = param.clickNoUrl;
                } else {
                    layer.msg(data.msg);
                }
            } else {
                layer.msg("网络异常，请稍后重试！");
            }
        },'json');
    });

}

/**
 * 输出附件html
 */
function outputAffixHtml(data) {
    /*
     var html = "<li class='affixdata' >" +
     "<p style='display:none;'>" +
     data.lastPath +
     "</p>" +
     "<p style='display:none;'>" +
     data.originalFilename +
     "</p>" +
     "<div class='uk-grid'>" +
     "<div class='uk-width-4-5 uk-text-truncate'>" +
     data.originalFilename +
     "</div>" +
     "<div class='uk-width-1-5 uk-text-muted uk-text-right'>" +
     " <a href='javascript:;' onclick='deleteAffix(this);'>" +
     "删除" +
     "</a>" +
     "</div>" +
     "</div>" +
     "</li>";
     $('#affixDatas').append(html);
     */
    var str = data.lastPath.substring(data.lastPath.lastIndexOf('/') + 1, data.lastPath.length);
    var lastPath = '/files/' + param.uploadParamFileName + '/' + str;

    var _ = DOMBuilder;
    $('#affixDatas').append(_.DOM(
        _('li.affixdata')._([
            _('p.uk-hidden').H(lastPath),
            _('p.uk-hidden').H(data.originalFilename),
            _('div.uk-grid')._([
                _('div.uk-width-4-5.uk-text-truncate').H(data.originalFilename),
                _('div.uk-width-1-5.uk-text-muted.uk-text-right')._([
                    _('a[href=javascript:;][onclick=deleteAffix(this)]').H('删除')
                ])
            ])
        ])
    ));

}

/**
 * 删除附件
 * @param path
 */
function deleteAffix(obj) {
    var r = $(obj).parent().parent().parent();
    var p = r.children();
    var path = $(p[0]).text();
    if (param.cleanAffixFromClient) {
        var index = layer.load(1, {shade: false});
        $.post(param.cleanUrl, {
            'path': path
        }, function (data, status) {
            layer.close(index);
            if (status) {/*网络正常*/
                if (data.state) {
                    r.remove();
                } else {
                    layer.msg(data.msg);
                }
            } else {
                layer.msg("网络异常，请稍后重试！");
            }
        });
    } else {
        r.remove();
    }
}

