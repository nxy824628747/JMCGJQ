package com.numberone.system.service.impl;

import com.numberone.system.domain.SysUserMarkLeader;
import com.numberone.system.mapper.SysUserMarkLeaderMapper;
import com.numberone.system.service.ISysUserMarkLeaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysUserMarkLeaderServiceImpl implements ISysUserMarkLeaderService {
    @Autowired
    SysUserMarkLeaderMapper mapper;

    @Override
    public List<SysUserMarkLeader> selectByDeptId(Map<String, Object> map) {
        List<SysUserMarkLeader> list = mapper.selectByDeptId(map);

        for (SysUserMarkLeader markLeader : list) {
            //处理修改过自评的数据
            if (markLeader.getChangeTimes() > 0) {
                markLeader.setMarkSelf(markLeader.getMarkChange());
                markLeader.setMarkReason(markLeader.getChangeReason());
                markLeader.setMarkTime(markLeader.getChangeTime());
            }else{
                //未修改项修改时间置空
                markLeader.setChangeTime(null);
                markLeader.setMarkChange(null);
            }
            //处理修改过小组评的数据
            if (markLeader.getChangeLeaderTimes() > 0) {
                markLeader.setMarkLeader(markLeader.getMarkLeaderChange());
                markLeader.setMarkLeaderReason(markLeader.getChangeLeaderReason());
                markLeader.setMarkLeaderTime(markLeader.getChangeLeaderTime());
            }else{
                //未修改项修改时间置空
                markLeader.setChangeLeaderTime(null);
                markLeader.setMarkLeaderChange(null);
            }
        }
        return list;
    }

    @Override
    public int insert(SysUserMarkLeader markLeader, String month) {
        //传入myBatis的map
        Map<String, Object> m = new HashMap();
        m.put("mid", markLeader.getMid());
        //靠l.mid或者s.mark_id就可以唯一命中两个表中的一条记录，不要多加乱七八糟的字段复杂逻辑！
//        m.put("deptId",markLeader.getDeptId());
        List<SysUserMarkLeader> l = selectByDeptId(m);
        m.put("markId", markLeader.getMid());
        m.remove("mid");
        //评价下级单位人员时不可传入本级部门账号
        m.remove("deptId");
        List<SysUserMarkLeader> l2 = selectByDeptId(m);
        if (l.size() > 0) {
            //已存在小组评，不可新增
            return 999;
        } else if (l2.size() > 0) {
            //自评时间不是本月
            Timestamp markTimeT = l2.get(0).getMarkTime();
            String markTime = markTimeT.toString();
            String markMonth = markTime.substring(5, 7);
            if (!month.equals(markMonth)) {
                return 997;
            }
        } else if (l2.size() == 0) {
            //未自评，不可小组评
            return 998;
        }
        return mapper.insert(markLeader);
    }

    @Override
    public int update(SysUserMarkLeader markLeader) {
        Map m = new HashMap<String, Object>();
        //查询当前是否已修改
        m.put("mid", markLeader.getMid());
        List<SysUserMarkLeader> markLeaderNow = mapper.selectByDeptId(m);
        if (markLeaderNow != null && markLeaderNow.size() > 0) {
            //前台未记录修改次数数据，数据库查出放进pojo
            markLeader.setChangeLeaderTimes(markLeaderNow.get(0).getChangeLeaderTimes());
            return mapper.update(markLeader);
        } else {
            //未小组评不可修改
            return 999;
        }
    }
}
