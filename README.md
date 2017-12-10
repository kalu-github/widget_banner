[ ![Download](https://api.bintray.com/packages/zhanghang/maven/bannerlayout/images/download.svg) ](https://bintray.com/zhanghang/maven/bannerlayout/_latestVersion)

```
compile 'lib.kalu.banner:bannerlayout:<latest-version>'
```
[戳我下载 ==>](https://pan.baidu.com/s/1hsOrodI)

![image](https://github.com/153437803/BannerLayout/blob/master/Screenrecorder-2017-12-04.gif ) 
![image](https://github.com/153437803/BannerLayout/blob/master/Screenrecorder-2017-12-05.gif ) 

```
解决问题：

1.Banner不可见的情况下还会发送轮播消息
2.Banne可见时, 偶发性的不再发送轮播消息
3.使用Handler发送延时消息, 不使用第三方库
4.ViewPager可以垂直方向，手势触摸滑动, 也可以水平方向
```


```
使用方法：

BannerLayout.setBannerList(List<String> urlList);
```

```
自定义属性：

<declare-styleable name="BannerLayout">
        <!-- 指示器, 高度 -->
        <attr name="bl_indicator_parent_height" format="dimension|reference" />
        <!-- 指示器孩子, 选中颜色 -->
        <attr name="bl_indicator_select_color" format="color|reference" />
        <!-- 指示器孩子, 默认颜色 -->
        <attr name="bl_indicator_normal_color" format="color|reference" />
        <!-- 指示器孩子, 选中高度 -->
        <attr name="bl_indicator_select_height" format="dimension|reference" />
        <!-- 指示器孩子, 默认高度 -->
        <attr name="bl_indicator_normal_height" format="dimension|reference" />
        <!-- 指示器孩子, 选中宽度 -->
        <attr name="bl_indicator_select_width" format="dimension|reference" />
        <!-- 指示器孩子, 默认宽度 -->
        <attr name="bl_indicator_normal_width" format="dimension|reference" />
        <!-- 指示器孩子间距 -->
        <attr name="bl_indicator_child_space" format="dimension|reference" />
        <!-- 指示器外边距 -->
        <attr name="bl_indicator_margin" format="dimension|reference" />
        <!-- 指示器背景 -->
        <attr name="bl_indicator_background_color" format="color|reference" />
        <!-- 指示器矩形, 默认圆形 -->
        <attr name="bl_indicator_rectangle" format="boolean" />
        <!-- 循环时长 -->
        <attr name="bl_time_loop" format="integer|reference" />
        <!-- 滚动时长 -->
        <attr name="bl_time_scroller" format="integer|reference" />
        <!-- 垂直Pager -->
        <attr name="bl_pager_vertical" format="boolean" />
</declare-styleable>
```

```
垂直滚动(BannerViewPager.class)

@Override
public boolean onInterceptTouchEvent(MotionEvent ev) {

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

    float width = getWidth();
    float height = getHeight();
    float swappedX = (ev.getY() / height) * width;
    float swappedY = (ev.getX() / width) * height;
    ev.setLocation(swappedX, swappedY);

    return super.onTouchEvent(ev);
}
```

```
循环轮播,停止轮播(BannerLayout.class)：

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

@Overrid
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
```
