package com.example.product.controller;

import com.example.product.model.Product;
import java.util.List;

import com.example.product.service.ProductService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/api/product")
public class ProductController {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductService productService;

    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts(){
        logger.info("*** Getting Products from DB ***");
        List<Product> productList = productService.getAllProducts();
        logger.info("*** Products fetched from DB :: {}", productList);
        return ResponseEntity.ok().body(productList);
    };

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id){

        logger.info("*** Getting Product from DB for Id :: {}", id);
        Product product = productService.getProductById(id);

        if (StringUtils.isEmpty(product.getName()))
            return ResponseEntity.notFound().build();

        logger.info("*** Product fetched :: {}", product);
        return ResponseEntity.ok().body(product);
    };

    @PostMapping("/")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {

        logger.info("*** Adding Product to DB :: {}", product);
        Product savedProduct = productService.addProduct(product);
        logger.info("*** Product added to DB ***");

        return ResponseEntity.ok(savedProduct);
    }

    @PutMapping("/")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {

        logger.info("*** Updating product :: {}", product);
        Product updatedProduct = productService.updateProduct(product);
        logger.info("*** Updated product to DB ***");

        return ResponseEntity.ok().body(updatedProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {

        logger.info("*** Deleting Product from DB for Id :: {}", id);
        productService.deleteProduct(id);
        logger.info("*** Deleted Product from DB for Id :: {}", id);

        return ResponseEntity.ok().body("Deleted successfully...!");
    }

}


