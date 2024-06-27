package org.example.mystoreh.service;

import org.example.mystoreh.entity.ProductImage;

import java.util.List;

public interface ProductImageService {
    List<ProductImage> findByProductId(Long productId);
}
