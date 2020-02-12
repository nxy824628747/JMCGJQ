package com.numberone.system.service.base;import com.numberone.common.utils.DateUtils;import com.numberone.common.utils.StatisticUtils;import com.numberone.system.mapper.base.BaseMarkMapper;import java.text.ParseException;import java.text.SimpleDateFormat;import java.util.*;public class BaseMarkService {    /**     * @Author Nxy     * @Date 2020/2/10 13:38     * @Param     * @Return     * @Exception     * @Description 统计总得分     */    protected List<Map<String, Object>> statistic(String startTime, String endTime, String deptId, BaseMarkMapper b) throws ParseException {        Map<String, String> params = new HashMap<String, String>();        if (startTime != null) {            params.put("startTime", startTime);        }        if (endTime != null) {            params.put("endTime", endTime);        }        if (deptId != null) {            params.put("deptId", deptId);        }        List<Map<String, Object>> source = b.statistic(params);        if (source == null || source.size() == 0) {            return null;        }        List<String> keys = new ArrayList<String>(1);        keys.add("user_id");        List<String> nums = new ArrayList<String>(3);        nums.add("smark_mark");        nums.add("lmark_mark");        nums.add("dmark_mark");        //计算平均分使用        if (startTime == null || startTime.length() == 0) {            startTime = "2019-06-01";        }        if (endTime == null || endTime.length() == 0) {            endTime = DateUtils.getDate();        }        Date st = new SimpleDateFormat("yyyy-MM-dd").parse(startTime);        Date ed = new SimpleDateFormat("yyyy-MM-dd").parse(endTime);        int day = (int) ((ed.getTime() - st.getTime()) / (1000 * 60 * 60 * 24));        return StatisticUtils.getStatistic(source, keys, nums, day / 30);    }}