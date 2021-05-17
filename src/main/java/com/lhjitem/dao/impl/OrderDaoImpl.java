package com.lhjitem.dao.impl;

import com.lhjitem.dao.OrderDao;
import com.lhjitem.pojo.File;
import com.lhjitem.pojo.Order;
import com.lhjitem.pojo.OrderItem;
import com.lhjitem.pojo.User;

import java.util.List;

public class OrderDaoImpl extends BaseDao implements OrderDao {


    @Override
    public int saveOrder(Order order) {
        String sql = "insert into a_order(`orderId`,`createTime`,`price`,`status`,`userId`)values(?,?,?,?,?)";
        return update(sql,order.getOrderId(),order.getCreateTime(),order.getPrice(),0,order.getUserId());
    }

    @Override
    public List<File> queryOrders() {
        return null;
    }

    @Override
    public Integer changeOrderStatus(int orderId, String status) {
        return null;
    }

    @Override
    public Integer queryOrderByUserId(int userId) {
        return null;
    }
}
