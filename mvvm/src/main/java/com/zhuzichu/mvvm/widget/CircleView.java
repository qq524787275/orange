package com.zhuzichu.mvvm.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.FrameLayout;
import android.widget.Toast;
import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.FloatRange;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

public class CircleView extends FrameLayout {
    private final int borderWidthSmall;
    private final int borderWidthLarge;
    private final Paint outerPaint;
    private final Paint whitePaint;
    private final Paint innerPaint;
    private boolean selected;

    public CircleView(Context context) {
        this(context, (AttributeSet) null, 0);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Resources r = this.getResources();
        this.borderWidthSmall = (int) TypedValue.applyDimension(1, 3.0F, r.getDisplayMetrics());
        this.borderWidthLarge = (int) TypedValue.applyDimension(1, 5.0F, r.getDisplayMetrics());
        this.whitePaint = new Paint();
        this.whitePaint.setAntiAlias(true);
        this.whitePaint.setColor(-1);
        this.innerPaint = new Paint();
        this.innerPaint.setAntiAlias(true);
        this.outerPaint = new Paint();
        this.outerPaint.setAntiAlias(true);
        this.update(-12303292);
        this.setWillNotDraw(false);
    }

    @ColorInt
    private static int translucentColor(int color) {
        float factor = 0.7F;
        int alpha = Math.round((float) Color.alpha(color) * 0.7F);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    @ColorInt
    public static int shiftColor(@ColorInt int color, @FloatRange(from = 0.0D, to = 2.0D) float by) {
        if (by == 1.0F) {
            return color;
        } else {
            float[] hsv = new float[3];
            Color.colorToHSV(color, hsv);
            hsv[2] *= by;
            return Color.HSVToColor(hsv);
        }
    }

    @ColorInt
    public static int shiftColorDown(@ColorInt int color) {
        return shiftColor(color, 0.9F);
    }

    @ColorInt
    public static int shiftColorUp(@ColorInt int color) {
        return shiftColor(color, 1.1F);
    }

    private void update(@ColorInt int color) {
        this.innerPaint.setColor(color);
        this.outerPaint.setColor(shiftColorDown(color));
        Drawable selector = this.createSelector(color);
        if (Build.VERSION.SDK_INT >= 21) {
            int[][] states = new int[][]{{16842919}};
            int[] colors = new int[]{shiftColorUp(color)};
            ColorStateList rippleColors = new ColorStateList(states, colors);
            this.setForeground(new RippleDrawable(rippleColors, selector, (Drawable) null));
        } else {
            this.setForeground(selector);
        }

    }

    public void setBackgroundColor(@ColorInt int color) {
        this.update(color);
        this.requestLayout();
        this.invalidate();
    }

    public void setBackgroundResource(@ColorRes int color) {
        this.setBackgroundColor(ContextCompat.getColor(this.getContext(), color));
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void setBackground(Drawable background) {
        throw new IllegalStateException("Cannot use setBackground() on CircleView.");
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void setBackgroundDrawable(Drawable background) {
        throw new IllegalStateException("Cannot use setBackgroundDrawable() on CircleView.");
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void setActivated(boolean activated) {
        throw new IllegalStateException("Cannot use setActivated() on CircleView.");
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        this.requestLayout();
        this.invalidate();
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        this.setMeasuredDimension(this.getMeasuredWidth(), this.getMeasuredWidth());
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int outerRadius = this.getMeasuredWidth() / 2;
        if (this.selected) {
            int whiteRadius = outerRadius - this.borderWidthLarge;
            int innerRadius = whiteRadius - this.borderWidthSmall;
            canvas.drawCircle((float) (this.getMeasuredWidth() / 2), (float) (this.getMeasuredHeight() / 2), (float) outerRadius, this.outerPaint);
            canvas.drawCircle((float) (this.getMeasuredWidth() / 2), (float) (this.getMeasuredHeight() / 2), (float) whiteRadius, this.whitePaint);
            canvas.drawCircle((float) (this.getMeasuredWidth() / 2), (float) (this.getMeasuredHeight() / 2), (float) innerRadius, this.innerPaint);
        } else {
            canvas.drawCircle((float) (this.getMeasuredWidth() / 2), (float) (this.getMeasuredHeight() / 2), (float) outerRadius, this.innerPaint);
        }

    }

    private Drawable createSelector(int color) {
        ShapeDrawable darkerCircle = new ShapeDrawable(new OvalShape());
        darkerCircle.getPaint().setColor(translucentColor(shiftColorUp(color)));
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842919}, darkerCircle);
        return stateListDrawable;
    }

    public void showHint(int color) {
        int[] screenPos = new int[2];
        Rect displayFrame = new Rect();
        this.getLocationOnScreen(screenPos);
        this.getWindowVisibleDisplayFrame(displayFrame);
        Context context = this.getContext();
        int width = this.getWidth();
        int height = this.getHeight();
        int midy = screenPos[1] + height / 2;
        int referenceX = screenPos[0] + width / 2;
        if (ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_LTR) {
            int screenWidth = context.getResources().getDisplayMetrics().widthPixels;
            referenceX = screenWidth - referenceX;
        }

        Toast cheatSheet = Toast.makeText(context, String.format("#%06X", 16777215 & color), Toast.LENGTH_SHORT);
        if (midy < displayFrame.height()) {
            cheatSheet.setGravity(8388661, referenceX, screenPos[1] + height - displayFrame.top);
        } else {
            cheatSheet.setGravity(81, 0, height);
        }

        cheatSheet.show();
    }
}
