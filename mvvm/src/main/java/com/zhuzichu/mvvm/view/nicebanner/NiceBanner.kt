package com.zhuzichu.mvvm.view.nicebanner

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.viewpager.widget.ViewPager
import com.zhuzichu.mvvm.R
import kotlinx.android.synthetic.main.view_nice_banner.view.*

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
    FrameLayout(context, attrs, defStyleAttr) {

    private var contentItemMargin = resources.getDimension(R.dimen.dimens_4dp)
    private var contentHorizontalPadding = resources.getDimension(R.dimen.dimens_32dp)
    private val niceBannnerAdapter by lazy {
        NiceBannerAdapter(
            this, contentItemMargin,
            contentHorizontalPadding
        )
    }
    private val paletteCacheManager by lazy {
        PaletteCacheManager()
    }
    private val argbEvaluator: ArgbEvaluator by lazy {
        ArgbEvaluator()
    }
    private var nicePagerAdapter: INicePagerAdapter? = null

    init {
        View.inflate(context, R.layout.view_nice_banner, this)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.NiceBanner)
        try {
            contentHorizontalPadding =
                attributes.getDimension(R.styleable.NiceBanner_contentHorizontalPadding, contentHorizontalPadding)
            contentItemMargin =
                attributes.getDimension(R.styleable.NiceBanner_contentItemMargin, contentItemMargin)

            setUpContent()
        } finally {
            attributes.recycle()
        }
    }

    private fun setUpContent() {
        banner_viewpager.setPageTransformer(
            false,
            NiceContentPageTransformer(contentHorizontalPadding)
        )
        banner_viewpager.pageMargin = contentItemMargin.toInt()
        banner_viewpager.addOnPageChangeListener(OnContentPageChangeListener())
    }

    private inner class OnContentPageChangeListener : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) {
        }

        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            refreshBackgroundColor(position, positionOffset)
        }

        override fun onPageSelected(position: Int) {
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
}