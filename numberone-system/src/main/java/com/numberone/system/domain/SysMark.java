package com.numberone.system.domain;

import com.numberone.common.annotation.MarkReason;
import com.numberone.common.base.BaseEntity;

import java.sql.Timestamp;

public class SysMark extends BaseEntity {

    private String markReasons;
    private String fj1ReasonSelf;
    @MarkReason(reasonName = "责任区党员群众发生违法行为", isFouJue = true)
    private Double fj1MarkSelf;
    private String fj2ReasonSelf;
    @MarkReason(reasonName = "责任区党员群众发生撞“红线”及以上问题", isFouJue = true)
    private Double fj2MarkSelf;
    private String fj3ReasonSelf;
    @MarkReason(reasonName = "责任区党员群众参与群体上访、越级上访", isFouJue = true)
    private Double fj3MarkSelf;
    private String fj4ReasonSelf;
    @MarkReason(reasonName = "责任区发生打架斗殴等不良行为", isFouJue = true)
    private Double fj4MarkSelf;
    private String fj5ReasonSelf;
    @MarkReason(reasonName = "经研究其他否决项目的问题", isFouJue = true)
    private Double fj5MarkSelf;
    private String jf1ReasonSelf;
    @MarkReason(reasonName = "责任区党员群众发现安全重大隐患、防止安全事故、受到段级及以上表扬表彰或通报嘉奖", isSubtraction = false)
    private Double jf1MarkSelf;
    private String jf2ReasonSelf;
    @MarkReason(reasonName = "责任区党员群众参加技术比武获得名次", isSubtraction = false)
    private Double jf2MarkSelf;
    private String jf3ReasonSelf;
    @MarkReason(reasonName = "积极组织责任区党员群众 围绕安全、运输和技术难题立项攻关取得实效，受到总公司、 集团公司、段表彰", isSubtraction = false)
    private Double jf3MarkSelf;
    private String jf4ReasonSelf;
    @MarkReason(reasonName = "责任区党员群众完成急难险重任务成绩突出", isSubtraction = false)
    private Double jf4MarkSelf;
    private String jf5ReasonSelf;
    @MarkReason(reasonName = "责任区党员群众做好人好事、见义勇为事迹受到表彰奖励或媒体表扬", isSubtraction = false)
    private Double jf5MarkSelf;
    private String jf6ReasonSelf;
    @MarkReason(reasonName = "其他受到集团公司及以上表彰奖励", isSubtraction = false)
    private Double jf6MarkSelf;
    private String llxz1ReasonSelf;
    @MarkReason(reasonName = "责任区党员群众无故不参加上级组织的集体活动")
    private Double llxz1MarkSelf;
    private String llxz2ReasonSelf;
    @MarkReason(reasonName = "责任区内环境卫生差、备品摆放不整齐")
    private Double llxz2MarkSelf;
    private String llxz3ReasonSelf;
    @MarkReason(reasonName = "班组标准化验收不达标")
    private Double llxz3MarkSelf;
    private String llxz4ReasonSelf;
    @MarkReason(reasonName = "班组未完成生产任务，运输组织工作，旅客、货主等服务工作受到上级批评")
    private Double llxz4MarkSelf;
    private String zygw1ReasonSelf;
    @MarkReason(reasonName = "责任区党员群众发生迟到、早退")
    private Double zygw1MarkSelf;
    private String zygw2ReasonSelf;
    @MarkReason(reasonName = "责任区党员群众发生严重“两违”问题")
    private Double zygw2MarkSelf;
    private String zygw3ReasonSelf;
    @MarkReason(reasonName = "作业提醒不到位")
    private Double zygw3MarkSelf;
    private String jsyw1ReasonSelf;
    @MarkReason(reasonName = "不参加月度业务考试、模拟演练")
    private Double jsyw1MarkSelf;
    private String jsyw2ReasonSelf;
    @MarkReason(reasonName = "责任区党员群众月度考试或抽考成绩不达标")
    private Double jsyw2MarkSelf;
    private String jsyw3ReasonSelf;
    @MarkReason(reasonName = "应知应会考试、专业技能考核不达标")
    private Double jsyw3MarkSelf;
    private String jsyw4ReasonSelf;
    @MarkReason(reasonName = "责任区党员群众技术业务帮带效果不明显")
    private Double jsyw4MarkSelf;
    private String zgsx1ReasonSelf;
    @MarkReason(reasonName = "对责任区内职工思想动态不掌握、不熟悉、不了解，不能及时做思想工作")
    private Double zgsx1MarkSelf;
    private String zgsx2ReasonSelf;
    @MarkReason(reasonName = "未及时与发生“两违”问题的党员群众谈心谈")
    private Double zgsx2MarkSelf;
    private String zgsx3ReasonSelf;
    @MarkReason(reasonName = "未及时与困难党员群众谈心谈话")
    private Double zgsx3MarkSelf;
    private String zgsx4ReasonSelf;
    @MarkReason(reasonName = "未及时化解矛盾造成不良影响")
    private Double zgsx4MarkSelf;
    private java.sql.Timestamp markTime;
    private java.sql.Timestamp updateTime;
    private String startTime;
    private String endTime;
    private String markId;
    private Double markMark;
    private String markMonth;
    private String markYear;
    private String markJiDu;
    private long uid;
    private String deptId;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public String getMarkYear() {
        return markYear;
    }

