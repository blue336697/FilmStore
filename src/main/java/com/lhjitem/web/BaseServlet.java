package com.lhjitem.web;

import com.lhjitem.utils.JdbcUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public abstract class BaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //解决post请求中文乱码问题（这种设置字符集的一定要写在代码的最前面）
        req.setCharacterEncoding("utf-8");
        //解决响应代码乱码问题
        resp.setContentType("text/html; charset=utf-8");
        String action = req.getParameter("action");

        //对注册和登录的两个servlet进行集成
        /*if ("login".equals(action)) {
        }else if("regist".equals(action)){
            regist(req,resp);
        }*/

        //对上的代码继续进行优化，不需要自己再来添加另外的功能，让其自动化
        try {
            //获取action业务鉴别字符串，获取相应业务 方法反射对象
            Method method = this.getClass().getDeclaredMethod(action,HttpServletRequest.class,HttpServletResponse.class);
            method.setAccessible(true);
            //调用目标业务 方法
            method.invoke(this,req,resp);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);//把异常抛给filter过滤器
        }
    }
}
