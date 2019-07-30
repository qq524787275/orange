package com.zhuzichu.mvvm.bean


data class BrandBean(
    var brand_logo: String = "",
    var brandcat: String = "",
    var fq_brand_name: String = "",
    var id: String = "",
    var introduce: String = "",
    var item: List<Item> = listOf(),
    var tb_brand_name: String = ""
) {
    data class Item(
        var activity_type: String = "",
        var brand_name: String = "",
        var couponendtime: String = "",
        var couponexplain: String = "",
        var couponmoney: String = "",
        var couponstarttime: String = "",
        var couponurl: String = "",
        var end_time: String = "",
        var general_index: String = "",
        var itemdesc: String = "",
        var itemendprice: String = "",
        var itemid: String = "",
        var itempic: String = "",
        var itempic_copy: String = "",
        var itemprice: String = "",
        var itemsale: String = "",
        var itemsale2: String = "",
        var itemshorttitle: String = "",
        var itemtitle: String = "",
        var sellernick: String = "",
        var shopid: String = "",
        var shopname: String = "",
        var shoptype: String = "",
        var son_category: String = "",
        var start_time: String = "",
        var tkmoney: String = "",
        var tkrates: String = "",
        var tktype: String = "",
        var todaysale: String = ""
    )
}