    public void setMarkYear(String markYear) {
        this.markYear = markYear;
    }


    public String getFj1ReasonSelf() {
        return fj1ReasonSelf;
    }

    public void setFj1ReasonSelf(String fj1ReasonSelf) {
//        if("".equals(fj1ReasonSelf)){this.fj1ReasonSelf=null;}
        this.fj1ReasonSelf = fj1ReasonSelf;
    }


    public Double getFj1MarkSelf() {
        return fj1MarkSelf;
    }

    public void setFj1MarkSelf(Double fj1MarkSelf) {
        this.fj1MarkSelf = fj1MarkSelf;
    }


    public String getFj2ReasonSelf() {
        return fj2ReasonSelf;
    }

    public void setFj2ReasonSelf(String fj2ReasonSelf) {
//        if("".equals(fj2ReasonSelf)){this.fj2ReasonSelf=null;}
        this.fj2ReasonSelf = fj2ReasonSelf;
    }


    public Double getFj2MarkSelf() {
        return fj2MarkSelf;
    }

    public void setFj2MarkSelf(Double fj2MarkSelf) {
        this.fj2MarkSelf = fj2MarkSelf;
    }


    public String getFj3ReasonSelf() {
        return fj3ReasonSelf;
    }

    public void setFj3ReasonSelf(String fj3ReasonSelf) {
//        if("".equals(fj3ReasonSelf)){this.fj3ReasonSelf=null;}
        this.fj3ReasonSelf = fj3ReasonSelf;
    }


    public Double getFj3MarkSelf() {
        return fj3MarkSelf;
    }

    public void setFj3MarkSelf(Double fj3MarkSelf) {
        this.fj3MarkSelf = fj3MarkSelf;
    }


    public String getFj4ReasonSelf() {
        return fj4ReasonSelf;
    }

    public void setFj4ReasonSelf(String fj4ReasonSelf) {
        this.fj4ReasonSelf = fj4ReasonSelf;
    }


    public Double getFj4MarkSelf() {
        return fj4MarkSelf;
    }

    public void setFj4MarkSelf(Double fj4MarkSelf) {
        this.fj4MarkSelf = fj4MarkSelf;
    }


    public String getFj5ReasonSelf() {
        return fj5ReasonSelf;
    }

    public void setFj5ReasonSelf(String fj5ReasonSelf) {
        this.fj5ReasonSelf = fj5ReasonSelf;
    }


    public Double getFj5MarkSelf() {
        return fj5MarkSelf;
    }

    public void setFj5MarkSelf(Double fj5MarkSelf) {
        this.fj5MarkSelf = fj5MarkSelf;
    }


    public String getJf1ReasonSelf() {
        return jf1ReasonSelf;
    }

    public void setJf1ReasonSelf(String jf1ReasonSelf) {
        this.jf1ReasonSelf = jf1ReasonSelf;
    }


    public Double getJf1MarkSelf() {
        return jf1MarkSelf;
    }

    public void setJf1MarkSelf(Double jf1MarkSelf) {
        this.jf1MarkSelf = jf1MarkSelf;
    }


    public String getJf2ReasonSelf() {
        return jf2ReasonSelf;
    }

    public void setJf2ReasonSelf(String jf2ReasonSelf) {
        this.jf2ReasonSelf = jf2ReasonSelf;
    }


    public Double getJf2MarkSelf() {
        return jf2MarkSelf;
    }

    public void setJf2MarkSelf(Double jf2MarkSelf) {
        this.jf2MarkSelf = jf2MarkSelf;
    }


    public String getJf3ReasonSelf() {
        return jf3ReasonSelf;
    }

    public void setJf3ReasonSelf(String jf3ReasonSelf) {
        this.jf3ReasonSelf = jf3ReasonSelf;
    }


    public Double getJf3MarkSelf() {
        return jf3MarkSelf;
    }

    public void setJf3MarkSelf(Double jf3MarkSelf) {
        this.jf3MarkSelf = jf3MarkSelf;
    }


    public String getJf4ReasonSelf() {
        return jf4ReasonSelf;
    }

    public void setJf4ReasonSelf(String jf4ReasonSelf) {
        this.jf4ReasonSelf = jf4ReasonSelf;
    }


