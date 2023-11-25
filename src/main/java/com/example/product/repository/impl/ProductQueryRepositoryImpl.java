package com.example.product.repository.impl;

import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.Optional;

public class ProductQueryRepositoryImpl implements ProductRepository {
    @Autowired
    private CassandraOperations cassandraTemplate;

    @Override
    public Optional<Product> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public <S extends Product> S save(S entity) {
        return entity;
    }

    @Override
    public <S extends Product> List<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public List<Product> findAll() {
        return cassandraTemplate.select(Query.empty(), Product.class);
    }

    @Override
    public Product findById(int id) {
        return cassandraTemplate.selectOne(Query.query(Criteria.where("id").is(id)).withAllowFiltering(), Product.class);
    }

    @Override
    public List<Product> findAllById(Iterable<Integer> integers) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Product entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Integer> integers) {

    }

    @Override
    public void deleteAll(Iterable<? extends Product> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Slice<Product> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Product> S insert(S entity) {
        return entity;
    }

    @Override
    public <S extends Product> List<S> insert(Iterable<S> entities) {
        return null;
    }
}
