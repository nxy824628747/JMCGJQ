package com.numberone.system.service.impl;

import com.numberone.system.domain.SysMark;
import com.numberone.system.domain.SysMarkImport;
import com.numberone.system.mapper.SysMarkSelfMapper;
import com.numberone.system.service.ISysMarkService;
import com.numberone.system.service.base.BaseSysMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SysMarkSelfServiceImpl extends BaseSysMarkService implements ISysMarkService {
    @Autowired
    SysMarkSelfMapper mapper;

    /**
     * 查询对象信息
     *
     * @param m
     * @return
     */
    @Override
    public List<SysMark> selectObject(SysMark m) {
        return mapper.selectObject(m);
    }


    /**
     * 查询列表
     * @param m
     * @return
     */
    @Override
    public List<SysMark> selectList(SysMark m) {
        return mapper.selectList(m);
    }

    /**
     * 插入一条新记录
     * 必填项   uid,
     * mark_id,
     * mark_mark,
     * mark_month,
     * mark_time,
     * mark_JiDu
     *
     * @param m
     * @return
     */
    @Override
    public int insert(SysMark m) {
        //判断本月是否已自评
        return insert(m,mapper);
    }

    @Override
    public int update(SysMark m,int isZeroFlag) {
        return super.update(m,mapper,null,isZeroFlag);
    }

    @Override
    public List<Map<String, Object>> selectAllList(Map m) {
        return mapper.selectAllList(m);
    }

    @Override
    public List<SysMarkImport>  selectImpotList(SysMark m){
        return null;
    }

    @Override
    public int delete(String markId) {
        return mapper.delete(markId);
    }

}
