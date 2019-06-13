package com.zhuzichu.mvvm.databinding.textview;

import android.text.Html;
import android.widget.TextView;
import androidx.databinding.BindingAdapter;

public class ViewAdapter {

    @BindingAdapter({"strikethrough"})
    public static void strikethrough(final TextView textView, String text) {
        textView.setText(Html.fromHtml("<s>" + text + "</s>"));
    }
}
