/**
 * Created by Administrator on 2016/5/4.
 */
/**
 * 添加文本
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeText(title, title_variable, headContent, updateContent) {
    if (isUpdate) {
        var HEADTYPE_TEXT = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='text' class='uk-form-width-large' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' value='" + updateContent + "' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_TEXT;
    } else {
        var HEADTYPE_TEXT = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='text' class='uk-form-width-large' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' value='" + headContent + "' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_TEXT;
    }
}

/**
 * 添加多文本
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeTextarea(title, title_variable, headContent, updateContent) {
    if (isUpdate) {
        var HEADTYPE_TEXTAREA = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<textarea id='" + title_variable + "' name='" + title_variable + "' cols='78' rows='5' placeholder='" + title + "'>" + updateContent +
            "</textarea>" +
            "</div>" +
            "</div>";
        return HEADTYPE_TEXTAREA;
    } else {
        var HEADTYPE_TEXTAREA = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<textarea id='" + title_variable + "' name='" + title_variable + "' cols='78' rows='5' placeholder='" + title + "'>" + headContent +
            "</textarea>" +
            "</div>" +
            "</div>";
        return HEADTYPE_TEXTAREA;
    }

}

/**
 * 添加select
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeRadio(title, title_variable, headContent, updateContent) {
    if (isUpdate) {
        var c = headContent.split(",");
        var s = '<option value="" >请选择</option>';
        for (var i = 0; i < c.length; i++) {
            if (c[i] === updateContent) {
                s = s + "<option value='" + c[i] + "' selected='selected' >" + c[i] + "</option>";
            } else {
                s = s + "<option value='" + c[i] + "' >" + c[i] + "</option>";
            }

        }

        var HEADTYPE_RADIO = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<select id='" + title_variable + "' name='" + title_variable + "' >" +
            s +
            "</select>" +
            "</div>" +
            "</div>";
        return HEADTYPE_RADIO;
    } else {
        var c = headContent.split(",");
        var s = '<option value="" >请选择</option>';
        for (var i = 0; i < c.length; i++) {
            s = s + "<option value='" + c[i] + "' >" + c[i] + "</option>";
        }

        var HEADTYPE_RADIO = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<select id='" + title_variable + "' name='" + title_variable + "' >" +
            s +
            "</select>" +
            "</div>" +
            "</div>";
        return HEADTYPE_RADIO;
    }
}

/**
 * 添加多选
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeCheckbox(title, title_variable, headContent, updateContent) {
    if (isUpdate) {
        var c = headContent.split(",");
        var d = updateContent.split(",");
        var s = '';
        for (var i = 0; i < c.length; i++) {
            var isChecked = false;
            for (var j = 0; j < d.length; j++) {
                if (c[i] === d[j]) {
                    s = s + "<label>" +
                        "<input type='checkbox' value='" + d[j] + "' checked='checked' name='" + title_variable + "' />" +
                        c[i] +
                        "</label><br/>";
                    isChecked = true;
                    break;
                }
            }

            if (!isChecked) {
                s = s + "<label>" +
                    "<input type='checkbox' value='" + c[i] + "' name='" + title_variable + "' />" +
                    c[i] +
                    "</label><br/>";
            }
        }

        var HEADTYPE_CHECKBOX = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            s +
            "</div>" +
            "</div>";
        return HEADTYPE_CHECKBOX;
    } else {
        var c = headContent.split(",");
        var s = '';
        for (var i = 0; i < c.length; i++) {
            if (i != c.length - 1) {
                s = s + "<label>" +
                    "<input type='checkbox' value='" + c[i] + "' name='" + title_variable + "' />" +
                    c[i] +
                    "</label><br/>";
            } else {
                s = s + "<label>" +
                    "<input type='checkbox' value='" + c[i] + "' name='" + title_variable + "' />" +
                    c[i] +
                    "</label>";
            }
        }

        var HEADTYPE_CHECKBOX = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            s +
            "</div>" +
            "</div>";
        return HEADTYPE_CHECKBOX;
    }
}

/**
 * 添加开关
 * @param title
 * @param title_variable
 * @param headContent
 * @param updateContent
 * @returns {string}
 */
