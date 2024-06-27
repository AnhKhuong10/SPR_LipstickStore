package org.example.mystoreh.controller;

import org.example.mystoreh.entity.Brand;
import org.example.mystoreh.entity.Product;
import org.example.mystoreh.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("main")
public class BrandController {
    private BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/brands")
    public String index(Model model){
        List<Brand> brands = brandService.findAll();
        brands.forEach(System.out::println);
        model.addAttribute("brands", brands);
        return "layout";
    }
}
