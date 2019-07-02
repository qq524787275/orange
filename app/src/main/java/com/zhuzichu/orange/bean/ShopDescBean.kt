package com.zhuzichu.orange.bean

data class ShopDescBean(
    var detailDesc: DetailDesc = DetailDesc()
) {

    data class DetailDesc(
        var newWapDescDynUrl: String = "",
        var newWapDescJson: List<NewWapDescJson> = listOf()
    ) {
        data class NewWapDescJson(
            var `data`: List<Data> = listOf(),
            var component: String = "",
            var enable: Boolean = false,
            var moduleKey: String = "",
            var moduleName: String = "",
            var moduleType: Int = 0
        ) {
            data class Data(
                var height: Int = 0,
                var img: String = "",
                var width: Int = 0
            )
        }
    }
}