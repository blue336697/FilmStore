package com.lhjitem.dao.impl;

import com.lhjitem.dao.OrderItemDao;
import com.lhjitem.pojo.OrderItem;

import java.util.List;

public class OrderItemDaoImpl extends BaseDao implements OrderItemDao {

    @Override
    public int saveOrderItem(OrderItem orderItem) {
        String sql = "insert into a_order_item(`id`,`name`,`count`,`price`,`totalPrice`,`orderId`) values (?,?,?,?,?,?)";
        return update(sql,orderItem.getId(),orderItem.getName(),orderItem.getCount(),orderItem.getPrice(),orderItem.getTotalPrice(),orderItem.getOrderId());
    }

    @Override
    public List<OrderItem> queryOrderItemsByOrderId(int orderId) {
        return null;
    }
}
