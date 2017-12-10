package lib.kalu.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * description: 自定义轮播图
 * created by kalu on 2016/10/28 18:11
 */
public class BannerLayout extends RelativeLayout implements Handler.Callback, ViewPager.OnPageChangeListener, ViewTreeObserver.OnScrollChangedListener, View.OnLongClickListener {

    private final int LOOP_NEXT = -1;
    private final Context mContext = getContext().getApplicationContext();
    private final ArrayMap<String, ImageView> mImageList = new ArrayMap<>(); // 图片集合
    private final ArrayList<ImageView> mindicatorList = new ArrayList<>();
    private final Handler mHandler = new Handler(this);
    private final BannerAdapter mBannerAdapter = new BannerAdapter(mImageList);
    private final LinearLayout mLinearLayout = new LinearLayout(mContext); // 指示器容器
    private final int[] LOCAL_WINDOW = new int[2];

    private final GradientDrawable normal = new GradientDrawable();
    private final GradientDrawable select = new GradientDrawable();

    private BannerViewPager mViewPager;

    // 默认指示器宽高, 选中状态指示器宽高
    private int normalColor, selectColor, selectHeight, selectWidth, normalHeight, normalWidth, indicatorHeight;
    // 默认边缘缓慢滚动时长, 默认滚动间隔时长
    private int scrollerTime, loopTime;

    private int indicatorSpace, indicatorMargin, indicatorBackground;
    // 指示器形状
    private boolean indicatorRectangle;

    private int realImageSize = 0;
    private boolean isTouch = false;
    private boolean isPagerVertical = false;

    /**********************************************************************************************/

    public BannerLayout(Context context) {
        this(context, null, 0);
    }

    public BannerLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BannerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 获取自定义属性
        TypedArray array = null;

