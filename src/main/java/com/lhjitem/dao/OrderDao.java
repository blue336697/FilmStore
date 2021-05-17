package com.lhjitem.dao;

import com.lhjitem.pojo.File;
import com.lhjitem.pojo.Order;
import com.lhjitem.pojo.OrderItem;
import com.lhjitem.pojo.User;

import java.util.List;

public interface OrderDao {
    public int saveOrder(Order order);

    public List<File> queryOrders();

    public Integer changeOrderStatus(int orderId, String status);

    public Integer queryOrderByUserId(int userId);
}
