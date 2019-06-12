package com.zhuzichu.mvvm.databinding.textview;

import android.widget.TextView;
import androidx.databinding.BindingAdapter;

public class ViewAdapter {

    @BindingAdapter({"isSelected"})
    public static void isSelected(final TextView textView, final boolean isSelected) {
        textView.setSelected(isSelected);
    }
}
