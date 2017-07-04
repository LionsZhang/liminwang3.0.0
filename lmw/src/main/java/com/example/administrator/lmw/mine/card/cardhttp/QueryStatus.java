package com.example.administrator.lmw.mine.card.cardhttp;


/**
 * 卡券红包查询状态
 * <p>
 * [取值 ALL:所有;UNUSED:未使用;INUSE:使用中;USED:已使用;INUSE_USED:使用中和已使用;EXPIRED:已过期]，[示例：UNUSED]
 *
 * @author snubee
 * @email snubee96@gmail.com
 * @time 2017/2/15 14:35
 **/
public enum QueryStatus {
    ALL("ALL"),
    UNUSED("UNUSED"),
    INUSE("INUSE"),
    USED("USED"),
    INUSE_USED("INUSE_USED"),
    EXPIRED("EXPIRED");

    private String status;

    private QueryStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
