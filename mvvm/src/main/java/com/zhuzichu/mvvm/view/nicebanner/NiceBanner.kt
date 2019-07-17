package com.zhuzichu.mvvm.view.nicebanner

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewpager.widget.ViewPager
import com.zhuzichu.mvvm.utils.toColorById
import com.zhuzichu.mvvm.view.banner.WeakHandler
import kotlinx.android.synthetic.main.view_nice_banner.view.*
import java.util.*


/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-07-16
 * Time: 14:23
 */
class NiceBanner @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr), LifecycleObserver {
    private lateinit var sourceUrls: List<Any>
    private val targetUrls by lazy { ArrayList<Any>() }
    private val scroller by lazy { BannerScroller(banner_viewpager.context) }
    private var count: Int = 0
    private var currentItem: Int = 2
    private var contentItemMargin = resources.getDimension(com.zhuzichu.mvvm.R.dimen.dimens_4dp)
    private var contentHorizontalPadding = resources.getDimension(com.zhuzichu.mvvm.R.dimen.dimens_32dp)
    private var delayTime = 1500
    private val handler = WeakHandler()
    private var isAutoPlay = true
    private var isScroll = true
    private var onPageChangeListener: ViewPager.OnPageChangeListener? = null
    private val niceBannnerAdapter by lazy {
        NiceBannerAdapter(
            this,
            contentHorizontalPadding
        ) {
            if (it == 1) {
            }
        }
    }

    fun toRealPosition(position: Int): Int {
        var realPosition = (position - 2) % count
        if (realPosition < 0)
            realPosition += count
        return realPosition
    }

    private val paletteCacheManager by lazy {
        PaletteCacheManager()
    }
    private val argbEvaluator: ArgbEvaluator by lazy {
        ArgbEvaluator()
    }
    private var nicePagerAdapter: INicePagerAdapter? = null

    init {
        View.inflate(context, com.zhuzichu.mvvm.R.layout.view_nice_banner, this)
        val attributes = context.obtainStyledAttributes(attrs, com.zhuzichu.mvvm.R.styleable.NiceBanner)
        try {
            contentHorizontalPadding =
                attributes.getDimension(
                    com.zhuzichu.mvvm.R.styleable.NiceBanner_contentHorizontalPadding,
                    contentHorizontalPadding
                )
            contentItemMargin =
                attributes.getDimension(com.zhuzichu.mvvm.R.styleable.NiceBanner_contentItemMargin, contentItemMargin)

            setUpContent()
        } finally {
            attributes.recycle()
        }
        (banner_root.background as GradientDrawable).colors =
            intArrayOf(
                android.R.color.transparent.toColorById(),
                android.R.color.transparent.toColorById()
            )
        initViewPagerScroll()
    }

    private fun initViewPagerScroll() {
        try {
            val mField = ViewPager::class.java.getDeclaredField("mScroller")
            mField.isAccessible = true
            mField.set(banner_viewpager, scroller)
        } catch (e: Exception) {
        }

    }


    private fun setUpContent() {
        banner_viewpager.setPageTransformer(
            true,
            DefaultTransformer(contentHorizontalPadding)
        )
        banner_viewpager.pageMargin = contentItemMargin.toInt()
        banner_viewpager.addOnPageChangeListener(OnContentPageChangeListener())
    }

