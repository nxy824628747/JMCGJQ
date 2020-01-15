package com.numberone.system.service;

import com.numberone.system.domain.SysUser;
import com.numberone.system.domain.SysUserMarkDzb;

import java.util.List;
import java.util.Map;

public interface ISysUserMarkDZBService {
    public List<SysUserMarkDzb> select(Map m);

    public int insert(SysUserMarkDzb dzb,String month);

    public int update(SysUserMarkDzb dzb,String month);

}
