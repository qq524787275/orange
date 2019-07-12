package com.zhuzichu.orange.flutter.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.FrameLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentationMagician
import com.zhuzichu.mvvm.base.BaseFragment
import com.zhuzichu.mvvm.global.color.ColorGlobal
import com.zhuzichu.mvvm.utils.logi
import com.zhuzichu.orange.R
import io.flutter.facade.Flutter
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.MethodChannel
import io.flutter.view.FlutterView
import me.yokeyword.fragmentation.ExtraTransaction
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragmentDelegate
import me.yokeyword.fragmentation.SupportHelper
import me.yokeyword.fragmentation.anim.DefaultNoAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-05
 * Time: 15:21
 */
abstract class BaseFlutterFragment : Fragment(), ISupportFragment, BasicMessageChannel.MessageHandler<String> {

    companion object {
        const val FLUTTER_LOG_CHANNEL = "android_log"
        const val FLUTTER_NAVIGATION_CHANNEL = "flutter_navigation"
    }

    private val _delegate by lazy { SupportFragmentDelegate(this) }
    private val _contentView: View by lazy {
        layoutInflater.inflate(R.layout.fragment_base_flutter, null)
    }
    private lateinit var _activity: FragmentActivity
    private lateinit var _flutterView: FlutterView
    private var _isCurrent = true

    abstract fun setRoute(): String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _flutterView = Flutter.createView(_activity, lifecycle, setRoute())
        val loadingLayout = _contentView.findViewById<View>(R.id.layout_loading)
        ColorGlobal.contentBackground.get()?.let { loadingLayout.setBackgroundColor(it) }
        _flutterView.addFirstFrameListener {
            _contentView.postDelayed({
                loadingLayout.visibility = View.GONE
            }, 100)
        }
        MethodChannel(
            _flutterView,
            FLUTTER_LOG_CHANNEL
        ).setMethodCallHandler { call, _ ->
            val tag: String = call.argument("tag")!!
            val message: String = call.argument("msg")!!
            message.logi(tag)
        }

