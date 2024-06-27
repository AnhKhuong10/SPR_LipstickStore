package org.example.mystoreh.controller.admin;

import org.example.mystoreh.entity.Product;
import org.example.mystoreh.service.ProductService;
import org.example.mystoreh.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Controller
@RequestMapping("admin/report")
public class ReportController {

    private final ReportService reportService;
    private final ProductService productService;

    @Autowired
    public ReportController(ReportService reportService, ProductService productService) {
        this.reportService = reportService;
        this.productService = productService;
    }

    @GetMapping("/excel")
    public ResponseEntity<ByteArrayResource> outputExcelListProduct() {
        try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
            String fileName = "PRODUCTS.xlsx";
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "vnd.ms-excel"));
            header.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
            List<Product> products = this.productService.findAll();
            reportService.outputExcelListProduct(stream, products);
            return new ResponseEntity<>(
                    new ByteArrayResource(stream.toByteArray()),
                    header,
                    HttpStatus.CREATED
            );
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
