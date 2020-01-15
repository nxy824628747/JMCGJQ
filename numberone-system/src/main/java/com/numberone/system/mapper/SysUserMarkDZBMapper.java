package com.numberone.system.mapper;

import com.numberone.system.domain.SysUserMarkDzb;

import java.util.List;
import java.util.Map;

public interface SysUserMarkDZBMapper {
    public List<SysUserMarkDzb> select(Map m);

    public int insert(SysUserMarkDzb dzb);

    public int update(SysUserMarkDzb dzb);
}
