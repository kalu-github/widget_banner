package com.demo.banner;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * description: 图片加载工具类
 * created by kalu on 2016/11/19 11:58
 */
public class GlideUtil {

    private static final String TAG = GlideUtil.class.getSimpleName();

    private static final int defaultImage = 0;

    // 条件设置
    // DiskCacheStrategy.NONE  不缓存文件
    // DiskCacheStrategy.RESOURCE  只缓存原图
    // DiskCacheStrategy.RESULT  只缓存最终加载的图（默认的缓存策略）
    // DiskCacheStrategy.ALL  同时缓存原图和结果图
    private static final RequestOptions options = new RequestOptions()
            .dontAnimate()
            .dontTransform()
            .centerCrop()
            .priority(Priority.HIGH)
            .diskCacheStrategy(DiskCacheStrategy.ALL);

    /**
     * Glide.with()方法中尽量传入Activity或Fragment，而不是Application，不然没办法进行生命周期管理。
     */
    public static void loadBanner(Context context, ImageView imageView, String url) {
//        String style = "!app_boot1";
        String style = "";
        loadImage(context, imageView, url, style, 0, 0, false);
    }


    /**
     * @param imageView
     * @param url
     * @param style
     * @param defaultImage 默认占位图
     * @param blurRadiu    1-99, 越大越模糊
     * @param isCircle     圆形图片处理
     */
    private static void loadImage(Context context, ImageView imageView, String url, String style, int defaultImage, int blurRadiu, boolean isCircle) {

        // 网络图片
        if (url != null && url.length() > 0) {
            if (url.startsWith("https://img.cdn.")) {
                url += style;
            }

            RequestBuilder builder = Glide
                    .with(context)
                    .load(url);

            if (defaultImage != 0) {
                options.placeholder(defaultImage)
                        .error(defaultImage);
            }

            if (isCircle) {
                options.circleCrop();
            }

            if (blurRadiu > 0 && blurRadiu < 100) {
            }

            builder.apply(options).into(imageView);
        }
        // 错误默认图片
        else {
            if (defaultImage != 0) {
                imageView.setImageResource(defaultImage);
            }
        }
    }
}
