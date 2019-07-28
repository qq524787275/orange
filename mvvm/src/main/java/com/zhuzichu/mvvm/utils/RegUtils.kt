package com.zhuzichu.mvvm.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern




/**
 * 正则：电话号码
 */
const val REGEX_TEL = "^0\\d{2,3}[- ]?\\d{7,8}"
/**
 * 正则：身份证号码15位
 */
const val REGEX_IDCARD15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$"
/**
 * 正则：身份证号码18位
 */
const val REGEX_IDCARD18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9Xx])$"
/**
 * 正则：身份证号码15或18位 包含以x结尾
 */
const val REGEX_IDCARD =
    "(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$|^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|x|X)$)"
/**
 * 正则：邮箱
 */
const val REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$"
/**
 * 正则：URL
 */
const val REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?"
/**
 * 正则：汉字
 */
const val REGEX_CHZ = "^[\\u4e00-\\u9fa5]+$"
/**
 * 正则：用户名，取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位
 */
const val REGEX_USERNAME = "^[\\w\\u4e00-\\u9fa5]{6,20}(?<!_)$"
/**
 * 正则：yyyy-MM-dd格式的日期校验，已考虑平闰年
 */
const val REGEX_DATE =
    "^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$"
/**
 * 正则：IP地址
 */
const val REGEX_IP = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)"

const val REGEX_MOBILE_SIMPLE = "^[1]\\d{10}$"
/**
 * 正则：手机号（精确）
 * 移动：134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188
 * 联通：130、131、132、145、155、156、175、176、185、186
 * 电信：133、153、173、177、180、181、189
 * 全球星：1349
 * 虚拟运营商：170
 */
const val REGEX_MOBILE_EXACT = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|(147))\\d{8}$"

/**
 * 判断是否为真实手机号
 *
 * @param mobiles
 * @return
 */
fun isMobile(mobiles: String?): Boolean {
    val p =
        Pattern.compile("^(13[0-9]|15[012356789]|17[03678]|18[0-9]|14[57])[0-9]{8}$")
    val m = p.matcher(mobiles)
    return m.matches()
}

/**
 * 验证银卡卡号
 *
 * @param cardNo
 * @return
 */
fun isBankCard(cardNo: String?): Boolean {
    val p =
        Pattern.compile("^\\d{16,19}$|^\\d{6}[- ]\\d{10,13}$|^\\d{4}[- ]\\d{4}[- ]\\d{4}[- ]\\d{4,7}$")
    val m = p.matcher(cardNo)
    return m.matches()
}

/**
 * 15位和18位身份证号码的正则表达式 身份证验证
 *
 * @param idCard
 * @return
 */
fun validateIdCard(idCard: String?): Boolean {
    // 15位和18位身份证号码的正则表达式

    val regIdCard =
        "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$"
    val p = Pattern.compile(regIdCard)
    return p.matcher(idCard).matches()
}
//=========================================正则表达式=============================================

//=========================================正则表达式=============================================

//=========================================正则表达式=============================================

/**
 * 验证手机号（简单）
 *
 * @param string 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun isMobileSimple(string: String): Boolean {
    return isMatch(REGEX_MOBILE_SIMPLE, string)
}

/**
 * 验证手机号（精确）
 *
 * @param string 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun isMobileExact(string: String): Boolean {
    return isMatch(REGEX_MOBILE_EXACT, string)
}

/**
 * 验证电话号码
 *
 * @param string 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun isTel(string: String): Boolean {
    return isMatch(REGEX_TEL, string)
}

/**
 * 验证身份证号码15位
 *
 * @param string 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun isIDCard15(string: String): Boolean {
    return isMatch(REGEX_IDCARD15, string)
}

/**
 * 验证身份证号码18位
 *
 * @param string 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun isIDCard18(string: String): Boolean {
    return isMatch(REGEX_IDCARD18, string)
}

/**
 * 验证身份证号码15或18位 包含以x结尾
 *
 * @param string 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun isIDCard(string: String): Boolean {
    return isMatch(REGEX_IDCARD, string)
}

/**
 * 功能：身份证的有效验证
 *
 * @param IDStr 身份证号
 * @return 有效：返回"有效" 无效：返回String信息
 * @throws ParseException
 */
