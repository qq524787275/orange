package com.zhuzichu.mvvm.utils

import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-31
 * Time: 14:41
 */
private val yearLevelValue = (365 * 24 * 60 * 60 * 1000).toLong()
private val monthLevelValue = (30 * 24 * 60 * 60 * 1000).toLong()
private val dayLevelValue = (24 * 60 * 60 * 1000).toLong()
private val hourLevelValue = (60 * 60 * 1000).toLong()
private val minuteLevelValue = (60 * 1000).toLong()
private val secondLevelValue: Long = 1000

fun getDifference(nowTime: Long, targetTime: Long): String {// 目标时间与当前时间差
    val period = targetTime - nowTime
    return getDifference(period)
}

fun getDifference(period: Long): String {// 根据毫秒差计算时间差
    var result: String?
    /******* 计算出时间差中的年、月、日、天、时、分、秒  */
    val year = getYear(period)
    val month = getMonth(period - year * yearLevelValue)
    val day = getDay(period - year * yearLevelValue - month * monthLevelValue)
    val hour = getHour(period - year * yearLevelValue - month * monthLevelValue - day * dayLevelValue)
    val minute =
        getMinute(period - year * yearLevelValue - month * monthLevelValue - day * dayLevelValue - hour * hourLevelValue)
    val second =
        getSecond(period - year * yearLevelValue - month * monthLevelValue - day * dayLevelValue - hour * hourLevelValue - minute * minuteLevelValue)
    if (year == 0) {
        result = month.toString() + "月" + day + "天" + hour + "小时" + minute + "分" + second + "秒"
        if (month == 0) {
            result = day.toString() + "天" + hour + "小时" + minute + "分" + second + "秒"
            /*if(day==0){
                result = hour + "时" + minute + "分" + second + "秒";
                if(hour==0){
                    result = minute + "分" + second + "秒";
                    if(minute==0){
                        result = second + "秒";
                        if(second==0){
                            result="时间已到";
                        }
                    }
                }
            }*/
        }
    } else {
        result = year.toString() + "年" + month + "月" + day + "天" + hour + "小时" + minute + "分" + second + "秒"
    }
    return result
}

fun getYear(period: Long): Int {
    return (period / yearLevelValue).toInt()
}

fun getMonth(period: Long): Int {
    return (period / monthLevelValue).toInt()
}

fun getDay(period: Long): Int {
    return (period / dayLevelValue).toInt()
}

fun getHour(period: Long): Int {
    return (period / hourLevelValue).toInt()
}

fun getMinute(period: Long): Int {
    return (period / minuteLevelValue).toInt()
}

fun getSecond(period: Long): Int {
    return (period / secondLevelValue).toInt()
}

/**
 * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
 * 若是当天，则显示时间12:00，若是其他时间（当年），月/日；其他年份，年/月/日
 * @param
 * @return
 */
fun convertTimeToFormata(timeStamp: Long): String {
    try {
        var str = ""
        val now = Calendar.getInstance()
        val ms =
            (1000 * (now.get(Calendar.HOUR_OF_DAY) * 3600 + now.get(Calendar.MINUTE) * 60 + now.get(Calendar.SECOND))).toLong()// 毫秒数
        val ms_now = now.timeInMillis
        val newTime = SimpleDateFormat("HH:mm").format(Date(timeStamp)).toString()
        if (ms_now - timeStamp < ms) {
            str = "今天 $newTime"
        } else if (ms_now - timeStamp < ms + 24 * 3600 * 1000) {

        }
        return str
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(timeStamp)).toString()
}


/**
 * 将一个时间戳转换成提示性时间字符串，如刚刚，1秒前
 * @param timeStamp
 * @return
 */
fun convertTimeToFormat(timeStamp: Long): String {
    return if (isNow(timeStamp)) {
        SimpleDateFormat("HH:mm").format(timeStamp).toString()
    } else if (isYear(timeStamp)) {//yyyy-MM-dd
        SimpleDateFormat("MM/dd").format(timeStamp).toString()
    } else {
        SimpleDateFormat("yyyy/MM/dd").format(timeStamp).toString()
    }
}

/**
 * 判断时间是不是今天
 * @return    是返回true，不是返回false
 */
private fun isNow(timeStamp: Long): Boolean {
    //当前时间
    val now = Date()
    val sf = SimpleDateFormat("yyyyMMdd")
    //获取今天的日期
    val nowDay = sf.format(now)
    //对比的时间
    val day = sf.format(Date(timeStamp))
    return day == nowDay
}