    public Double getJf4MarkSelf() {
        return jf4MarkSelf;
    }

    public void setJf4MarkSelf(Double jf4MarkSelf) {
        this.jf4MarkSelf = jf4MarkSelf;
    }


    public String getJf5ReasonSelf() {
        return jf5ReasonSelf;
    }

    public void setJf5ReasonSelf(String jf5ReasonSelf) {
        this.jf5ReasonSelf = jf5ReasonSelf;
    }


    public Double getJf5MarkSelf() {
        return jf5MarkSelf;
    }

    public void setJf5MarkSelf(Double jf5MarkSelf) {
        this.jf5MarkSelf = jf5MarkSelf;
    }


    public String getJf6ReasonSelf() {
        return jf6ReasonSelf;
    }

    public void setJf6ReasonSelf(String jf6ReasonSelf) {
        this.jf6ReasonSelf = jf6ReasonSelf;
    }


    public Double getJf6MarkSelf() {
        return jf6MarkSelf;
    }

    public void setJf6MarkSelf(Double jf6MarkSelf) {
        this.jf6MarkSelf = jf6MarkSelf;
    }


    public String getLlxz1ReasonSelf() {
        return llxz1ReasonSelf;
    }

    public void setLlxz1ReasonSelf(String llxz1ReasonSelf) {
        this.llxz1ReasonSelf = llxz1ReasonSelf;
    }


    public Double getLlxz1MarkSelf() {
        return llxz1MarkSelf;
    }

    public void setLlxz1MarkSelf(Double llxz1MarkSelf) {
        this.llxz1MarkSelf = llxz1MarkSelf;
    }


    public String getLlxz2ReasonSelf() {
        return llxz2ReasonSelf;
    }

    public void setLlxz2ReasonSelf(String llxz2ReasonSelf) {
        this.llxz2ReasonSelf = llxz2ReasonSelf;
    }


    public Double getLlxz2MarkSelf() {
        return llxz2MarkSelf;
    }

    public void setLlxz2MarkSelf(Double llxz2MarkSelf) {
        this.llxz2MarkSelf = llxz2MarkSelf;
    }


    public String getLlxz3ReasonSelf() {
        return llxz3ReasonSelf;
    }

    public void setLlxz3ReasonSelf(String llxz3ReasonSelf) {
        this.llxz3ReasonSelf = llxz3ReasonSelf;
    }


    public Double getLlxz3MarkSelf() {
        return llxz3MarkSelf;
    }

    public void setLlxz3MarkSelf(Double llxz3MarkSelf) {
        this.llxz3MarkSelf = llxz3MarkSelf;
    }


    public String getLlxz4ReasonSelf() {
        return llxz4ReasonSelf;
    }

    public void setLlxz4ReasonSelf(String llxz4ReasonSelf) {
        this.llxz4ReasonSelf = llxz4ReasonSelf;
    }


    public Double getLlxz4MarkSelf() {
        return llxz4MarkSelf;
    }

    public void setLlxz4MarkSelf(Double llxz4MarkSelf) {
        this.llxz4MarkSelf = llxz4MarkSelf;
    }


    public String getZygw1ReasonSelf() {
        return zygw1ReasonSelf;
    }

    public void setZygw1ReasonSelf(String zygw1ReasonSelf) {
        this.zygw1ReasonSelf = zygw1ReasonSelf;
    }


    public Double getZygw1MarkSelf() {
        return zygw1MarkSelf;
    }

    public void setZygw1MarkSelf(Double zygw1MarkSelf) {
        this.zygw1MarkSelf = zygw1MarkSelf;
    }


    public String getZygw2ReasonSelf() {
        return zygw2ReasonSelf;
    }

    public void setZygw2ReasonSelf(String zygw2ReasonSelf) {
        this.zygw2ReasonSelf = zygw2ReasonSelf;
    }


    public Double getZygw2MarkSelf() {
        return zygw2MarkSelf;
    }

    public void setZygw2MarkSelf(Double zygw2MarkSelf) {
        this.zygw2MarkSelf = zygw2MarkSelf;
    }


    public String getZygw3ReasonSelf() {
        return zygw3ReasonSelf;
    }

    public void setZygw3ReasonSelf(String zygw3ReasonSelf) {
        this.zygw3ReasonSelf = zygw3ReasonSelf;
    }


    public Double getZygw3MarkSelf() {
        return zygw3MarkSelf;
    }

    public void setZygw3MarkSelf(Double zygw3MarkSelf) {
        this.zygw3MarkSelf = zygw3MarkSelf;
    }


    public String getJsyw1ReasonSelf() {
        return jsyw1ReasonSelf;
    }

    public void setJsyw1ReasonSelf(String jsyw1ReasonSelf) {
        this.jsyw1ReasonSelf = jsyw1ReasonSelf;
    }


    public Double getJsyw1MarkSelf() {
        return jsyw1MarkSelf;
    }

