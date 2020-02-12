package com.numberone.common.utils.bean;

import com.numberone.common.json.JSONObject;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.ui.ModelMap;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Bean 工具类
 *
 * @author numberone
 */
public class BeanUtils {
    /**
     * Bean方法名中属性名开始的下标
     */
    private static final int BEAN_METHOD_PROP_INDEX = 3;

    /**
     * 匹配getter方法的正则表达式
     */
    private static final Pattern GET_PATTERN = Pattern.compile("get(\\p{javaUpperCase}\\w*)");

    /**
     * 匹配setter方法的正则表达式
     */
    private static final Pattern SET_PATTERN = Pattern.compile("set(\\p{javaUpperCase}\\w*)");

    /**
     * Bean属性复制工具方法。
     *
     * @param dest 目标对象
     * @param src  源对象
     */
    public static void copyBeanProp(Object dest, Object src) {
        List<Method> destSetters = getSetterMethods(dest);
        List<Method> srcGetters = getGetterMethods(src);
        try {
            for (Method setter : destSetters) {
                for (Method getter : srcGetters) {
                    if (isMethodPropEquals(setter.getName(), getter.getName())
                            && setter.getParameterTypes()[0].equals(getter.getReturnType())) {
                        setter.invoke(dest, getter.invoke(src));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取对象的setter方法。
     *
     * @param obj 对象
     * @return 对象的setter方法列表
     */
    public static List<Method> getSetterMethods(Object obj) {
        // setter方法列表
        List<Method> setterMethods = new ArrayList<Method>();

        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();

        // 查找setter方法

        for (Method method : methods) {
            Matcher m = SET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 1)) {
                setterMethods.add(method);
            }
        }
        // 返回setter方法列表
        return setterMethods;
    }

    /**
     * 获取对象的getter方法。
     *
     * @param obj 对象
     * @return 对象的getter方法列表
     */

    public static List<Method> getGetterMethods(Object obj) {
        // getter方法列表
        List<Method> getterMethods = new ArrayList<Method>();
        // 获取所有方法
        Method[] methods = obj.getClass().getMethods();
        // 查找getter方法
        for (Method method : methods) {
            Matcher m = GET_PATTERN.matcher(method.getName());
            if (m.matches() && (method.getParameterTypes().length == 0)) {
                getterMethods.add(method);
            }
        }
        // 返回getter方法列表
        return getterMethods;
    }

    /**
     * 检查Bean方法名中的属性名是否相等。<br>
     * 如getName()和setName()属性名一样，getName()和setAge()属性名不一样。
     *
     * @param m1 方法名1
     * @param m2 方法名2
     * @return 属性名一样返回true，否则返回false
     */

    public static boolean isMethodPropEquals(String m1, String m2) {
        return m1.substring(BEAN_METHOD_PROP_INDEX).equals(m2.substring(BEAN_METHOD_PROP_INDEX));
    }

    /**
     * 将POJO对象转为JSON对象
     *
     * @param obj 需要转为json对象的对象
     * @return
     * @Time 2019/06/16
     */
    public static JSONObject getJSON(Object obj) {
        JSONObject json = new JSONObject();
        if (obj != null) {
            Class cls = obj.getClass();
            Field[] fieldArray = cls.getDeclaredFields();
            for (Field field : fieldArray) {
                String fieldName = field.getName();
                String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                    Method method = cls.getMethod(methodName);
                    Object fieldObject = method.invoke(obj, new Object[0]);
                    String fieldValue = null;
                    if (fieldObject != null) {
                        fieldValue = fieldObject.toString();
                    }
                    json.put(fieldName, fieldValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return json;
    }

    /**
     * POJO拼装进modelMap
     *
     * @param obj
     * @return
     */
    public static void getModelMap(Object obj, ModelMap m) {
        if (obj != null) {
            Class cls = obj.getClass();
            Field[] fieldArray = cls.getDeclaredFields();
            for (Field field : fieldArray) {
                String fieldName = field.getName();
                String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                    Method method = cls.getMethod(methodName);
                    Object fieldObject = method.invoke(obj, new Object[0]);
                    String fieldValue = null;
                    if (fieldObject != null) {
                        fieldValue = fieldObject.toString();
                    }
                    m.put(fieldName, fieldValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将List<T>转为JSONArray
     *
     * @param l
     * @return
     * @Time 2019/06/16
     */
    public static JSONObject.JSONArray getJSONArr(List l) {
        JSONObject.JSONArray array = new JSONObject.JSONArray();
        if (l.size() > 0) {
            for (Object o : l) {
                array.add(getJSON(o));
            }
        }
        return array;
    }

    /**
     * 将List<T>转为JSONArray
     *
     * @param l
     * @return
     * @Time 2019/06/16
     */
    public static JSONObject.JSONArray getJSONArr2(List<String> l) {
        JSONObject.JSONArray array = new JSONObject.JSONArray();
        if (l.size() > 0) {
            for (String s : l) {
                array.add(s);
            }
        }
        return array;
    }

    /**
     * JSONArray 转 List<JSONObject>
     *
     * @param array
     * @return
     * @Time 2019/06/16
     */
    public static ArrayList<JSONObject> getList(JSONObject.JSONArray array) {
        ArrayList<JSONObject> list = new ArrayList<>();
        int arraySize = array.size();
        if (array != null && arraySize > 0) {
            for (int i = 0; i < arraySize; i++) {
                list.add((JSONObject) array.get(i));
            }
        }
        return list;
    }

    /**
     * 合并两个List
     */
    public static List combineLists(List<String> list1, List<String> list2) {
        List result = new ArrayList();
        if ((list1 == null || list1.size() == 0) && (list2 == null || list2.size() == 0)) {
            return result;
        }
        for (int i = 0; i < list1.size(); i++) {
            Object o = list1.get(i);
            result.add(o);
        }
        for (int j = 0; j < list2.size(); j++) {
            Object o = list2.get(j);
            result.add(o);
        }
        return result;
    }

    /**
     * List去除null元素
     *
     * @param l
     * @return
     */
    public static ArrayList cleanNull(List l) {
        ArrayList resultL = new ArrayList();
        int lSize = l.size();
        if (l != null && lSize > 0) {
            for (int i = 0; i < lSize; i++) {
                Object o = l.get(i);
                if (o != null) {
                    resultL.add(o);
                }
            }
        }
        return resultL;
    }


    /**
     * 两个POJO对象的List按数据库主表从表对应关系合并为一个List<JSONObject>
     * 从表记录为空则只返回主表信息
     *
     * @param list1 主表List
     * @param k1    主表主键
     * @param list2 从表List
     * @param k2    从表外键
     * @return
     * @Time 2019/06/16
     */
    public static ArrayList<JSONObject> combinPOJOList(List list1, String k1, List list2, String k2) {
        //存放list1
        JSONObject.JSONArray array1 = getJSONArr(list1);
        //存放list2
        JSONObject.JSONArray array2 = getJSONArr(list2);
        //存放结果
        JSONObject.JSONArray resultArr = new JSONObject.JSONArray();
        //允许从表没有记录，但主表必须有记录
        if (list2.size() > 0) {
            //遍历父表
            for (int i = 0; i < array1.size(); i++) {
                JSONObject jsoni = (JSONObject) array1.get(i);
                //父表主键
                String parentK = jsoni.getStr(k1);
                //遍历子表
                for (int j = 0; j < array2.size(); j++) {
                    JSONObject jsonj = (JSONObject) array2.get(j);
                    //子表外键
                    String sonK = jsonj.getStr(k2);
                    //外键与主键匹配时合并该条记录
                    if (parentK != null && parentK.equals(sonK)) {
                        jsoni.putAll(jsonj);
                    }
                }
            }
            resultArr = array1;
        } else {
            resultArr = array1;
        }
        return getList(resultArr);
    }


    public static double getDoubleValue(Double d) {
        if (d == null) {
            return 0;
        }
        //针对评分管理，评分必须为正数
        if (d < 0) {
            d = d * (-1);
        }
        return d.doubleValue();
    }


    /**
     * @Author Nxy
     * @Date 2020/2/12 12:43
     * @Param sourceMap:源Map target:目标对象的Class对象
     * @Return 目标对象的实例
     * @Exception
     * @Description 将 Map 转为相关对象，浅拷贝
     */
    public static Object getBeanFromMap(Map sourceMap, Class target) throws IllegalAccessException, InstantiationException {
        if (sourceMap == null || target == null) {
            throw new NullPointerException("sourceMap or target Class is null!");
        }
        Object o = target.newInstance();
        Field[] fs = target.getDeclaredFields();
        int fLength = fs.length;
        if (fLength == 0) {
            throw new RuntimeException("Fields of Class is null!");
        }
        Set mKeySet = sourceMap.keySet();
        if (mKeySet == null || mKeySet.size() == 0) {
            throw new RuntimeException("keySet of Map is null!");
        }

        for (int i = 0; i < fLength; i++) {
            Field fieldI = fs[i];
            //取消访问检查
            fieldI.setAccessible(true);
            String fieldName = fieldI.getName();
            if (mKeySet.contains(fieldName)) {
                setFieldValue(o, fieldI, sourceMap.get(fieldName));
            }
        }
        return o;
    }

    /**
     * @Author Nxy
     * @Date 2020/2/12 14:14
     * @Param target:目标对象，field：待赋值属性属性对象，value：属性值
     * @Return
     * @Exception
     * @Description 反射为属性赋值
     */
    public static void setFieldValue(Object target, Field field, Object value) throws IllegalAccessException {
        String fieldType = field.getGenericType().toString();
        switch (fieldType) {
            case "class java.lang.String":
                field.set(target, String.valueOf(value));
                break;
            case "class java.lang.Integer":
                field.set(target, Integer.valueOf(value.toString()));
                break;
            case "class java.lang.Double":
                field.set(target, Double.valueOf(value.toString()));
                break;
            case "class java.lang.Float":
                field.set(target, Float.valueOf(value.toString()));
                break;
            case "int":
                field.set(target, Integer.valueOf(value.toString()));
                break;
            case "float":
                field.set(target, Float.valueOf(value.toString()));
                break;
            case "boolean":
                field.set(target, Boolean.valueOf(value.toString()));
                break;
            default:
                field.set(target, value);
        }
    }
}
