package com.zhuzichu.mvvm.bean

class TalentcatBean(
    var clickdata: List<Clickdata> = listOf(),
    var newdata: List<Newdata> = listOf(),
    var topdata: List<Topdata> = listOf()
) {
    data class Newdata(
        var app_image: Any = Any(),
        var article_banner: String = "",
        var compose_image: String = "",
        var head_img: String = "",
        var highquality: String = "",
        var id: String = "",
        var image: String = "",
        var itemnum: String = "",
        var label: String = "",
        var name: String = "",
        var readtimes: String = "",
        var shorttitle: String = "",
        var talent_id: String = "",
        var talent_name: String = "",
        var talentcat: String = "",
        var tk_item_id: String = ""
    )

    data class Clickdata(
        var app_image: Any = Any(),
        var article: String = "",
        var article_banner: String = "",
        var compose_image: String = "",
        var head_img: String = "",
        var highquality: String = "",
        var id: String = "",
        var image: String = "",
        var itemnum: String = "",
        var label: String = "",
        var name: String = "",
        var readtimes: String = "",
        var shorttitle: String = "",
        var talent_id: String = "",
        var talent_name: String = "",
        var talentcat: String = "",
        var tk_item_id: String = ""
    )

    data class Topdata(
        var app_image: String = "",
        var article_banner: String = "",
        var compose_image: String = "",
        var highquality: String = "",
        var id: String = "",
        var image: String = "",
        var itemnum: String = "",
        var label: String = "",
        var name: String = "",
        var readtimes: String = "",
        var shorttitle: String = "",
        var talent_id: String = "",
        var talent_name: String = "",
        var talentcat: String = "",
        var tk_item_id: String = ""
    )
}
