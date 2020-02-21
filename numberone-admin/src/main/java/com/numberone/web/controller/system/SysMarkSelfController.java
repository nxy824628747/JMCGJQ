package com.numberone.web.controller.system;


import com.numberone.common.annotation.Log;
import com.numberone.common.base.AjaxResult;
import com.numberone.common.enums.BusinessType;
import com.numberone.common.page.TableDataInfo;
import com.numberone.common.utils.ServletUtils;
import com.numberone.common.utils.bean.BeanUtils;
import com.numberone.system.domain.SysMark;
import com.numberone.system.domain.SysRole;
import com.numberone.system.domain.SysUser;
import com.numberone.system.service.ISysConfigService;
import com.numberone.system.service.impl.SysMarkLeaderServiceImpl;
import com.numberone.system.service.impl.SysMarkSelfServiceImpl;
import com.numberone.web.controller.system.base.BaseSysMarkController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("system/mark/markSelf")
public class SysMarkSelfController extends BaseSysMarkController {
    private String prefix = "system/mark/markSelf";

    @Autowired
    SysMarkSelfServiceImpl service;
    @Autowired
    SysMarkLeaderServiceImpl leaderService;
    @Autowired
    private ISysConfigService configService;

    @GetMapping()
    public String mark() {
        return prefix + "/markSelf";
    }

    @PostMapping("list")
    @ResponseBody
    public Object getList(SysMark m) {
        return getList(m, service);
    }

    @GetMapping("/add")
    public String add() {
        return prefix + "/markSelfadd";
    }

    @Log(title = "党员责任区评分-新增自评", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult add(SysMark m) {
        String isOpen = configService.selectConfigByKey("sys.markSelf.open");
        if ("0".equals(isOpen)) {
            return error("管理员未开启本月自评权限！");
        }
        if (isOpen == null) {
            return error("请检查sys.zrgMarkSelf.open配置项！");
        }
        SysUser user = getSysUser();
        List<SysRole> roles = user.getRoles();
        if (roles == null || roles.size() == 0) {
            return error("非党员责任区角色！请联系管理员分配角色");
        }
        int isZRGFlag = 0;
        for (SysRole role : roles) {
            if ("dyzrq".equals(role.getRoleKey())) {
                isZRGFlag = 1;
            }
        }
        if (isZRGFlag == 0) {
            return error("非党员责任区角色！请联系管理员分配角色");
        }
        //Spring的自动装箱总是装不进去，手动获取
//        String markTimeStr= ServletUtils.getParameter("markTime");
//        if(markTimeStr!=null){
//            String markTimeStr2=markTimeStr.substring(0,10)+markTimeStr.substring(12,8);
//            m.setMarkTime(Timestamp.valueOf(markTimeStr2));
//        }
        return add(m, service);
    }

    /**
     * 查看自评详细
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
        return "system/mark/markSelf/markUpdate";
    }

    @Log(title = "责任区评分-个人修改", businessType = BusinessType.UPDATE)
    @PostMapping("/editReal")
    @ResponseBody
    public AjaxResult update(SysMark s) {
        return update(s, leaderService, service, 0);
    }

    @Log(title = "责任区评分-个人导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysMark m) {
        return error("权限不足，请联系管理员");
    }

    @Log(title = "责任区评分自评-删除", businessType = BusinessType.EXPORT)
    @PostMapping("removeReal/{markId}")
    @ResponseBody
    public AjaxResult edit(@PathVariable("markId") String markId) {
        if (markId == null || markId.length() == 0) {
            return error("请传入正确的markId");
        }
        SysMark s = new SysMark();
        s.setMarkId(markId);
        List<SysMark> res = BeanUtils.cleanNull(leaderService.selectObject(s));
        if (res != null && res.size() != 0) {
            return error("小组已在此基础上评分，不可删除！");
        }
        int re = service.delete(markId);
        if (re == 0) {
            return error("删除失败");
        }
        return success("删除成功");
    }

}
