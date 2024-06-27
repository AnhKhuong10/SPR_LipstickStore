package org.example.mystoreh.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

    @Getter
    @Setter
    @Entity
    @Table(name = "[Category]")
    public class Category {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "category_id")
        private Long id;

        @Column(name = "category_name")
        private String categoryName;

        @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
        private List<Product> productList;
    }
