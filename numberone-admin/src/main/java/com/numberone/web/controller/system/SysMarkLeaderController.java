package com.numberone.web.controller.system;

import com.numberone.common.annotation.Log;
import com.numberone.common.base.AjaxResult;
import com.numberone.common.enums.BusinessType;
import com.numberone.common.page.TableDataInfo;
import com.numberone.common.utils.bean.BeanUtils;
import com.numberone.common.utils.poi.ExcelUtil;
import com.numberone.system.domain.SysMark;
import com.numberone.system.domain.SysMarkImport;
import com.numberone.system.domain.SysUser;
import com.numberone.system.service.ISysMarkService;
import com.numberone.system.service.impl.SysMarkDZBServiceImpl;
import com.numberone.system.service.impl.SysMarkLeaderServiceImpl;
import com.numberone.system.service.impl.SysMarkSelfServiceImpl;
import com.numberone.web.controller.system.base.BaseSysMarkController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("system/mark/markLeader")
public class SysMarkLeaderController extends BaseSysMarkController {
    private String prefix = "system/mark/markLeader";

    @Autowired
    SysMarkSelfServiceImpl selfService;
    @Autowired
    SysMarkLeaderServiceImpl service;
    @Autowired
    SysMarkDZBServiceImpl dzbService;

    @GetMapping()
    public String mark() {
        return prefix + "/markLeader";
    }

    @PostMapping("list")
    @ResponseBody
    public Object getList(SysMark m) {
        return getList(m,service);
    }

    @GetMapping("/add/{markId}")
    public String add(@PathVariable("markId") String markId, ModelMap map) {
        map.put("markId",markId);
        return prefix + "/markLeaderadd";
    }

    @Log(title = "责任区评分管理-小组新增", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult add(SysMark m){
        return addLeader(m,service);
    }


    /**
     * 查看小组评详细
     */
    @GetMapping("/detail/{markId}")
    public String detail(@PathVariable("markId") String markId, ModelMap mmap) {
        getMarkPOJO(markId,service,mmap);
        return "system/mark/markDetail";
    }

    /**
     * 新增小组评
     * @param m
     * @param service
     * @return
     */
    private AjaxResult addLeader(SysMark m, ISysMarkService service) {
        String markId=m.getMarkId();
        setInsertSelectBaseInfo(m, BusinessType.INSERT);
        m.setMarkId(markId);
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

    @Log(title = "责任区评分导出-小组导出", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(SysMark m) {
        SysUser curUser=getSysUser();
        Long uid=curUser.getUserId();
        m.setUid(uid);
        if(m.getDeptId()==null||m.getDeptId().length()==0){
            Long deptId=curUser.getDeptId();
            m.setDeptId(String.valueOf(deptId));
        }
        List<SysMarkImport> list = service.selectImpotList(m);
        ExcelUtil<SysMarkImport> util = new ExcelUtil<SysMarkImport>(SysMarkImport.class);
        return util.exportExcel(list, "责任区评分统计");
    }


    @Log(title = "责任区评分小组评-小组删除", businessType = BusinessType.EXPORT)
    @PostMapping("removeReal/{markId}")
    @ResponseBody
    public AjaxResult edit(@PathVariable("markId") String markId){
        if(markId==null||markId.length()==0){
            return error("请传入正确的markId");
        }
        SysMark s=new SysMark();
        s.setMarkId(markId);
        List<SysMark> res= BeanUtils.cleanNull(dzbService.selectObject(s));
        if(res!=null&&res.size()!=0){
            return error("党支部已在此基础上评分，不可删除！");
        }
        int re=service.delete(markId);
        if(re==0){return error("还未进行小组评分，无法删除！");}
        return success("删除成功");
    }

    @GetMapping("/edit/{markId}")
    public String update(@PathVariable("markId") String markId, ModelMap mmap) {
        getMarkPOJO(markId, service, mmap);
        return "system/mark/markLeader/markLeaderUpdate";
    }

    @Log(title = "责任区评分导出-小组修改", businessType = BusinessType.UPDATE)
    @PostMapping("/editReal")
    @ResponseBody
    public AjaxResult update(SysMark s){
        SysMark sm=new SysMark();
        s.setMarkId(s.getMarkId());
        List<SysMark> res= BeanUtils.cleanNull(selfService.selectObject(s));
        double selfMark=res.get(0).getMarkMark();
        int flag=0;
        if(selfMark==0.0){flag=1;}
        return update(s,dzbService,service,flag);
    }
}
