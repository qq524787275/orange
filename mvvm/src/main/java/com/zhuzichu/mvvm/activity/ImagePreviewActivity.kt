package com.zhuzichu.mvvm.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.zhuzichu.mvvm.R
import com.zhuzichu.mvvm.utils.helper.QMUIStatusBarHelper
import kotlinx.android.synthetic.main.activity_image_preview.*

class ImagePreviewActivity : AppCompatActivity() {

    private val list by lazy { intent.getStringArrayListExtra("images") }
    private val current by lazy { intent.getStringExtra("current") }
    private val adapter by lazy { ImagePreviewAdapter(list) }
    private val layoutManager by lazy { LinearLayoutManager(this) }
    private val pagerSnapHelper: PagerSnapHelper by lazy { PagerSnapHelper() }

    companion object {
        fun start(context: Context, list: ArrayList<String>, current: String) {
            val intent = Intent(context, ImagePreviewActivity::class.java)
            intent.putStringArrayListExtra("images", list)
            intent.putExtra("current", current)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QMUIStatusBarHelper.translucent(this)
        setContentView(R.layout.activity_image_preview)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recycler.layoutManager = layoutManager
        recycler.adapter = adapter
        layoutManager.scrollToPositionWithOffset(list.indexOf(current), 0)
        pagerSnapHelper.attachToRecyclerView(recycler)
    }
}
