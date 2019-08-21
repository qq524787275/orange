package com.zhuzichu.mvvm.bean

import java.io.Serializable

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-08-19
 * Time: 14:23
 */
data class GoodsBean(
    var tbk_dg_material_optional_response: TbkDgMaterialOptionalResponse = TbkDgMaterialOptionalResponse()
) {
    data class TbkDgMaterialOptionalResponse(
        var request_id: String = "",
        var result_list: ResultList = ResultList(),
        var total_results: Int = 0
    ) {
        data class ResultList(
            var map_data: List<MapData> = listOf()
        ) {
            data class MapData(
                var category_id: Int = 0,
                var category_name: String = "",
                var commission_rate: String = "",
                var commission_type: String = "",
                var coupon_amount: String = "",
                var coupon_end_time: String = "",
                var coupon_id: String = "",
                var coupon_info: String = "",
                var coupon_remain_count: Int = 0,
                var coupon_share_url: String = "",
                var coupon_start_fee: String = "",
                var coupon_start_time: String = "",
                var coupon_total_count: Int = 0,
                var include_dxjh: String = "",
                var include_mkt: String = "",
                var info_dxjh: String = "",
                var item_description: String = "",
                var item_id: Long = 0,
                var item_url: String = "",
                var level_one_category_id: Int = 0,
                var level_one_category_name: String = "",
                var nick: String = "",
                var num_iid: Long = 0,
                var pict_url: String = "",
                var provcity: String = "",
                var real_post_fee: String = "",
                var reserve_price: String = "",
                var seller_id: Long = 0,
                var shop_dsr: Int = 0,
                var shop_title: String = "",
                var short_title: String = "",
                var small_images: SmallImages = SmallImages(),
                var title: String = "",
                var tk_total_commi: String = "",
                var tk_total_sales: String = "",
                var url: String = "",
                var user_type: Int = 0,
                var volume: Int = 0,
                var white_image: String = "",
                var x_id: String = "",
                var zk_final_price: String = ""
            ) : Serializable {
                data class SmallImages(
                    var string: List<String> = listOf()
                ) : Serializable
            }
        }
    }
}