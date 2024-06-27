package org.example.mystoreh.service.impl;

import org.example.mystoreh.entity.Item;
import org.example.mystoreh.entity.Order;
import org.example.mystoreh.repository.OrderRepository;
import org.example.mystoreh.repository.ProductRepository;
import org.example.mystoreh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }
}
