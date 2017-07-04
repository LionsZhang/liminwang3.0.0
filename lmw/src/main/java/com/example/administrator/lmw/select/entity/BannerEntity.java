package com.example.administrator.lmw.select.entity;


import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/25 0025.
 */
public class BannerEntity implements Serializable {


    /**
     * code : 0
     * msg : success
     * data : {"datas":[{"bannerTitle":"新Banner","bannerImage":"http://192.168.2.130:8087/imageUrl/product/201608/1471415316096.jpg","bannerUrl":"http://www.limin.com/chineseValentineDay.html","id":2,"isBlank":1},{"bannerTitle":"ttt333","bannerImage":"http://www.limin.com/sys/upload/images/banner/banner_b.jpg","bannerUrl":"http://www.limin.com/yuebao.html","id":3,"isBlank":1}]}
     */

    public String code;
    public String msg;
    public BannerDataBean data;

    public BannerEntity() {
        super();
    }


}
