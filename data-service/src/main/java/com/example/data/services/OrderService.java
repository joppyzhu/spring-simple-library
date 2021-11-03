package com.example.data.services;

import com.example.data.Util;
import com.example.data.dao.OrderDao;
import com.example.data.models.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@ComponentScan({"com.example.library"})
public class OrderService {
  @Autowired
  OrderDao orderDao;

  public List<Order> findAll() {
    List<Order> orderList = orderDao.findAll();
    return orderList;
  }

  public long count() {
    long countItem = orderDao.count();
    return countItem;
  }

  public List<Order> findOrder(String value) {
    List<Order> orderList = orderDao.findByUsernameContaining(value);
    return orderList;
  }

  public Order findById(Integer id) {
    Order customer = orderDao.findOne(id);
    return customer;
  }

  public Order create(Order order) {
    return orderDao.save(order);
  }

  public Order update(Order order) {
    Order orderTemp = orderDao.findOne(order.getOrderId());
    Util.copyNonNullProperties(order, orderTemp);
    return orderDao.save(orderTemp);
  }

  public void delete(Integer id) {
    orderDao.deleteById(id);
  }


}
