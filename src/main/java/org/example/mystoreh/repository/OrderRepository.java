package org.example.mystoreh.repository;

import org.example.mystoreh.entity.Category;
import org.example.mystoreh.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Category, Long> {
    Order save(Order order);
}
