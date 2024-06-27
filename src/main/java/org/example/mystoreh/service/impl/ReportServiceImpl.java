package org.example.mystoreh.service.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.mystoreh.entity.Product;
import org.example.mystoreh.service.ReportService;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {
    @Override
    public void outputExcelListProduct(ByteArrayOutputStream stream, List<Product> list) throws IOException {
        try (XSSFWorkbook wb = new XSSFWorkbook()) {
            XSSFSheet sheet = wb.createSheet("products");

            int rowCount = 0;
            XSSFRow headerRow = sheet.createRow(rowCount++);
//            String[] headers = {"ID", "Product Name", "Product Image", "Product Price", "Product Quantity", "Product Description", "Discount"};
//            for (int i = 0; i < headers.length; i++) {
//                XSSFCell cell = headerRow.createCell(i);
//                cell.setCellValue(headers[i]);
//            }

            for (Product product : list) {
                XSSFRow row = sheet.createRow(rowCount++);
                int cellCount = 0;
                row.createCell(cellCount++).setCellValue(product.getId());
                row.createCell(cellCount++).setCellValue(product.getProductName());
                row.createCell(cellCount++).setCellValue(product.getProductImage());
                row.createCell(cellCount++).setCellValue(product.getProductPrice());
                row.createCell(cellCount++).setCellValue(product.getProductQuantity());
                row.createCell(cellCount++).setCellValue(product.getProductDescription());
                row.createCell(cellCount).setCellValue(product.getCategory().getCategoryName());
                row.createCell(cellCount).setCellValue(product.getBrand().getBrandName());

            }

            wb.write(stream);
// File file = new File("test.xlsx");
// try(
// FileOutputStream fileOutputStream = new FileOutputStream(file);
// ) {
// wb.write(fileOutputStream);
// System.out.println("OK");
// } catch (Exception e) {
// System.out.println("Error");
// }
        }
    }

//    @Override
//    public void outputExcelListProduct(ByteArrayOutputStream stream, List<Product> productList) throws IOException {
//        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
//            XSSFSheet sheet = workbook.createSheet("productList");
//
//            int rowCount = 0;
//            for (Product product : productList) {
//                XSSFRow row = sheet.createRow(rowCount++);
//
//                int cellCount = 0;
//                for (int i = 0; i < Product.class.getDeclaredFields().length; i++) {
//                    Cell cell = row.createCell(cellCount++);
//                    if (cellCount == 1) {
//                        cell.setCellValue(product.getId());
//                    }
//                    if (cellCount == 2) {
//                        cell.setCellValue(product.getProductName());
//                    }
//                    if (cellCount == 3) {
//                        cell.setCellValue(product.getProductImage());
//                    }
//                    if (cellCount == 4) {
//                        cell.setCellValue(product.getProductPrice());
//                    }
//                    if (cellCount == 5) {
//                        cell.setCellValue(product.getProductQuantity());
//                    }
//                    if (cellCount == 6) {
//                        cell.setCellValue(product.getProductDescription());
//                    }
//                    if (cellCount == 7) {
//                        cell.setCellValue(product.getCategory().getCategoryName());
//                    }
//                    if (cellCount == 8) {
//                        cell.setCellValue(product.getBrand().getBrandName());
//                    }
//                    if (cellCount == 9) {
//                        cell.setCellValue(product.getProductImageList().size());
//                    }
//                }
//                workbook.write(stream);
//            }
//        }
//        //java core
////        File file = new File("test.xlsx");
////        try(
////                FileOutputStream fileOutputStream = new FileOutputStream(file);
////        ) {
////            workbook.write(fileOutputStream);
////            System.out.println("OK");
////        } catch (Exception e) {
////            System.out.println("Error");
////        }
//    }
}
