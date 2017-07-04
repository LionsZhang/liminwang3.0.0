package com.example.administrator.lmw.select.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/8/29 0029.
 */
public class StatisticsEntity {

    public DataBean data;
    /**
     * data : {"datas":[{"type":"累计注册人数","count":"30000"},{"type":"累计交易额","count":"30000"},{"type":"运营天数","count":"30000"}]}
     * code : 0
     * msg : success
     */

    public String code;
    public String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class DataBean {
        /**
         * type : 累计注册人数
         * count : 30000
         */

        public List<DatasBean> datas;


        public static class DatasBean {
            public String type;
            public String count;
            public String units;


        }
    }
}
