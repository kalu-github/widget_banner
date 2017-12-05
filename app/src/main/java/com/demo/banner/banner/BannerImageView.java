package com.demo.banner.banner;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * description: 处理其长按和点击事件
 * created by kalu on 2016/12/3 18:17
 */
class BannerImageView extends AppCompatImageView {

    private static Context mContext;

    public BannerImageView(Context context) {
        this(context, null, 0);
    }

    public BannerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLongClickable(false);
        mContext = getContext().getApplicationContext();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (null != mContext) {
            mSimpleOnGestureListener.onTouchEvent(event);
        }
        return true;
    }

    /**********************************************************************************************/

    private static final GestureDetector mSimpleOnGestureListener = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {

        @Override
        public void onLongPress(MotionEvent e) {
            //  Log.e("kalu", "onLongPress");
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {

            if (null != onSingleTapConfirmedListener) {
                onSingleTapConfirmedListener.onSingleTapConfirmed();
            }
            return false;
        }
    });

    private static OnSingleTapConfirmedListener onSingleTapConfirmedListener;

    public interface OnSingleTapConfirmedListener {
        void onSingleTapConfirmed();
    }

    public void setOnSingleTapConfirmedListener(OnSingleTapConfirmedListener onSingleTapConfirmedListener) {
        this.onSingleTapConfirmedListener = onSingleTapConfirmedListener;
    }
}