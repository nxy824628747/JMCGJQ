package com.numberone.system.service;

import com.numberone.system.domain.SysUser;
import com.numberone.system.domain.SysUserMarkSelf;

import java.util.HashMap;
import java.util.List;

/**
 * 业务角色层
 */
public interface ISysUserMarkSelfService {
    /**
     * 查询个人自评列表信息
     * @param uid
     * @return
     */

    public List<SysUserMarkSelf> selectAll(SysUser user);

    /**
     * 根据markId查询自评信息
     * @return
     */
    public List<SysUserMarkSelf> selectByMid(String markId);
    /**
     * 根据自评id修改自评信息
     * @param sysUserMarkSelf
     * @return
     */
    public int update(SysUserMarkSelf sysUserMarkSelf);

    /**
     * 新增自评信息
     * @param sysUserMarkSelf
     * @return
     */
    public int insert(SysUserMarkSelf sysUserMarkSelf);

    /**
     * 删除自评信息
     * @param sysUserMarkSelf
     * @return
     */
    public int delete(SysUserMarkSelf sysUserMarkSelf);

    /**
     * 查询当前月是否已有自评
     * @param map
     * @return
     */
    public List<SysUserMarkSelf> selectByYearAndMon(HashMap<String,String> map);
}
