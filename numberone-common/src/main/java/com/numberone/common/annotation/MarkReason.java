package com.numberone.common.annotation;import java.lang.annotation.ElementType;import java.lang.annotation.Retention;import java.lang.annotation.RetentionPolicy;import java.lang.annotation.Target;/** * @Author Nxy * @Date 2020/2/15 13:17 * @Description 自定义评分原因注解 */@Retention(RetentionPolicy.RUNTIME)@Target(value = ElementType.FIELD)public @interface MarkReason {    //评分项目名称    public String reasonName();    //是否减分项    public boolean isSubtraction() default true;    //是否否决项    public boolean isFouJue() default false;}