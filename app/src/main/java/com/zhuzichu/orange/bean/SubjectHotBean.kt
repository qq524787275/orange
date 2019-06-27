package com.zhuzichu.orange.bean

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-27
 * Time: 16:50
 */
data class SubjectHotBean(
    var activity_end_time: String = "",
    var activity_start_time: String = "",
    var addtime: String = "",
    var app_hot_image: String = "",
    var content: String = "",
    var copy_text: String = "",
    var item_data: List<ItemData> = listOf(),
    var item_type: String = "",
    var name: String = "",
    var share_times: String = "",
    var show_text: String = "",
    var subject_id: String = ""
) {
    data class ItemData(
        var activity_type: String = "",
        var activityid: Any = Any(),
        var clickurl: String = "",
        var couponendtime: String = "",
        var couponmoney: String = "",
        var couponstarttime: String = "",
        var couponurl: String = "",
        var fqcat: String = "",
        var guide_article: String = "",
        var is_brand: String = "",
        var is_live: String = "",
        var itemdesc: String = "",
        var itemendprice: Double = 0.0,
        var itemid: String = "",
        var itempic: String = "",
        var itempic_copy: String = "",
        var itempic_type: String = "",
        var itemprice: Double = 0.0,
        var itemsale: String = "",
        var itemsale2: String = "",
        var itemshorttitle: String = "",
        var itemtitle: String = "",
        var product_id: String = "",
        var shoptype: String = "",
        var son_category: String = "",
        var subject_id: String = "",
        var tkrates: Double = 0.0,
        var todaysale: String = "",
        var videoid: String = ""
    )
}
