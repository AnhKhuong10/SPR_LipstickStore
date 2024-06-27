package org.example.mystoreh.repository;

import org.example.mystoreh.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository  extends JpaRepository<Brand, Long> {
    List<Brand> findAll();
}