        try {
            array = getContext().obtainStyledAttributes(attrs, R.styleable.BannerLayout, defStyleAttr, 0);

            isPagerVertical = array.getBoolean(R.styleable.BannerLayout_bl_pager_vertical, false);
            indicatorRectangle = array.getBoolean(R.styleable.BannerLayout_bl_indicator_rectangle, false);
            loopTime = array.getInt(R.styleable.BannerLayout_bl_time_loop, 3000);
            scrollerTime = array.getInt(R.styleable.BannerLayout_bl_time_scroller, 1000);
            selectColor = array.getColor(R.styleable.BannerLayout_bl_indicator_select_color, 0xffffffff);
            normalColor = array.getColor(R.styleable.BannerLayout_bl_indicator_normal_color, 0x66666666);
            indicatorHeight = array.getDimensionPixelSize(R.styleable.BannerLayout_bl_indicator_parent_height, 50);
            normalHeight = array.getDimensionPixelSize(R.styleable.BannerLayout_bl_indicator_normal_height, 4);
            normalWidth = array.getDimensionPixelSize(R.styleable.BannerLayout_bl_indicator_normal_width, 4);
            selectWidth = array.getDimensionPixelSize(R.styleable.BannerLayout_bl_indicator_select_width, 6);
            selectHeight = array.getDimensionPixelSize(R.styleable.BannerLayout_bl_indicator_select_height, 6);
            indicatorSpace = array.getDimensionPixelSize(R.styleable.BannerLayout_bl_indicator_child_space, 10);
            indicatorMargin = array.getDimensionPixelSize(R.styleable.BannerLayout_bl_indicator_margin, 0);
            indicatorBackground = array.getColor(R.styleable.BannerLayout_bl_indicator_background_color, Color.TRANSPARENT);
        } catch (Exception e) {
            Log.e("", e.getMessage(), e);
        } finally {
            normal.setShape(indicatorRectangle ? GradientDrawable.RECTANGLE : GradientDrawable.OVAL);
            normal.setColor(normalColor);
            normal.setSize(normalWidth, normalHeight);
            select.setShape(indicatorRectangle ? GradientDrawable.RECTANGLE : GradientDrawable.OVAL);
            select.setColor(selectColor);
            select.setSize(selectWidth, selectHeight);

            if (null == array) return;
            array.recycle();
        }
    }

    /**********************************************************************************************/

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case LOOP_NEXT:
                if (mViewPager == null) break;
                mHandler.sendEmptyMessageDelayed(LOOP_NEXT, loopTime);
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                break;
        }
        return false;
    }

    /*********************************************************************************************/

    @Override
    protected void onDetachedFromWindow() {
        setImageLoop(false);
        super.onDetachedFromWindow();
    }

    @Override
    public void onFinishTemporaryDetach() {
        setImageLoop(false);
        super.onFinishTemporaryDetach();
    }

    @Override
    protected void onAttachedToWindow() {
        setImageLoop(true);
        super.onAttachedToWindow();
    }

    @Override
    public void onStartTemporaryDetach() {
        setImageLoop(true);
        super.onStartTemporaryDetach();
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        if (visibility == View.VISIBLE) {
            setImageLoop(true);
            getViewTreeObserver().addOnScrollChangedListener(this);
        } else {
            setImageLoop(false);
            getViewTreeObserver().removeOnScrollChangedListener(this);
        }
        super.onVisibilityChanged(changedView, visibility);
    }

    @Override
    public void onScrollChanged() {

        if (isTouch) return;

        getLocationInWindow(LOCAL_WINDOW);
        // 上划
        if (LOCAL_WINDOW[1] < 0) {
            setImageLoop(Math.abs(LOCAL_WINDOW[1]) <= getHeight());
        } else {
            setImageLoop(Math.abs(LOCAL_WINDOW[1]) >= 0);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_POINTER_DOWN:
            case MotionEvent.ACTION_DOWN:
                isTouch = true;
                setImageLoop(false);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_POINTER_UP:
            case MotionEvent.ACTION_UP:
                isTouch = false;
                setImageLoop(true);
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private void setImageLoop(boolean loop) {

        if (null == mViewPager) return;

        if (loop) {
            if (mHandler.hasMessages(LOOP_NEXT)) return;
            mViewPager.removeOnPageChangeListener(this);
            mViewPager.addOnPageChangeListener(this);
            mHandler.sendEmptyMessageDelayed(LOOP_NEXT, loopTime);
            // Log.e("kaluee", "开始Loop");
        } else {
            if (!mHandler.hasMessages(LOOP_NEXT)) return;
            mHandler.removeMessages(LOOP_NEXT);
            mViewPager.removeOnPageChangeListener(this);
            mViewPager.addOnPageChangeListener(null);
            //  Log.e("kaluee", "结束Loop");
        }
    }

    /*********************************************************************************************/

    public void setBannerList(ArrayList<String> urlList) {

        // 1.容错
        if (null == urlList || urlList.size() == 0) return;

        // 2.主要是解决当item为小于3个的时候滑动有问题，这里将其拼凑成3个以上
        realImageSize = urlList.size();
        if (realImageSize < 3) {
            final String lastUrl = urlList.get(realImageSize - 1);
            final int count = 3 - realImageSize;
            for (int i = realImageSize; i <= (3 - count); i++) {
                urlList.add(lastUrl);
            }
        }

        if (null == mViewPager) {
            mViewPager = new BannerViewPager(mContext, isPagerVertical);
            mViewPager.setPageTransformer(true, new TransformerVertical());
            // ViewPager
            final LayoutParams params1 = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mViewPager.setLayoutParams(params1);
            addView(mViewPager);
            try {
                Field mScroller = ViewPager.class.getDeclaredField("mScroller");
                mScroller.setAccessible(true);
                BannerScroller scroller = new BannerScroller(mViewPager.getContext(), null, scrollerTime);
                mScroller.set(mViewPager, scroller);
            } catch (Exception e) {
                Log.e("", e.getMessage(), e);
            }
        }

        // 指示器
        LayoutParams paramsIndicator = new LayoutParams(LayoutParams.MATCH_PARENT, indicatorHeight);
        paramsIndicator.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        mLinearLayout.setGravity(Gravity.CENTER);
        paramsIndicator.setMargins(indicatorMargin, 0, indicatorMargin, 0);
        mLinearLayout.setBackgroundColor(indicatorBackground);
        addView(mLinearLayout, paramsIndicator);

        // 3.图片集合
        for (int i = 0; i < urlList.size(); i++) {

            ImageView image = new ImageView(getContext().getApplicationContext());
            final String url = urlList.get(i);
            image.setOnLongClickListener(BannerLayout.this);
            mImageList.put(url + i, image);

            ImageView point = new ImageView(getContext().getApplicationContext());
            final LinearLayout.LayoutParams paramsImage = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            paramsImage.gravity = Gravity.CENTER_VERTICAL;
            paramsImage.leftMargin = indicatorSpace;
            paramsImage.rightMargin = indicatorSpace;
            point.setImageDrawable(i == 0 ? select : normal);
            mLinearLayout.addView(point, paramsImage);
            mindicatorList.add(point);
        }

        mViewPager.setAdapter(mBannerAdapter);
        int position = Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % mindicatorList.size();
        mViewPager.setCurrentItem(position);
        onPageSelected(0);
        setImageLoop(true);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

        final int size = mImageList.size();
        int real = position % size;
        //  Log.e("kaluee", "onPageSelected ==> position = " + position + ", real = " + real);

        for (int i = 0; i < size; i++) {
            mindicatorList.get(i).setImageDrawable(real == i ? select : normal);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public boolean onLongClick(View v) {
        return true;
    }

    /**********************************************************************************************/

    public interface OnBannerImageChangeListener {

        void onBannerCreate(ImageView image, String imageUrl);

        void onBannerCilck(int position);
    }

    public void setOnBannerChangeListener(final OnBannerImageChangeListener onBannerImageChangeListener) {

        for (int i = 0; i < mImageList.size(); i++) {

            final int position = i;
            final String url = mImageList.keyAt(i);
            final ImageView image = mImageList.get(url);

            if (null == image) continue;

            if (null != onBannerImageChangeListener) {
                onBannerImageChangeListener.onBannerCreate(image, url.substring(0, url.length() - 1));
            }

            image.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBannerImageChangeListener.onBannerCilck(position);
                }
            });
        }
    }
}