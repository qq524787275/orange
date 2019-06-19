package com.zhuzichu.mvvm.widget;

import android.os.Parcel;
import android.os.Parcelable;
import com.zhuzichu.mvvm.R;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class FadeAnimator extends FragmentAnimator implements Parcelable {

    public FadeAnimator() {
        enter = R.anim.fade_fragment_enter;
        exit = R.anim.fade_fragment_exit;
        popEnter = R.anim.fade_fragment_pop_enter;
        popExit = R.anim.fade_fragment_pop_exit;
    }

    protected FadeAnimator(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FadeAnimator> CREATOR = new Creator<FadeAnimator>() {
        @Override
        public FadeAnimator createFromParcel(Parcel in) {
            return new FadeAnimator(in);
        }

        @Override
        public FadeAnimator[] newArray(int size) {
            return new FadeAnimator[size];
        }
    };
}