package com.numberone.web.controller.system;

import com.numberone.common.annotation.Log;
import com.numberone.common.base.AjaxResult;
import com.numberone.common.enums.BusinessType;
import com.numberone.common.page.TableDataInfo;
import com.numberone.common.utils.ServletUtils;
import com.numberone.common.utils.YamlUtil;
import com.numberone.common.utils.poi.ExcelUtil;
import com.numberone.framework.web.base.BaseController;
import com.numberone.system.domain.SysUserMarkLeader;
import com.numberone.system.domain.SysUserMarkSelf;
import com.numberone.system.service.ISysDeptService;
import com.numberone.system.service.ISysUserMarkLeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("system/user/markLeader")
public class SysUserMarkLeaderController extends BaseController {

    @Autowired
    ISysUserMarkLeaderService markLeaderService;
    @Autowired
    ISysDeptService deptService;

    private String prefix = "/system/user/markLeader";

    @GetMapping()
    public String mark() {
        return prefix + "/markLeader";
    }

    @GetMapping("/add/{markId}")
    public String add(@PathVariable("markId") String markId, ModelMap map) {
        map.put("markId", markId);
        return prefix + "/markLeaderadd";
    }

    @GetMapping("/edit/{mid}")
    public String update(@PathVariable("mid") String mid, ModelMap map) {
        map.put("mid", mid);
        return prefix + "/markLeaderedit";
    }

    @Log(title = "小组月评-查询", businessType = BusinessType.OTHER)
    @PostMapping("/list")
    @ResponseBody
    public Object list() {
        Long userDeptId = getSysUser().getDeptId();
        List<SysUserMarkLeader> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<String, Object>();
        String deptId = ServletUtils.getParameter("deptId");
        String beginTime = ServletUtils.getParameter("params[beginTime]");
        String loginName = ServletUtils.getParameter("loginName");
        String phonenumber = ServletUtils.getParameter("phonenumber");
        String endTime = ServletUtils.getParameter("params[endTime]");
        //0:正常，1：停用
        String status = ServletUtils.getParameter("status");

        map.put("status", status);
        map.put("beginTime", beginTime);
        map.put("loginName", loginName);
        map.put("phonenumber", phonenumber);
        map.put("endTime", endTime);

        if (deptId != null && deptId.length() > 0) {
            if (deptService.selectAncestors(userDeptId).contains(deptId)) {
                return error("不可查看上级机构信息");
            }
            startPage();
            map.put("deptId", Long.parseLong(deptId));
            list = markLeaderService.selectByDeptId(map);
            clearPage();
        } else {
            startPage();
            map.put("deptId", userDeptId);
            list = markLeaderService.selectByDeptId(map);
            clearPage();
        }

        return getDataTable(list);
    }

    @Log(title = "小组月评-新增", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult add(SysUserMarkLeader markLeader) {
        if (markLeader.getMarkLeader() > 100 || markLeader.getMarkLeader() < 0) {
            return error("请填写0-100之间的分值！");
        }
        markLeader.setMarkLeaderId(YamlUtil.getUUID());
        markLeader.setDeptId(getSysUser().getDeptId());
        String month = GetNowDate().substring(5,7);
        int result = markLeaderService.insert(markLeader,month);
        if (result == 999) {
            return error("已进行过小组评分，不可重复评分。请在原评分基础上修改。");
        } else if (result == 0) {
            return error("评分失败，请联系管理员");
        } else if (result == 998) {
            return error("个人未自评，不可进行小组评分");
        } else if(result==997){
            return error("该条数据已过评分时间");
        } else {
            return success("评分提交成功");
        }
    }

    @Log(title = "小组月评-修改", businessType = BusinessType.INSERT)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult update(SysUserMarkLeader markLeader) {
        int resultNum = markLeaderService.update(markLeader);
        if (resultNum == 1) {
            return success("修改成功");
        } else if(resultNum==999){
            return error("本月未进行小组评，请先新增本月小组评分");
        }else{
            return error("系统异常，请查看系统日志");
        }
    }

    @Log(title = "自评管理-导出excel", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export() {
        Long userDeptId = getSysUser().getDeptId();
        List<SysUserMarkLeader> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<String, Object>();
        String deptId = ServletUtils.getParameter("deptId");
        String beginTime = ServletUtils.getParameter("params[beginTime]");
        String loginName = ServletUtils.getParameter("loginName");
        String phonenumber = ServletUtils.getParameter("phonenumber");
        String endTime = ServletUtils.getParameter("params[endTime]");
        //0:正常，1：停用
        String status = ServletUtils.getParameter("status");

        map.put("status", status);
        map.put("beginTime", beginTime);
        map.put("loginName", loginName);
        map.put("phonenumber", phonenumber);
        map.put("endTime", endTime);

        if (deptId != null && deptId.length() > 0) {
            if (deptService.selectAncestors(userDeptId).contains(deptId)) {
                return error("不可查看上级机构信息");
            }
            map.put("deptId", Long.parseLong(deptId));
            list = markLeaderService.selectByDeptId(map);
        } else {
            map.put("deptId", userDeptId);
            list = markLeaderService.selectByDeptId(map);
        }

        ExcelUtil<SysUserMarkLeader> util = new ExcelUtil<SysUserMarkLeader>(SysUserMarkLeader.class);
        return util.exportExcel(list, "小组评数据");
    }

}
