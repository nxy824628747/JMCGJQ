package com.numberone.system.mapper.base;

import com.numberone.common.base.BaseEntity;
import com.numberone.system.domain.SysMark;
import com.numberone.system.domain.SysMarkImport;

import java.util.List;
import java.util.Map;

public interface BaseSysMarkMapper{
    public List<SysMark> selectObject(BaseEntity m);

    public List<SysMark> selectList(BaseEntity m);

    public int insert(BaseEntity m);

    public int update(BaseEntity m);

    public List<Map<String,Object>> selectAllList(Map m);

    public List<SysMarkImport>  selectImpotList(SysMark m);

    public int delete(String markId);
}
