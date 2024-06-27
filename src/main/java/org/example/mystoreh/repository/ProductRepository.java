package org.example.mystoreh.repository;

import org.example.mystoreh.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();
    Product findProductById(Long id);
    Optional<Product> findById(Long id);
    Product findProductByProductNameAndBrandId(String name, Long brandId);
    List<Product> findByCategoryId(Long id);
    List<Product> findByProductNameContaining(String productName);
    List<Product> findByBrandId(Long id);
    Product save(Product product);
    Page<Product> findByProductNameContaining(String productName, Pageable pageable);
    Page<Product> findByBrandId(Long id, Pageable pageable);
    Page<Product> findByCategoryId(Long id, Pageable pageable);
}
