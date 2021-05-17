package com.lhjitem.web;

import com.google.gson.Gson;
import com.lhjitem.pojo.User;
import com.lhjitem.service.UserService;
import com.lhjitem.service.impl.UserServiceImpl;
import com.lhjitem.utils.JdbcUtils;
import com.lhjitem.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

public class UserServlet extends BaseServlet {

    //因为页面曾调用不了Dao层的数据，所以我们需要创建一个US对象来验证数据
    private UserService userService = new UserServiceImpl();

    /*实现注销用户功能*/
    protected void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.销毁session中的用户信息（或者直接销毁session）
        req.getSession().invalidate();

        //2.重定向到首页
        resp.sendRedirect(req.getContextPath());
    }

    //由于代码的冗余和难维护性，我们将登录和注册两个功能分别用两个方法实现
    protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //处理登录
        //1.获取请求参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        //2.调用service登录处理业务
        User loginUser = userService.login(new User(null, username, password, null));
        if(loginUser == null){
            System.out.println("用户名、密码错误或用户不存在");
            //把错误信息，和回显的表单项信息，保存到request域中
            req.setAttribute("msg","用户名、密码错误或用户不存在");
            req.setAttribute("username",username);
            //跳回登录页面(请求转发)
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
        }else {
            //跳到登陆成功页面
            //保存用户登录成功之后的用户信息到session域中
            req.getSession().setAttribute("user",loginUser);
            System.out.println("登陆成功");
            req.getRequestDispatcher("/pages/user/login_success.jsp").forward(req,resp);
        }
    }


    protected void regist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //一、处理验证码
        //1.获取session中的验证码
        String token = (String) req.getSession().getAttribute(KAPTCHA_SESSION_KEY);
        //2.马上删除验证码
        req.getSession().removeAttribute(KAPTCHA_SESSION_KEY);
        //3.获得表单中的验证码
        String code = req.getParameter("code");


        //二、处理注册
        //1.获取请求的参数(用户名、密码、邮箱、验证码)
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");


        //使用beanutils工具类对对象进行封装
        User user = WebUtils.copyParamToBean(req.getParameterMap(),new User());

        //2.检查验证码是否正确，由于该知识点还未讲，所以先将验证码写死：abcde,记得忽略大小写
        if (token != null && token.equalsIgnoreCase(code)) {
            //两种结果，正确的话就要检查用户名和密码还有邮箱是否合法
            //检查用户名是否可用
            if (userService.existsUsername(username) == false) {
                //返回false就是可用,可用就要提供给注册的函数，然后提交给数据库
                userService.registerUser(new User(null, username, password, email));
                //注册成功，跳转到成功页面
                req.getRequestDispatcher("/pages/user/regist_success.jsp").forward(req, resp);
            } else {
                //返回true就是不可用,不可用就要跳回注册页面
                //把回显的信息保存到request域中
                req.setAttribute("msg", "用户名已存在");
                req.setAttribute("username", username);
                req.setAttribute("email", email);
                System.out.println("用户名[" + username + "]已存在");
                req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
            }

        } else {
            //不正确，就要跳回注册页面
            //把回显的信息保存到request域中
            req.setAttribute("msg", "验证码错误！");
            req.setAttribute("username", username);
            req.setAttribute("email", email);

            System.out.println("验证码[" + code + "]错误");
            req.getRequestDispatcher("/pages/user/regist.jsp").forward(req, resp);
        }
    }


    protected void ajaxExistsUsername(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的参数
        String username = req.getParameter("username");
        //2.调用service的方法进行核验
//        if(userService.existsUsername(username) == false){
//            //等于false就是没有
//        }
        boolean existsUsername = userService.existsUsername(username);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("existsUsername",existsUsername);
        //3.将map转换为json对象
        Gson gson = new Gson();
        String json = gson.toJson(resultMap);
        //响应传回json字符
        resp.getWriter().write(json);

    }
}
