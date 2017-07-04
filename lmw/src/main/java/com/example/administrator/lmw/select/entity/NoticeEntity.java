package com.example.administrator.lmw.select.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/19 0019.
 */
public class NoticeEntity {

    /**
     * unreadMessageNum : 41878
     * pageSize : 87300
     * totalCount : 25448
     * pageCount : 80220
     * datas : [{"messageContent":"测试内容819d","id":72402,"messageStatus":21136,"messageTitle":"测试内容4vu4","createTime":"测试内容2evs"}]
     * pageIndex : 28022
     */

    private DataBean data;
    /**
     * data : {"unreadMessageNum":41878,"pageSize":87300,"totalCount":25448,"pageCount":80220,"datas":[{"messageContent":"测试内容819d","id":72402,"messageStatus":21136,"messageTitle":"测试内容4vu4","createTime":"测试内容2evs"}],"pageIndex":28022}
     * code : 67644
     * msg : success
     */

    private String code;
    private String msg;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private int unreadMessageNum;
        private int pageSize;
        private int totalCount;
        private int pageCount;
        private int pageIndex;
        /**
         * messageContent : 测试内容819d
         * id : 72402
         * messageStatus : 21136
         * messageTitle : 测试内容4vu4
         * createTime : 测试内容2evs
         */

        private List<NoticeDatasBean> datas;

        public int getUnreadMessageNum() {
            return unreadMessageNum;
        }

        public void setUnreadMessageNum(int unreadMessageNum) {
            this.unreadMessageNum = unreadMessageNum;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(int totalCount) {
            this.totalCount = totalCount;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public List<NoticeDatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<NoticeDatasBean> datas) {
            this.datas = datas;
        }
    }
}
