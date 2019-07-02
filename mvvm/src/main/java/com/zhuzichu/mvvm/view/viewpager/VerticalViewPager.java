package com.zhuzichu.mvvm.view.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class VerticalViewPager extends ViewPager {

    public VerticalViewPager(@NonNull Context context) {
        this(context, null);
    }

    public VerticalViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(true, new VerticalViewPager.DefaultTransformer());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercept = super.onInterceptTouchEvent(swapEvent(ev));
        swapEvent(ev);
        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(swapEvent(ev));
    }

    private MotionEvent swapEvent(MotionEvent ev) {
        float width = getWidth();
        float height = getHeight();
        float swappedX = (ev.getY() / height) * width;

        float swappedY = (ev.getX() / width) * height;
        ev.setLocation(swappedX, swappedY);

        return ev;
    }

    private class DefaultTransformer implements PageTransformer {
        @Override
        public void transformPage(@NonNull View page, float position) {
            float alpha = 0;
            if (0 <= position && position <= 1) {
                alpha = 1 - position;
            } else if (-1 < position && position < 0) {
                alpha = position + 1;
            }
            page.setAlpha(alpha);
            float transX = page.getWidth() * -position;
            page.setTranslationX(transX);
            float transY = position * page.getHeight();
            page.setTranslationY(transY);
        }
    }
}
