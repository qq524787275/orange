package com.zhuzichu.mvvm.widget;

import android.graphics.Bitmap;
import android.graphics.Color;
import androidx.annotation.NonNull;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

public class WhiteToAlphaTransformation extends BitmapTransformation {

    private static final String ID =
            "com.zhuzichu.mvvm.widget.WriteToAlphaTransformation";

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        return drawWriteToAlpha(toTransform);
    }

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update((ID).getBytes(CHARSET));
    }

    public static Bitmap drawWriteToAlpha(Bitmap mBitmap) {
        Bitmap createBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        int mWidth = mBitmap.getWidth();
        int mHeight = mBitmap.getHeight();
        for (int i = 0; i < mHeight; i++) {
            for (int j = 0; j < mWidth; j++) {
                int color = mBitmap.getPixel(j, i);
                int g = Color.green(color);
                int r = Color.red(color);
                int b = Color.blue(color);
                int a = Color.alpha(color);
                if (g >= 250 && r >= 250 && b >= 250) {
                    a = 0;
                }
                color = Color.argb(a, r, g, b);
                createBitmap.setPixel(j, i, color);
            }
        }
        return createBitmap;
    }

    @Override
    public String toString() {
        return "WriteToAlphaTransformation()";
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof WhiteToAlphaTransformation;
    }

    @Override
    public int hashCode() {
        return ID.hashCode();
    }
}
