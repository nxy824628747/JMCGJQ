package com.numberone.system.service;

import com.numberone.system.domain.SysMark;
import com.numberone.system.domain.SysMarkImport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ISysMarkService {
    public List<SysMark> selectObject(SysMark m);

    public List<SysMark> selectList(SysMark m);

    public List<SysMarkImport>  selectImpotList(SysMark m);

    public int insert(SysMark m);

    public int update(SysMark m,int isZeroFlag);

    public int delete(String markId);

    public List<Map<String,Object>> selectAllList(Map m);

}
