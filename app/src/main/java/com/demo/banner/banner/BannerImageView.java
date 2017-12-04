package com.demo.banner.banner;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * description: 处理其长按和点击事件
 * created by kalu on 2016/12/3 18:17
 */
class BannerImageView extends AppCompatImageView {
    //是否释放了
    private boolean isReleased;
    //计数器，防止多次点击导致最后一次形成longpress的时间变短
    private int mCounter;
    //长按的runnable
    private Runnable mLongPressRunnable;
    //定义相应时间 以此为依据判断是长按还是短按 默认为300毫秒
    private int minTime = 300;

    public int getMinTime() {
        return minTime;
    }

    public void setMinTime(int minTime) {
        this.minTime = minTime;
    }

    public OnTakePhotoClickListener getShortClickListener() {
        return shortClickListener;
    }

    public void setShortClickListener(OnTakePhotoClickListener shortClickListener) {
        this.shortClickListener = shortClickListener;
    }

    private OnTakePhotoClickListener shortClickListener;


    private OnLongTakePhotoClickListener longClickListener;

    public OnLongTakePhotoClickListener getLongClickListener() {
        return longClickListener;
    }

    public void setLongClickListener(OnLongTakePhotoClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public BannerImageView(Context context) {
        this(context, null, 0);
    }

    public BannerImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLongPressRunnable = new Runnable() {
            @Override
            public void run() {
                mCounter--;
                //计数器大于0，说明当前执行的Runnable不是最后一次down产生的 判断为短按。
                if (mCounter > 0 || isReleased) {
                    if (shortClickListener != null) {
                        shortClickListener.onClick();
                    }
                    return;
                }
                //否则判断为长按操作
                if (longClickListener != null) {
                    longClickListener.onLongClick();
                }
            }
        };
    }

    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mCounter++;
                isReleased = false;
                postDelayed(mLongPressRunnable, minTime);
                break;

            case MotionEvent.ACTION_UP:
                //释放了
                isReleased = true;
                break;
        }
        return true;
    }

    /**
     * 为长按拍照设置一个监听事件
     */
    public interface OnLongTakePhotoClickListener {
        void onLongClick();
    }

    /**
     * 为短按拍照设置一个监听事件
     */
    public interface OnTakePhotoClickListener {
        void onClick();
    }
}