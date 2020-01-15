package com.numberone.web.controller.system;

import com.numberone.common.annotation.Log;
import com.numberone.common.base.AjaxResult;
import com.numberone.common.enums.BusinessType;
import com.numberone.common.utils.bean.BeanUtils;
import com.numberone.common.utils.poi.ExcelUtil;
import com.numberone.system.domain.SysDyzegMark;
import com.numberone.system.domain.SysMark;
import com.numberone.system.domain.SysMarkImport;
import com.numberone.system.domain.SysUser;
import com.numberone.system.service.ISysdyzegMarkService;
import com.numberone.system.service.ISysdyzegMarkService;
import com.numberone.system.service.impl.SysdyzegMarkDZBServiceImpl;
import com.numberone.system.service.impl.SysdyzegMarkLeaderServiceImpl;
import com.numberone.system.service.impl.SysdyzegMarkLeaderServiceImpl;
import com.numberone.system.service.impl.SysdyzegMarkSelfServiceImpl;
import com.numberone.web.controller.system.base.BaseSysdyzegMarkController;
import com.numberone.web.controller.system.base.BaseSysdyzegMarkController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("system/dyzrgMark/markLeader")
public class SysdyzegMarkLeaderController extends BaseSysdyzegMarkController {
    private String prefix = "system/dyzrgMark/markLeader";
    @Autowired
    SysdyzegMarkSelfServiceImpl selfService;
    @Autowired
    SysdyzegMarkLeaderServiceImpl service;

    @Autowired
    SysdyzegMarkDZBServiceImpl dzbService;

    @GetMapping()
    public String mark() {
        return prefix + "/markLeader";
    }

    @PostMapping("list")
    @ResponseBody
    public Object getList(SysDyzegMark m) {
        return getZRGList(m,service);
    }

    @GetMapping("/add/{markId}")
    public String add(@PathVariable("markId") String markId, ModelMap map) {
        map.put("markId",markId);
        return prefix + "/markLeaderadd";
    }

    @Log(title = "党员责任岗评分-新增小组评", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult add(SysDyzegMark m){
        return addLeader(m,service);
    }


    /**
     * 查看小组评详细
     */
    @GetMapping("/detail/{markId}")
    public String edit(@PathVariable("markId") String markId, ModelMap mmap) {
        getMarkZRGPOJO(markId,service,mmap);
        return "system/dyzrgMark/markDetail";
    }

    /**
     * 新增小组评
     * @param m
     * @param service
     * @return
     */
    private AjaxResult addLeader(SysDyzegMark m, ISysdyzegMarkService service) {
        String markId=m.getMarkId();
        setZRGInsertSelectBaseInfo(m, BusinessType.INSERT);
        m.setMarkId(markId);
        return service.insert(m);
    }

    @Log(title = "责任岗评分导出-党小组导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysDyzegMark m) {
        SysUser curUser=getSysUser();
        Long uid=curUser.getUserId();
        m.setUid(uid);
        if(m.getDeptId()==null||m.getDeptId().length()==0){
            Long deptId=curUser.getDeptId();
            m.setDeptId(String.valueOf(deptId));
        }
        List<SysMarkImport> list = service.selectImpotList(m);
        ExcelUtil<SysMarkImport> util = new ExcelUtil<SysMarkImport>(SysMarkImport.class);
        return util.exportExcel(list, "责任岗评分统计");
    }

    @Log(title = "责任岗评分小组评-删除", businessType = BusinessType.EXPORT)
    @PostMapping("removeReal/{markId}")
    @ResponseBody
    public AjaxResult edit(@PathVariable("markId") String markId){
        if(markId==null||markId.length()==0){
            return error("请传入正确的markId");
        }
        SysDyzegMark s=new SysDyzegMark();
        s.setMarkId(markId);
        List<SysDyzegMark> res= BeanUtils.cleanNull(dzbService.selectObject(s));
        if(res!=null&&res.size()!=0){
            return error("党支部已在此基础上评分，不可删除！");
        }
        int re=service.delete(markId);
        if(re==0){return error("还未进行小组评分，无法删除！");}
        return success("删除成功");
    }

    @GetMapping("/edit/{markId}")
    public String update(@PathVariable("markId") String markId, ModelMap mmap) {
        getMarkZRGPOJO(markId, service, mmap);
        return "system/dyzrgMark/markLeader/markLeaderUpdate";
    }

    @Log(title = "责任岗评分导出-小组修改", businessType = BusinessType.UPDATE)
    @PostMapping("/editReal")
    @ResponseBody
    public AjaxResult update(SysDyzegMark s){
        SysDyzegMark sm=new SysDyzegMark();
        s.setMarkId(s.getMarkId());
        List<SysDyzegMark> res= BeanUtils.cleanNull(selfService.selectObject(s));
        double selfMark=res.get(0).getMarkMark();
        int flag=0;
        if(selfMark==0.0){flag=1;}
        return update(s,dzbService,service,flag);
    }
}
