package com.example.product.service;

import com.example.product.model.Product;

import java.util.List;

public interface ProductService {

    Product addProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(int id);
    Product updateProduct(Product product);
    void deleteProduct(int id);

}
