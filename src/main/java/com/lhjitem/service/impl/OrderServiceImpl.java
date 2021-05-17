package com.lhjitem.service.impl;

import com.lhjitem.dao.FileDao;
import com.lhjitem.dao.OrderDao;
import com.lhjitem.dao.OrderItemDao;
import com.lhjitem.dao.impl.FileDaoImpl;
import com.lhjitem.dao.impl.OrderDaoImpl;
import com.lhjitem.dao.impl.OrderItemDaoImpl;
import com.lhjitem.pojo.*;
import com.lhjitem.service.OrderService;
//import com.sun.org.apache.xpath.internal.operations.Or;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();
    private OrderItemDao orderItemDao = new OrderItemDaoImpl();
    private FileDao fileDao = new FileDaoImpl();

    @Override
    public String createOrder(Cart cart, Integer userId) {
        //订单号唯一性
        String orderId = System.currentTimeMillis() + "" + userId;
        //创建订单项
        Order order = new Order(orderId,new Date(),cart.getTotalPrice(),0,userId);
        //保存订单项
        orderDao.saveOrder(order);

        //订单项其实就是购物车中的那些购物车项目，我们将已有的项目转化成订单项就可以了
        //遍历购物车中的每一个项目转换成为订单项保存到数据库
        for(Map.Entry<Integer, CartItem>entry : cart.getItems().entrySet()){
            //获取购物车中的每一个商品项
            CartItem cartItem = entry.getValue();
            //将购物车项转换成订单项
            OrderItem orderItem = new OrderItem(null,cartItem.getName(),cartItem.getCount(),cartItem.getPrice(),cartItem.getTotalPrice(),orderId);
            //保存到数据库
            orderItemDao.saveOrderItem(orderItem);

            //对销量和库存进行更行
            File file = fileDao.queryFileById(cartItem.getId());
            file.setSales(file.getSales() + 1);
            file.setStock(file.getStock() - 1);
        }
        //结完账购物车自动清空
        cart.clear();
        return orderId;
    }

    @Override
    public List<Order> showAllOrders() {
        return null;
    }

    @Override
    public void sendOrder(int orderId) {

    }

    @Override
    public List<OrderItem> showOrderDetail(int orderId) {
        return null;
    }

    @Override
    public List<Order> showMyOrders(int userId) {
        return null;
    }

    @Override
    public void receiverOrder(int orderId) {

    }
}
