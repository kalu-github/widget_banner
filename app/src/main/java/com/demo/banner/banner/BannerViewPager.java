package com.demo.banner.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * description: 水平+垂直
 * created by kalu on 2017/12/4 22:22
 */
class BannerViewPager extends ViewPager {

    private boolean isPagerVertical;

    public BannerViewPager(Context context, boolean isPagerVertical) {
        super(context);
        this.isPagerVertical = isPagerVertical;
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        if (!isPagerVertical) return super.onInterceptTouchEvent(ev);

        float width = getWidth();
        float height = getHeight();
        float swappedX = (ev.getY() / height) * width;
        float swappedY = (ev.getX() / width) * height;
        ev.setLocation(swappedX, swappedY);
        boolean intercept = super.onInterceptTouchEvent(ev);

        float width1 = getWidth();
        float height1 = getHeight();
        float swappedX1 = (ev.getY() / height1) * width1;
        float swappedY1 = (ev.getX() / width1) * height1;
        ev.setLocation(swappedX1, swappedY1);

        return intercept;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (!isPagerVertical) return super.onTouchEvent(ev);

        float width = getWidth();
        float height = getHeight();
        float swappedX = (ev.getY() / height) * width;
        float swappedY = (ev.getX() / width) * height;
        ev.setLocation(swappedX, swappedY);

        return super.onTouchEvent(ev);
    }
}
