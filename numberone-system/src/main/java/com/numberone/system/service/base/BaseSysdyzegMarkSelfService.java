package com.numberone.system.service.base;

import com.numberone.common.base.AjaxResult;
import com.numberone.common.utils.bean.BeanUtils;
import com.numberone.system.domain.SysDyzegMark;
import com.numberone.system.domain.SysMark;
import com.numberone.system.mapper.base.BaseSysDyzegMarkMapper;

import java.util.List;

import static com.numberone.common.base.AjaxResult.error;

/**
 * 党员责任岗评分Service层基类
 */
public abstract class BaseSysdyzegMarkSelfService extends BaseMarkService{

    public int update(SysDyzegMark m, BaseSysDyzegMarkMapper mapper,BaseSysDyzegMarkMapper sonMapper,int isZeroFlag){
        if(m==null||m.getMarkId()==null){return 0;}
        sumZRGAll(m);
        if(sonMapper!=null&&!isZRGFJ(m)) {
            List<SysDyzegMark> ls = BeanUtils.cleanNull(sonMapper.selectObject(m));
            double sonMark = ls.get(0).getMarkMark();
            double realMark=m.getMarkMark()-100+sonMark;
            m.setMarkMark(realMark);
        }
        if(isZeroFlag==1){
            m.setMarkMark(0.0);
        }
        return mapper.update(m);
    }


    public AjaxResult insertZRG(SysDyzegMark m, BaseSysDyzegMarkMapper mapper) {
        //判断本月是否已自评
        if (isZRGMarked(mapper, m)) {
            //本月已自评
            return error("本月已评分，不可新增");
        }
        //计算总得分
        sumZRGAll(m);
        int re = mapper.insert(m);
        if (re == 1) {
            return AjaxResult.success("新增成功");
        }
        return error("新增失败");
    }

    /**
     * 判断责任岗本月是否已自评
     *
     * @param mapper
     * @param m
     * @return
     */
    protected boolean isZRGMarked(BaseSysDyzegMarkMapper mapper, SysDyzegMark m) {
        SysDyzegMark m2 = new SysDyzegMark();
        m2.setMarkMonth(m.getMarkMonth());
        m2.setMarkYear(m.getMarkYear());
        m2.setUid(m.getUid());
        List<SysMark> list = BeanUtils.cleanNull(mapper.selectList(m2));
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 计算责任岗总得分
     *
     * @param m
     * @return
     */
    protected void sumZRGAll(SysDyzegMark m) {
        if (isZRGFJ(m)) {
            m.setMarkMark(0.0);
            return;
        }
        double sumAll = 100 + sumZRGJF(m) - sumZRGZZSZ(m) - sumZRGGWJN(m) - sumZRGGZYJ(m)
                - sumZRGQZPJ(m);
        m.setMarkMark(sumAll);
    }


    /**
     * 计算否决
     *
     * @param m
     * @return
     */
    protected boolean isZRGFJ(SysDyzegMark m) {
        double result = BeanUtils.getDoubleValue(m.getFj1MarkSelf()) +
                BeanUtils.getDoubleValue(m.getFj2MarkSelf()) +
                BeanUtils.getDoubleValue(m.getFj3MarkSelf()) +
                BeanUtils.getDoubleValue(m.getFj4MarkSelf()) +
                BeanUtils.getDoubleValue(m.getFj5MarkSelf()) +
                BeanUtils.getDoubleValue(m.getFj6MarkSelf()) +
                BeanUtils.getDoubleValue(m.getFj7MarkSelf());
        String resultReason = m.getFj1ReasonSelf() + m.getFj2ReasonSelf() + m.getFj3ReasonSelf()
                + m.getFj4ReasonSelf() + m.getFj5ReasonSelf() + m.getFj6ReasonSelf() + m.getFj7ReasonSelf();
        if (result > 0 || (resultReason != null && resultReason.length() > 0)) {
            return true;
        }
        return false;
    }

    /**
     * 责任岗计算加分
     *
     * @param m
     * @return
     */
    protected double sumZRGJF(SysDyzegMark m) {
        double result = BeanUtils.getDoubleValue(m.getJf1MarkSelf()) +
                BeanUtils.getDoubleValue(m.getJf2MarkSelf()) +
                BeanUtils.getDoubleValue(m.getJf3MarkSelf()) +
                BeanUtils.getDoubleValue(m.getJf4MarkSelf()) +
                BeanUtils.getDoubleValue(m.getJf5MarkSelf()) +
                BeanUtils.getDoubleValue(m.getJf6MarkSelf());
        return result;
    }

    /**
     * 计算政治素质减分
     *
     * @param m
     * @return
     */
    protected double sumZRGZZSZ(SysDyzegMark m) {
        double result = BeanUtils.getDoubleValue(m.getZzsz1MarkSelf()) +
                BeanUtils.getDoubleValue(m.getZzsz2MarkSelf()) +
                BeanUtils.getDoubleValue(m.getZzsz3MarkSelf()) +
                BeanUtils.getDoubleValue(m.getZzsz4MarkSelf()) +
                BeanUtils.getDoubleValue(m.getZzsz5MarkSelf()) +
                BeanUtils.getDoubleValue(m.getZzsz6MarkSelf()) +
                BeanUtils.getDoubleValue(m.getZzsz47ArkSelf());
        return result;
    }

    /**
     * 计算岗位技能减分
     *
     * @param m
     * @return
     */
    protected double sumZRGGWJN(SysDyzegMark m) {
        double result = BeanUtils.getDoubleValue(m.getGwjn1MarkSelf()) +
                BeanUtils.getDoubleValue(m.getGwjn2MarkSelf()) +
                BeanUtils.getDoubleValue(m.getGwjn3MarkSelf()) +
                BeanUtils.getDoubleValue(m.getGwjn4MarkSelf());
        return result;
    }

    /**
     * 计算工作业绩减分
     *
     * @param m
     * @return
     */
    protected double sumZRGGZYJ(SysDyzegMark m) {
        double result = BeanUtils.getDoubleValue(m.getGzyj1MarkSelf()) +
                BeanUtils.getDoubleValue(m.getGzyj2MarkSelf()) +
                BeanUtils.getDoubleValue(m.getGzyj3MarkSelf()) +
                BeanUtils.getDoubleValue(m.getGzyj4MarkSelf()) +
                BeanUtils.getDoubleValue(m.getGzyj5MarkSelf()) +
                BeanUtils.getDoubleValue(m.getGzyj6MarkSelf());
        return result;
    }

    /**
     * 计算群众评价减分
     *
     * @param m
     * @return
     */
    protected double sumZRGQZPJ(SysDyzegMark m) {
        double result = BeanUtils.getDoubleValue(m.getQzpj1MarkSelf()) +
                BeanUtils.getDoubleValue(m.getQzpj2MarkSelf()) +
                BeanUtils.getDoubleValue(m.getQzpj3MarkSelf()) +
                BeanUtils.getDoubleValue(m.getQzpj4MarkSelf()) +
                BeanUtils.getDoubleValue(m.getQzpj5MarkSelf()) +
                BeanUtils.getDoubleValue(m.getQzpj6MarkSelf()) +
                BeanUtils.getDoubleValue(m.getQzpj7MarkSelf());
        return result;
    }
}
