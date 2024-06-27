package org.example.mystoreh.service.impl;

import org.example.mystoreh.entity.Product;
import org.example.mystoreh.entity.User;
import org.example.mystoreh.repository.ProductRepository;
import org.example.mystoreh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

//    C1
//    @Autowired
//    private ProductRepository repository;
    private ProductRepository repository;

    @Autowired
    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public Product findProductById(Long id) {
        return repository.findProductById(id);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Product findProductByProductNameAndBrandId(String name, Long brandId) {
        return repository.findProductByProductNameAndBrandId(name, brandId);
    }

    @Override
    public List<Product> findByCategoryId(Long id) {
        return repository.findByCategoryId(id);
    }

    @Override
    public List<Product> findByProductNameContaining(String productName) {
        return repository.findByProductNameContaining(productName);
    }

    @Override
    public List<Product> findByBrandId(Long id) {
        return repository.findByBrandId(id);
    }

    @Override
    public boolean updateProductQuantity(Long productId, int quantity) {
        Product product = repository.findById(productId).orElse(null);
        if (product != null) {
            int newQuantity = product.getProductQuantity() - quantity;
            if (newQuantity >= 0) {
                product.setProductQuantity(newQuantity);
                repository.save(product);
                return true; // Cập nhật thành công
            }
        }
        return false; // Cập nhật không thành công do số lượng không đủ
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Page<Product> findPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    @Override
    public Page<Product> findByProductNameContaining(String productName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByProductNameContaining(productName, pageable);
    }

    @Override
    public Page<Product> findByBrandId(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByBrandId(id, pageable);
    }

    @Override
    public Page<Product> findByCategoryId(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByCategoryId(id, pageable);
    }
}
