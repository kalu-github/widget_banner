package com.demo.banner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.demo.banner.banner.BannerLayout;
import com.google.gson.Gson;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final BannerLayout banner = findViewById(R.id.banner1);
        final ArrayList<String> images = new ArrayList<>();

        //get请求简洁版实现
        RxVolley.get("http://rap2api.taobao.org/app/mock/844/GET//banner", new HttpCallback() {
            @Override
            public void onSuccess(String t) {
                final BannerModel bannerModel = new Gson().fromJson(t, BannerModel.class);
                final List<BannerModel.DataBean> result = bannerModel.getData();

                for (int i = 0; i < result.size(); i++) {
                    images.add(result.get(i).getImage());
                }

                banner.setBannerList(images);
                banner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
                    @Override
                    public void onBannerItemClick(int position) {
                        final String url = result.get(position).getUrl();
                        Toast.makeText(getApplicationContext(), url, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}