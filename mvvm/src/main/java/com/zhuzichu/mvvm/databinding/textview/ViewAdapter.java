package com.zhuzichu.mvvm.databinding.textview;

import android.widget.TextView;
import androidx.core.text.HtmlCompat;
import androidx.databinding.BindingAdapter;

public class ViewAdapter {

    @BindingAdapter({"strikethrough"})
    public static void strikethrough(final TextView textView, String text) {
        textView.setText(HtmlCompat.fromHtml("<s>" + text + "</s>", HtmlCompat.FROM_HTML_MODE_COMPACT));
    }

    @BindingAdapter({"fromHtml"})
    public static void fromHtml(final TextView textView, String text) {
        textView.setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_COMPACT));
    }
}
