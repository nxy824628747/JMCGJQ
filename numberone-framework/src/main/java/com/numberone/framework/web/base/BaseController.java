package com.numberone.framework.web.base;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.numberone.common.base.AjaxResult;
import com.numberone.common.page.PageDomain;
import com.numberone.common.page.TableDataInfo;
import com.numberone.common.page.TableSupport;
import com.numberone.common.utils.DateUtils;
import com.numberone.common.utils.StringUtils;
import com.numberone.framework.util.ShiroUtils;
import com.numberone.system.domain.SysUser;

/**
 * web层通用数据处理
 * 
 * @author numberone
 */
public class BaseController
{
    /**
     * 将前台传递过来的日期格式的字符串，自动转化为Date类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder)
    {
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport()
        {
            @Override
            public void setAsText(String text)
            {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    /**
     * 获得当前日期
     * @return
     */
    public String GetNowDate(){
        String temp_str="";
        Date dt = new Date();
        //最后的aa表示“上午”或“下午”    HH表示24小时制    如果换成hh表示12小时制
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss aa");
        temp_str=sdf.format(dt);
        return temp_str;
    }

    /**
     * 根据年月日计算季度
     * @param date
     * @return
     */
    public String getJiDu(String date){
        if(date==null&&date.length()<2){
            return "输入日期为"+date+",请输入正确格式的的年月日";
        }
        String month = date.substring(5,7);
        String jiDu=null;
        if("01".equals(month)||"02".equals(month)||"03".equals(month)){
            jiDu="01";
        }else if("04".equals(month)||"05".equals(month)||"06".equals(month)){
            jiDu="02";
        }else if("07".equals(month)||"08".equals(month)||"09".equals(month)){
            jiDu="03";
        }else if("10".equals(month)||"11".equals(month)||"12".equals(month)){
            jiDu="04";
        }
        return jiDu;
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage()
    {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtils.isNotNull(pageNum) && StringUtils.isNotNull(pageSize))
        {
            String orderBy = pageDomain.getOrderBy();
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 清空分页数据
     */
    protected void clearPage(){
        PageHelper.clearPage();
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected TableDataInfo getDataTable(List<?> list)
    {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }

    /**
     * 响应返回结果
     * 
     * @param rows 影响行数
     * @return 操作结果
     */
    protected AjaxResult toAjax(int rows)
    {
        return rows > 0 ? success() : error();
    }

    /**
     * 响应返回结果
     * 
     * @param result 结果
     * @return 操作结果
     */
    protected AjaxResult toAjax(boolean result)
    {
        return result ? success() : error();
    }

    /**
     * 返回成功
     */
    public AjaxResult success()
    {
        return AjaxResult.success();
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error()
    {
        return AjaxResult.error();
    }

    /**
     * 返回成功消息
     */
    public AjaxResult success(String message)
    {
        return AjaxResult.success(message);
    }

    /**
     * 返回失败消息
     */
    public AjaxResult error(String message)
    {
        return AjaxResult.error(message);
    }

    /**
     * 返回错误码消息
     */
    public AjaxResult error(int code, String message)
    {
        return AjaxResult.error(code, message);
    }

    /**
     * 页面跳转
     */
    public String redirect(String url)
    {
        return StringUtils.format("redirect:{}", url);
    }

    public SysUser getSysUser()
    {
        return ShiroUtils.getSysUser();
    }

    public void setSysUser(SysUser user)
    {
        ShiroUtils.setSysUser(user);
    }

    public Long getUserId()
    {
        return getSysUser().getUserId();
    }

    public String getLoginName()
    {
        return getSysUser().getLoginName();
    }
}