function addHeadTypeSwitch(title, title_variable, headContent, updateContent) {
    if (isUpdate) {
        var c = headContent.split(",");
        var d = updateContent.split(",");
        var s = '';
        if (c[0] === d[0]) {
            s = s + "<label>" +
                "<input type='checkbox' checked='checked' name='" + title_variable + "' />" +
                c[0] +
                "</label>";
        } else {
            s = s + "<label>" +
                "<input type='checkbox' name='" + title_variable + "' />" +
                c[0] +
                "</label>";
        }
        var HEADTYPE_CHECKBOX = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            s +
            "</div>" +
            "</div>";
        return HEADTYPE_CHECKBOX;
    } else {
        var c = headContent.split(",");
        var s = '';
        s = s + "<label>" +
            "<input type='checkbox' name='" + title_variable + "' />" +
            c[0] +
            "</label>";
        var HEADTYPE_CHECKBOX = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            s +
            "</div>" +
            "</div>";
        return HEADTYPE_CHECKBOX;
    }
}

/**
 * 添加密码
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypePassword(title, title_variable, headContent, updateContent) {
    if (isUpdate) {
        var HEADTYPE_PASSWORD = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='password' class='uk-form-width-large' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' value='" + updateContent + "' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_PASSWORD;
    } else {
        var HEADTYPE_PASSWORD = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='password' class='uk-form-width-large' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' value='" + headContent + "' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_PASSWORD;
    }
}

/**
 * 添加日期
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeDate(title, title_variable, headContent, updateContent) {
    if (isUpdate) {
        var HEADTYPE_DATE = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='text' class='uk-form-width-large' readonly='readonly' value='" + updateContent + "' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' data-uk-datepicker='{format:\"YYYY-MM-DD\"}' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_DATE;
    } else {
        var HEADTYPE_DATE = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='text' class='uk-form-width-large' readonly='readonly' value='" + headContent + "' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' data-uk-datepicker='{format:\"YYYY-MM-DD\"}' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_DATE;
    }

}

/**
 * 添加时间
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeTime(title, title_variable, headContent, updateContent) {
    if (isUpdate) {
        var HEADTYPE_TIME = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='text' class='uk-form-width-large' readonly='readonly' value='" + updateContent + "' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' data-uk-timepicker='' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_TIME;
    } else {
        var HEADTYPE_TIME = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='text' class='uk-form-width-large' readonly='readonly' value='" + headContent + "' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' data-uk-timepicker='' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_TIME;
    }
}

/**
 * 添加邮箱
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeEmail(title, title_variable, headContent, updateContent) {
    if (isUpdate) {
        var HEADTYPE_EMAIL = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='email' class='uk-form-width-large' value='" + updateContent + "' pattern='^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_EMAIL;
    } else {
        var HEADTYPE_EMAIL = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='email' class='uk-form-width-large' value='" + headContent + "' pattern='^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_EMAIL;
    }

}

/**
 * 添加数字
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeNumber(title, title_variable, headContent, updateContent) {
    if (isUpdate) {
        var HEADTYPE_NUMBER = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='number' class='uk-form-width-large' value='" + updateContent + "' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_NUMBER;
    } else {
        var HEADTYPE_NUMBER = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='number' class='uk-form-width-large' value='" + headContent + "' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_NUMBER;
    }
}

/**
 * 添加手机号
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeMobile(title, title_variable, headContent, updateContent) {
    if (isUpdate) {
        var HEADTYPE_MOBILE = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='number' class='uk-form-width-large' value='" + updateContent + "' pattern='^1\d{10}$' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_MOBILE;
    } else {
        var HEADTYPE_MOBILE = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='number' class='uk-form-width-large' value='" + headContent + "' pattern='^1\d{10}$' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_MOBILE;
    }

}

/**
 * 添加电话
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeTelephone(title, title_variable, headContent, updateContent) {
    if (isUpdate) {
        var HEADTYPE_TEL = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='text' class='uk-form-width-large' value='" + updateContent + "' pattern='^0\d{2,3}-?\d{7,8}$' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_TEL;
    } else {
        var HEADTYPE_TEL = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='text' class='uk-form-width-large' value='" + headContent + "' pattern='^0\d{2,3}-?\d{7,8}$' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_TEL;
    }

}

/**
 * 添加邮编
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypePostcode(title, title_variable, headContent, updateContent) {
    if (isUpdate) {
        var HEADTYPE_POSTCODE = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='number' class='uk-form-width-large' value='" + updateContent + "' pattern='^[1-9][0-9]{5}$' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_POSTCODE;
    } else {
        var HEADTYPE_POSTCODE = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='number' class='uk-form-width-large' value='" + headContent + "' pattern='^[1-9][0-9]{5}$' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_POSTCODE;
    }

}

/**
 * 添加qq
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeQq(title, title_variable, headContent, updateContent) {
    if (isUpdate) {
        var HEADTYPE_QQ = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='number' class='uk-form-width-large' value='" + updateContent + "' pattern='^[1-9]\d{4,15}$' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_QQ;
    } else {
        var HEADTYPE_QQ = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='number' class='uk-form-width-large' value='" + headContent + "' pattern='^[1-9]\d{4,15}$' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_QQ;
    }

}

/**
 * 添加身份证
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addHeadTypeIDCard(title, title_variable, headContent, updateContent) {
    if (isUpdate) {
        var HEADTYPE_ID_card = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='text' class='uk-form-width-large' value='" + updateContent + "' pattern='(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_ID_card;
    } else {
        var HEADTYPE_ID_card = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='text' class='uk-form-width-large' value='" + headContent + "' pattern='(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_ID_card;
    }

}

/**
 * 添加数据库表student性别
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function addDatabaseStudentSex(title, title_variable, headContent, updateContent) {
    if (isUpdate) {
        var s = '<option value="" >请选择</option>';
        if (updateContent === '男') {
            s = s + "<option value='" + updateContent + "' selected='selected' >" + updateContent + "</option>";
            s = s + "<option value='女'>女</option>";
        } else if (updateContent === '女') {
            s = s + "<option value='" + updateContent + "' selected='selected' >" + updateContent + "</option>";
            s = s + "<option value='男'>男</option>";
        } else {
            s = s + "<option value='女' >女</option>";
            s = s + "<option value='男'>男</option>";
        }

        var HEADTYPE_RADIO = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<select id='" + title_variable + "' name='" + title_variable + "' >" +
            s +
            "</select>" +
            "</div>" +
            "</div>";
        return HEADTYPE_RADIO;
    } else {
        var s = '<option value="" >请选择</option>';
        if (headContent === '男') {
            s = s + "<option value='" + headContent + "' selected='selected' >" + headContent + "</option>";
            s = s + "<option value='女'>女</option>";
        } else if (headContent === '女') {
            s = s + "<option value='" + headContent + "' selected='selected' >" + headContent + "</option>";
            s = s + "<option value='男'>男</option>";
        } else {
            s = s + "<option value='女' >女</option>";
            s = s + "<option value='男'>男</option>";
        }

        var HEADTYPE_RADIO = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<select id='" + title_variable + "' name='" + title_variable + "' >" +
            s +
            "</select>" +
            "</div>" +
            "</div>";
        return HEADTYPE_RADIO;
    }

}

/**
 * 添加只读文本
 * @param title
 * @param title_variable
 * @param headContent
 * @param updateContent
 * @returns {string}
 */
