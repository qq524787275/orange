package com.zhuzichu.mvvm

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.zhuzichu.mvvm.global.language.LangConfig
import com.zhuzichu.mvvm.global.language.Zh
import com.zhuzichu.mvvm.widget.MaterialHeader
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig
import me.yokeyword.fragmentation.Fragmentation
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService
import uk.co.chrisjenx.calligraphy.CalligraphyConfig


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-27
 * Time: 16:17
 */
open class App : Application() {
    companion object {
        lateinit var context: Application
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        LangConfig.initLang(Zh())
        initAutoSize()
        initFragmention()
        initDebugDb()
        initFont()

    }

    private fun initFont() {
        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }

    private fun initDebugDb() {
        SQLiteStudioService.instance().start(this)
    }

    private fun initFragmention() {
        Fragmentation.builder()
            // 设置 栈视图 模式为 （默认）悬浮球模式   SHAKE: 摇一摇唤出  NONE：隐藏， 仅在Debug环境生效
            .stackViewMode(Fragmentation.BUBBLE)
            .debug(true) // 实际场景建议.debug(BuildConfig.DEBUG)
            .install()
    }

    private fun initAutoSize() {
        AutoSizeConfig.getInstance()
            .setCustomFragment(false)
            .setLog(false)
            .setUseDeviceSize(false).isBaseOnWidth = true
        AutoSize.initCompatMultiProcess(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    init {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            //全局设置（优先级最低）
            layout.setEnableAutoLoadMore(true)
            layout.setEnableOverScrollDrag(false)
            layout.setEnableOverScrollBounce(true)
            layout.setEnableLoadMoreWhenContentNotFull(true)
            layout.setEnableScrollContentWhenRefreshed(true)
            layout.setPrimaryColorsId(R.color.white, R.color.colorPrimaryText)//全局设置主题颜色
//            ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Scale)//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            MaterialHeader(context)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            layout.setEnableFooterFollowWhenNoMoreData(true)//设置是否在全部加载结束之后Footer跟随内容
            layout.setEnableFooterTranslationContent(true)
            val footer = ClassicsFooter(context)
            ClassicsFooter.REFRESH_FOOTER_NOTHING = "我也是有底线的！"
            footer.spinnerStyle = SpinnerStyle.Scale
            footer
        }
    }
}