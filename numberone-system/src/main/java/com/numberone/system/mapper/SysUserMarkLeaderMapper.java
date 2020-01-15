package com.numberone.system.mapper;

import com.numberone.system.domain.SysUserMarkLeader;

import java.util.List;
import java.util.Map;

public interface SysUserMarkLeaderMapper {

    public List<SysUserMarkLeader> selectByDeptId(Map<String,Object> map);

    public int insert(SysUserMarkLeader markLeader);

    public int update(SysUserMarkLeader markLeader);
}
