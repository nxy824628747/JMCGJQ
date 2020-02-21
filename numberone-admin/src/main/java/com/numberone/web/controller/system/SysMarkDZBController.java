package com.numberone.web.controller.system;

import com.numberone.common.annotation.Log;
import com.numberone.common.base.AjaxResult;
import com.numberone.common.enums.BusinessType;
import com.numberone.common.page.TableDataInfo;
import com.numberone.common.utils.ServletUtils;
import com.numberone.common.utils.bean.BeanUtils;
import com.numberone.common.utils.poi.ExcelUtil;
import com.numberone.system.domain.SysMark;
import com.numberone.system.domain.SysMarkImport;
import com.numberone.system.domain.SysMarkStatistic;
import com.numberone.system.domain.SysUser;
import com.numberone.system.mapper.SysMarkLeaderMapper;
import com.numberone.system.service.ISysMarkService;
import com.numberone.system.service.impl.SysMarkDZBServiceImpl;
import com.numberone.system.service.impl.SysMarkLeaderServiceImpl;
import com.numberone.web.controller.system.base.BaseSysMarkController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("system/mark/markDZB")
public class SysMarkDZBController extends BaseSysMarkController {
    private String prefix = "system/mark/markDZB";

    @Autowired
    SysMarkDZBServiceImpl service;

    @Autowired
    SysMarkLeaderServiceImpl leaderService;

    @GetMapping()
    public String mark() {
        return prefix + "/markDZB";
    }

    @GetMapping("/statistic")
    public String statistic() {
        return "system/mark/markStatistic";
    }

    @PostMapping("statistic/list")
    @ResponseBody
    @Log(title = "责任区评分统计", businessType = BusinessType.OTHER)
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
    @Log(title = "责任区评分统计导出", businessType = BusinessType.OTHER)
    public AjaxResult exportStatistic() {
        String startTime = ServletUtils.getParameter("params[startTime]");
        String endTime = ServletUtils.getParameter("params[endTime]");
        String deptId = ServletUtils.getParameter("deptId");
        try {
            List<Map<String, Object>> result = service.stat(startTime, endTime, deptId);
            ExcelUtil<SysMarkStatistic> util = new ExcelUtil<SysMarkStatistic>(SysMarkStatistic.class);
            return util.exportExcelFromMap(result, "(" + startTime + "至" + endTime + ")" + "责任区评分统计", SysMarkStatistic.class);
        } catch (Exception e) {
            e.printStackTrace();
            return error("导出失败");
        }
    }

    @PostMapping("list")
    @ResponseBody
    public Object getList(SysMark m) {
        return getList(m, service);
    }

    @GetMapping("/add/{markId}")
    public String add(@PathVariable("markId") String markId, ModelMap map) {
        map.put("markId", markId);
        return prefix + "/markDZBadd";
    }

    @Log(title = "党支部评管理二版-新增", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult add(SysMark m) {
        return addLeader(m, service);
    }


    /**
     * 查看党支部评详细
     */
    @GetMapping("/detail/{markId}")
    public String edit(@PathVariable("markId") String markId, ModelMap mmap) {
        try {
            getMarkPOJO(markId, service, mmap);
        } catch (IllegalAccessException ie) {
            ie.printStackTrace();
            return "error/500";
        } catch (NoSuchFieldException ne) {
            ne.printStackTrace();
            return "error/500";
        }
        return "system/mark/markDetail";
    }

    /**
     * 新增党支部评
     *
     * @param m
     * @param service
     * @return
     */
    private AjaxResult addLeader(SysMark m, ISysMarkService service) {
        String markId = m.getMarkId();
        setInsertSelectBaseInfo(m, BusinessType.INSERT);
        m.setMarkId(markId);
        int i = service.insert(m);
        if (i == 1) {
            return success("新增成功");
        }
        if (i == 998) {
            return error("本月还未自评，不可党支部评分");
        }
        if (i == 997) {
            return error("本月还未小组评分，不可党支部评分");
        }
        if (i == 999) {
            return error("本月已评分，不可新增");
        }
        return error("系统错误，请联系管理员");
    }

    @Log(title = "责任区评分导出-党支部导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysMark m) {
        SysUser curUser = getSysUser();
        Long uid = curUser.getUserId();
        m.setUid(uid);
        if (m.getDeptId() == null || m.getDeptId().length() == 0) {
            Long deptId = curUser.getDeptId();
            m.setDeptId(String.valueOf(deptId));
        }
        List<SysMarkImport> list = leaderService.selectImpotList(m);
        ExcelUtil<SysMarkImport> util = new ExcelUtil<SysMarkImport>(SysMarkImport.class);
        return util.exportExcel(list, "责任区评分统计");
    }

    @GetMapping("/edit/{markId}")
    public String update(@PathVariable("markId") String markId, ModelMap mmap) {
        try {
            getMarkPOJO(markId, service, mmap);
        } catch (IllegalAccessException ie) {
            ie.printStackTrace();
            return "error/500";
        } catch (NoSuchFieldException ne) {
            ne.printStackTrace();
            return "error/500";
        }
        return "system/mark/markDZB/markDZBUpdate";
    }

    @Log(title = "责任区评分导出-党支部修改", businessType = BusinessType.UPDATE)
    @PostMapping("/editReal")
    @ResponseBody
    public AjaxResult update(SysMark s) {
        SysMark sm = new SysMark();
        s.setMarkId(s.getMarkId());
        List<SysMark> res = BeanUtils.cleanNull(leaderService.selectObject(s));
        double selfMark = res.get(0).getMarkMark();
        int flag = 0;
        if (selfMark == 0.0) {
            flag = 1;
        }
        int res1 = service.update(s, flag);
        if (res1 == 1) {
            return success("修改成功");
        }
        return error("修改失败");
    }


    @Log(title = "责任区评分党支部评-删除", businessType = BusinessType.EXPORT)
    @PostMapping("removeReal/{markId}")
    @ResponseBody
    public AjaxResult edit(@PathVariable("markId") String markId) {
        if (markId == null || markId.length() == 0) {
            return error("请传入正确的markId");
        }
        int re = service.delete(markId);
        if (re == 0) {
            return error("还未进行党支部评分，无法删除！");
        }
        return success("删除成功");
    }
}
