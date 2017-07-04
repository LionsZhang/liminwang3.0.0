package com.example.administrator.lmw.finance.entity;

/**
 * Created by Administrator on 2017/3/22.
 */

public class MatchVerifyEntity {

    /**
     * code	        错误码(0:成功;其它:失败)	string
     * data		    object
     * verifyCode	验证码(0:通过;1:非最优;2:不匹配;3:未使用)	string
     * verifyMsg	验证内容	string
     * msg	        错误内容
     */

    private String msg;
    private DataBean data;
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class DataBean {

        private String verifyCode;
        private String verifyMsg;

        public String getVerifyCode() {
            return verifyCode;
        }

        public void setVerifyCode(String verifyCode) {
            this.verifyCode = verifyCode;
        }

        public String getVerifyMsg() {
            return verifyMsg;
        }

        public void setVerifyMsg(String verifyMsg) {
            this.verifyMsg = verifyMsg;
        }
    }
}