fun IDCardValidate(IDStr: String): String {
    var errorInfo = ""// 记录错误信息

    val ValCodeArr = arrayOf(
        "1", "0", "x", "9", "8", "7", "6", "5", "4",
        "3", "2"
    )
    val Wi = arrayOf(
        "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
        "9", "10", "5", "8", "4", "2"
    )
    var Ai = ""
    // ================ 号码的长度 15位或18位 ================


    if (IDStr.length != 15 && IDStr.length != 18) {
        errorInfo = "身份证号码长度应该为15位或18位。"
        return errorInfo
    }
    // =======================(end)========================

    // ================ 数字 除最后以为都为数字 ================


    if (IDStr.length == 18) {
        Ai = IDStr.substring(0, 17)
    } else if (IDStr.length == 15) {
        Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15)
    }
    if (!isNumeric(Ai)) {
        errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。"
        return errorInfo
    }
    // =======================(end)========================

    // ================ 出生年月是否有效 ================


    val strYear = Ai.substring(6, 10)// 年份

    val strMonth = Ai.substring(10, 12)// 月份

    val strDay = Ai.substring(12, 14)// 月份

    if (!isDate("$strYear-$strMonth-$strDay")) {
        errorInfo = "身份证生日无效。"
        return errorInfo
    }
    val gc = GregorianCalendar()
    val s = SimpleDateFormat("yyyy-MM-dd")
    try {
        if (gc.get(Calendar.YEAR) - Integer.parseInt(strYear) > 150
            || gc.time.time - s.parse("$strYear-$strMonth-$strDay").time < 0
        ) {
            errorInfo = "身份证生日不在有效范围。"
            return errorInfo
        }
    } catch (e: NumberFormatException) {
        e.printStackTrace()
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
        errorInfo = "身份证月份无效"
        return errorInfo
    }
    if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
        errorInfo = "身份证日期无效"
        return errorInfo
    }
    // =====================(end)=====================

    // ================ 地区码时候有效 ================


    val h = GetAreaCode()
    if (h.get(Ai.substring(0, 2)) == null) {
        errorInfo = "身份证地区编码错误。"
        return errorInfo
    }
    // ==============================================

    // ================ 判断最后一位的值 ================


    var TotalmulAiWi = 0
    for (i in 0..16) {
        TotalmulAiWi = (TotalmulAiWi
                + Integer.parseInt(Ai[i].toString())
                * Integer.parseInt(Wi[i]))
    }
    val modValue = TotalmulAiWi % 11
    val strVerifyCode = ValCodeArr[modValue]
    Ai += strVerifyCode
    if (IDStr.length == 18) {
        if (Ai == IDStr == false) {
            errorInfo = "身份证无效，不是合法的身份证号码"
            return errorInfo
        }
    }
    // =====================(end)=====================
    else {
        return "有效"
    }

    return "有效"
}

/**
 * 功能：设置地区编码
 *
 * @return Hashtable 对象
 */
fun GetAreaCode(): Hashtable<String, String> {
    val hashtable: Hashtable<String, String> = Hashtable()
    hashtable["11"] = "北京"
    hashtable["12"] = "天津"
    hashtable["13"] = "河北"
    hashtable["14"] = "山西"
    hashtable["15"] = "内蒙古"
    hashtable["21"] = "辽宁"
    hashtable["22"] = "吉林"
    hashtable["23"] = "黑龙江"
    hashtable["31"] = "上海"
    hashtable["32"] = "江苏"
    hashtable["33"] = "浙江"
    hashtable["34"] = "安徽"
    hashtable["35"] = "福建"
    hashtable["36"] = "江西"
    hashtable["37"] = "山东"
    hashtable["41"] = "河南"
    hashtable["42"] = "湖北"
    hashtable["43"] = "湖南"
    hashtable["44"] = "广东"
    hashtable["45"] = "广西"
    hashtable["46"] = "海南"
    hashtable["50"] = "重庆"
    hashtable["51"] = "四川"
    hashtable["52"] = "贵州"
    hashtable["53"] = "云南"
    hashtable["54"] = "西藏"
    hashtable["61"] = "陕西"
    hashtable["62"] = "甘肃"
    hashtable["63"] = "青海"
    hashtable["64"] = "宁夏"
    hashtable["65"] = "新疆"
    hashtable["71"] = "台湾"
    hashtable["81"] = "香港"
    hashtable["82"] = "澳门"
    hashtable["91"] = "国外"
    return hashtable
}

