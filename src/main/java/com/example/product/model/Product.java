package com.example.product.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.io.Serializable;

@Data
@Builder
@Table("products")
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    @PrimaryKey
    private int id;
    private String name;
    private int quantity;
    private double amount;
}