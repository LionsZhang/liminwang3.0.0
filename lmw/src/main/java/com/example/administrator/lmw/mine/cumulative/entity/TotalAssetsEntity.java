package com.example.administrator.lmw.mine.cumulative.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/9/12 0012.
 */
public class TotalAssetsEntity {

    /**
     * totalAssets : 11040.0
     * accuIncome : 20.0
     * dueinIncome : 0.0
     * dueinPrincipal : 0.0
     * dueinPrincipalRatio : 0.0
     * frozenAmount : 1000.0
     * frozenAmountRatio : 9.057971014492754
     * usableAmount : 10040.0
     * usableAmountRatio : 90.94202898550725
     * financeProductAmount : 0.0
     * financeProductAmountRatio : 0.0
     * debtProductAmount : 0.0
     * debtProductAmountRatio : 0.0
     */

    private AssetInfoBean assetInfo;
    private List<?> itemList;

    public AssetInfoBean getAssetInfo() {
        return assetInfo;
    }

    public void setAssetInfo(AssetInfoBean assetInfo) {
        this.assetInfo = assetInfo;
    }

    public List<?> getItemList() {
        return itemList;
    }

    public void setItemList(List<?> itemList) {
        this.itemList = itemList;
    }

    public static class AssetInfoBean {
        private double totalAssets;
        private double accuIncome;
        private double dueinIncome;
        private double dueinPrincipal;
        private double dueinPrincipalRatio;
        private double frozenAmount;
        private double frozenAmountRatio;
        private double usableAmount;
        private double usableAmountRatio;
        private double financeProductAmount;
        private double financeProductAmountRatio;
        private double debtProductAmount;
        private double debtProductAmountRatio;

        public double getTotalAssets() {
            return totalAssets;
        }

        public void setTotalAssets(double totalAssets) {
            this.totalAssets = totalAssets;
        }

        public double getAccuIncome() {
            return accuIncome;
        }

        public void setAccuIncome(double accuIncome) {
            this.accuIncome = accuIncome;
        }

        public double getDueinIncome() {
            return dueinIncome;
        }

        public void setDueinIncome(double dueinIncome) {
            this.dueinIncome = dueinIncome;
        }

        public double getDueinPrincipal() {
            return dueinPrincipal;
        }

        public void setDueinPrincipal(double dueinPrincipal) {
            this.dueinPrincipal = dueinPrincipal;
        }

        public double getDueinPrincipalRatio() {
            return dueinPrincipalRatio;
        }

        public void setDueinPrincipalRatio(double dueinPrincipalRatio) {
            this.dueinPrincipalRatio = dueinPrincipalRatio;
        }

        public double getFrozenAmount() {
            return frozenAmount;
        }

        public void setFrozenAmount(double frozenAmount) {
            this.frozenAmount = frozenAmount;
        }

        public double getFrozenAmountRatio() {
            return frozenAmountRatio;
        }

        public void setFrozenAmountRatio(double frozenAmountRatio) {
            this.frozenAmountRatio = frozenAmountRatio;
        }

        public double getUsableAmount() {
            return usableAmount;
        }

        public void setUsableAmount(double usableAmount) {
            this.usableAmount = usableAmount;
        }

        public double getUsableAmountRatio() {
            return usableAmountRatio;
        }

        public void setUsableAmountRatio(double usableAmountRatio) {
            this.usableAmountRatio = usableAmountRatio;
        }

        public double getFinanceProductAmount() {
            return financeProductAmount;
        }

        public void setFinanceProductAmount(double financeProductAmount) {
            this.financeProductAmount = financeProductAmount;
        }

        public double getFinanceProductAmountRatio() {
            return financeProductAmountRatio;
        }

        public void setFinanceProductAmountRatio(double financeProductAmountRatio) {
            this.financeProductAmountRatio = financeProductAmountRatio;
        }

        public double getDebtProductAmount() {
            return debtProductAmount;
        }

        public void setDebtProductAmount(double debtProductAmount) {
            this.debtProductAmount = debtProductAmount;
        }

        public double getDebtProductAmountRatio() {
            return debtProductAmountRatio;
        }

        public void setDebtProductAmountRatio(double debtProductAmountRatio) {
            this.debtProductAmountRatio = debtProductAmountRatio;
        }
    }
}