/**
 * 功能：判断字符串是否为数字
 *
 * @param str
 * @return
 */
fun isNumeric(str: String): Boolean {
    val pattern = Pattern.compile("[0-9]*")
    val isNum = pattern.matcher(str)
    return isNum.matches()
}

/**
 * 验证邮箱
 *
 * @param string 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun isEmail(string: String): Boolean {
    return isMatch(REGEX_EMAIL, string)
}

/**
 * 验证URL
 *
 * @param string 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun isURL(string: String): Boolean {
    return isMatch(REGEX_URL, string)
}

/**
 * 验证汉字
 *
 * @param string 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun isChz(string: String): Boolean {
    return isMatch(REGEX_CHZ, string)
}

/**
 * 验证用户名
 *
 * 取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位
 *
 * @param string 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun isUsername(string: String): Boolean {
    return isMatch(REGEX_USERNAME, string)
}

/**
 * 验证yyyy-MM-dd格式的日期校验，已考虑平闰年
 *
 * @param string 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun isDate(string: String): Boolean {
    return isMatch(REGEX_DATE, string)
}

/**
 * 验证IP地址
 *
 * @param string 待验证文本
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun isIP(string: String): Boolean {
    return isMatch(REGEX_IP, string)
}

/**
 * string是否匹配regex正则表达式字符串
 *
 * @param regex  正则表达式字符串
 * @param string 要匹配的字符串
 * @return `true`: 匹配<br></br>`false`: 不匹配
 */
fun isMatch(regex: String?, string: String): Boolean {
    return string.isNotBlank() && Pattern.matches(regex, string)
}

/**
 * 验证固定电话号码
 *
 * @param phone 电话号码，格式：国家（地区）电话代码 + 区号（城市代码） + 电话号码，如：+8602085588447
 *
 * **国家（地区） 代码 ：**标识电话号码的国家（地区）的标准国家（地区）代码。它包含从 0 到 9 的一位或多位数字，
 * 数字之后是空格分隔的国家（地区）代码。
 *
 * **区号（城市代码）：**这可能包含一个或多个从 0 到 9 的数字，地区或城市代码放在圆括号——
 * 对不使用地区或城市代码的国家（地区），则省略该组件。
 *
 * **电话号码：**这包含从 0 到 9 的一个或多个数字
 * @return 验证成功返回true，验证失败返回false
 */
fun checkPhone(phone: String?): Boolean {
    val regex = "(\\+\\d+)?(\\d{3,4}\\-?)?\\d{7,8}$"
    return Pattern.matches(regex, phone)
}

/**
 * 验证整数（正整数和负整数）
 *
 * @param digit 一位或多位0-9之间的整数
 * @return 验证成功返回true，验证失败返回false
 */
fun checkDigit(digit: String?): Boolean {
    val regex = "\\-?[1-9]\\d+"
    return Pattern.matches(regex, digit)
}

/**
 * 验证整数和浮点数（正负整数和正负浮点数）
 *
 * @param decimals 一位或多位0-9之间的浮点数，如：1.23，233.30
 * @return 验证成功返回true，验证失败返回false
 */
fun checkDecimals(decimals: String?): Boolean {
    val regex = "\\-?[1-9]\\d+(\\.\\d+)?"
    return Pattern.matches(regex, decimals)
}

/**
 * 验证空白字符
 *
 * @param blankSpace 空白字符，包括：空格、\t、\n、\r、\f、\x0B
 * @return 验证成功返回true，验证失败返回false
 */
fun checkBlankSpace(blankSpace: String?): Boolean {
    val regex = "\\s+"
    return Pattern.matches(regex, blankSpace)
}

/**
 * 验证日期（年月日）
 *
 * @param birthday 日期，格式：1992-09-03，或1992.09.03
 * @return 验证成功返回true，验证失败返回false
 */
fun checkBirthday(birthday: String?): Boolean {
    val regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}"
    return Pattern.matches(regex, birthday)
}

/**
 * 匹配中国邮政编码
 *
 * @param postcode 邮政编码
 * @return 验证成功返回true，验证失败返回false
 */
fun checkPostcode(postcode: String?): Boolean {
    val regex = "[1-9]\\d{5}"
    return Pattern.matches(regex, postcode)
}