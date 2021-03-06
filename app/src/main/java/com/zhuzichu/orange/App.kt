package com.zhuzichu.orange

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.baichuan.android.trade.AlibcTradeSDK
import com.alibaba.baichuan.android.trade.callback.AlibcTradeInitCallback
import com.alibaba.baichuan.trade.biz.login.AlibcLogin
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.zhuzichu.mvvm.R
import com.zhuzichu.mvvm.crash.CaocConfig
import com.zhuzichu.mvvm.global.AppGlobal
import com.zhuzichu.mvvm.utils.isAppMainProcess
import com.zhuzichu.mvvm.utils.logi
import com.zhuzichu.orange.main.activity.MainActivity
import io.flutter.view.FlutterMain
import io.reactivex.plugins.RxJavaPlugins
import me.jessyan.autosize.AutoSize
import me.jessyan.autosize.AutoSizeConfig
import me.yokeyword.fragmentation.Fragmentation

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-27
 * Time: 16:17
 */
class App : Application() {
    companion object {
        lateinit var context: Application
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        AppGlobal.init(context)
        if (isAppMainProcess()) {
            initAutoSize()
            initFragmention()
//        initDebugDb()
            FlutterMain.startInitialization(applicationContext)
            initSdk()
            RxJavaPlugins.setErrorHandler {}
            initCrash()
        } else {
            "不是主进程".logi("ahhahahahaha")
        }
    }

    private fun initSdk() {
        //电商SDK初始化
        AlibcTradeSDK.asyncInit(this, object : AlibcTradeInitCallback {
            override fun onSuccess() {
                AppGlobal.isAuth.set(AlibcLogin.getInstance().isLogin)
                AppGlobal.session.set(AlibcLogin.getInstance().session)
                "初始化成功".plus(AlibcLogin.getInstance().isLogin).logi()
            }

            override fun onFailure(code: Int, msg: String) {
                ("初始化失败,错误码=$code / 错误消息=$msg").logi()
            }
        })
    }

    private fun initCrash() {
        CaocConfig.Builder.create()
            .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
            .enabled(true) //是否启动全局异常捕获
            .showErrorDetails(true) //是否显示错误详细信息
            .showRestartButton(true) //是否显示重启按钮
            .trackActivities(true) //是否跟踪Activity
            .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
//            .errorDrawable(R.mipmap.ic_launcher)
            .restartActivity(MainActivity::class.java) //重新启动后的activity
            .apply()
    }

//    private fun initDebugDb() {
//        SQLiteStudioService.instance().start(this)
//    }

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
//            layout.setDisableContentWhenRefresh(true)//是否在刷新的时候禁止列表的操作
            layout.setPrimaryColorsId(R.color.transparent, R.color.colorPrimaryText)//全局设置主题颜色
            ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Scale)//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            layout.setEnableFooterFollowWhenNoMoreData(true)//设置是否在全部加载结束之后Footer跟随内容
            layout.setEnableFooterTranslationContent(true)
            layout.setEnableAutoLoadMore(true)
//            layout.setDisableContentWhenLoading(true)//是否在加载的时候禁止列表的操作
            val footer = ClassicsFooter(context)
            ClassicsFooter.REFRESH_FOOTER_NOTHING = "我也是有底线的！"
            footer.spinnerStyle = SpinnerStyle.Scale
            footer
        }
    }
}