package com.numberone.web.controller.system.base;

import com.numberone.common.base.AjaxResult;
import com.numberone.common.enums.BusinessType;
import com.numberone.common.json.JSONObject;
import com.numberone.common.page.TableDataInfo;
import com.numberone.common.utils.ServletUtils;
import com.numberone.common.utils.YamlUtil;
import com.numberone.common.utils.bean.BeanUtils;
import com.numberone.common.utils.bean.MarkBeanUtils;
import com.numberone.framework.web.base.BaseController;
import com.numberone.system.domain.SysDyzegMark;
import com.numberone.system.domain.SysMark;
import com.numberone.system.domain.SysUser;
import com.numberone.system.service.ISysDeptService;
import com.numberone.system.service.ISysMarkService;
import com.numberone.system.service.ISysdyzegMarkSelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
public class BaseSysMarkController extends BaseController {
    @Autowired
    ISysDeptService deptService;

    /**
     * 获取单个评分对象拼装进modelMap
     *
     * @param markId
     * @param service
     * @return
     */
    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    protected void getMarkPOJO(String markId, ISysMarkService service, ModelMap modelMap )
            throws IllegalAccessException, NoSuchFieldException {
        SysMark m = new SysMark();
        m.setMarkId(markId);
        List<SysMark> list0 = service.selectObject(m);
        List<SysMark> list = BeanUtils.cleanNull(list0);
        if (list == null || list.size() == 0) {
            //如果结果为空，返回key齐全value空的map防止前台报错
            BeanUtils.getModelMap(m, modelMap);
            return;
        }
        m = list.get(0);
        MarkBeanUtils.setMarkReasons(m);
        BeanUtils.getModelMap(m, modelMap);
    }


    /**
     * 查询本人自评列表
     *
     * @param m
     * @param service
     * @return
     */
    protected Object getList(SysMark m, ISysMarkService service) {
        //查询条件写入对象
        setInsertSelectBaseInfo(m, BusinessType.SELECT);
        //List<SysMark> returnList = service.selectList(m);
        JSONObject mJson = BeanUtils.getJSON(m);
        //小组评部门权限判断开始,若无deptId传入，查询本部门人员评分
        //效率方面，个人评分不会传入deptId参数开启复杂的if的判断，封装为通用方法
        SysUser user = getSysUser();
        Long userDeptId = user.getDeptId();
        String deptId = ServletUtils.getParameter("deptId");
        if (deptId != null && deptId.length() > 0) {
            if (deptService.selectAncestors(userDeptId).contains(deptId)) {
                return error("不可查看上级机构信息");
            }
            mJson.put("deptId", deptId);
        } else {
            deptId = userDeptId.toString();
            mJson.put("deptId", deptId);
        }
        startPage();
        //小组评部门权限判断结束
        List<Map<String, Object>> returnList = service.selectAllList(mJson);
        clearPage();
        return getDataTable(returnList);
    }


    /**
     * 新增自评记录
     * 处理逻辑：
     * 判断是否本月已自评
     *
     * @param m
     * @param service
     * @return
     */
    protected AjaxResult add(SysMark m, ISysMarkService service) {
        setInsertSelectBaseInfo(m, BusinessType.INSERT);
        int i = service.insert(m);
        if (i == 1) {
            return success("新增成功");
        }
        if (i == 999) {
            return error("本月已评分，不可新增");
        }
        if (i == 998) {
            return error("本月还未自评，不可小组评分");
        }
        return error("系统错误，请联系管理员");
    }


    /**
     * 将查询的开始结束时间拼装进POJO
     *
     * @param m
     */
    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    protected void setBETime(SysMark m) {
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
     * 处理前端传入的SysMarkSelf对象，补充基本数据
     * 必填项补充
     * uid,
     * mark_id,
     * mark_mark 未补充,
     * mark_month,
     * mark_time,
     * mark_JiDu
     *
     * @param m
     * @return
     */
    protected void setInsertSelectBaseInfo(SysMark m, BusinessType b) {
        Long uid = getUserId();
        if (!(m.getUid() > 0)) {
            m.setUid(uid);
        }
        /**
         * when the requestion is for selection,set start and end time by this one
         */
        setBETime(m);
        /**
         * Users choose their own self-evaluation time, which means that
         * the evaluation time can not be transferred when other types of evaluation are added;
         * by NiuXinYu at 2019/08/27
         */
        String timeString;
        Timestamp t = m.getMarkTime();
        if (t != null) {
            timeString = t.toString();
        } else {
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
     * 修改评分
     *
     * @param s
     * @param fatherService
     * @param service
     * @return
     */
    public AjaxResult update(SysMark s, ISysMarkService fatherService, ISysMarkService service, int isZeroFlag) {
        SysMark sm = new SysMark();
        sm.setMarkId(s.getMarkId());
        List<SysMark> leader = BeanUtils.cleanNull(fatherService.selectObject(sm));
        if (leader != null && leader.size() != 0) {
            return error("上级已在此基础上评分，不可修改！");
        }
        int re = service.update(s, isZeroFlag);
        if (re == 1) {
            return success("修改成功");
        }
        return error("修改失败");
    }

}
