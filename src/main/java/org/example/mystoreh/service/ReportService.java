package org.example.mystoreh.service;

import org.example.mystoreh.entity.Product;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public interface ReportService {
    void outputExcelListProduct(ByteArrayOutputStream stream, List<Product> productList) throws IOException;
}
