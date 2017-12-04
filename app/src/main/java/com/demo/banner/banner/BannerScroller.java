package com.demo.banner.banner;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * description: 滚动
 * created by kalu on 2016/10/28 18:11
 */
class BannerScroller extends Scroller {

    private int mDuration = 1000;

    public BannerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerScroller(Context context, Interpolator interpolator, int duration) {
        this(context, interpolator);
        mDuration = duration;
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mDuration);
    }
}