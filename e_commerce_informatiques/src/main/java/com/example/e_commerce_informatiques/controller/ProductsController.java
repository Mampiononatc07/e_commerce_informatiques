package com.example.e_commerce_informatiques.controller;

import org.springframework.web.bind.annotation.*;
import com.example.e_commerce_informatiques.model.Products;
import com.example.e_commerce_informatiques.service.ProductsService;

import java.sql.SQLException;
import java.util.List;

@RestController
public class ProductsController {

    private final ProductsService productsService;

    public ProductsController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @PostMapping("/products/newProducts")
    public boolean createProduct(@RequestBody Products product) throws SQLException {
        return productsService.createProduct(product);
    }

    @GetMapping("/products/{product_Id}")
    public Products getProductById(@PathVariable int product_Id) throws SQLException {
        return productsService.getProductById(product_Id);
    }

    @PutMapping("/products/{product_Id}/update")
    public boolean updateProduct(@PathVariable int product_Id, @RequestBody Products updatedProduct) throws SQLException {
        return productsService.updateProduct(
            product_Id,
            updatedProduct.getProduct_name(),
            updatedProduct.getDescription(),
            updatedProduct.getCategory(),
            updatedProduct.getPrice()
        );
    }

    @DeleteMapping("/products/delete/{product_Id}")
    public void deleteProduct(@PathVariable int product_Id) throws SQLException {
        productsService.deleteProduct(product_Id);
    }

    @GetMapping("/products")
    public List<Products> getAllProducts() throws SQLException {
        return productsService.getAllProducts();
    }
}
