package com.numberone.system.domain;

import com.numberone.common.annotation.Excel;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 用户自评对象
 */
public class SysUserMarkSelf implements Serializable {
    //用户id
    @Excel(name = "用户id")
    private long uid;
    //自评得分
    @Excel(name = "自评得分")
    private Double markSelf;
    //自评时间
    @Excel(name = "自评时间")
    private java.sql.Timestamp markTime;
    //自评描述
    @Excel(name = "自评描述")
    private String markReason;
    //评分id
    @Excel(name = "评分id")
    private String markId;
    //修改时间
    @Excel(name = "修改时间")
    private java.sql.Timestamp changeTime;
    //修改原因
    @Excel(name = "修改原因")
    private String changeReason;
    //修改得分
    @Excel(name = "修改得分")
    private Double markChange;
    //修改次数
    @Excel(name = "修改次数")
    private long changeTimes;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public Double getMarkSelf() {
        return markSelf;
    }

    public void setMarkSelf(Double markSelf) {
        this.markSelf = markSelf;
    }

    public Timestamp getMarkTime() {
        return markTime;
    }

    public void setMarkTime(Timestamp markTime) {
        this.markTime = markTime;
    }

    public String getMarkReason() {
        return markReason;
    }

    public void setMarkReason(String markReason) {
        this.markReason = markReason;
    }

    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }

    public Timestamp getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Timestamp changeTime) {
        this.changeTime = changeTime;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public Double getMarkChange() {
        return markChange;
    }

    public void setMarkChange(Double markChange) {
        this.markChange = markChange;
    }

    public long getChangeTimes() {
        return changeTimes;
    }

    public void setChangeTimes(long changeTimes) {
        this.changeTimes = changeTimes;
    }
}
