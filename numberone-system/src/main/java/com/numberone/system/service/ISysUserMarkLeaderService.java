package com.numberone.system.service;

import com.numberone.system.domain.SysUserMarkLeader;

import java.util.List;
import java.util.Map;

public interface ISysUserMarkLeaderService {

    /**
     * 根据部门编码查询部门人员小组评数据
     * @param depiId
     * @return
     */
    public List<SysUserMarkLeader> selectByDeptId(Map<String,Object> map);

    public int insert(SysUserMarkLeader markLeader,String month);

    public int update(SysUserMarkLeader markLeader);
}
