package com.numberone.system.service.impl;

import com.numberone.common.utils.DateUtils;
import com.numberone.system.domain.SysUser;
import com.numberone.system.domain.SysUserMarkSelf;
import com.numberone.system.mapper.SysUserMarkSelfMapper;
import com.numberone.system.service.ISysUserMarkSelfService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自评信息业务层
 */
@Service
public class SysUserMarkSelfImpl implements ISysUserMarkSelfService {
    @Autowired
    private SysUserMarkSelfMapper mapper;
    public List<SysUserMarkSelf> selectAll(SysUser user){
        Long uid=user.getUserId();
        List<SysUserMarkSelf> markSelfList = mapper.selectAll(uid);
        //结果不为空，处理未修改次数为0的修改信息数据
        if(markSelfList!=null&&markSelfList.size()>0){
            for(SysUserMarkSelf s:markSelfList){
                if(s.getChangeTimes()==0){
                    s.setChangeTime(null);
                    s.setMarkChange(null);
                }
            }
        }
        return markSelfList;
    }

    @Override
    public List<SysUserMarkSelf> selectByMid(String markId) {
        return mapper.selectByMid(markId);
    }

    @Override
    public int update(SysUserMarkSelf sysUserMarkSelf) {
        return mapper.update(sysUserMarkSelf);
    }

    /**
     * 新增自评
     *
     * @param sysUserMarkSelf
     * @return 999 本月已自评
     */
    @Override
    public int insert(SysUserMarkSelf sysUserMarkSelf) {
        String YearAndMon = DateUtils.getDate();
        HashMap<String,String> map=new HashMap<String,String>();
        map.put("uid",String.valueOf(sysUserMarkSelf.getUid()));
        map.put("yearAndMon",YearAndMon.substring(0,7));
        int markNum=this.selectByYearAndMon(map).size();
        //按年月查询，本月已有自评记录则不可自评
        if(markNum>0){
            return 999;
        }else {
            return mapper.insert(sysUserMarkSelf);
        }
    }

    @Override
    public int delete(SysUserMarkSelf sysUserMarkSelf) {
        return 0;
    }

    @Override
    public List<SysUserMarkSelf> selectByYearAndMon(HashMap<String,String> map){
        return mapper.selectByYearAndMon(map);
    }
}
