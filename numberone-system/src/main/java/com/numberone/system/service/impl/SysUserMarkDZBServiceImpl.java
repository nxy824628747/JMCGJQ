package com.numberone.system.service.impl;

import com.numberone.common.json.JSONObject;
import com.numberone.common.utils.bean.BeanUtils;
import com.numberone.system.domain.SysUser;
import com.numberone.system.domain.SysUserMarkDzb;
import com.numberone.system.domain.SysUserMarkLeader;
import com.numberone.system.mapper.SysUserMarkDZBMapper;
import com.numberone.system.mapper.SysUserMarkLeaderMapper;
import com.numberone.system.service.ISysUserMarkDZBService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class SysUserMarkDZBServiceImpl implements ISysUserMarkDZBService {
    @Autowired
    SysUserMarkDZBMapper mapper;
    @Autowired
    SysUserMarkLeaderMapper leaderMapper;
    @Override
    public List<SysUserMarkDzb> select(Map m) {
        return BeanUtils.cleanNull(mapper.select(m));
    }

    @Override
    public int insert(SysUserMarkDzb dzb,String month) {
        Map<String,Object> m=new HashMap<>();
        String mlid=dzb.getMlid();
        //查看自评月份是否已小组评
        m.put("markLeaderId",mlid);
        List<SysUserMarkLeader> listLeader=BeanUtils.cleanNull(leaderMapper.selectByDeptId(m));
        if(listLeader==null||listLeader.size()==0){
            //未进行小组评价，不可党支部评价
            return 998;
        }else{
            //如已小组评，查看自评是否为本月自评
            SysUserMarkLeader markLeader=listLeader.get(0);
            Timestamp markTimeT = markLeader.getMarkTime();
            String markTime = markTimeT.toString();
            String markMonth = markTime.substring(5, 7);
            if(!month.equals(markMonth)){
                //非本月自评，已过党支部评时间
                return 997;
            }
        }
        //查询是否已有党支部评价
        m.remove("markLeaderId");
        m.put("mlid",mlid);
        List<SysUserMarkDzb> list=BeanUtils.cleanNull(select(m));
        if(list!=null&&list.size()>0){
            //已存在党支部评价
            return 999;
        }
        return mapper.insert(dzb);
    }

    @Override
    public int update(SysUserMarkDzb dzb,String month) {
        String mlid=dzb.getMlid();
        Map m=new HashMap();
        m.put("mlid",mlid);
        List<SysUserMarkDzb> list=BeanUtils.cleanNull(select(m));
        //检查自评时间开始
        m.remove("mlid");
        m.put("markLeaderId",mlid);
        List<SysUserMarkLeader> listLeader=BeanUtils.cleanNull(leaderMapper.selectByDeptId(m));
        if(listLeader!=null&&listLeader.size()>0) {
            SysUserMarkLeader leader = listLeader.get(0);
            String markTime=leader.getMarkTime().toString();
            String markMonth=markTime.substring(5, 7);
            if(month.equals(markMonth)) {
                //检验该条记录是否已进行过党支部评分
                if (list != null && list.size() > 0) {
                    SysUserMarkDzb dzbNow = list.get(0);
                    String history = dzbNow.getMarkDzbHistory();
                    long changeTimes = dzbNow.getChangeDzbTimes();
                    //放入当前修改次数
                    dzb.setChangeDzbTimes(changeTimes);
                    //拼装修改历史放入
                    if (changeTimes > 0) {
                        //如果修改次数大于0，拿出history继续拼装
                        List<String> historylist = Arrays.asList(history.substring(1,history.length()-1));
                        JSONObject json = new JSONObject();
                        json.put("MarkDzbChange", dzbNow.getMarkDzbChange());
                        json.put("ChangeDzbReason", dzbNow.getChangeDzbReason());
                        json.put("ChangeDzbTime", dzbNow.getChangeDzbTime().toString());
                        json.put("ChangeDzbTimes", changeTimes);
                        ArrayList<String> resultHistory=new ArrayList<>();
                        resultHistory.add(json.toString());
                        dzb.setMarkDzbHistory(BeanUtils.combineLists(resultHistory,historylist).toString());
                    } else {
                        //第一次修改
                        List historylist = new ArrayList();
                        JSONObject json = new JSONObject();
                        json.put("MarkDzb", dzbNow.getMarkDzb());
                        json.put("MarkDzbReason", dzbNow.getMarkDzbReason());
                        json.put("MarkDzbTime", dzbNow.getMarkDzbTime().toString());
                        json.put("ChangeDzbTimes", 0);
                        historylist.add(json.toString());
                        dzb.setMarkDzbHistory(historylist.toString());
                    }
                    //拼装修改历史放入结束
                    return mapper.update(dzb);
                } else {
                    //还未进行党支部评分
                    return 999;
                }
            }else{
                //自评时间非当月
                return 997;
            }
        }else{
            //还未进行小组评分
            return 998;
        }
    }
}