    private inner class OnContentPageChangeListener : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
            onPageChangeListener?.onPageScrollStateChanged(state)
            when (state) {
                0//No operation
                -> if (currentItem == 1) {
                    banner_viewpager.setCurrentItem(count + 1, false)
                } else if (currentItem == count + 2) {
                    banner_viewpager.setCurrentItem(2, false)
                }
                1//start Sliding
                -> if (currentItem == count + 2) {
                    banner_viewpager.setCurrentItem(2, false)
                } else if (currentItem == 1) {
                    banner_viewpager.setCurrentItem(count + 1, false)
                }
                2//end Sliding
                -> {
                }
            }
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            refreshBackgroundColor(position, positionOffset)
            onPageChangeListener?.onPageScrolled(toRealPosition(position), positionOffset, positionOffsetPixels)
        }

        override fun onPageSelected(position: Int) {
            currentItem = position
            onPageChangeListener?.onPageSelected(toRealPosition(position))
            paletteCacheManager.cachePalettesAroundPositionAsync(position)
        }
    }

    private fun refreshBackgroundColor(position: Int, positionOffset: Float) {
        if (nicePagerAdapter?.isUpdatingBackgroundColor() == true) {
            // Retrieve palettes from cache
            val startPalette = paletteCacheManager.getPaletteForPosition(position)
            val endPalette = paletteCacheManager.getPaletteForPosition(position + 1)

            var startTopColor = Color.TRANSPARENT
            var startBottomColor = Color.TRANSPARENT
            var endTopColor = Color.TRANSPARENT
            var endBottomColor = Color.TRANSPARENT

            if (startPalette != null) {
                startTopColor = startPalette.getVibrantColor(
                    startPalette.getLightVibrantColor(
                        startPalette.getMutedColor(Color.TRANSPARENT)
                    )
                )

                startBottomColor = startPalette.getDominantColor(
                    startPalette.getDarkVibrantColor(
                        startPalette.getDarkMutedColor(
                            Color.TRANSPARENT
                        )
                    )
                )
            }

            if (endPalette != null) {
                endTopColor = endPalette.getVibrantColor(
                    endPalette.getLightVibrantColor(
                        endPalette.getMutedColor(Color.TRANSPARENT)
                    )
                )

                endBottomColor = endPalette.getDominantColor(
                    endPalette.getDarkVibrantColor(
                        endPalette.getDarkMutedColor(
                            Color.TRANSPARENT
                        )
                    )
                )
            }

            val topColor = argbEvaluator.evaluate(positionOffset, startTopColor, endTopColor) as Int
            val bottomColor = argbEvaluator.evaluate(
                positionOffset, startBottomColor,
                endBottomColor
            ) as Int

            (banner_root.background as GradientDrawable).colors = intArrayOf(topColor, bottomColor)
        }
    }

    fun setNiceViewPagerAdapter(nicePagerAdapter: INicePagerAdapter) {
        post {
            this.nicePagerAdapter = nicePagerAdapter
            // Setup adapter for palette manager
            paletteCacheManager.setCreativeViewAdapter(nicePagerAdapter)
            paletteCacheManager.cachePalettesAroundPositionAsync(0) {
                refreshBackgroundColor(0, 0f)
            }
            // Setup content adapter
            niceBannnerAdapter.nicePagerAdapter = nicePagerAdapter
            banner_viewpager.adapter = niceBannnerAdapter
        }
    }

    fun setImages(imageUrls: List<Any>): NiceBanner {
        this.sourceUrls = imageUrls
        this.count = imageUrls.size
        return this@NiceBanner
    }

    fun start(lifecycle: Lifecycle): NiceBanner {
        setImageList(sourceUrls)
        setData()
        lifecycle.addObserver(this)
        startAutoPlay()
        return this@NiceBanner
    }

    fun setViewPagerIsScroll(isScroll: Boolean): NiceBanner {
        this.isScroll = isScroll
        return this@NiceBanner
    }

    private fun setData() {
        post {
            currentItem = 2
            if (nicePagerAdapter == null) {
                nicePagerAdapter = NicePagerAdapter(context, targetUrls)
            }
            // Setup adapter for palette manager
            paletteCacheManager.setCreativeViewAdapter(nicePagerAdapter)
            paletteCacheManager.cachePalettesAroundPositionAsync(currentItem) {
                refreshBackgroundColor(currentItem, 0f)
            }
            // Setup content adapter
            niceBannnerAdapter.nicePagerAdapter = nicePagerAdapter as INicePagerAdapter
            banner_viewpager.adapter = niceBannnerAdapter
            banner_viewpager.isFocusable = true
            banner_viewpager.currentItem = currentItem
            if (isScroll && count > 1) {
                banner_viewpager.setScrollable(true)
            } else {
                banner_viewpager.setScrollable(false)
            }
            if (isAutoPlay)
                startAutoPlay()
        }
    }

    private fun setImageList(imagesUrl: List<Any>) {
        if (imagesUrl.isEmpty()) {
            return
        }
        targetUrls.clear()
        for (i in 0..count + 3) {
            var url: Any?
            url = when (i) {
                0 -> imagesUrl[count - 2]
                1 -> imagesUrl[count - 1]
                count + 2 -> imagesUrl[0]
                count + 3 -> imagesUrl[1]
                else -> imagesUrl[i - 2]
            }
            targetUrls.add(url)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (isAutoPlay) {
            val action = ev.action
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL
                || action == MotionEvent.ACTION_OUTSIDE
            ) {
                startAutoPlay()
            } else if (action == MotionEvent.ACTION_DOWN) {
                stopAutoPlay()
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    fun setOnPageChangeListener(onPageChangeListener: ViewPager.OnPageChangeListener) {
        this@NiceBanner.onPageChangeListener = onPageChangeListener
    }


    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        handler.removeCallbacksAndMessages(null)
    }

    fun startAutoPlay() {
        handler.removeCallbacks(task)
        handlerPostTaskDelayed()
    }

    fun stopAutoPlay() {
        handler.removeCallbacks(task)
    }

    fun handlerPostTask() {
        handler.post(task)
    }

    fun handlerPostTaskDelayed() {
        handler.postDelayed(task, delayTime.toLong())
    }


    private val task = Runnable {
        if (count > 1 && isAutoPlay) {
            currentItem = currentItem % (count + 3) + 1
            if (currentItem == 2) {
                banner_viewpager.setCurrentItem(currentItem, false)
                handlerPostTask()
            } else {
                banner_viewpager.currentItem = currentItem
                handlerPostTaskDelayed()
            }
        }
    }

    /**
     * 切换到下一页
     */
    private fun switchToNextPage() {
        if (banner_viewpager != null) {
            banner_viewpager.currentItem = banner_viewpager.currentItem + 1
        }
    }

}