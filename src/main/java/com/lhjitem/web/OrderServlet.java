package com.lhjitem.web;

import com.lhjitem.pojo.Cart;
import com.lhjitem.pojo.User;
import com.lhjitem.service.OrderService;
import com.lhjitem.service.impl.OrderServiceImpl;
import com.lhjitem.utils.JdbcUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderServlet extends BaseServlet{
    private OrderService orderService = new OrderServiceImpl();
    /**
     * 生成订单
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    protected void createOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取cart购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        //2.获取userId,这里要做一个判断，判断用户是否已经登录，没有登陆拿来的结账，也更不可能有用户的id值了
        User loginUser = (User) req.getSession().getAttribute("user");
        if(loginUser != null){
            Integer userId = loginUser.getId();
            //3.调用orderService.createOrder(cart,userId);生成订单(与数据库进行交互),返回订单号
            String orderId = null;
            orderId = orderService.createOrder(cart, userId);

            //4.保存订单号到rep域中,但由于重定向无法保存到req域中，所以我们采用session域的方法保存
//            req.setAttribute("orderId",orderId);
            req.getSession().setAttribute("orderId",orderId);
            //5.请求转发到结账成功返回订单号的页面（改用重定向，防止表单重复提交）
            System.out.println(req.getContextPath());
            //http://localhost:8080/FileStore/
//            resp.sendRedirect(req.getContextPath()+"/pages/cart/checkout.jsp");
            resp.sendRedirect("http://localhost:8080/FileStore/pages/cart/checkout.jsp");
        }else {
            //如果没有登录则跳到登录页面
            req.getRequestDispatcher("/pages/user/login.jsp").forward(req,resp);
        }

    }
}
