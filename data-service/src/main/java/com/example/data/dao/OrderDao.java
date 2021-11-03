package com.example.data.dao;

import com.example.data.models.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDao extends CrudRepository<Order, Integer> {
  public List<Order> findAll();

  public List<Order> findByUsernameContaining(String username);

  @Query(value = "SELECT * FROM simplelibrary.order WHERE order_id = :id", nativeQuery = true)
  public Order findOne(@Param("id") Integer id);

}
