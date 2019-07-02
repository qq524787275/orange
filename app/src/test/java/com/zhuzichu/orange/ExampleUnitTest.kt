package com.zhuzichu.orange

import org.junit.Test
import java.util.regex.Pattern

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val s = "\n" +
                "    Hub = {};\n" +
                "    Hub.config = {\n" +
                "        config: {},\n" +
                "        get: function(key) {\n" +
                "            if (key in this.config) {\n" +
                "                return this.config[key];\n" +
                "            } else {\n" +
                "                return null;\n" +
                "            }\n" +
                "        },\n" +
                "        set: function(key, val) {\n" +
                "            this.config[key] = val;\n" +
                "        }\n" +
                "    };\n" +
                "\n" +
                "    Hub.config.set('sku', {\n" +
                "        valCartInfo      : {\n" +
                "            itemId : '596033772003',\n" +
                "            cartUrl: '//cart.taobao.com/cart.htm'\n" +
                "        },\n" +
                "        apiRelateMarket  : '//tui.taobao.com/recommend?appid=16&count=4&itemid=596033772003',\n" +
                "        apiAddCart       : '//cart.taobao.com/add_cart_item.htm?item_id=596033772003',\n" +
                "        apiInsurance     : '',\n" +
                "        wholeSibUrl      : '//detailskip.taobao.com/service/getData/1/p1/item/detail/sib.htm?itemId=596033772003&sellerId=4233786060&modules=dynStock,qrcode,viewer,price,duty,xmpPromotion,delivery,activity,fqg,zjys,couponActivity,soldQuantity,originalPrice,tradeContract',\n" +
                "        areaLimit        : '',\n" +
                "        bigGroupUrl      : '',\n" +
                "        valPostFee       : '',\n" +
                "        coupon           : {\n" +
                "            couponApi         : '//detailskip.taobao.com/json/activity.htm?itemId=596033772003&sellerId=4233786060',\n" +
                "            couponWidgetDomain: '//assets.alicdn.com',\n" +
                "            cbUrl             : '/cross.htm?type=weibo'\n" +
                "        },\n" +
                "        valItemInfo      : {\n" +
                "            \n" +
                "        }\n" +
                "    });\n" +
                "\n" +
                "    Hub.config.set('desc', {\n" +
                "        dummy       : false,\n" +
                "        apiImgInfo  : '//tds.alicdn.com/json/item_imgs.htm?t=TB1hmfvdlWD3KVjSZKPXXap7FXa&sid=4233786060&id=596033772003&s=49ebe0dd13845cf41dc4ec587698893a&v=2&m=1',\n" +
                "        similarItems: {\n" +
                "            api           : '//tds.alicdn.com/recommended_same_type_items.htm?v=1',\n" +
                "            rstShopId     : '211991002',\n" +
                "            rstItemId     : '596033772003',\n" +
                "            rstdk         : 0,\n" +
                "            rstShopcatlist: ''\n" +
                "        }\n" +
                "    });\n" +
                "\n" +
                "    \n" +
                "    Hub.config.set('async_dc', {\n" +
                "        newDc : true,\n" +
                "        api   : '//hdc1new.taobao.com/asyn.htm?userId=4233786060&pageId=1671318848&v=2014'\n" +
                "    });\n" +
                "    \n" +
                "\n" +
                "    Hub.config.set('support', {\n" +
                "        url : '//osdsc.alicdn.com/designsystem/TB1F5lYclCw3KVjSZR0UxncUpXa.groupmember|var^groupMember;sign^fa442eb32bc652e4e2e6b5604f81bb9d;lang^gbk;t^1561984621000'\n" +
                "    });\n" +
                "\n" +
                "    Hub.config.set('async_sys', {\n" +
                "        api: '//item.taobao.com/asyn.htm?g=sys&v=2'\n" +
                "    });\n"

        val p = Pattern.compile("apiImgInfo  : '.*'")
        val m = p.matcher(s)
        if (m.find()) {
            print(m.group().split("'")[1])
        }
    }
}
