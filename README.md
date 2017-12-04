[戳我下载, 体验DEMO](https://pan.baidu.com/s/1hsOrodI)

```
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
