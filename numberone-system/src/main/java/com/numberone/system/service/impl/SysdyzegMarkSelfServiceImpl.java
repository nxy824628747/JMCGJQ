package com.numberone.system.service.impl;

import com.numberone.common.base.AjaxResult;
import com.numberone.system.domain.SysDyzegMark;
import com.numberone.system.domain.SysMark;
import com.numberone.system.domain.SysMarkImport;
import com.numberone.system.domain.SysUser;
import com.numberone.system.mapper.SysMarkSelfMapper;
import com.numberone.system.mapper.SysdyzegMarkSelfMapper;
import com.numberone.system.service.ISysMarkService;
import com.numberone.system.service.ISysdyzegMarkSelfService;
import com.numberone.system.service.ISysdyzegMarkService;
import com.numberone.system.service.base.BaseSysMarkService;
import com.numberone.system.service.base.BaseSysdyzegMarkSelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysdyzegMarkSelfServiceImpl extends BaseSysdyzegMarkSelfService implements ISysdyzegMarkService {
    @Autowired
    SysdyzegMarkSelfMapper mapper;

    /**
     * 查询对象信息
     *
     * @param m
     * @return
     */

    public List<SysDyzegMark> selectObject(SysDyzegMark m) {
        return mapper.selectObject(m);
    }


    /**
     * 查询列表
     * @param m
     * @return
     */

    public List<SysDyzegMark> selectList(SysDyzegMark m) {
        return mapper.selectList(m);
    }

    @Override
    public int delete(String markId) {
        return mapper.delete(markId);
    }

    /**
     * 插入一条新记录
     * 必填项   uid,
     * mark_id,
     * mark_mark,
     * mark_month,
     * mark_time,
     * mark_JiDu
     *
     * @param m
     * @return
     */

    public AjaxResult insert(SysDyzegMark m) {
        //判断本月是否已自评
        return insertZRG(m,mapper);
    }


    public int update(SysDyzegMark m,int isZeroFlag) {
        return super.update(m,mapper,null,isZeroFlag);
    }


    public List<Map<String, Object>> selectAllList(Map m) {
        return mapper.selectAllList(m);
    }

    @Override
    public List<SysMarkImport> selectImpotList(SysDyzegMark m){
        return null;
    }
}
