package com.numberone.system.domain;


import com.numberone.common.annotation.Excel;
import com.numberone.common.base.BaseEntity;

public class SysUserMarkDzb extends BaseEntity {

  @Excel(name="小组评id")
  private String mlid;
  @Excel(name="党支部评分")
  private double markDzb;
  @Excel(name="党支部评分时间")
  private java.sql.Timestamp markDzbTime;
  @Excel(name="党支部评分描述")
  private String markDzbReason;
  @Excel(name="党支部评分id")
  private String markDzbId;
  @Excel(name="党支部修改评分时间")
  private java.sql.Timestamp changeDzbTime;
  @Excel(name="党支部修改评分原因")
  private String changeDzbReason;
  @Excel(name="党支部评分修改次数")
  private long changeDzbTimes;
  @Excel(name="党支部修改评分")
  private double markDzbChange;
  @Excel(name="党支部修改评分历史")
  private String markDzbHistory;


  public String getMlid() {
    return mlid;
  }

  public void setMlid(String mlid) {
    this.mlid = mlid;
  }


  public double getMarkDzb() {
    return markDzb;
  }

  public void setMarkDzb(double markDzb) {
    this.markDzb = markDzb;
  }


  public java.sql.Timestamp getMarkDzbTime() {
    return markDzbTime;
  }

  public void setMarkDzbTime(java.sql.Timestamp markDzbTime) {
    this.markDzbTime = markDzbTime;
  }


  public String getMarkDzbReason() {
    return markDzbReason;
  }

  public void setMarkDzbReason(String markDzbReason) {
    this.markDzbReason = markDzbReason;
  }


  public String getMarkDzbId() {
    return markDzbId;
  }

  public void setMarkDzbId(String markDzbId) {
    this.markDzbId = markDzbId;
  }


  public java.sql.Timestamp getChangeDzbTime() {
    return changeDzbTime;
  }

  public void setChangeDzbTime(java.sql.Timestamp changeDzbTime) {
    this.changeDzbTime = changeDzbTime;
  }


  public String getChangeDzbReason() {
    return changeDzbReason;
  }

  public void setChangeDzbReason(String changeDzbReason) {
    this.changeDzbReason = changeDzbReason;
  }


  public long getChangeDzbTimes() {
    return changeDzbTimes;
  }

  public void setChangeDzbTimes(long changeDzbTimes) {
    this.changeDzbTimes = changeDzbTimes;
  }


  public double getMarkDzbChange() {
    return markDzbChange;
  }

  public void setMarkDzbChange(double markDzbChange) {
    this.markDzbChange = markDzbChange;
  }


  public String getMarkDzbHistory() {
    return markDzbHistory;
  }

  public void setMarkDzbHistory(String markDzbHistory) {
    this.markDzbHistory = markDzbHistory;
  }

}
