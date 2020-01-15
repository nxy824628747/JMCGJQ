package com.numberone.system.service.impl;

import com.numberone.common.annotation.Log;
import com.numberone.common.base.AjaxResult;
import com.numberone.common.enums.BusinessType;
import com.numberone.common.utils.bean.BeanUtils;
import com.numberone.common.utils.poi.ExcelUtil;
import com.numberone.system.domain.SysMark;
import com.numberone.system.domain.SysMarkImport;
import com.numberone.system.domain.SysUser;
import com.numberone.system.mapper.SysMarkLeaderMapper;
import com.numberone.system.mapper.SysMarkSelfMapper;
import com.numberone.system.service.ISysMarkService;
import com.numberone.system.service.base.BaseSysMarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Service
public class SysMarkLeaderServiceImpl extends BaseSysMarkService implements ISysMarkService {
    @Autowired
    SysMarkLeaderMapper mapper;
    @Autowired
    SysMarkSelfMapper selfMapper;

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
    public List<SysMarkImport> selectImpotList(SysMark m) {
        return mapper.selectImpotList(m);
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
        List<SysMark> markLeaderList = BeanUtils.cleanNull(mapper.selectObject(m));
        if (markLeaderList.size() > 0) {
            return 999;
        }
        //计算总得分
        sumAll(m);
        double markSelf = markSelfList.get(0).getMarkMark();
        if (markSelf == 0||isFouJue(m)) {
            m.setMarkMark(0.0);
        } else {
            m.setMarkMark(m.getMarkMark() + markSelf - 100);
        }
        return mapper.insert(m);
    }

    @Override
    public int update(SysMark m,int isZeroFlag) {
        return super.update(m,mapper,selfMapper,isZeroFlag);
    }

    @Override
    public int delete(String markId) {
        return mapper.delete(markId);
    }

    @Override
    public List<Map<String, Object>> selectAllList(Map m) {
        return mapper.selectAllList(m);
    }


}
