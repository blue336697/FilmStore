package com.lhjitem.web;

import com.google.gson.Gson;
import com.lhjitem.pojo.Cart;
import com.lhjitem.pojo.CartItem;
import com.lhjitem.pojo.File;
import com.lhjitem.service.FileService;
import com.lhjitem.service.impl.FileServiceImpl;
import com.lhjitem.utils.WebUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class CartServlet extends BaseServlet{
    private FileService fileService = new FileServiceImpl();
    //加入购物车
    protected void addItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取请求的参数 商品编号
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        //2.调用fileService.queryFileById，得到file的信息
        File file = fileService.queryFileById(id);
        //3.把影片信息转为cartItem商品项
        CartItem cartItem = new CartItem(file.getId(),file.getName(),1,file.getPrice(),file.getPrice());
        //4.调用cart.addItem，添加商品项
        //Cart cart = new Cart(); 不能直接这样写，因为每次响应都要创建一个购物车，里面的影片总数叠加无效
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if(cart == null){
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }
        cart.addItem(cartItem);
        //获取最后一个添加到购物车的影片名字
        req.getSession().setAttribute("lastName",cartItem.getName());
//        System.out.println(cart);
        //5.重定向到商品列表页面
        //为了优化在不同页加入购物车，返回网址还是在原来不变的页，我们采用http请求头中的referer栏，来写返回地址
        //referer栏中的信息就是请求的网页的地址，达到出去回来都是同一个地方
        resp.sendRedirect(req.getHeader("Referer"));
    }

    //删除购物车项目
    protected void deleteItem(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取商品对象
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        //2.获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        //3.判断是否为空，不为空删除
        if(cart != null){
            cart.deleteItem(id);
            //4.保持当前页面不变并且刷新
            resp.sendRedirect(req.getHeader("Referer"));
        }

    }

    //清空购物车
    protected void clear(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.获取购物车对象
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if(cart != null){
            cart.clear();
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }

    //更改影片数量
    protected void updateCount(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取请求的商品编号
        int id = WebUtils.parseInt(req.getParameter("id"), 0);
        int count = WebUtils.parseInt(req.getParameter("count"),1);
        //调用cart对象中的updateCount方法
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if(cart != null){
            cart.updateCount(id,count);
            //更新完后回到本页面
            resp.sendRedirect(req.getHeader("Referer"));
        }
    }

    //用ajax的方法将添加进购物车进行重新
    protected void addItemAjax(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取商品编号
        int id = WebUtils.parseInt(req.getParameter("id"),0);
        //调用fileService.queryFileById，得到file的信息
        File file = fileService.queryFileById(id);
        //把file对象转换成cartItem
        CartItem cartItem = new CartItem(file.getId(),file.getName(),1,file.getPrice(),file.getPrice());
        //获取session的购物车对象cart
        Cart cart = (Cart) req.getSession().getAttribute("cart");
        if(cart == null){
            cart = new Cart();
            req.getSession().setAttribute("cart",cart);
        }
        //调用cart.addItem添加商品项
        cart.addItem(cartItem);
        //获取最后一个添加到购物车的影片名字
        req.getSession().setAttribute("lastName",cartItem.getName());
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("totalCount",cart.getTotalCount());
        resultMap.put("lastName",cartItem.getName());

        Gson gson = new Gson();
        String jsonMapString = gson.toJson(resultMap);
        //响应返回json字符串对象
        resp.getWriter().write(jsonMapString);

    }
}