        val methodChannel = MethodChannel(
            _flutterView,
            FLUTTER_NAVIGATION_CHANNEL
        )
        methodChannel.setMethodCallHandler { call, _ ->
            if (call.method == "setCurrent") {
                _isCurrent = call.argument("isCurrent")!!
            }
        }
        _contentView.findViewById<FrameLayout>(R.id.container).addView(_flutterView)
        return _contentView
    }

    override fun onMessage(s: String?, p1: BasicMessageChannel.Reply<String>) {
        s?.logi("onCreateView")
    }

    override fun getSupportDelegate(): SupportFragmentDelegate {
        return _delegate
    }

    /**
     * Perform some extra transactions.
     * 额外的事务：自定义Tag，添加SharedElement动画，操作非回退栈Fragment
     */
    override fun extraTransaction(): ExtraTransaction {
        return _delegate.extraTransaction()
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        _delegate.onAttach(activity!!)
        _activity = _delegate.activity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _delegate.onCreate(savedInstanceState)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        _delegate.onActivityCreated(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        _delegate.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        _delegate.onResume()
    }

    override fun onPause() {
        super.onPause()
        _delegate.onPause()
    }

    override fun onDestroyView() {
        _delegate.onDestroyView()
        super.onDestroyView()
    }

    override fun onDestroy() {
        _delegate.onDestroy()
        super.onDestroy()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        _delegate.onHiddenChanged(hidden)
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        _delegate.setUserVisibleHint(isVisibleToUser)
    }

    /**
     * Causes the Runnable r to be added to the action queue.
     *
     *
     * The runnable will be run after all the previous action has been run.
     *
     *
     * 前面的事务全部执行后 执行该Action
     *
     */
    @Deprecated("Use {@link #post(Runnable)} instead.", ReplaceWith("_delegate.enqueueAction(runnable)"))
    override fun enqueueAction(runnable: Runnable) {
        _delegate.enqueueAction(runnable)
    }

    /**
     * Causes the Runnable r to be added to the action queue.
     *
     *
     * The runnable will be run after all the previous action has been run.
     *
     *
     * 前面的事务全部执行后 执行该Action
     */
    override fun post(runnable: Runnable) {
        _delegate.post(runnable)
    }

    /**
     * Called when the enter-animation end.
     * 入栈动画 结束时,回调
     */
    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        _delegate.onEnterAnimationEnd(savedInstanceState)
    }


    /**
     * Lazy initial，Called when fragment is first called.
     *
     *
     * 同级下的 懒加载 ＋ ViewPager下的懒加载  的结合回调方法
     */
    override fun onLazyInitView(savedInstanceState: Bundle?) {
        _delegate.onLazyInitView(savedInstanceState)
    }

    /**
     * Called when the fragment is visible.
     * 当Fragment对用户可见时回调
     *
     *
     * Is the combination of  [onHiddenChanged() + onResume()/onPause() + setUserVisibleHint()]
     */
    override fun onSupportVisible() {
        _delegate.onSupportVisible()
    }

    /**
     * Called when the fragment is invivible.
     *
     *
     * Is the combination of  [onHiddenChanged() + onResume()/onPause() + setUserVisibleHint()]
     */
    override fun onSupportInvisible() {
        _delegate.onSupportInvisible()
    }

    /**
     * Return true if the fragment has been supportVisible.
     */
    override fun isSupportVisible(): Boolean {
        return _delegate.isSupportVisible
    }

    /**
     * Set fragment animation with a higher priority than the ISupportActivity
     * 设定当前Fragmemt动画,优先级比在SupportActivity里高
     */
    override fun onCreateFragmentAnimator(): FragmentAnimator {
//        return _delegate.onCreateFragmentAnimator()
        return DefaultNoAnimator()
    }

    /**
     * 获取设置的全局动画 copy
     *
     * @return FragmentAnimator
     */
    override fun getFragmentAnimator(): FragmentAnimator {
        return _delegate.fragmentAnimator
    }

    /**
     * 设置Fragment内的全局动画
     */
    override fun setFragmentAnimator(fragmentAnimator: FragmentAnimator) {
        _delegate.fragmentAnimator = fragmentAnimator
    }

    /**
     * 按返回键触发,前提是SupportActivity的onBackPressed()方法能被调用
     *
     * @return false则继续向上传递, true则消费掉该事件
     */
    override fun onBackPressedSupport(): Boolean {
        if (_isCurrent) {
            return _delegate.onBackPressedSupport()
        }
        _flutterView.popRoute()
        return true
    }

    /**
     * 类似 [Activity.setResult]
     *
     *
     * Similar to [Activity.setResult]
     *
     * @see .startForResult
     */
    override fun setFragmentResult(resultCode: Int, bundle: Bundle) {
        _delegate.setFragmentResult(resultCode, bundle)
    }

    /**
     * 类似  [Activity.onActivityResult]
     *
     *
     * Similar to [Activity.onActivityResult]
     *
     * @see .startForResult
     */
    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        _delegate.onFragmentResult(requestCode, resultCode, data)
    }

    /**
     * 在start(TargetFragment,LaunchMode)时,启动模式为SingleTask/SingleTop, 回调TargetFragment的该方法
     * 类似 [Activity.onNewIntent]
     *
     *
     * Similar to [Activity.onNewIntent]
     *
     * @param args putNewBundle(Bundle newBundle)
     * @see .start
     */
    override fun onNewBundle(args: Bundle) {
        _delegate.onNewBundle(args)
    }

    /**
     * 添加NewBundle,用于启动模式为SingleTask/SingleTop时
     *
     * @see .start
     */
    override fun putNewBundle(newBundle: Bundle) {
        _delegate.putNewBundle(newBundle)
    }


    /****************************************以下为可选方法(Optional methods)******************************************************/

    /**
     * 隐藏软键盘
     */
    protected fun hideSoftInput() {
        _delegate.hideSoftInput()
    }

    /**
     * 显示软键盘,调用该方法后,会在onPause时自动隐藏软键盘
     */
    protected fun showSoftInput(view: View) {
        _delegate.showSoftInput(view)
    }

    /**
     * 加载根Fragment, 即Activity内的第一个Fragment 或 Fragment内的第一个子Fragment
     *
     * @param containerId 容器id
     * @param toFragment  目标Fragment
     */
    fun loadRootFragment(containerId: Int, toFragment: ISupportFragment) {
        _delegate.loadRootFragment(containerId, toFragment)
    }

    fun loadRootFragment(containerId: Int, toFragment: ISupportFragment, addToBackStack: Boolean, allowAnim: Boolean) {
        _delegate.loadRootFragment(containerId, toFragment, addToBackStack, allowAnim)
    }

    /**
     * 加载多个同级根Fragment,类似Wechat, QQ主页的场景
     */
    fun loadMultipleRootFragment(containerId: Int, showPosition: Int, vararg toFragments: ISupportFragment) {
        _delegate.loadMultipleRootFragment(containerId, showPosition, *toFragments)
    }

    /**
     * show一个Fragment,hide其他同栈所有Fragment
     * 使用该方法时，要确保同级栈内无多余的Fragment,(只有通过loadMultipleRootFragment()载入的Fragment)
     *
     *
     * 建议使用更明确的[.showHideFragment]
     *
     * @param showFragment 需要show的Fragment
     */
    fun showHideFragment(showFragment: ISupportFragment) {
        _delegate.showHideFragment(showFragment)
    }

    /**
     * show一个Fragment,hide一个Fragment ; 主要用于类似微信主页那种 切换tab的情况
     */
    fun showHideFragment(showFragment: ISupportFragment, hideFragment: ISupportFragment) {
        _delegate.showHideFragment(showFragment, hideFragment)
    }

    fun start(toFragment: ISupportFragment) {
        _delegate.start(toFragment)
    }

    /**
     * @param launchMode Similar to Activity's LaunchMode.
     */
    fun start(toFragment: ISupportFragment, @ISupportFragment.LaunchMode launchMode: Int) {
        _delegate.start(toFragment, launchMode)
    }

    /**
     * Launch an fragment for which you would like a result when it poped.
     */
    fun startForResult(toFragment: ISupportFragment, requestCode: Int) {
        _delegate.startForResult(toFragment, requestCode)
    }

    /**
     * Start the target Fragment and pop itself
     */
    fun startWithPop(toFragment: ISupportFragment) {
        _delegate.startWithPop(toFragment)
    }

    /**
     * @see .popTo
     * @see .start
     */
    fun startWithPopTo(toFragment: ISupportFragment, targetFragmentClass: Class<*>, includeTargetFragment: Boolean) {
        _delegate.startWithPopTo(toFragment, targetFragmentClass, includeTargetFragment)
    }


    fun replaceFragment(toFragment: ISupportFragment, addToBackStack: Boolean) {
        _delegate.replaceFragment(toFragment, addToBackStack)
    }

    fun pop() {
        _delegate.pop()
    }

    /**
     * Pop the child fragment.
     */
    fun popChild() {
        _delegate.popChild()
    }

    /**
     * Pop the last fragment transition from the manager's fragment
     * back stack.
     *
     *
     * 出栈到目标fragment
     *
     * @param targetFragmentClass   目标fragment
     * @param includeTargetFragment 是否包含该fragment
     */
    fun popTo(targetFragmentClass: Class<*>, includeTargetFragment: Boolean) {
        _delegate.popTo(targetFragmentClass, includeTargetFragment)
    }

    /**
     * If you want to begin another FragmentTransaction immediately after popTo(), use this method.
     * 如果你想在出栈后, 立刻进行FragmentTransaction操作，请使用该方法
     */
    fun popTo(targetFragmentClass: Class<*>, includeTargetFragment: Boolean, afterPopTransactionRunnable: Runnable) {
        _delegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable)
    }

    fun popTo(
        targetFragmentClass: Class<*>,
        includeTargetFragment: Boolean,
        afterPopTransactionRunnable: Runnable,
        popAnim: Int
    ) {
        _delegate.popTo(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable, popAnim)
    }

    fun popToChild(targetFragmentClass: Class<*>, includeTargetFragment: Boolean) {
        _delegate.popToChild(targetFragmentClass, includeTargetFragment)
    }

    fun popToChild(
        targetFragmentClass: Class<*>,
        includeTargetFragment: Boolean,
        afterPopTransactionRunnable: Runnable
    ) {
        _delegate.popToChild(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable)
    }

    fun popToChild(
        targetFragmentClass: Class<*>,
        includeTargetFragment: Boolean,
        afterPopTransactionRunnable: Runnable,
        popAnim: Int
    ) {
        _delegate.popToChild(targetFragmentClass, includeTargetFragment, afterPopTransactionRunnable, popAnim)
    }

    /**
     * 得到位于栈顶Fragment
     */
    fun getTopFragment(): ISupportFragment {
        return SupportHelper.getTopFragment(fragmentManager!!)
    }

    fun getSuperTopFragment(): BaseFragment<*, *> {
        return FragmentationMagician.getActiveFragments(_activity.supportFragmentManager)[0] as BaseFragment<*, *>
    }

    fun getTopChildFragment(): ISupportFragment {
        return SupportHelper.getTopFragment(childFragmentManager)
    }

    /**
     * @return 位于当前Fragment的前一个Fragment
     */
    fun getPreFragment(): ISupportFragment {
        return SupportHelper.getPreFragment(this)
    }


    /**
     * 获取栈内的fragment对象
     */
    fun <T : ISupportFragment> findFragment(fragmentClass: Class<T>): T {
        return SupportHelper.findFragment(fragmentManager!!, fragmentClass)
    }

    /**
     * 获取栈内的fragment对象
     */
    fun <T : ISupportFragment> findChildFragment(fragmentClass: Class<T>): T {
        return SupportHelper.findFragment(childFragmentManager, fragmentClass)
    }

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        return _delegate.onCreateAnimation(transit, enter, nextAnim)
    }
}