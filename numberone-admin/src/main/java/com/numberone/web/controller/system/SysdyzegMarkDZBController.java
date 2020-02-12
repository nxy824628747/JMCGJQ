package com.numberone.web.controller.system;

import com.numberone.common.annotation.Log;
import com.numberone.common.base.AjaxResult;
import com.numberone.common.enums.BusinessType;
import com.numberone.common.utils.ServletUtils;
import com.numberone.common.utils.bean.BeanUtils;
import com.numberone.common.utils.poi.ExcelUtil;
import com.numberone.system.domain.*;
import com.numberone.system.service.ISysMarkService;
import com.numberone.system.service.ISysdyzegMarkService;
import com.numberone.system.service.impl.SysMarkDZBServiceImpl;
import com.numberone.system.service.impl.SysdyzegMarkDZBServiceImpl;
import com.numberone.system.service.impl.SysdyzegMarkLeaderServiceImpl;
import com.numberone.web.controller.system.base.BaseSysMarkController;
import com.numberone.web.controller.system.base.BaseSysdyzegMarkController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("system/dyzrgMark/markDZB")
public class SysdyzegMarkDZBController extends BaseSysdyzegMarkController {
    private String prefix = "system/dyzrgMark/markDZB";

    @Autowired
    SysdyzegMarkLeaderServiceImpl leaderService;

    @Autowired
    SysdyzegMarkDZBServiceImpl service;

    @GetMapping()
    public String mark() {
        return prefix + "/markDZB";
    }

    @PostMapping("list")
    @ResponseBody
    public Object getList(SysDyzegMark m) {
        return getZRGList(m, service);
    }

    @GetMapping("/add/{markId}")
    public String add(@PathVariable("markId") String markId, ModelMap map) {
        map.put("markId", markId);
        return prefix + "/markDZBadd";
    }

    @Log(title = "责任岗评分-党支新增", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult add(SysDyzegMark m) {
        return addLeader(m, service);
    }


    /**
     * 查看党支部评详细
     */
    @GetMapping("/detail/{markId}")
    public String edit(@PathVariable("markId") String markId, ModelMap mmap) {
        getMarkZRGPOJO(markId, service, mmap);
        return "system/dyzrgMark/markDetail";
    }

    /**
     * 新增党支部评
     *
     * @param m
     * @param service
     * @return
     */
    private AjaxResult addLeader(SysDyzegMark m, ISysdyzegMarkService service) {
        String markId = m.getMarkId();
        setZRGInsertSelectBaseInfo(m, BusinessType.INSERT);
        m.setMarkId(markId);
        return service.insert(m);
    }

    @Log(title = "责任岗评分-党支部导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysDyzegMark m) {
        SysUser curUser = getSysUser();
        Long uid = curUser.getUserId();
        m.setUid(uid);
        if (m.getDeptId() == null || m.getDeptId().length() == 0) {
            Long deptId = curUser.getDeptId();
            m.setDeptId(String.valueOf(deptId));
        }
        List<SysMarkImport> list = service.selectImpotList(m);
        ExcelUtil<SysMarkImport> util = new ExcelUtil<SysMarkImport>(SysMarkImport.class);
        return util.exportExcel(list, "责任岗评分统计");
    }

    @Log(title = "责任岗评分-党支部删除", businessType = BusinessType.EXPORT)
    @PostMapping("removeReal/{markId}")
    @ResponseBody
    public AjaxResult edit(@PathVariable("markId") String markId){
        if(markId==null||markId.length()==0){
            return error("请传入正确的markId");
        }
        int re=service.delete(markId);
        if(re==0){return error("还未进行党支部评分，无法删除！");}
        return success("删除成功");
    }

    @GetMapping("/edit/{markId}")
    public String update(@PathVariable("markId") String markId, ModelMap mmap) {
        getMarkZRGPOJO(markId, service, mmap);
        return "system/dyzrgMark/markDZB/markDZBUpdate";
    }

    @Log(title = "责任岗评分-党支部修改", businessType = BusinessType.UPDATE)
    @PostMapping("/editReal")
    @ResponseBody
    public AjaxResult update(SysDyzegMark s){
        SysDyzegMark sm=new SysDyzegMark();
        s.setMarkId(s.getMarkId());
        List<SysDyzegMark> res= BeanUtils.cleanNull(leaderService.selectObject(s));
        double selfMark=res.get(0).getMarkMark();
        int flag=0;
        if(selfMark==0.0){flag=1;}
        int res1=service.update(s,flag);
        if(res1==1){return success("修改成功");}
        return error("修改失败");
    }


    @GetMapping("/statistic")
    public String statistic() {
        return "system/mark/dyzrgMarkStatistic";
    }

    @PostMapping("statistic/list")
    @ResponseBody
    @Log(title = "责任岗评分统计", businessType = BusinessType.OTHER)
    public Object statisticList() {
        String startTime = ServletUtils.getParameter("params[startTime]");
        String endTime = ServletUtils.getParameter("params[endTime]");
        String deptId = ServletUtils.getParameter("deptId");
        try {
            startPage();
            List<Map<String, Object>> result = service.stat(startTime, endTime, deptId);
            if (result == null || result.size() == 0) {
                return error("所选部门及时间段内无评分记录");
            }
            clearPage();
            return getDataTable(result);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostMapping("statistic/export")
    @ResponseBody
    @Log(title = "责任岗评分统计导出", businessType = BusinessType.OTHER)
    public AjaxResult exportStatistic() {
        String startTime = ServletUtils.getParameter("params[startTime]");
        String endTime = ServletUtils.getParameter("params[endTime]");
        String deptId = ServletUtils.getParameter("deptId");
        try {
            List<Map<String, Object>> result = service.stat(startTime, endTime, deptId);
            ExcelUtil<SysMarkStatistic> util = new ExcelUtil<SysMarkStatistic>(SysMarkStatistic.class);
            return util.exportExcelFromMap(result, "(" + startTime + "至" + endTime + ")" + "先锋岗评分统计", SysMarkStatistic.class);
        } catch (Exception e) {
            e.printStackTrace();
            return error("导出失败");
        }
    }
}