function addHeadTypeReadonlyText(title, title_variable, headContent, updateContent) {
    if (isUpdate) {
        var HEADTYPE_TEXT = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='text' readonly='readonly' class='uk-form-width-large' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' value='" + updateContent + "' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_TEXT;
    } else {
        var HEADTYPE_TEXT = "<div class='uk-form-row'>" +
            "<label class='uk-form-label' for='" + title_variable + "'>" +
            title +
            "</label>" +
            "<div class='uk-form-controls'>" +
            "<input type='text' readonly='readonly' class='uk-form-width-large' id='" + title_variable + "' name='" + title_variable + "' placeholder='" + title + "' value='" + headContent + "' />" +
            "</div>" +
            "</div>";
        return HEADTYPE_TEXT;
    }
}

/**
 * 检验所有字段
 */
function validationAll() {
    var isRight = true;
    if (heads != null) {
        for (var i = 0; i < heads.length; i++) {
            if (useTitle(heads[i].authority)) {
                var isRequired = heads[i].isRequired == 1 ? true : false;

                if (heads[i].typeValue === 'database') {//数据库字段
                    var id = "#" + heads[i].titleVariable;
                    if (!validateField(heads[i].title, heads[i].typeValue, $(id).val(), heads[i].databaseTableField, isRequired)) {
                        isRight = false;
                        break;
                    }
                } else {//自定义字段
                    if (heads[i].typeValue === 'checkbox') {
                        if (!validateField(heads[i].title, heads[i].typeValue, $("input[name='" + heads[i].titleVariable + "']:checked"), '', isRequired)) {
                            isRight = false;
                            break;
                        }
                    } else {
                        var id = "#" + heads[i].titleVariable;
                        if (!validateField(heads[i].title, heads[i].typeValue, $(id).val(), '', isRequired)) {
                            isRight = false;
                            break;
                        }
                    }
                }
            }
        }
    }
    return isRight;
}

