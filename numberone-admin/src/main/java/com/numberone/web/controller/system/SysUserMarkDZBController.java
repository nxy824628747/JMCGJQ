package com.numberone.web.controller.system;

import com.numberone.common.annotation.Log;
import com.numberone.common.base.AjaxResult;
import com.numberone.common.enums.BusinessType;
import com.numberone.common.json.JSONObject;
import com.numberone.common.page.TableDataInfo;
import com.numberone.common.utils.ServletUtils;
import com.numberone.common.utils.YamlUtil;
import com.numberone.common.utils.bean.BeanUtils;
import com.numberone.framework.web.base.BaseController;
import com.numberone.system.domain.SysUserMarkDzb;
import com.numberone.system.domain.SysUserMarkLeader;
import com.numberone.system.service.ISysDeptService;
import com.numberone.system.service.ISysUserMarkDZBService;
import com.numberone.system.service.ISysUserMarkLeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("system/user/markDZB")
public class SysUserMarkDZBController extends BaseController {
    @Autowired
    ISysUserMarkDZBService markDZBService;
    @Autowired
    ISysUserMarkLeaderService markLeaderService;
    @Autowired
    ISysDeptService deptService;

    private String prefix = "/system/user/markDZB";

    @GetMapping()
    public String mark() {
        return prefix + "/markDZB";
    }

    @Log(title = "党支部月评-查询", businessType = BusinessType.OTHER)
    @PostMapping("/list")
    @ResponseBody
    public Object list() {
        clearPage();
        //拿到小组评信息
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
        List<JSONObject> resultList = new ArrayList<>();
        TableDataInfo table = null;
        //拿到所有党支部评信息
        List<SysUserMarkDzb> listDZB = markDZBService.select(new HashMap());
        if (listDZB != null && listDZB.size() > 0) {
            //处理修改过党支部评的信息
            for (SysUserMarkDzb d : listDZB) {
                if (d.getChangeDzbTimes() != 0) {
                    d.setMarkDzb(d.getMarkDzbChange());
                    d.setMarkDzbTime(d.getChangeDzbTime());
                    d.setMarkDzbReason(d.getChangeDzbReason());
                }
            }
        }
        if (deptId != null && deptId.length() > 0) {
            if (deptService.selectAncestors(userDeptId).contains(deptId)) {
                return error("不可查看上级机构信息");
            }
            map.put("deptId", Long.parseLong(deptId));
            startPage();
            list = markLeaderService.selectByDeptId(map);
            table = getDataTable(list);
            //党支部评信息拼装到人员信息
            resultList = BeanUtils.combinPOJOList(list, "markLeaderId", listDZB, "mlid");
            table.setRows(resultList);
            clearPage();
        } else {
            map.put("deptId", userDeptId);
            startPage();
            list = markLeaderService.selectByDeptId(map);
            table = getDataTable(list);
            //党支部评信息拼装到人员信息
            resultList = BeanUtils.combinPOJOList(list, "markLeaderId", listDZB, "mlid");
            table.setRows(resultList);
            clearPage();
        }
        //拿到小组评信息结束
        return table;
    }

    @GetMapping("/add/{mlid}")
    public String add(@PathVariable("mlid") String mlid, ModelMap map) {
        map.put("mlid", mlid);
        return prefix + "/markDzbadd";
    }

    @Log(title = "党支部月评-新增", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult add(SysUserMarkDzb dzb) {
        double markDzb = dzb.getMarkDzb();
        if (markDzb > 100 || markDzb < 0) {
            return error("请填写0-100之间的分值！");
        }
        dzb.setMarkDzbId(YamlUtil.getUUID());
        String month = GetNowDate().substring(5, 7);
        int result = markDZBService.insert(dzb, month);
        if (result == 998) {
            return error("未进行小组评分，不可进行党支部评分");
        } else if (result == 997) {
            return error("该条记录已过党支部评时间");
        } else if (result == 999) {
            return error("已进行党支部评分，不可重复评分");
        } else if (result == 1) {
            return success("党支部评分成功");
        } else {
            return error("系统错误，请查看日志");
        }
    }

    @GetMapping("/edit/{markLeaderId}")
    public String update(@PathVariable("markLeaderId") String markLeaderId, ModelMap map) {
        map.put("markLeaderId", markLeaderId);
        return prefix + "/markDzbedit";
    }


    @Log(title = "党支部月评-修改", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult update(SysUserMarkDzb dzb) {
        double markDzb = dzb.getMarkDzb();
        if (markDzb > 100 || markDzb < 0) {
            return error("请填写0-100之间的分值！");
        }
        String month = GetNowDate().substring(5, 7);
        int result = markDZBService.update(dzb, month);
        if (result == 997) {
            return error("该条数据已过评分时间");
        }
        if (result == 998) {
            return error("还未进行小组评分");
        }
        if (result == 999) {
            return error("还未进行党支部评分");
        }
        return success("修改成功");
    }

}
