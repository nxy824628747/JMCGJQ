package com.numberone.system.service.base;

import com.numberone.common.utils.bean.BeanUtils;
import com.numberone.system.domain.SysDyzegMark;
import com.numberone.system.domain.SysMark;
import com.numberone.system.mapper.SysdyzegMarkSelfMapper;
import com.numberone.system.mapper.base.BaseSysDyzegMarkMapper;
import com.numberone.system.mapper.base.BaseSysMarkMapper;

import java.util.List;

/**
 * 自评二版Service层通用方法
 */
public abstract class BaseSysMarkService extends BaseMarkService{


    public int update(SysMark m, BaseSysMarkMapper mapper,BaseSysMarkMapper sonMapper,int isZeroFlag){
        if(m==null||m.getMarkId()==null){return 0;}
        sumAll(m);
        if(sonMapper!=null&&!isFouJue(m)) {
            List<SysMark> ls = BeanUtils.cleanNull(sonMapper.selectObject(m));
            double sonMark = ls.get(0).getMarkMark();
            double realMark=m.getMarkMark()-100+sonMark;
            m.setMarkMark(realMark);
        }
        if(isZeroFlag==1){
            m.setMarkMark(0.0);
        }
        return mapper.update(m);
    }

    public int insert(SysMark m, BaseSysMarkMapper mapper) {
        //判断本月是否已自评
        if (isMarked(mapper, m)) {
            //本月已自评
            return 999;
        }
        //计算总得分
        sumAll(m);
        return mapper.insert(m);
    }


    /**
     * 判断本月是否已自评,controller放入年份及月份
     *
     * @param m
     * @return
     */
    protected boolean isMarked(BaseSysMarkMapper mapper, SysMark m) {
        SysMark m2=new SysMark();
        m2.setUid(m.getUid());
        m2.setMarkMonth(m.getMarkMonth());
        m2.setMarkYear(m.getMarkYear());
        List<SysMark> list = BeanUtils.cleanNull(mapper.selectList(m2));
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }


    /**
     * 计算总得分
     *
     * @param m
     * @return
     */
    protected void sumAll(SysMark m) {
        if (isFouJue(m)) {
            m.setMarkMark(0.0);
            return;
        }
        double sumAll = 100 + sumJiaFen(m) - sumLLXZ(m) - sumZZGW(m) - sumJSYW(m)
                - sumZGSX(m);
        m.setMarkMark(sumAll);
    }

    /**
     * 是否否决
     *
     * @param m
     * @return
     */
    protected boolean isFouJue(SysMark m) {
        double result = BeanUtils.getDoubleValue(m.getFj1MarkSelf())
                + BeanUtils.getDoubleValue(m.getFj2MarkSelf())
                + BeanUtils.getDoubleValue(m.getFj3MarkSelf())
                + BeanUtils.getDoubleValue(m.getFj4MarkSelf())
                + BeanUtils.getDoubleValue(m.getFj5MarkSelf());
        String resultReason=m.getFj1ReasonSelf()+m.getFj2ReasonSelf()+m.getFj3ReasonSelf()
                +m.getFj4ReasonSelf()+m.getFj5ReasonSelf();
        if (result > 0||(resultReason!=null&&resultReason.length()>0)) {
            return true;
        }
        return false;
    }

    /**
     * 计算加分项
     *
     * @param m
     * @return
     */
    protected double sumJiaFen(SysMark m) {
        double result = BeanUtils.getDoubleValue(m.getJf1MarkSelf())
                + BeanUtils.getDoubleValue(m.getJf2MarkSelf())
                + BeanUtils.getDoubleValue(m.getJf3MarkSelf())
                + BeanUtils.getDoubleValue(m.getJf4MarkSelf())
                + BeanUtils.getDoubleValue(m.getJf5MarkSelf())
                + BeanUtils.getDoubleValue(m.getJf6MarkSelf());
        return result;
    }

    /**
     * 计算联劳协作减分
     *
     * @param m
     * @return
     */
    protected double sumLLXZ(SysMark m) {
        double result = BeanUtils.getDoubleValue(m.getLlxz1MarkSelf())
                + BeanUtils.getDoubleValue(m.getLlxz2MarkSelf())
                + BeanUtils.getDoubleValue(m.getLlxz3MarkSelf())
                + BeanUtils.getDoubleValue(m.getLlxz4MarkSelf());
        return result;
    }

    /**
     * 计算作业岗位减分
     *
     * @param m
     * @return
     */
    protected double sumZZGW(SysMark m) {
        double result = BeanUtils.getDoubleValue(m.getZygw1MarkSelf())
                + BeanUtils.getDoubleValue(m.getZygw2MarkSelf())
                + BeanUtils.getDoubleValue(m.getZygw3MarkSelf());
        return result;
    }

    /**
     * 计算技术业务减分
     *
     * @param m
     * @return
     */
    protected double sumJSYW(SysMark m) {
        double result = BeanUtils.getDoubleValue(m.getJsyw1MarkSelf())
                + BeanUtils.getDoubleValue(m.getJsyw2MarkSelf())
                + BeanUtils.getDoubleValue(m.getJsyw3MarkSelf())
                + BeanUtils.getDoubleValue(m.getJsyw4MarkSelf());
        return result;
    }

    /**
     * 计算职工思想减分
     *
     * @param m
     * @return
     */
    protected double sumZGSX(SysMark m) {
        double result = BeanUtils.getDoubleValue(m.getZgsx1MarkSelf())
                + BeanUtils.getDoubleValue(m.getZgsx2MarkSelf())
                + BeanUtils.getDoubleValue(m.getZgsx3MarkSelf())
                + BeanUtils.getDoubleValue(m.getZgsx4MarkSelf());
        return result;
    }


}
