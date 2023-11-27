package com.example.product.response;

import com.example.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetProductByIdResponse<T> {
    private int statusCode;
    private String message;
    private boolean success;
    private Product data;
}
