package com.zhuzichu.orange.ui.previewimage

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Pair
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.zhuzichu.mvvm.utils.helper.QMUIStatusBarHelper
import com.zhuzichu.orange.R
import kotlinx.android.synthetic.main.activity_preview_image.*


class PreviewImageActivity : AppCompatActivity() {

    private val list by lazy { intent.getStringArrayListExtra("images")!! }
    private val current by lazy { intent.getStringExtra("current")!! }
    private var currentIndex = 0
    private val adapter by lazy { PreviewImageAdapter(list, currentIndex) }

    companion object {
        fun start(context: Context, list: ArrayList<String>, current: String, imageView: ImageView? = null) {
            val intent = Intent(context, PreviewImageActivity::class.java)
            intent.putStringArrayListExtra("images", list)
            intent.putExtra("current", current)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && imageView != null) {
                context.startActivity(
                    intent,
                    ActivityOptions.makeSceneTransitionAnimation(
                        context as Activity,
                        Pair<View, String>(imageView, imageView.transitionName)
                    ).toBundle()
                )
            } else {
                context.startActivity(intent)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QMUIStatusBarHelper.translucent(this)
        currentIndex = list.indexOf(current)
        setContentView(R.layout.activity_preview_image)
        viewpager.adapter = adapter
        viewpager.setCurrentItem(currentIndex, false)
        textView.text = (currentIndex + 1).toString().plus("/").plus(list.size)
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                currentIndex = position
                textView.text = (currentIndex + 1).toString().plus("/").plus(list.size.toString())
            }
        })
    }
}
