package com.zhuzichu.mvvm.utils

import com.zhuzichu.mvvm.App
import java.util.regex.Pattern

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-31
 * Time: 15:35
 */

fun Int.toStringById(): String {
    return App.context.resources.getString(this)
}

/**
 * 判断字符串是否为空
 *
 * @param str
 * @return
 */
fun isBlank(str: String?): Boolean {
    return "" == str || "null" == str || str == null
}

/**
 * 判断字符串是否不为空
 *
 * @param str
 * @return
 */
fun isNotBlank(str: String): Boolean {
    return !isBlank(str)
}

/**
 * 判断字符串是否不为空
 *
 * @param str
 * @return
 */
fun getBeanString(str: String): String {
    return if (isNotBlank(str)) {
        str
    } else ""
}


/**
 * 判断是否是手机号
 *
 * @param mobile
 * @return
 */
fun isMobileNO(mobile: String): Boolean {
    return if (mobile.length == 11) {
        true
    } else false
}

/**
 * 判断是否是手机号
 *
 * @param mobile
 * @return
 */
fun isMobileNO_(mobile: String): Boolean {
    val regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,1,3,5-8])|(18[0-9])|166|198|199|(147))\\\\d{8}$"
    val p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
    val m = p.matcher(mobile)
    return m.matches()
}

/**
 * @return
 * @describe: 校验以太坊钱包地址
 * 它只能包含以下字母：a，b，c，d，e和f。
 * 包括最初的“0x”
 * 可以具有0到9之间的任何数字
 */
fun isEthereumSite(eth: String): Boolean {
    val p = Pattern.compile("(^0x[0-9a-fA-F]{40}$)")
    val m = p.matcher(eth)
    return m.matches()
}

/**
 * @param idcard
 * @return
 * @describe: 验证身份证号
 * @author bixingfang 2013-9-20 下午5:34:03 boolean
 */
fun isIdcard(idcard: String): Boolean {
    val p = Pattern.compile("(^([0-9]{17})([0-9Xx]{1})$)|(^([0-9a-zA-Z]{8})\\-([0-9a-zA-Z]{1})$)")
    val m = p.matcher(idcard)
    return m.matches()

}

/**
 * @param email
 * @return
 * @describe: 验证邮箱是否合法
 * @author yujian 2013-5-17 上午10:36:30 boolean
 */

fun isEmail(email: String): Boolean {
    val pattern = Pattern.compile(
        "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?"
    )
    val m = pattern.matcher(email)
    return m.matches()
}

/**
 * 判断是否为整数
 *
 * @param str 传入的字符串
 * @return 是整数返回true, 否则返回false
 */
fun isInteger(str: String): Boolean {
    val pattern = Pattern.compile("^[-\\+]?[\\d]*$")
    return pattern.matcher(str).matches()
}

/**
 * 判断字符串是否为数字
 */
fun hasDigit(content: String): Boolean {
    var flag = false
    val p = Pattern.compile(".*\\d+.*")
    val m = p.matcher(content)
    if (m.matches())
        flag = true
    return flag
}

/**
 * 判断字符串是否为英文
 */
fun ischeckEnglish(str: String): Boolean {
    val p = Pattern.compile("^[a-zA-Z0-9]+$")
    val m = p.matcher(str)
    return m.find()
}

/**
 * 判断字符串是否包含中文
 */
fun ischeckChinese(str: String): Boolean {
    val p = Pattern.compile("[\\u4e00-\\u9fa5]+")
    val m = p.matcher(str)
    return m.find()
}

/**
 * 提取短信中的验证码6位
 */
fun getSmsNumber(sms: String): String? {
    val pattern = Pattern.compile("\\d{6}")
    val matcher = pattern.matcher(sms)
    return if (matcher.find()) {
        matcher.group()
    } else null
}

