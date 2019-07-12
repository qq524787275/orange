package com.zhuzichu.mvvm.widget;

import android.os.Parcel;
import android.os.Parcelable;
import com.zhuzichu.mvvm.R;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

public class FlutterFadeAnimator extends FragmentAnimator implements Parcelable {

    public FlutterFadeAnimator() {
        enter = 0;
        exit = R.anim.fade_fragment_exit;
        popEnter = R.anim.fade_fragment_pop_enter;
        popExit = R.anim.fade_fragment_pop_exit;
    }

    protected FlutterFadeAnimator(Parcel in) {
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

    public static final Creator<FlutterFadeAnimator> CREATOR = new Creator<FlutterFadeAnimator>() {
        @Override
        public FlutterFadeAnimator createFromParcel(Parcel in) {
            return new FlutterFadeAnimator(in);
        }

        @Override
        public FlutterFadeAnimator[] newArray(int size) {
            return new FlutterFadeAnimator[size];
        }
    };
}