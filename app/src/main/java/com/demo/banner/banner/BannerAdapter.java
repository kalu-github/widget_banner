package com.demo.banner.banner;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * description: 轮播图适配器
 * created by kalu on 2016/10/28 18:12
 */
class BannerAdapter extends PagerAdapter {

    private List<BannerImageView> views;

    public BannerAdapter(List<BannerImageView> views) {
        this.views = views;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (views.size() > 0) {
            View view = views.get(position % views.size());
            if (container.equals(view.getParent())) {
                container.removeView(view);
            }
            container.addView(view);
            return view;
        }
        return null;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}