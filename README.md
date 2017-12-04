轮播图+视察动画+水平垂直+可见开始循环+隐藏停止循环

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
