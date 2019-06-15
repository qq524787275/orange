package com.zhuzichu.mvvm;

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.zhuzichu.mvvm.crash.CaocConfig
import com.zhuzichu.mvvm.global.language.LangConfig
import com.zhuzichu.mvvm.global.language.Zh
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig
import me.yokeyword.fragmentation.Fragmentation
import android.app.LauncherActivity
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService


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
        initCrash()
        initDebugDb()
    }

    private fun initDebugDb() {
        SQLiteStudioService.instance().start(this)
    }

    private fun initCrash() {
        CaocConfig.Builder.create()
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
            .enabled(true) //是否启动全局异常捕获
            .showErrorDetails(true) //是否显示错误详细信息
            .showRestartButton(true) //是否显示重启按钮
            .trackActivities(true) //是否跟踪Activity
            .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
            .restartActivity(LauncherActivity::class.java) //重新启动后的activity
            //           java     .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
            //                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
            .apply()
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
            layout.setEnableOverScrollDrag(false)//禁止越界拖动（1.0.4以上版本）
            layout.setEnableOverScrollBounce(false)//关闭越界回弹功能
            layout.setEnableLoadMoreWhenContentNotFull(false)
            layout.setEnableAutoLoadMore(true)//设置是否监听列表在滚动到底部时触发加载事件
            layout.setPrimaryColorsId(R.color.white, R.color.colorPrimaryText)//全局设置主题颜色
            ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Scale)//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            layout.setEnableFooterFollowWhenLoadFinished(true)//设置是否在全部加载结束之后Footer跟随内容
            val footer = ClassicsFooter(context)
            ClassicsFooter.REFRESH_FOOTER_NOTHING = "我也是有底线的！"
            footer.spinnerStyle = SpinnerStyle.Scale
            footer
        }
    }
}