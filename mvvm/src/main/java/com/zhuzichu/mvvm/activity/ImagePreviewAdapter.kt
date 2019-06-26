package com.zhuzichu.mvvm.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.zhuzichu.mvvm.R
import com.zhuzichu.mvvm.global.glide.GlideApp

/**
 * Created by Android Studio.
 * Blog: zhuzichu.com
 * User: zhuzichu
 * Date: 2019-06-26
 * Time: 18:01
 */
class ImagePreviewAdapter(
    var list: List<String>
) : RecyclerView.Adapter<ImagePreviewAdapter.ViewHolder>() {

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_image_preview, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        GlideApp.with(holder.imageView).load(list[position]).into(holder.imageView)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView = itemView.findViewById<ImageView>(R.id.image)
    }
}