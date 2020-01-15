package com.numberone.web.controller.system;

import com.numberone.common.annotation.Log;
import com.numberone.common.base.AjaxResult;
import com.numberone.common.enums.BusinessType;
import com.numberone.common.page.TableDataInfo;
import com.numberone.common.utils.YamlUtil;
import com.numberone.common.utils.poi.ExcelUtil;
import com.numberone.framework.util.ShiroUtils;
import com.numberone.framework.web.base.BaseController;
import com.numberone.system.domain.SysRole;
import com.numberone.system.domain.SysUserMarkSelf;
import com.numberone.system.service.ISysUserMarkSelfService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/system/user/mark")
public class SysUserMarkSelfController extends BaseController {
    private String prefix = "system/user/mark";
    @Autowired
    private ISysUserMarkSelfService iSysUserMarkSelfService;

    @RequiresPermissions("system:mark:view")
    @GetMapping()
    public String mark() {
        return prefix + "/markSelf";
    }

    @RequiresPermissions("system:mark:list")
    @PostMapping("getAllByUid")
    @ResponseBody
    public TableDataInfo getAllByUid() {
        startPage();
        List<SysUserMarkSelf> returnList = iSysUserMarkSelfService.selectAll(getSysUser());
        clearPage();
        return getDataTable(returnList);
    }

    @Log(title = "自评管理-导出excel", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export() {
        List<SysUserMarkSelf> list = iSysUserMarkSelfService.selectAll(getSysUser());
        ExcelUtil<SysUserMarkSelf> util = new ExcelUtil<SysUserMarkSelf>(SysUserMarkSelf.class);
        return util.exportExcel(list, "自评数据");
    }

    /**
     * 新增自评
     */
    @GetMapping("/add")
    public String add() {
        return prefix + "/markSelfadd";
    }

    @RequiresPermissions("system:mark:add")
    @Log(title = "自评管理-新增自评", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult addSave(SysUserMarkSelf markSelf) {
        double mark=markSelf.getMarkSelf();
        if(mark>100||mark<0){
            return error("请填写0-100之间的合法分值！");
        }
        String date=GetNowDate().substring(8,10);
        if(Integer.valueOf(date)>15){
            return error("自评时间为每月1-15日");
        }
        //获取操作者信息
        markSelf.setUid(ShiroUtils.getUserId());
        //清空权限缓存
        ShiroUtils.clearCachedAuthorizationInfo();
        //插入操作
        markSelf.setMarkId(YamlUtil.getUUID());
        int rows = iSysUserMarkSelfService.insert(markSelf);
        //插入行数大于0且不为999（重复自评）
        if (rows > 0 && rows != 999) {
            return success("添加成功");
        } else if(rows==999) {
            return error("本月已自评，不可重复提交");
        }else{
            return error("插入失败");
        }
    }

    /**
     * 修改自评
     */
    @GetMapping("/edit/{markId}")
    public String edit(@PathVariable("markId") String markId, ModelMap mmap) {
        mmap.put("markId", markId);
        return prefix + "/markSelfedit";
    }

    //    @RequiresPermissions("system:mark:add")
    @Log(title = "自评管理-修改自评", businessType = BusinessType.UPDATE)
    @PostMapping("/markSelfedit")
    @Transactional(rollbackFor = Exception.class)
    @ResponseBody
    public AjaxResult editSave(SysUserMarkSelf markSelf) {
        String date=GetNowDate().substring(5,7);
        if(Integer.valueOf(date)>15){
            return error("自评时间为每月1-15日");
        }
        double mark=markSelf.getMarkChange();
        if(mark>100||mark<0){
            return error("请填写0-100之间的合法分值！");
        }
        String markId = markSelf.getMarkId();
        markSelf.setUid(ShiroUtils.getUserId());
        markSelf.setChangeTimes(iSysUserMarkSelfService.selectByMid(markId).get(0).getChangeTimes());
        ShiroUtils.clearCachedAuthorizationInfo();
        return toAjax(iSysUserMarkSelfService.update(markSelf));

    }


}
