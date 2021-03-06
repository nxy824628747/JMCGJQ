package com.numberone.system.service.impl;

import com.numberone.common.base.AjaxResult;
import com.numberone.common.utils.bean.BeanUtils;
import com.numberone.system.domain.SysDyzegMark;
import com.numberone.system.domain.SysMark;
import com.numberone.system.domain.SysMarkImport;
import com.numberone.system.mapper.*;
import com.numberone.system.service.ISysMarkService;
import com.numberone.system.service.ISysdyzegMarkService;
import com.numberone.system.service.base.BaseSysMarkService;
import com.numberone.system.service.base.BaseSysdyzegMarkSelfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public class SysdyzegMarkDZBServiceImpl extends BaseSysdyzegMarkSelfService implements ISysdyzegMarkService {
    @Autowired
    SysdyzegMarkDZBMapper mapper;
    @Autowired
    SysdyzegMarkSelfMapper selfMapper;
    @Autowired
    SysdyzegMarkLeaderMapper leaderMapper;

    /**
     * @Author Nxy
     * @Date 2020/2/10 14:14
     * @Param
     * @Return
     * @Exception
     * @Description 区间总得分统计
     */
    public List<Map<String, Object>> stat(String startTime, String endTime, String deptId) throws ParseException {
        return statistic(startTime, endTime, deptId, mapper);
    }

    /**
     * 查询对象信息,必须拼装入mark_id
     *
     * @param m
     * @return
     */
    @Override
    public List<SysDyzegMark> selectObject(SysDyzegMark m) {
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
    public List<SysDyzegMark> selectList(SysDyzegMark m) {
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
    public AjaxResult insert(SysDyzegMark m) {
        //m中必须有mark_id,判断是否已自评
        List<SysDyzegMark> markSelfList = BeanUtils.cleanNull(selfMapper.selectObject(m));
        if (markSelfList == null || markSelfList.size() == 0) {
            return AjaxResult.error("本月未自评，不可支部评");
        }
        //判断是否已小组评
        List<SysDyzegMark> markLeaderList = BeanUtils.cleanNull(leaderMapper.selectObject(m));
        if (markLeaderList.size() == 0) {
            return AjaxResult.error("本月未小组评，不可支部评");
        }
        //判断是否已党支部评
        List<SysDyzegMark> markDZBList = BeanUtils.cleanNull(mapper.selectObject(m));
        if (markDZBList.size() > 0) {
            return AjaxResult.error("本月已支部评，不可重复评分");
        }
        //计算总得分
        sumZRGAll(m);
        double markLeader = markLeaderList.get(0).getMarkMark();
        double markDZB = m.getMarkMark();
        if (markLeader == 0||isZRGFJ(m)) {
            m.setMarkMark(0.0);
        } else {
            m.setMarkMark(markLeader - 100 + markDZB);
        }
        int re = mapper.insert(m);
        if (re != 1) {
            return AjaxResult.error("未定义的系统错误");
        }
        return AjaxResult.success("新增成功");
    }

    @Override
    public int update(SysDyzegMark m, int isZeroFlag) {
        return super.update(m, mapper, leaderMapper,isZeroFlag);
    }

    @Override
    public List<Map<String, Object>> selectAllList(Map m) {
        return mapper.selectAllList(m);
    }

    @Override
    public List<SysMarkImport> selectImpotList(SysDyzegMark m) {
        return mapper.selectImpotList(m);
    }


}