    public void setJsyw1MarkSelf(Double jsyw1MarkSelf) {
        this.jsyw1MarkSelf = jsyw1MarkSelf;
    }


    public String getJsyw2ReasonSelf() {
        return jsyw2ReasonSelf;
    }

    public void setJsyw2ReasonSelf(String jsyw2ReasonSelf) {
        this.jsyw2ReasonSelf = jsyw2ReasonSelf;
    }


    public Double getJsyw2MarkSelf() {
        return jsyw2MarkSelf;
    }

    public void setJsyw2MarkSelf(Double jsyw2MarkSelf) {
        this.jsyw2MarkSelf = jsyw2MarkSelf;
    }


    public String getJsyw3ReasonSelf() {
        return jsyw3ReasonSelf;
    }

    public void setJsyw3ReasonSelf(String jsyw3ReasonSelf) {
        this.jsyw3ReasonSelf = jsyw3ReasonSelf;
    }


    public Double getJsyw3MarkSelf() {
        return jsyw3MarkSelf;
    }

    public void setJsyw3MarkSelf(Double jsyw3MarkSelf) {
        this.jsyw3MarkSelf = jsyw3MarkSelf;
    }


    public String getJsyw4ReasonSelf() {
        return jsyw4ReasonSelf;
    }

    public void setJsyw4ReasonSelf(String jsyw4ReasonSelf) {
        this.jsyw4ReasonSelf = jsyw4ReasonSelf;
    }


    public Double getJsyw4MarkSelf() {
        return jsyw4MarkSelf;
    }

    public void setJsyw4MarkSelf(Double jsyw4MarkSelf) {
        this.jsyw4MarkSelf = jsyw4MarkSelf;
    }


    public String getZgsx1ReasonSelf() {
        return zgsx1ReasonSelf;
    }

    public void setZgsx1ReasonSelf(String zgsx1ReasonSelf) {
        this.zgsx1ReasonSelf = zgsx1ReasonSelf;
    }


    public Double getZgsx1MarkSelf() {
        return zgsx1MarkSelf;
    }

    public void setZgsx1MarkSelf(Double zgsx1MarkSelf) {
        this.zgsx1MarkSelf = zgsx1MarkSelf;
    }


    public String getZgsx2ReasonSelf() {
        return zgsx2ReasonSelf;
    }

    public void setZgsx2ReasonSelf(String zgsx2ReasonSelf) {
        this.zgsx2ReasonSelf = zgsx2ReasonSelf;
    }


    public Double getZgsx2MarkSelf() {
        return zgsx2MarkSelf;
    }

    public void setZgsx2MarkSelf(Double zgsx2MarkSelf) {
        this.zgsx2MarkSelf = zgsx2MarkSelf;
    }


    public String getZgsx3ReasonSelf() {
        return zgsx3ReasonSelf;
    }

    public void setZgsx3ReasonSelf(String zgsx3ReasonSelf) {
        this.zgsx3ReasonSelf = zgsx3ReasonSelf;
    }


    public Double getZgsx3MarkSelf() {
        return zgsx3MarkSelf;
    }

    public void setZgsx3MarkSelf(Double zgsx3MarkSelf) {
        this.zgsx3MarkSelf = zgsx3MarkSelf;
    }


    public String getZgsx4ReasonSelf() {
        return zgsx4ReasonSelf;
    }

    public void setZgsx4ReasonSelf(String zgsx4ReasonSelf) {
        this.zgsx4ReasonSelf = zgsx4ReasonSelf;
    }


    public Double getZgsx4MarkSelf() {
        return zgsx4MarkSelf;
    }

    public void setZgsx4MarkSelf(Double zgsx4MarkSelf) {
        this.zgsx4MarkSelf = zgsx4MarkSelf;
    }


    public java.sql.Timestamp getMarkTime() {
        return markTime;
    }

    public void setMarkTime(java.sql.Timestamp markTime) {
        this.markTime = markTime;
    }

    public java.sql.Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(java.sql.Timestamp updateTime) {
        this.updateTime = updateTime;
    }


    public String getMarkId() {
        return markId;
    }

    public void setMarkId(String markId) {
        this.markId = markId;
    }


    public Double getMarkMark() {
        return markMark;
    }

    public void setMarkMark(Double markMark) {
        this.markMark = markMark;
    }


    public String getMarkMonth() {
        return markMonth;
    }

    public void setMarkMonth(String markMonth) {
        this.markMonth = markMonth;
    }


    public String getMarkJiDu() {
        return markJiDu;
    }

    public void setMarkJiDu(String markJiDu) {
        this.markJiDu = markJiDu;
    }


    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getMarkReasons() {
        return markReasons;
    }

    public void setMarkReasons(String markReasons) {
        this.markReasons = markReasons;
    }
}
