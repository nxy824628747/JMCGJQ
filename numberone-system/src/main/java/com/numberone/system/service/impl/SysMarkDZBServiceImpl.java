package com.numberone.system.service.impl;

import com.numberone.common.utils.bean.BeanUtils;
import com.numberone.system.domain.SysMark;
import com.numberone.system.domain.SysMarkImport;
import com.numberone.system.mapper.SysMarkDZBMapper;
import com.numberone.system.mapper.SysMarkLeaderMapper;
import com.numberone.system.mapper.SysMarkSelfMapper;
import com.numberone.system.service.ISysMarkService;
import com.numberone.system.service.base.BaseSysMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SysMarkDZBServiceImpl extends BaseSysMarkService implements ISysMarkService {
    @Autowired
    SysMarkDZBMapper mapper;
    @Autowired
    SysMarkSelfMapper selfMapper;
    @Autowired
    SysMarkLeaderMapper leaderMapper;

    /**
     * 查询对象信息,必须拼装入mark_id
     *
     * @param m
     * @return
     */
    @Override
    public List<SysMark> selectObject(SysMark m) {
        return mapper.selectObject(m);
    }

    @Override
    public int delete(String markId) {
        return mapper.delete(markId);
    }

    /**
     * 查询列表
     *
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
        //m中必须有mark_id,判断是否已自评
        List<SysMark> markSelfList = BeanUtils.cleanNull(selfMapper.selectObject(m));
        if (markSelfList == null || markSelfList.size() == 0) {
            return 998;
        }
        //判断是否已小组评
        List<SysMark> markLeaderList = BeanUtils.cleanNull(leaderMapper.selectObject(m));
        if (markLeaderList.size() == 0) {
            return 997;
        }
        //判断是否已党支部评
        List<SysMark> markDZBList = BeanUtils.cleanNull(mapper.selectObject(m));
        if (markDZBList.size() > 0) {
            return 999;
        }
        //计算总得分
        sumAll(m);
        double markLeader = markLeaderList.get(0).getMarkMark();
        if (markLeader == 0||isFouJue(m)) {
            m.setMarkMark(0.0);
        } else {
            m.setMarkMark(m.getMarkMark() + markLeader - 100);
        }
        return mapper.insert(m);
    }

    @Override
    public int update(SysMark m,int isZeroFlag) {
        return super.update(m,mapper,leaderMapper,isZeroFlag);
    }

    @Override
    public List<Map<String, Object>> selectAllList(Map m) {
        return mapper.selectAllList(m);
    }

    @Override
    public List<SysMarkImport> selectImpotList(SysMark m) {
        return null;
    }

}
