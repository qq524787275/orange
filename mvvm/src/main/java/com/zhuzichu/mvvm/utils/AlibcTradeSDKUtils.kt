package com.zhuzichu.mvvm.utils

import android.app.Activity
import android.text.TextUtils
import android.widget.Toast
import com.alibaba.baichuan.android.trade.AlibcTrade
import com.alibaba.baichuan.android.trade.callback.AlibcTradeCallback
import com.alibaba.baichuan.android.trade.model.AlibcShowParams
import com.alibaba.baichuan.android.trade.model.OpenType
import com.alibaba.baichuan.android.trade.page.*
import com.alibaba.baichuan.trade.biz.context.AlibcTradeResult
import com.alibaba.baichuan.trade.biz.core.taoke.AlibcTaokeParams


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-19
 * Time: 16:51
 */

//页面打开方式，默认，H5，Native
private var alibcShowParams: AlibcShowParams = AlibcShowParams(OpenType.Native, false).apply {
    clientType = "taobao_scheme"
}
//淘客参数，包括pid，unionid，subPid
private var alibcTaokeParams: AlibcTaokeParams = AlibcTaokeParams().apply {
    pid = "mm_440730086_576500325_109076000438"
    extraParams = mapOf(Pair("taokeAppkey", "27640838"))
}

private val tradeCallback = object : AlibcTradeCallback {
    override fun onFailure(code: Int, msg: String?) {

    }

    override fun onTradeSuccess(result: AlibcTradeResult?) {

    }

}

/**
 * @param url
 * 打开指定链接
 */
fun showTradeUrl(
    context: Activity?,
    url: String,
    extras: Map<String, String>? = mapOf(),
    alibcTradeCallback: AlibcTradeCallback? = tradeCallback
) {
    if (TextUtils.isEmpty(url)) {
        Toast.makeText(context, "URL为空", Toast.LENGTH_SHORT).show()
        return
    }
    AlibcTrade.show(context, AlibcPage(url), alibcShowParams, alibcTaokeParams, extras, alibcTradeCallback)
}

/**
 * @param itemId
 * 显示商品详情页
 */
fun showTradeDetail(
    context: Activity?,
    itemId: String,
    extras: Map<String, String>? = mapOf(),
    alibcTradeCallback: AlibcTradeCallback? = tradeCallback
) {
    val alibcBasePage = AlibcDetailPage(itemId)
    AlibcTrade.show(context, alibcBasePage, alibcShowParams, alibcTaokeParams, extras, alibcTradeCallback)

}

/**
 * @param context
 * 分域显示我的订单，或者全部显示我的订单
 */
fun showTradeOrder(
    context: Activity?,
    type: Int = 0,
    extras: Map<String, String>? = mapOf(),
    alibcTradeCallback: AlibcTradeCallback? = tradeCallback
) {
    val alibcBasePage = AlibcMyOrdersPage(type, true)
    AlibcTrade.show(context, alibcBasePage, alibcShowParams, alibcTaokeParams, extras, alibcTradeCallback)
}

/**
 *店铺，店铺id，支持明文id
 */
fun showTradeShop(
    context: Activity?,
    shopId: String,
    extras: Map<String, String>? = mapOf(),
    alibcTradeCallback: AlibcTradeCallback? = tradeCallback
) {
    val alibcBasePage = AlibcShopPage(shopId)
    AlibcTrade.show(context, alibcBasePage, alibcShowParams, alibcTaokeParams, extras, alibcTradeCallback)
}

/**
 *店铺，店铺id，支持明文id
 */
fun showTradeShopCart(
    context: Activity?,
    extras: Map<String, String>? = mapOf(),
    alibcTradeCallback: AlibcTradeCallback? = tradeCallback
) {
    val alibcBasePage = AlibcMyCartsPage()
    AlibcTrade.show(context, alibcBasePage, alibcShowParams, alibcTaokeParams, extras, alibcTradeCallback)
}

/**
 *添加购物车，支持itemId和openItemId的商品，必填，不允许为null；
 */
fun showTradeAddCart(
    context: Activity?,
    id: String,
    extras: Map<String, String>? = mapOf(),
    alibcTradeCallback: AlibcTradeCallback? = tradeCallback
) {
    val alibcBasePage = AlibcAddCartPage(id)
    AlibcTrade.show(context, alibcBasePage, alibcShowParams, alibcTaokeParams, extras, alibcTradeCallback)
}