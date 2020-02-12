package com.numberone.system.service;

import com.numberone.common.base.AjaxResult;
import com.numberone.common.base.BaseEntity;
import com.numberone.common.utils.StatisticUtils;
import com.numberone.system.domain.SysDyzegMark;
import com.numberone.system.domain.SysMarkImport;
import com.numberone.system.mapper.base.BaseSysDyzegMarkMapper;

import java.util.List;
import java.util.Map;

/**
 * 党员责任岗评分业务层
 * 恰当的抽象保证最高决策层及业务模型决定层的复用
 * 在自评/小组评/党支部评逻辑基本相同的情况下使用泛型使数据类型与业务逻辑分离
 * 泛型T的类型为Dao层类型，复用Service层代码
 * 而Service层代码的面向抽象保证Controller层与Servcie层均依赖该抽象而不是具体实现
 * 但泛型的应用需注意，一旦应用泛型，则数据类型应该在代码中不被关心，即不应调用泛型的属性及方法
 */
public interface ISysdyzegMarkService {

    //传入T后给具体实现类的导航类一个ThreadLocal<Dao>变量，用构造方法接收该属性
    //以下为对业务模型所需要的组件进行抽象，其余具体实现需使用的方法抽取到具体实现类的父类中

    /**
     * 传入POJO对象，根据POJO的唯一标识mark_id查询唯一的一条记录数据
     *
     * @param m
     * @return
     */
    public List<SysDyzegMark> selectObject(SysDyzegMark m);

    /**
     * 根据用户唯一标识uid、年、月、季度、开始/结束时间查询单表数据
     *
     * @param m
     * @return
     */
    public List<SysDyzegMark> selectList(SysDyzegMark m);

    /**
     * 插入一条新纪录，但应该注意的是，自评单表插入主键自动生成、而组评及支部评插入时
     * 主键应使用对应自评的主键！
     *
     * @param m
     * @return
     */
    public AjaxResult insert(SysDyzegMark m);

    /**
     * 修改评分
     *
     * @param m
     * @return
     */
    public int update(SysDyzegMark m, int isZeroFlag);

    /**
     * 根据用户唯一标识uid、年、月、季度、开始/结束时间查询自评、组评、支部评联合查询数据
     *
     * @param m
     * @return
     */
    public List<Map<String, Object>> selectAllList(Map m);

    public List<SysMarkImport> selectImpotList(SysDyzegMark m);

    public int delete(String markId);


}
