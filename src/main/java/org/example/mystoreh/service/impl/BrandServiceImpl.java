package org.example.mystoreh.service.impl;

import org.example.mystoreh.entity.Brand;
import org.example.mystoreh.repository.BrandRepository;
import org.example.mystoreh.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {
    private BrandRepository repository;

    @Autowired
    public BrandServiceImpl(BrandRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Brand> findAll() {
        return repository.findAll();
    }
}
