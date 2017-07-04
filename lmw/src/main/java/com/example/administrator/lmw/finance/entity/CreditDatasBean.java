package com.example.administrator.lmw.finance.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/8/30 0030.
 */
public class CreditDatasBean implements Serializable {


    /**
     * borrowType : 1
     * deadLineValue : 142
     * deadLineType : 天
     * debtId : DEBT_O624xKAtIGQEo4CDkf3
     * debtTitle :
     * hasDebtInvestAmt :
     * labels :
     * labelStrArr : [""]
     * cornerlabels :
     * cornerlabelStrArr : [""]
     * repayMode : 按月等额本息还款
     * status : 0
     * transferAmount : 50000.00
     * transferEndDate : 2016-04-26 09:32:57
     * transferRate : 25.25
     * productType : 1
     */

    private String borrowType;
    private String deadLineValue;
    private String deadLineType;
    private String debtId;
    private String debtTitle;
    private String hasDebtInvestAmt;
    private String labels;
    private String cornerlabels;
    private String repayMode;
    private String status;
    private String transferAmount;
    private String debtPrincipalAmt;
    private String transferEndDate;
    private String transferRate;
    private String productType;
    private String discount;
    private List<String> labelStrArr;
    private List<String> cornerlabelStrArr;

    public String getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(String borrowType) {
        this.borrowType = borrowType;
    }

    public String getDeadLineValue() {
        return deadLineValue;
    }

    public void setDeadLineValue(String deadLineValue) {
        this.deadLineValue = deadLineValue;
    }

    public String getDeadLineType() {
        return deadLineType;
    }

    public void setDeadLineType(String deadLineType) {
        this.deadLineType = deadLineType;
    }

    public String getDebtId() {
        return debtId;
    }

    public void setDebtId(String debtId) {
        this.debtId = debtId;
    }

    public String getDebtTitle() {
        return debtTitle;
    }

    public void setDebtTitle(String debtTitle) {
        this.debtTitle = debtTitle;
    }

    public String getHasDebtInvestAmt() {
        return hasDebtInvestAmt;
    }

    public void setHasDebtInvestAmt(String hasDebtInvestAmt) {
        this.hasDebtInvestAmt = hasDebtInvestAmt;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public String getCornerlabels() {
        return cornerlabels;
    }

    public void setCornerlabels(String cornerlabels) {
        this.cornerlabels = cornerlabels;
    }

    public String getRepayMode() {
        return repayMode;
    }

    public void setRepayMode(String repayMode) {
        this.repayMode = repayMode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(String transferAmount) {
        this.transferAmount = transferAmount;
    }

    public String getDebtPrincipalAmt() {
        return debtPrincipalAmt;
    }

    public void setDebtPrincipalAmt(String debtPrincipalAmt) {
        this.debtPrincipalAmt = debtPrincipalAmt;
    }

    public String getTransferEndDate() {
        return transferEndDate;
    }

    public void setTransferEndDate(String transferEndDate) {
        this.transferEndDate = transferEndDate;
    }

    public String getTransferRate() {
        return transferRate;
    }

    public void setTransferRate(String transferRate) {
        this.transferRate = transferRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public List<String> getLabelStrArr() {
        return labelStrArr;
    }

    public void setLabelStrArr(List<String> labelStrArr) {
        this.labelStrArr = labelStrArr;
    }

    public List<String> getCornerlabelStrArr() {
        return cornerlabelStrArr;
    }

    public void setCornerlabelStrArr(List<String> cornerlabelStrArr) {
        this.cornerlabelStrArr = cornerlabelStrArr;
    }

    public CreditDatasBean() {
        super();
    }


}