/**
 * 判断时间是不是今年
 * @return    是返回true，不是返回false
 */
private fun isYear(timeStamp: Long): Boolean {
    //当前时间
    val now = Calendar.getInstance()
    return now.get(Calendar.YEAR).toString() == getTimeToMs1(timeStamp)
}

/**
 * 将时间改成今天、昨天、前天
 */
fun getTimeType(timeStamp: Long, pattern: String, addTime: Boolean): String {
    var pattern = pattern
    try {
        var str: String
        val now = Calendar.getInstance()
        val ms =
            (1000 * (now.get(Calendar.HOUR_OF_DAY) * 3600 + now.get(Calendar.MINUTE) * 60 + now.get(Calendar.SECOND))).toLong()// 毫秒数
        val ms_now = now.timeInMillis
        val newTime = SimpleDateFormat("HH:mm").format(Date(timeStamp)).toString()
        if (ms_now - timeStamp < ms) {
            str = "今天 $newTime"
        } else if (ms_now - timeStamp < ms + 24 * 3600 * 1000) {
            pattern = "HH:mm"
            val time = SimpleDateFormat(pattern).format(Date(timeStamp)).toString()
            str = if (addTime) "昨天 $time" else "昨天"
            //+newTime;
        } /*else if (ms_now - timeStamp < (ms + 24 * 3600 * 1000 * 2)) {
                str = "前天";
				//+newTim;
			}*/
        else {
            if (TextUtils.isEmpty(pattern))
                pattern = "MM-dd"
            str = SimpleDateFormat(pattern).format(Date(timeStamp)).toString()
        }
        return str
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date(timeStamp)).toString()
}

/**
 * 获取系统当前时间
 *
 * @param pattern
 * @return
 */
fun getTime(pattern: String): String {
    val format = SimpleDateFormat(pattern)
    val date = Date()
    return format.format(date)
}

/**
 * 毫秒转字符串时间
 *
 * @param time
 * @param pattern 格式
 * @return
 */
fun getMilltoTime(time: Long, pattern: String): String {
    val format = SimpleDateFormat(pattern)
    val date = Date(time)
    return format.format(date)
}

/**
 * 字符串时间转毫秒
 *
 * @param time
 * @param pattern 格式
 * @return
 */
fun getTimetoMill(time: String, pattern: String): Long {
    try {
        val c = Calendar.getInstance()
        c.time = SimpleDateFormat(pattern).parse(time)
        return c.timeInMillis
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return 0
}


/**
 * 毫秒数转日期
 */
fun getTimeToHH(seconds: Long): String {
    val d = Date(seconds)
    val sdf = SimpleDateFormat("HH")
    return sdf.format(d).toString()
}

fun getTimeToHm(seconds: Long): String {
    val d = Date(seconds)
    val sdf = SimpleDateFormat("HH:mm")
    return sdf.format(d).toString()
}

fun getTimeToHms(seconds: Long): String {
    val d = Date(seconds)
    val sdf = SimpleDateFormat("HH:mm:ss")
    return sdf.format(d).toString()
}

fun getTimeToYMDHms(seconds: Long): String {
    val d = Date(seconds)
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return sdf.format(d).toString()
}

/**
 * 毫秒数转日期
 */
fun getTimeToMs1(seconds: Long): String {
    val d = Date(seconds)
    val sdf = SimpleDateFormat("yyyy")
    return sdf.format(d).toString()
}

/**
 * 毫秒数转日期
 */
fun getTimeToM(seconds: Long): String {
    val d = Date(seconds)
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    return sdf.format(d).toString()
}

/**
 * 毫秒数转日期
 */
fun getTimeToE(seconds: Long): String {
    val d = Date(seconds)
    val sdf = SimpleDateFormat("yyyy-MM-dd  EEEE")
    return sdf.format(d).toString()
}

/**
 * 毫秒数转日期
 */
fun getTimeMDHM(seconds: Long): String {
    val d = Date(seconds)
    val sdf = SimpleDateFormat("MM.dd HH:mm")
    return sdf.format(d).toString()
}

/**
 * 毫秒数转日期
 */
fun getTimeToEhm(seconds: Long): String {
    val d = Date(seconds)
    val sdf = SimpleDateFormat("yyyy-MM-dd  EEEE HH:mm")
    return sdf.format(d).toString()
}
