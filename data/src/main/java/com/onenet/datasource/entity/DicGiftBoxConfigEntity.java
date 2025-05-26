package com.onenet.datasource.entity;

import java.util.List;

/**
 * DicGiftBoxConfig
 *
 * @author HaiYinLong
 * @version 2025/05/23 15:02
 **/
public class DicGiftBoxConfigEntity {
    private Integer showLv;
    private Integer interval;
    private Integer miss;
    private Integer fastMaterialAddLv;
    private List<RwdValueObject> rwdList;

    public Integer getShowLv() {
        return showLv;
    }

    public void setShowLv(Integer showLv) {
        this.showLv = showLv;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getMiss() {
        return miss;
    }

    public void setMiss(Integer miss) {
        this.miss = miss;
    }

    public Integer getFastMaterialAddLv() {
        return fastMaterialAddLv;
    }

    public void setFastMaterialAddLv(Integer fastMaterialAddLv) {
        this.fastMaterialAddLv = fastMaterialAddLv;
    }

    public List<RwdValueObject> getRwdList() {
        return rwdList;
    }

    public void setRwdList(List<RwdValueObject> rwdList) {
        this.rwdList = rwdList;
    }

    public static class RwdValueObject {
        private String accountType;
        private Integer rwd;
        private Integer num;

        public String getAccountType() {
            return accountType;
        }

        public void setAccountType(String accountType) {
            this.accountType = accountType;
        }

        public Integer getRwd() {
            return rwd;
        }

        public void setRwd(Integer rwd) {
            this.rwd = rwd;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }
    }
}
