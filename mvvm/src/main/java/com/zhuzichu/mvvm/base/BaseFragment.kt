package com.zhuzichu.mvvm.base

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavOptions
import com.trello.rxlifecycle3.components.support.RxFragment
import com.zhuzichu.mvvm.R
import com.zhuzichu.mvvm.view.layout.MultiStateView
import com.zhuzichu.mvvm.view.loading.DialogMaker
import java.lang.reflect.ParameterizedType


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-05-27
 * Time: 15:15
 */
abstract class BaseFragment<V : ViewDataBinding, VM : BaseViewModel> : RxFragment(), IBaseFragment {
    lateinit var mBind: V
    lateinit var mViewModel: VM
    lateinit var contentView: View
    lateinit var multiStateView: MultiStateView
    private lateinit var mHandler: Handler

    private var isPrepared: Boolean = false
    private var isFirstResume = true
    private var isFirstVisible = true
    private var isFirstInvisible = true

    abstract fun setLayoutId(): Int
    abstract fun bindVariableId(): Int


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mHandler = Handler(Looper.getMainLooper())
        val type = this.javaClass.genericSuperclass
        if (type is ParameterizedType) mViewModel =
            ViewModelProviders.of(this).get(type.actualTypeArguments[1] as Class<VM>)
        lifecycle.addObserver(mViewModel)
        mViewModel.injectLifecycleProvider(this)

        if (::contentView.isInitialized) {
            val parent = contentView.parent
            if (parent != null) {
                (parent as ViewGroup).removeView(contentView)
            }
        } else {
            contentView = inflater.inflate(setLayoutId(), container, false)
        }

        mBind = DataBindingUtil.bind(contentView)!!
        mBind.setVariable(bindVariableId(), mViewModel)

        mHandler.postDelayed(
            { onEnterAnimationEnd(savedInstanceState) },
            resources.getInteger(com.zhuzichu.mvvm.R.integer.fragment_anim_duration).toLong()
        )

        multiStateView = inflater.inflate(R.layout.layout_multi_state, null) as MultiStateView
        multiStateView.addView(mBind.root)
        return multiStateView.also {
            mBind.lifecycleOwner = this
            mBind.executePendingBindings()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initPrepare()
    }

    override fun onResume() {
        super.onResume()
        if (isFirstResume) {
            isFirstResume = false
            return
        }
        if (userVisibleHint) {
            onUserVisible()
        }
    }

    override fun onPause() {
        super.onPause()
        if (userVisibleHint) {
            onUserInvisible()
        }
    }

    @Synchronized
    private fun initPrepare() {
        if (isPrepared) {
            onFirstUserVisible()
        } else {
            isPrepared = true
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            if (isFirstVisible) {
                isFirstVisible = false
                initPrepare()
            } else {
                onUserVisible()
            }
        } else {
            if (isFirstInvisible) {
                isFirstInvisible = false
                onFirstUserInvisible()
            } else {
                onUserInvisible()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registorUIChangeLiveDataCallBack()
        initVariable()
        initView()
        initViewObservable()
    }

    //注册ViewModel与View的契约UI回调事件
    private fun registorUIChangeLiveDataCallBack() {
        //跳入新Fragment页面
        mViewModel.getUC().getStartFragmentEvent().observe(this, Observer { params ->
            run {
                val id = params[BaseViewModel.ParameterField.ID] as Int
                val options = NavOptions.Builder()
                    .setEnterAnim(com.zhuzichu.mvvm.R.anim.slide_in_right)
                    .setExitAnim(com.zhuzichu.mvvm.R.anim.slide_out_left)
                    .setPopEnterAnim(com.zhuzichu.mvvm.R.anim.slide_in_left)
                    .setPopExitAnim(com.zhuzichu.mvvm.R.anim.slide_out_right)
                    .build()
                getBaseActivity().mNavController.navigate(id, null, options)
            }
        })
        //跳转到新Activity页面
        mViewModel.getUC().getStartActivityEvent().observe(this, Observer { params ->
            run {
                val clz = params[BaseViewModel.ParameterField.CLASS] as Class<*>
                val bundle = params[BaseViewModel.ParameterField.BUNDLE] as Bundle?
                val options = params[BaseViewModel.ParameterField.OPTIONS] as Bundle?
                val isPop = params[BaseViewModel.ParameterField.POP] as Boolean?
                val intent = Intent(activity, clz)
                bundle?.let {
                    intent.putExtras(it)
                }
                if (options != null) {
                    startActivity(intent, options)
                } else {
                    startActivity(intent)
                }
                if (isPop == true) {
                    activity?.finish()
                }
            }
        })
        //直接退出Activity页面
        mViewModel.getUC().getFinishEvent().observe(this, Observer { getBaseActivity().finish() })
        //有Fragment 退出fragment
        mViewModel.getUC().getOnBackPressedEvent().observe(this, Observer { getBaseActivity().onBackPressed() })

        mViewModel.getUC().getShowLoadingDialogEvent()
            .observe(this, Observer { DialogMaker.showLoadingDialog(getBaseActivity()) })
        mViewModel.getUC().getHideLoadingDialogEvent().observe(this, Observer { DialogMaker.dismissLodingDialog() })

        mViewModel.getUC().getMultiStateEvent().observe(this, Observer { params ->
            run {
                multiStateView.viewState = params
            }
        })

    }


    fun getHandler(): Handler? {
        return mHandler
    }

    fun getBaseActivity(): BaseActivity {
        return activity as BaseActivity
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mViewModel.isInitialized)
            lifecycle.removeObserver(mViewModel)
        if (::mBind.isInitialized)
            mBind.unbind()
    }

    /**
     * 转场动画结束回调
     */
    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        mViewModel.onEnterAnimationEnd()
    }


    override fun onUserInvisible() {
        mViewModel.onUserInvisible()
    }

    override fun onUserVisible() {
        mViewModel.onUserVisible()
    }

    override fun onFirstUserVisible() {
        mViewModel.onFirstUserVisible()
    }
}