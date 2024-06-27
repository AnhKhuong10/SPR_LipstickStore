package org.example.mystoreh.service.impl;

import org.example.mystoreh.entity.ProductImage;
import org.example.mystoreh.repository.ProductImageRepository;
import org.example.mystoreh.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;

    @Autowired
    public ProductImageServiceImpl(ProductImageRepository productImageRepository) {
        this.productImageRepository = productImageRepository;
    }

    @Override
    public List<ProductImage> findByProductId(Long productId) {
        return productImageRepository.findByProductId(productId);
    }
}