package com.numberone.web.controller.system;

import com.numberone.common.annotation.Log;
import com.numberone.common.base.AjaxResult;
import com.numberone.common.enums.BusinessType;
import com.numberone.common.utils.ServletUtils;
import com.numberone.common.utils.bean.BeanUtils;
import com.numberone.system.domain.SysDyzegMark;
import com.numberone.system.domain.SysMark;
import com.numberone.system.domain.SysRole;
import com.numberone.system.domain.SysUser;
import com.numberone.system.mapper.SysdyzegMarkSelfMapper;
import com.numberone.system.service.ISysConfigService;
import com.numberone.system.service.ISysdyzegMarkService;
import com.numberone.system.service.impl.SysMarkSelfServiceImpl;
import com.numberone.system.service.impl.SysdyzegMarkLeaderServiceImpl;
import com.numberone.system.service.impl.SysdyzegMarkSelfServiceImpl;
import com.numberone.web.controller.system.base.BaseSysMarkController;
import com.numberone.web.controller.system.base.BaseSysdyzegMarkController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("system/dyzrgMark/markSelf")
public class SysdyzegMarkSelfController extends BaseSysdyzegMarkController {
    private String selfPrefix = "system/dyzrgMark/markSelf";
    @Autowired
    SysdyzegMarkSelfServiceImpl service;
    @Autowired
    SysdyzegMarkLeaderServiceImpl leaderService;
    @Autowired
    private ISysConfigService configService;

    @GetMapping()
    public String mark() {
        return selfPrefix + "/markSelf";
    }

    @PostMapping("/list")
    @ResponseBody
    public Object getList(SysDyzegMark m) {
        return getZRGList(m, service);
    }

    @GetMapping("/add")
    public String add() {
        return selfPrefix + "/markSelfadd";
    }

    @Log(title = "党员责任岗自评-新增自评", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult add(SysDyzegMark m) {
        String isOpen = configService.selectConfigByKey("sys.zrgMarkSelf.open");
        if ("0".equals(isOpen)) {
            return error("管理员未开启本月自评权限！");
        }
        if (isOpen == null) {
            return error("请检查sys.zrgMarkSelf.open配置项！");
        }
        //判断是否是责任岗人员开始
        SysUser user = getSysUser();
        List<SysRole> roles = user.getRoles();
        if (roles == null || roles.size() == 0) {
            return error("非党员先锋岗角色！请联系管理员分配角色");
        }
        int isZRGFlag = 0;
        for (SysRole role : roles) {
            if ("dyzrg".equals(role.getRoleKey())) {
                isZRGFlag = 1;
            }
        }
        if (isZRGFlag == 0) {
            return error("非党员先锋岗角色！请联系管理员分配角色");
        }

//        String s=new String();
//        s.substring(0,8);
        //判断是否是责任岗人员结束
        return addZRG(m, service);
    }

    /**
     * 查看自评详细
     */
    @GetMapping("/detail/{markId}")
    public String edit(@PathVariable("markId") String markId, ModelMap mmap) {
        getMarkZRGPOJO(markId, service, mmap);
        return "system/dyzrgMark/markDetail";
    }

    @Log(title = "责任岗评分导出-个人导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysDyzegMark m) {
        return error("权限不足，请联系管理员");
    }

    @Log(title = "责任岗评分自评-删除", businessType = BusinessType.EXPORT)
    @PostMapping("removeReal/{markId}")
    @ResponseBody
    public AjaxResult edit(@PathVariable("markId") String markId){
        if(markId==null||markId.length()==0){
            return error("请传入正确的markId");
        }
        SysDyzegMark s=new SysDyzegMark();
        s.setMarkId(markId);
        List<SysDyzegMark> res= BeanUtils.cleanNull(leaderService.selectObject(s));
        if(res!=null&&res.size()!=0){
            return error("小组已在此基础上评分，不可删除！");
        }
        int re=service.delete(markId);
        if(re==0){return error("删除失败");}
        return success("删除成功");
    }

    @GetMapping("/edit/{markId}")
    public String update(@PathVariable("markId") String markId, ModelMap mmap) {
        getMarkZRGPOJO(markId, service, mmap);
        return "system/dyzrgMark/markSelf/markUpdate";
    }

    @Log(title = "责任岗评分-个人修改", businessType = BusinessType.UPDATE)
    @PostMapping("/editReal")
    @ResponseBody
    public AjaxResult update(SysDyzegMark s) {
        return update(s, leaderService, service, 0);
    }
}
