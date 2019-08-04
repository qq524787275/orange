package com.zhuzichu.mvvm.bean

import com.google.gson.annotations.SerializedName

data class AddressBean(
    var cityList: List<City> = listOf(),
    var gisBd09Lat: Double = 0.0,
    var gisBd09Lng: Double = 0.0,
    var gisGcj02Lat: Double = 0.0,
    var gisGcj02Lng: Double = 0.0,
    var id: String = "",
    var name: String = "",
    var pinYin: String = ""
) {
    data class City(
        @SerializedName("cityList")
        var districtList: List<District> = listOf(),
        var gisBd09Lat: Double = 0.0,
        var gisBd09Lng: Double = 0.0,
        var gisGcj02Lat: Double = 0.0,
        var gisGcj02Lng: Double = 0.0,
        var id: String = "",
        var name: String = "",
        var pinYin: String = ""
    ) {
        data class District(
            var createAccount: Any = Any(),
            var createTime: Any = Any(),
            var gisBd09Lat: Double = 0.0,
            var gisBd09Lng: Double = 0.0,
            var gisGcj02Lat: Double = 0.0,
            var gisGcj02Lng: Double = 0.0,
            var id: String = "",
            var modifyAccount: Any = Any(),
            var modifyTime: Any = Any(),
            var name: String = "",
            var orderid: Any = Any(),
            var pinYin: String = "",
            var status: Int = 0,
            var stubGroupCnt: Any = Any()
        )
    }
}