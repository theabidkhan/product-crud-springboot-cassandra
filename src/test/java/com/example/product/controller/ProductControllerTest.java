package com.example.product.controller;

import com.example.product.model.Product;
import com.example.product.response.ApiResponse;
import com.example.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@WebMvcTest
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @InjectMocks
    private ProductController productController;
    private Product product;

    Logger logger = LoggerFactory.getLogger(getClass());

    @BeforeEach
    void setUp() {
        product = new Product(100001, "Product1",10, 10.0);
    }


    @Test
    void testGetAllProducts() throws Exception {

        List<Product> productList = Arrays.asList(product);
        when(productService.getAllProducts()).thenReturn(productList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(product.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").value(product.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].quantity").value(product.getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].amount").value(product.getAmount()));
    }

    @Test
    void testGetProductById() throws Exception {

        when(productService.getProductById(product.getId())).thenReturn(product);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/{id}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(product.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value(product.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.quantity").value(product.getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.amount").value(product.getAmount()));
    }

    @Test
    void testAddProduct() throws Exception {

        // Mock the behavior of the productService
        when(productService.addProduct(ArgumentMatchers.any(Product.class))).thenReturn(product);

        // Use ObjectMapper to convert the product object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String productJson = objectMapper.writeValueAsString(product);

        // Perform the POST request using mockMvc
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))

                // Verify the expected status and response content
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.success").value(true));

        // Verify that the productService.addProduct method was called with the correct argument
        verify(productService, times(1)).addProduct(ArgumentMatchers.any(Product.class));
    }

    @Test
    void testUpdateProduct() {
    }

    @Test
    void testDeleteProduct() {
    }
}