/**
 * 检验每个标题
 * @param title 标题
 * @param headType 标题类型
 * @param value 值 (checkbox除外)
 * @param databaseTableField
 * @param isRequired 是否必填
 * @returns {boolean}
 */
function validateField(title, headType, value, databaseTableField, isRequired) {
    if (headType === 'text') {
        if (value.trim().length > 500) {
            layer.msg("填写内容应在500字符以内!")
            return false;
        } else if (isRequired && value.trim().length <= 0) {
            layer.msg("请填写" + title + "!");
            return false;
        } else {
            return true;
        }
    }

    if (headType === 'textarea') {
        if (value.trim().length > 500) {
            layer.msg("填写内容应在500字符以内!")
            return false;
        } else if (isRequired && value.trim().length <= 0) {
            layer.msg("请填写" + title + "!");
            return false;
        } else {
            return true;
        }
    }

    if (headType === 'select') {
        if (isRequired && value.trim().length <= 0) {
            layer.msg("请选择" + title + "!");
            return false;
        } else {
            return true;
        }
    }

    //value $("input[name='authoritiesCheck']:checked")
    if (headType === 'checkbox') {
        if (isRequired && value.length <= 0) {
            layer.msg("请至少选择一个" + title + "!");
            return false;
        } else {
            return true;
        }
    }

    if (headType === 'switch') {
        return true;
    }

    if (headType === 'date') {
        if (isRequired && value.trim().length <= 0) {
            layer.msg("请选择" + title + "!");
            return false;
        } else {
            return true;
        }
    }

    if (headType === 'time') {
        if (isRequired && value.trim().length <= 0) {
            layer.msg("请选择" + title + "!");
            return false;
        } else {
            return true;
        }
    }

    if (headType === 'password') {
        if (value.trim().length > 500) {
            layer.msg("填写内容应在500字符以内!")
            return false;
        } else if (isRequired && value.trim().length <= 0) {
            layer.msg("请填写" + title + "!");
            return false;
        } else {
            return true;
        }
    }

    if (headType === 'email') {
        if (isRequired) {
            var regex = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
            if (regex.test(value.trim())) {
                return true;
            } else {
                layer.msg("请正确填写" + title + "!");
                return false;
            }
        } else {
            if (value.trim().length > 0) {
                var regex = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
                if (regex.test(value.trim())) {
                    return true;
                } else {
                    layer.msg("请正确填写" + title + "!");
                    return false;
                }
            } else {
                return true;
            }
        }
    }

    if (headType === 'number') {
        if (isRequired) {
            var regex = /^\d{1,500}$/;
            if (regex.test(value.trim())) {
                return true;
            } else {
                layer.msg("请正确填写" + title + "!");
                return false;
            }
        } else {
            if (value.trim().length > 0) {
                var regex = /^\d{1,500}$/;
                if (regex.test(value.trim())) {
                    return true;
                } else {
                    layer.msg("请正确填写" + title + "!");
                    return false;
                }
            } else {
                return true;
            }
        }
    }

    if (headType === 'mobile') {
        if (isRequired) {
            var regex = /^1\d{10}$/;
            if (regex.test(value.trim())) {
                return true;
            } else {
                layer.msg("请正确填写" + title + "!");
                return false;
            }
        } else {
            if (value.trim().length > 0) {
                var regex = /^1\d{10}$/;
                if (regex.test(value.trim())) {
                    return true;
                } else {
                    layer.msg("请正确填写" + title + "!");
                    return false;
                }
            } else {
                return true;
            }
        }
    }

    if (headType === 'telephone') {
        if (isRequired) {
            var regex = /^0\d{2,3}-?\d{7,8}$/;
            if (regex.test(value.trim())) {
                return true;
            } else {
                layer.msg("请正确填写" + title + "!");
                return false;
            }
        } else {
            if (value.trim().length > 0) {
                var regex = /^0\d{2,3}-?\d{7,8}$/;
                if (regex.test(value.trim())) {
                    return true;
                } else {
                    layer.msg("请正确填写" + title + "!");
                    return false;
                }
            } else {
                return true;
            }
        }
    }

    if (headType === 'postcode') {
        if (isRequired) {
            var regex = /^[1-9][0-9]{5}$/;
            if (regex.test(value.trim())) {
                return true;
            } else {
                layer.msg("请正确填写" + title + "!");
                return false;
            }
        } else {
            if (value.trim().length > 0) {
                var regex = /^[1-9][0-9]{5}$/;
                if (regex.test(value.trim())) {
                    return true;
                } else {
                    layer.msg("请正确填写" + title + "!");
                    return false;
                }
            } else {
                return true;
            }
        }

    }

    if (headType === 'qq') {
        if (isRequired) {
            var regex = /^[1-9]\d{4,15}$/;
            if (regex.test(value.trim())) {
                return true;
            } else {
                layer.msg("请正确填写" + title + "!");
                return false;
            }
        } else {
            if (value.trim().length > 0) {
                var regex = /^[1-9]\d{4,15}$/;
                if (regex.test(value.trim())) {
                    return true;
                } else {
                    layer.msg("请正确填写" + title + "!");
                    return false;
                }
            } else {
                return true;
            }
        }

    }

    if (headType === 'ID_card') {
        if(isRequired){
            var regex = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
            if (regex.test(value.trim())) {
                return true;
            } else {
                layer.msg("请正确填写" + title + "!");
                return false;
            }
        } else {
            if(value.trim().length>0){
                var regex = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
                if (regex.test(value.trim())) {
                    return true;
                } else {
                    layer.msg("请正确填写" + title + "!");
                    return false;
                }
            } else {
                return true;
            }
        }

    }

    if (headType === 'database') {
        if (databaseTableField === 'student_number') {
            if (value.trim().length > 500) {
                layer.msg("填写内容应在500字符以内!")
                return false;
            } else if (isRequired && value.trim().length <= 0) {
                layer.msg("请填写" + title + "!");
                return false;
            } else {
                return true;
            }
        }

        if (databaseTableField === 'student_name') {
            if (value.trim().length > 500) {
                layer.msg("填写内容应在500字符以内!")
                return false;
            } else if (isRequired && value.trim().length <= 0) {
                layer.msg("请填写" + title + "!");
                return false;
            } else {
                return true;
            }
        }

        if (databaseTableField === 'student_birthday') {
            if (isRequired && value.trim().length <= 0) {
                layer.msg("请选择" + title + "!");
                return false;
            } else {
                return true;
            }
        }

        if (databaseTableField === 'grade_name') {
            if (value.trim().length > 500) {
                layer.msg("填写内容应在500字符以内!")
                return false;
            } else if (isRequired && value.trim().length <= 0) {
                layer.msg("请填写" + title + "!");
                return false;
            } else {
                return true;
            }
        }

        if (databaseTableField === 'student_phone') {
            if(isRequired){
                var regex = /^1\d{10}$/;
                if (regex.test(value.trim())) {
                    return true;
                } else {
                    layer.msg("请正确填写" + title + "!");
                    return false;
                }
            } else {
                if(value.trim().length>0){
                    var regex = /^1\d{10}$/;
                    if (regex.test(value.trim())) {
                        return true;
                    } else {
                        layer.msg("请正确填写" + title + "!");
                        return false;
                    }
                } else {
                    return true;
                }
            }

        }

        if (databaseTableField === 'student_email') {
            if(isRequired){
                var regex = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
                if (regex.test(value.trim())) {
                    return true;
                } else {
                    layer.msg("请正确填写" + title + "!");
                    return false;
                }
            } else {
                if(value.trim().length>0){
                    var regex = /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/;
                    if (regex.test(value.trim())) {
                        return true;
                    } else {
                        layer.msg("请正确填写" + title + "!");
                        return false;
                    }
                } else {
                    return true;
                }
            }
        }

        if (databaseTableField === 'student_sex') {
            if (isRequired && value.trim().length <= 0) {
                layer.msg("请选择" + title + "!");
                return false;
            } else {
                return true;
            }
        }

        if (databaseTableField === 'student_identity_card') {
            if(isRequired){
                var regex = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
                if (regex.test(value.trim())) {
                    return true;
                } else {
                    layer.msg("请正确填写" + title + "!");
                    return false;
                }
            } else {
                if(value.trim().length>0){
                    var regex = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
                    if (regex.test(value.trim())) {
                        return true;
                    } else {
                        layer.msg("请正确填写" + title + "!");
                        return false;
                    }
                } else {
                    return true;
                }
            }

        }

        if (databaseTableField === 'student_address') {
            if (value.trim().length > 500) {
                layer.msg("填写内容应在500字符以内!")
                return false;
            } else if (isRequired && value.trim().length <= 0) {
                layer.msg("请填写" + title + "!");
                return false;
            } else {
                return true;
            }
        }
    }
}

