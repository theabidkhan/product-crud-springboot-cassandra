package com.example.product.repository;

import com.example.product.model.Product;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CassandraRepository<Product, Integer> {
    List<Product> findAll();

    Product findById(int id);
}
