package org.example.mystoreh.controller;

import jakarta.servlet.http.HttpSession;
import org.example.mystoreh.entity.*;
import org.example.mystoreh.service.BrandService;
import org.example.mystoreh.service.CategoryService;
import org.example.mystoreh.service.ProductImageService;
import org.example.mystoreh.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("main")
public class ProductController {

    private ProductService productService;
    private BrandService brandService;
    private CategoryService categoryService;
    private ProductImageService productImageService;

//    @Autowired
//    public MainController(ProductService productService) {
//        this.productService = productService;
//    }
    @Autowired
    public ProductController(ProductService productService, BrandService brandService, CategoryService categoryService,
                             ProductImageService productImageService) {
        this.productService = productService;
        this.brandService = brandService;
        this.categoryService = categoryService;
        this.productImageService = productImageService;
    }

    @ModelAttribute("brands")
    public List<Brand> populateBrands() {
        return brandService.findAll();
    }

    @ModelAttribute("category")
    public List<Category> Category(){
       return categoryService.findAll();
    }

    @GetMapping("/pages/products")
    public String index(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "8") int size,
            Model model)
    {
        model.addAttribute("title", "MyStore");
        //List<Product> products = productService.findAll();
        //products.forEach(System.out::println);
        //model.addAttribute("products", products);
        Page<Product> productPage = productService.findPaginated(page, size);
        model.addAttribute("productPage", productPage);
        return "pages/products";
    }

    //c1
//    @GetMapping("/find")
//    public String find(@RequestParam(name = "categoryId") Long id, Model model){
//        List<Product> products = productService.findByCategoryCategoryId(id);
//        model.addAttribute("products", products);
//        return "layout";
//    }

//    @GetMapping("/find")
//    public String find(@RequestParam Long categoryId, Model model){
//        List<Product> products = productService.findByCategoryCategoryId(categoryId);
//        model.addAttribute("products", products);
//        return "layout";
//    }

    //c2
    @GetMapping("/find/{id}")
    public String find(@PathVariable Long id, Model model) {
        List<Product> products = productService.findByCategoryId(id);
        model.addAttribute("products", products);
        return "pages/products";
    }

    @GetMapping("/search")
    public String searchByName(
            @RequestParam String productName,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "8") int size,
            Model model) {
        Page<Product> productPage = productService.findByProductNameContaining(productName, page, size);
        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", page);
        return "pages/products";
    }
//    @GetMapping("/search")
//    public String searchByName(@RequestParam String productName, Model model) {
//        List<Product> products = productService.findByProductNameContaining(productName);
//        model.addAttribute("products", products);
//        return "pages/products";
//    }

    @GetMapping("/findByBrand/{id}")
    public String findByBrand(
            @PathVariable Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "8") int size,
            Model model) {
        Page<Product> productPage = productService.findByBrandId(id, page, size);
        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", page);
        return "pages/products";
    }
//    @GetMapping("/findByBrand/{id}")
//    public String findByBrand(@PathVariable Long id, Model model) {
//        List<Product> products = productService.findByBrandId(id);
//        model.addAttribute("products", products);
//        return "pages/products";
//    }

    @GetMapping("/findByCategory/{id}")
    public String findByCategory(
            @PathVariable Long id,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "8") int size,
            Model model) {
        Page<Product> productPage = productService.findByCategoryId(id, page, size);
        model.addAttribute("productPage", productPage);
        model.addAttribute("currentPage", page);
        return "pages/products";
    }

//    @GetMapping("/findByCategory/{id}")
//    public String findByCategory(@PathVariable Long id, Model model) {
//        List<Product> products = productService.findByCategoryId(id);
//        model.addAttribute("products", products);
//        return "pages/products";
//    }

    @GetMapping("/detailProduct/{id}")
    public String getProductDetail(@PathVariable Long id, Model model) {
        Product product = productService.findProductById(id);
        //Product product = productService.findById(id).orElse(null);
        List<ProductImage> productImages = productImageService.findByProductId(product.getId());
        productImages.forEach(System.out::println);
        model.addAttribute("product", product);
        model.addAttribute("productImages", productImages);
        return "pages/product-detail";
    }

    @GetMapping("/products")
    public String getProductsPage(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");

        if (loggedInUser != null) {
            model.addAttribute("products", productService.findAll());
            return "pages/products";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/detail")
    public String detail() {
        return "detail";
    }

    @GetMapping("/test")
    public String test() {
        List<Product> products = productService.findByCategoryId(1L);
        products.forEach(System.out::println);
        return "detail";
    }
}