/**
 * 是否有权限使用该标题
 * @param headsAuthority
 * @returns {boolean}
 */
function useTitle(headsAuthority) {
    var authorities = headsAuthority.split(",");
    var isRight = false;
    if (currentAuthorities != null) {
        for (var i = 0; i < currentAuthorities.length; i++) {
            for (var j = 0; j < authorities.length; j++) {
                if (currentAuthorities[i].authority.trim() === authorities[j].trim()) {
                    isRight = true;
                    break;
                }
            }
            if (isRight) {
                break;
            }
        }
    }
    return isRight;
}

/**
 * 输出html
 */
function outputHtml() {
    $('#studentData').empty();
    $('#studentData').append($('<input id="autonomousPracticeInfoId" type="hidden" name="autonomousPracticeInfoId" />').val(autonomousPracticeInfoId))
    $('#studentData').append($('<input id="studentId" type="hidden" name="studentId" />').val(studentInfo.id))
    if (heads != null) {
        for (var i = 0; i < heads.length; i++) {
            if (useTitle(heads[i].authority)) {//有权限使用
                if (heads[i].isDatabase == 1) {//从数据库中选取
                    var headContent = '';
                    var updateContent = '';
                    if (isUpdate) {
                        for (var j = 0; j < myContents.length; j++) {
                            if (heads[i].id == myContents[j].autonomousPracticeHeadId) {
                                updateContent = myContents[j].content;
                                break;
                            }
                        }
                    }
                    headContent = getDataFromDatabase(heads[i].databaseTableField);
                    if (headContent == null) {
                        headContent = '';
                    }
                    if(updateContent == null || updateContent == ''){
                        updateContent = headContent;//修复存储失败时，数据库字段内容为空
                    }

                    $('#studentData').append(outputFieldHtml(heads[i].typeValue, heads[i].title, heads[i].titleVariable, headContent, updateContent, heads[i].databaseTableField));
                } else {
                    var headContent = '';
                    var updateContent = '';
                    if (isUpdate) {
                        for (var j = 0; j < myContents.length; j++) {
                            if (heads[i].id == myContents[j].autonomousPracticeHeadId) {
                                updateContent = myContents[j].content;
                                break;
                            }
                        }
                    }
                    headContent = heads[i].content;
                    if(updateContent == null){
                        updateContent = '';
                    }
                    $('#studentData').append(outputFieldHtml(heads[i].typeValue, heads[i].title, heads[i].titleVariable, headContent, updateContent, ''));
                }
            }
        }
    }
}

