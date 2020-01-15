package com.numberone.web.controller.system.base;

import com.numberone.common.base.AjaxResult;
import com.numberone.common.enums.BusinessType;
import com.numberone.common.json.JSONObject;
import com.numberone.common.utils.ServletUtils;
import com.numberone.common.utils.YamlUtil;
import com.numberone.common.utils.bean.BeanUtils;
import com.numberone.framework.web.base.BaseController;
import com.numberone.system.domain.SysDyzegMark;
import com.numberone.system.domain.SysUser;
import com.numberone.system.service.ISysDeptService;
import com.numberone.system.service.ISysdyzegMarkSelfService;
import com.numberone.system.service.ISysdyzegMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * 党员责任岗评分基类
 * 对自评、组评、支部评通用的处理逻辑进行抽取；
 * 抽取的前提是面向抽象编程，即Service层自评、组评、支部评均基于ISysdyzegMarkSelfService接口
 * 控制：
 * 1.部门权限控制
 * 2.用户id、当前时间、年、月、季度等基本信息的处理和写入
 * 3.新增时对自评是否已存在进行判断
 * 4.获取单个责任岗评分对象拼装进modelMap
 */
public class BaseSysdyzegMarkController extends BaseController {
    //注入部门对象
    @Autowired
    ISysDeptService deptService;

    /**
     * 查询自评、小组评、支部评联合查询列表
     *
     * @param m
     * @param service
     * @return
     */
    protected Object getZRGList(SysDyzegMark m, ISysdyzegMarkService service) {
        //查询条件写入对象
        setZRGInsertSelectBaseInfo(m, BusinessType.SELECT);
        //List<SysMark> returnList = service.selectList(m);
        JSONObject mJson = BeanUtils.getJSON(m);
        int deptResult = judgeDept(mJson);
        if (deptResult == 500) {
            return error("不可查看上级机构信息");
        }
        startPage();
        //小组评部门权限判断结束
        List<Map<String, Object>> returnList = service.selectAllList(mJson);
        clearPage();
        return getDataTable(returnList);
    }

    /**
     * 插入操作
     * @param m
     * @param service
     * @return
     */
    protected AjaxResult addZRG(SysDyzegMark m, ISysdyzegMarkService service) {
        setZRGInsertSelectBaseInfo(m, BusinessType.INSERT);
        return service.insert(m);
    }

    /**
     * 为入参POJO拼装基本信息用于查询及插入
     * 基本信息包括：查询：用户唯一标识uid（因为时间信息可能为查询参数，不做统一获取及处理）
     * 插入：用户唯一标识uid、年、月、日、季度
     *
     * @param m
     * @param b
     */
    protected void setZRGInsertSelectBaseInfo(SysDyzegMark m, BusinessType b) {
        Long uid = getUserId();
        if (!(m.getUid() > 0)) {
            m.setUid(uid);
        }
        setZRGBETime(m);
        String timeString;
        Timestamp t=m.getMarkTime();
        /**
         * Users choose their own self-evaluation time, which means that
         * the evaluation time can not be transferred when other types of evaluation are added;
         * by NiuXinYu at 2019/08/27
         */
        if(b==BusinessType.INSERT&&t!=null){
            timeString=t.toString();
        }else {
            timeString = GetNowDate();
        }
        //如果是查询，下述参数为前台传值
        if (b.equals(BusinessType.INSERT)) {
            String markId = YamlUtil.getUUID();
            String month = timeString.substring(5, 7);
            String jiDu = getJiDu(timeString);
            String markYear = timeString.substring(0, 4);
            m.setMarkId(markId);
            m.setMarkTime(Timestamp.valueOf(timeString.substring(0, 18)));
            m.setMarkMonth(month);
            m.setMarkJiDu(jiDu);
            m.setMarkYear(markYear);
        } else if (b.equals(BusinessType.UPDATE)) {
            m.setUpdateTime(Timestamp.valueOf(timeString));
        }
    }

    /**
     * 为入参POJO插入查询开始/结束时间参数
     * 因为插入依赖数据类型，不可用泛型处理
     * 代码复用不够彻底，再想办法优化
     *
     * @param m
     */
    protected void setZRGBETime(SysDyzegMark m) {
        String startTime = ServletUtils.getParameter("params[startTime]");
        String endTime = ServletUtils.getParameter("params[endTime]");
        if (startTime != null && startTime.length() > 0) {
            m.setStartTime(startTime);
        }
        if (endTime != null && endTime.length() > 0) {
            m.setEndTime(endTime);
        }
    }

    /**
     * 根据请求中deptId参数，查看当前操作用户是否可查看该部门信息
     * 小组评部门权限判断开始,若无deptId传入，查询本部门人员评分
     * 效率方面，个人评分不会传入deptId参数开启复杂的if的判断，封装为通用方法
     *
     * @param mJson
     * @return 500:不可查看，200：可查看并将depy_id拼装入入参Json
     */
    private int judgeDept(JSONObject mJson) {
        SysUser user = getSysUser();
        Long userDeptId = user.getDeptId();
        String deptId = ServletUtils.getParameter("deptId");
        if (deptId != null && deptId.length() > 0) {
            if (deptService.selectAncestors(userDeptId).contains(deptId)) {
                //不可查看上级机构信息
                return 500;
            }
            mJson.put("deptId", deptId);
        } else {
            deptId = userDeptId.toString();
            mJson.put("deptId", deptId);
        }
        return 200;
    }

    /**
     * 获取单个责任岗评分对象拼装进modelMap
     *
     * @param markId
     * @param service
     * @return
     */
    protected void getMarkZRGPOJO(String markId, ISysdyzegMarkService service, ModelMap modelMap) {
        SysDyzegMark m = new SysDyzegMark();
        m.setMarkId(markId);
        List<SysDyzegMark> list0=service.selectObject(m);
        List<SysDyzegMark> list = BeanUtils.cleanNull(list0);
        if (list == null || list.size() == 0) {
            //如果结果为空，返回key齐全value空的map防止前台报错
            BeanUtils.getModelMap(m,modelMap);
            return;
        }
        m = list.get(0);
        BeanUtils.getModelMap(m,modelMap);
    }

    public AjaxResult update(SysDyzegMark s,ISysdyzegMarkService fatherService,ISysdyzegMarkService service,int isZeroFlag){
        SysDyzegMark sm=new SysDyzegMark();
        sm.setMarkId(s.getMarkId());
        List<SysDyzegMark> leader=BeanUtils.cleanNull(fatherService.selectObject(sm));
        if(leader!=null&&leader.size()!=0){
            return error("上级已在此基础上评分，不可修改！");
        }
        int re=service.update(s,isZeroFlag);
        if(re==1){return success("修改成功");}
        return error("修改失败");
    }

}
