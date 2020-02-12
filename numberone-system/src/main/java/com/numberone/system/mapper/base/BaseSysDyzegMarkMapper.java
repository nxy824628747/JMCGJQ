package com.numberone.system.mapper.base;

import com.numberone.common.base.BaseEntity;
import com.numberone.system.domain.SysDyzegMark;
import com.numberone.system.domain.SysMarkImport;

import java.util.List;
import java.util.Map;

public interface BaseSysDyzegMarkMapper  extends BaseMarkMapper{
    public List<SysDyzegMark> selectObject(SysDyzegMark m);

    public List<SysDyzegMark> selectList(SysDyzegMark m);

    public int insert(SysDyzegMark m);

    public int update(SysDyzegMark m);

    public int delete(String markId);

    public List<Map<String,Object>> selectAllList(Map m);

    public List<SysMarkImport> selectImpotList(SysDyzegMark m);
}