/**
 * 输出标题html
 * @param title
 * @param title_variable
 * @param content
 * @returns {string}
 */
function outputFieldHtml(headType, title, title_variable, headContent, updateContent, databaseTableField) {
    if (headType === 'text') {
        return addHeadTypeText(title, title_variable, headContent, updateContent);
    }

    if (headType === 'textarea') {
        return addHeadTypeTextarea(title, title_variable, headContent, updateContent);
    }

    if (headType === 'select') {
        return addHeadTypeRadio(title, title_variable, headContent, updateContent);
    }

    if (headType === 'checkbox') {
        return addHeadTypeCheckbox(title, title_variable, headContent, updateContent);
    }

    if (headType === 'switch') {
        return addHeadTypeSwitch(title, title_variable, headContent, updateContent);
    }

    if (headType === 'password') {
        return addHeadTypePassword(title, title_variable, headContent, updateContent);
    }

    if (headType === 'date') {
        return addHeadTypeDate(title, title_variable, headContent, updateContent);
    }

    if (headType === 'time') {
        return addHeadTypeTime(title, title_variable, headContent, updateContent);
    }

    if (headType === 'email') {
        return addHeadTypeEmail(title, title_variable, headContent, updateContent);
    }

    if (headType === 'number') {
        return addHeadTypeNumber(title, title_variable, headContent, updateContent);
    }

    if (headType === 'mobile') {
        return addHeadTypeMobile(title, title_variable, headContent, updateContent);
    }

    if (headType === 'telephone') {
        return addHeadTypeTelephone(title, title_variable, headContent, updateContent);
    }

    if (headType === 'postcode') {
        return addHeadTypePostcode(title, title_variable, headContent, updateContent);
    }

    if (headType === 'qq') {
        return addHeadTypeQq(title, title_variable, headContent, updateContent);
    }

    if (headType === 'ID_card') {
        return addHeadTypeIDCard(title, title_variable, headContent, updateContent);
    }

    if (headType === 'database') {
        if (databaseTableField === 'student_number') {
            return addHeadTypeReadonlyText(title, title_variable, headContent, updateContent);
        }

        if (databaseTableField === 'student_name') {
            return addHeadTypeText(title, title_variable, headContent, updateContent);
        }

        if (databaseTableField === 'grade_name') {
            return addHeadTypeReadonlyText(title, title_variable, headContent, updateContent);
        }

        if (databaseTableField === 'student_phone') {
            return addHeadTypeMobile(title, title_variable, headContent, updateContent);
        }

        if (databaseTableField === 'student_email') {
            return addHeadTypeEmail(title, title_variable, headContent, updateContent);
        }

        if (databaseTableField === 'student_birthday') {
            return addHeadTypeDate(title, title_variable, headContent, updateContent);
        }

        if (databaseTableField === 'student_sex') {
            return addDatabaseStudentSex(title, title_variable, headContent, updateContent);
        }

        if (databaseTableField === 'student_identity_card') {
            return addHeadTypeIDCard(title, title_variable, headContent, updateContent);
        }

        if (databaseTableField === 'student_address') {
            return addHeadTypeText(title, title_variable, headContent, updateContent);
        }
    }
}

