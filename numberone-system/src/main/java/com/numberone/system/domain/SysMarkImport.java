package com.numberone.system.domain;

import com.numberone.common.annotation.Excel;

public class SysMarkImport {
    @Excel(name = "部门名称")
    private String deptName;
    @Excel(name = "用户名称")
    private String userName;
    @Excel(name = "自评年份")
    private String markYear;
    @Excel(name = "自评季度")
    private String markJiDu;
    @Excel(name = "自评月份")
    private String markMonth;
    @Excel(name = "自评得分")
    private Double markMarkSelf;
    @Excel(name = "小组评得分")
    private Double markMarkLeader;
    @Excel(name = "党支部评得分")
    private Double markMarkDZB;

    private String markId;

    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }

    public Double getMarkMarkSelf() {
        return markMarkSelf;
    }

    public void setMarkMarkSelf(Double markMarkSelf) {
        this.markMarkSelf = markMarkSelf;
    }

    public Double getMarkMarkLeader() {
        return markMarkLeader;
    }

    public void setMarkMarkLeader(Double markMarkLeader) {
        this.markMarkLeader = markMarkLeader;
    }

    public Double getMarkMarkDZB() {
        return markMarkDZB;
    }

    public void setMarkMarkDZB(Double markMarkDZB) {
        this.markMarkDZB = markMarkDZB;
    }

    public String getMarkMonth() {
        return markMonth;
    }

    public void setMarkMonth(String markMonth) {
        this.markMonth = markMonth;
    }

    public String getMarkYear() {
        return markYear;
    }

    public void setMarkYear(String markYear) {
        this.markYear = markYear;
    }

    public String getMarkJiDu() {
        return markJiDu;
    }

    public void setMarkJiDu(String markJiDu) {
        this.markJiDu = markJiDu;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }


}
