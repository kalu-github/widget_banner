package com.demo.banner;

import java.util.List;

/**
 * Created by kalu on 2017/12/4.
 */

public class BannerModel {


    /**
     * status : 0
     * msg : 返回数据成功
     * data : [{"image":"http://img.zcool.cn/community/01223c5541fed80000019ae9a6633a.jpg@2o.jpg","url":"http://www.jianshu.com/u/22a5d2ee8385"},{"image":"http://img.zcool.cn/community/01223c5541fed80000019ae9a6633a.jpg@2o.jpg","url":"http://www.jianshu.com/u/22a5d2ee8385"},{"image":"http://img.zcool.cn/community/01223c5541fed80000019ae9a6633a.jpg@2o.jpg","url":"http://www.jianshu.com/u/22a5d2ee8385"},{"image":"http://img.zcool.cn/community/01223c5541fed80000019ae9a6633a.jpg@2o.jpg","url":"http://www.jianshu.com/u/22a5d2ee8385"},{"image":"http://img.zcool.cn/community/01223c5541fed80000019ae9a6633a.jpg@2o.jpg","url":"http://www.jianshu.com/u/22a5d2ee8385"},{"image":"http://img.zcool.cn/community/01223c5541fed80000019ae9a6633a.jpg@2o.jpg","url":"http://www.jianshu.com/u/22a5d2ee8385"},{"image":"http://img.zcool.cn/community/01223c5541fed80000019ae9a6633a.jpg@2o.jpg","url":"http://www.jianshu.com/u/22a5d2ee8385"}]
     */

    private int status;
    private String msg;
    private List<DataBean> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * image : http://img.zcool.cn/community/01223c5541fed80000019ae9a6633a.jpg@2o.jpg
         * url : http://www.jianshu.com/u/22a5d2ee8385
         */

        private String image;
        private String url;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
