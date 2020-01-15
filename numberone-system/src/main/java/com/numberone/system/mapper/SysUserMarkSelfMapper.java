package com.numberone.system.mapper;

import com.numberone.system.domain.SysUserMarkSelf;

import java.util.HashMap;
import java.util.List;

/**
 * 数据层
 */
public interface SysUserMarkSelfMapper {
    /**
     * 查询个人自评列表信息
     * @param uid
     * @return
     */

    public List<SysUserMarkSelf> selectAll(Long uid);

    /**
     * 根据MarkId查询自评信息
     * @return
     */
    public List<SysUserMarkSelf> selectByMid(String markId);


    /**
     * 查询当前月是否已有自评
     * @param map uid+YearAndMonth
     * @return
     */
    public List<SysUserMarkSelf> selectByYearAndMon(HashMap<String,String> map);

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
}
