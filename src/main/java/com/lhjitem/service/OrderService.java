package com.lhjitem.service;

import com.lhjitem.pojo.Cart;
import com.lhjitem.pojo.Order;
import com.lhjitem.pojo.OrderItem;

import java.util.List;

public interface OrderService {
    public String createOrder(Cart cart,Integer userId);

    public List<Order> showAllOrders();

    public void sendOrder (int orderId);

    public List<OrderItem> showOrderDetail(int orderId);

    public List<Order> showMyOrders(int userId);

    public void receiverOrder(int orderId);
}
