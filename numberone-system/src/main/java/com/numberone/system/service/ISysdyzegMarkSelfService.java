package com.numberone.system.service;

import com.numberone.common.base.AjaxResult;
import com.numberone.system.domain.SysDyzegMark;
import com.numberone.system.domain.SysMark;

import java.util.List;
import java.util.Map;

public interface ISysdyzegMarkSelfService {
    public List<SysDyzegMark> selectObject(SysDyzegMark m);

    public List<SysDyzegMark> selectList(SysDyzegMark m);

    public AjaxResult insert(SysDyzegMark m);

    public int update(SysDyzegMark m);

    public List<Map<String,Object>> selectAllList(Map m);

    public int delete(String markId);

}
