package com.example.product.controller;

import com.example.product.constants.constants;
import com.example.product.model.Product;

import java.util.List;
import java.util.Objects;

import com.example.product.response.ApiResponse;
import com.example.product.response.GetAllProductResponse;
import com.example.product.response.GetProductByIdResponse;
import com.example.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<GetAllProductResponse<List<Product>>> getAllProducts() {
        logger.info("*** Getting Products from DB ***");
        List<Product> productList = productService.getAllProducts();

        GetAllProductResponse<List<Product>> response;

        if (productList.isEmpty()) {
            response = new GetAllProductResponse<>(HttpStatus.NOT_FOUND.value(),constants.API_RESPONSE_MESSAGES.NO_DATA_FOUND_MESSAGE, false, null );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        response = new GetAllProductResponse<>(HttpStatus.OK.value(),constants.API_RESPONSE_MESSAGES.DETAILS_FETCHED_MESSAGE, true, productList);
        logger.info("*** Products fetched from DB :: {}", productList);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetProductByIdResponse<Product>> getProductById(@PathVariable int id){

        logger.info("*** Getting Product from DB for Id :: {}", id);
        Product product = productService.getProductById(id);
        GetProductByIdResponse<Product> response;

        if (Objects.isNull(product)) {
            response = new GetProductByIdResponse<>(HttpStatus.NOT_FOUND.value(),constants.API_RESPONSE_MESSAGES.NO_DATA_FOUND_MESSAGE, false, null );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        logger.info("*** Product fetched :: {}", product);
        response = new GetProductByIdResponse<>(HttpStatus.OK.value(),constants.API_RESPONSE_MESSAGES.DETAILS_FETCHED_MESSAGE, true, product);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    };


    @PostMapping("/")
    public ResponseEntity<ApiResponse<Product>> addProduct(@RequestBody Product _product) {

        Product product = productService.getProductById(_product.getId());
        ApiResponse<Product> response;
        if (!(Objects.isNull(product))) {
            response = new ApiResponse<>(HttpStatus.CONFLICT.value(),constants.API_RESPONSE_MESSAGES.PRODUCT_ALREADY_EXIST, false );
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        logger.info("*** Adding Product to DB :: {}", _product);
        productService.addProduct(_product);
        logger.info("*** Product added to DB ***");
        response = new ApiResponse<>(HttpStatus.CREATED.value(),constants.API_RESPONSE_MESSAGES.PRODUCT_ADDED_MESSAGE, true );
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/")
    public ResponseEntity<ApiResponse<Product>> updateProduct(@RequestBody Product _product) {
        Product product = productService.getProductById(_product.getId());

        ApiResponse<Product> response;
        if (Objects.isNull(product)) {
            response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(),constants.API_RESPONSE_MESSAGES.NO_DATA_FOUND_MESSAGE, false );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        logger.info("*** Updating product :: {}", _product);
        Product updatedProduct = productService.updateProduct(_product);
        logger.info("*** Updated product to DB ***");
        response = new ApiResponse<>(HttpStatus.OK.value(),constants.API_RESPONSE_MESSAGES.PRODUCT_UPDATED_MESSAGE, true );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteProduct(@PathVariable int id) {
        Product product = productService.getProductById(id);
        ApiResponse<String> response;
        if (Objects.isNull(product)) {
            response = new ApiResponse<>(HttpStatus.NOT_FOUND.value(),constants.API_RESPONSE_MESSAGES.NO_DATA_FOUND_MESSAGE, false );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        logger.info("*** Deleting Product from DB for Id :: {}", id);
        productService.deleteProduct(id);
        logger.info("*** Deleted Product from DB for Id :: {}", id);
        response = new ApiResponse<>(HttpStatus.OK.value(),constants.API_RESPONSE_MESSAGES.PRODUCT_DELETED_MESSAGE, true );
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}


