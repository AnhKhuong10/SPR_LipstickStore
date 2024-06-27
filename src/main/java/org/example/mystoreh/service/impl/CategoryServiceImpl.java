package org.example.mystoreh.service.impl;

import org.example.mystoreh.entity.Category;
import org.example.mystoreh.repository.CategoryRepository;
import org.example.mystoreh.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private CategoryRepository categoryRepository;
    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }
    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
}