/**
 * 从数据库中获取数据
 * @param databaseTableField
 * @returns {*}
 */
function getDataFromDatabase(databaseTableField) {
    if (studentInfo != null) {
        if (databaseTableField === 'student_number') {
            return studentInfo.studentNumber;
        }

        if (databaseTableField === 'student_name') {
            return studentInfo.studentName;
        }

        if (databaseTableField === 'grade_name') {
            return studentInfo.gradeName;
        }

        if (databaseTableField === 'student_phone') {
            return studentInfo.studentPhone;
        }

        if (databaseTableField === 'student_email') {
            return studentInfo.studentEmail;
        }

        if (databaseTableField === 'student_birthday') {
            return studentInfo.studentBirthday;
        }

        if (databaseTableField === 'student_sex') {
            return studentInfo.studentSex;
        }

        if (databaseTableField === 'student_identity_card') {
            return studentInfo.studentIdentityCard;
        }

        if (databaseTableField === 'student_address') {
            return studentInfo.studentAddress;
        }
    } else {
        return '';
    }
}

/**
 * 保存学生表单
 */
function studentSave() {
    if (validationAll()) {
        console.log($('#studentData').serialize());
        $.post(web_path + '/student/autonomicpractice/addAutonomicPractice', $('#studentData').serialize(), function (data) {
            if (data.state) {
                window.location.href = web_path + '/student/autonomicpractice/autonomicPractice';
            } else {
                layer.msg(data.msg);
            }
        });
    }
}

$(document).ready(function () {

    outputHtml();//输出学生界面

});