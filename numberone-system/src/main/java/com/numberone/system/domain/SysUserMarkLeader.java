package com.numberone.system.domain;

import com.numberone.common.annotation.Excel;
import com.numberone.common.base.BaseEntity;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 上级评分对象
 */
public class SysUserMarkLeader extends BaseEntity {

    /** 用户ID */
    @Excel(name = "用户序号",type= Excel.Type.IMPORT)
    private Long userId;
    /** 部门ID */
    @Excel(name = "部门编号", type = Excel.Type.IMPORT)
    private Long deptId;

    /** 用户名称 */
    @Excel(name = "用户名称")
    private String userName;

    @Excel(name = "自评id",type= Excel.Type.IMPORT)
    private String mid;
    //自评得分
    @Excel(name = "自评得分")
    private Double markSelf;
    //自评时间
    @Excel(name = "自评时间")
    private java.sql.Timestamp markTime;
    //自评描述
    @Excel(name = "自评描述")
    private String markReason;
    //自评修改时间
    @Excel(name = "自评修改时间")
    private java.sql.Timestamp changeTime;
    //自评修改原因
    @Excel(name = "自评修改原因")
    private String changeReason;
    //自评修改得分
    @Excel(name = "自评修改得分")
    private Double markChange;
    //自评修改次数
    @Excel(name = "自评修改次数")
    private long changeTimes;
    @Excel(name = "小组评分")
    private Double markLeader;
    @Excel(name = "小组评分时间")
    private java.sql.Timestamp markLeaderTime;
    @Excel(name = "小组评分描述")
    private String markLeaderReason;
    @Excel(name = "小组评分id",type= Excel.Type.IMPORT)
    private String markLeaderId;
    @Excel(name = "小组修改评分")
    private Double markLeaderChange;
    @Excel(name = "小组评分修改时间")
    private java.sql.Timestamp changeLeaderTime;
    @Excel(name = "小组评分修改描述")
    private String changeLeaderReason;
    @Excel(name = "小组评分修改次数")
    private long changeLeaderTimes;


    //用户id
    @Excel(name = "用户id",type= Excel.Type.IMPORT)
    private long uid;

    //评分id
    @Excel(name = "评分id",type= Excel.Type.IMPORT)
    private String markId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public Double getMarkLeader() {
        return markLeader;
    }

    public void setMarkLeader(Double markLeader) {
        this.markLeader = markLeader;
    }

    public Timestamp getMarkLeaderTime() {
        return markLeaderTime;
    }

    public void setMarkLeaderTime(Timestamp markLeaderTime) {
        this.markLeaderTime = markLeaderTime;
    }

    public String getMarkLeaderReason() {
        return markLeaderReason;
    }

    public void setMarkLeaderReason(String markLeaderReason) {
        this.markLeaderReason = markLeaderReason;
    }

    public String getMarkLeaderId() {
        return markLeaderId;
    }

    public void setMarkLeaderId(String markLeaderId) {
        this.markLeaderId = markLeaderId;
    }

    public Timestamp getChangeLeaderTime() {
        return changeLeaderTime;
    }

    public void setChangeLeaderTime(Timestamp changeLeaderTime) {
        this.changeLeaderTime = changeLeaderTime;
    }

    public String getChangeLeaderReason() {
        return changeLeaderReason;
    }

    public void setChangeLeaderReason(String changeLeaderReason) {
        this.changeLeaderReason = changeLeaderReason;
    }

    public long getChangeLeaderTimes() {
        return changeLeaderTimes;
    }

    public void setChangeLeaderTimes(long changeLeaderTimes) {
        this.changeLeaderTimes = changeLeaderTimes;
    }

    public Double getMarkLeaderChange() {
        return markLeaderChange;
    }

    public void setMarkLeaderChange(Double markLeaderChange) {
        this.markLeaderChange = markLeaderChange;
    }

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
