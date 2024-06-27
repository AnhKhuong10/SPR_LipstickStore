package org.example.mystoreh.service;

import org.example.mystoreh.entity.Product;
import org.example.mystoreh.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();
    Product findProductById(Long id);
    Optional<Product> findById(Long id);
    Product findProductByProductNameAndBrandId(String name, Long brandId);
    List<Product> findByCategoryId(Long id);
    List<Product> findByProductNameContaining(String productName);
    List<Product> findByBrandId(Long id);
    boolean updateProductQuantity(Long productId, int quantity);
    Product save(Product product);
    Page<Product> findPaginated(int page, int size);

    Page<Product> findByProductNameContaining(String productName, int page, int size);
    Page<Product> findByBrandId(Long id, int page, int size);
    Page<Product> findByCategoryId(Long id, int page, int size);
}
