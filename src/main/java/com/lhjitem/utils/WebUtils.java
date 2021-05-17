package com.lhjitem.utils;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class WebUtils {
    //这个类就是将Map中的值注入到相应的JavaBean属性中去
    //将这里的第一个参数传入直接改为map，这样可以让DAO层和service层也能使用这个，虽然HttpServletRequest获取后也是一个map，但局限性太高
    public static <T> T copyParamToBean( Map value , T bean ){
        try {
            /**
             * 把所有请求的参数都注入到user对象中
             */
            BeanUtils.populate(bean, value);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }


    /*将字符串转化成int类型*/
    public static int parseInt(String strInt,int defaultValue){
        try {
            return Integer.parseInt(strInt);
        } catch (Exception e) {
            /*e.printStackTrace();*/
        }
        return defaultValue;
    }
}
