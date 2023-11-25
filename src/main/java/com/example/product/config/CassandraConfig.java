package com.example.product.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration{

    @Value("${spring.data.cassandra.keyspace-name: product_manager}")
    private String KEYSPACE;

    @Value("${spring.data.cassandra.contact-points: localhost}")
    private String CONTACT_POINT;

    @Value("${spring.data.cassandra.port: 9042}")
    private int PORT;

    @Override
    protected String getKeyspaceName() {
        return KEYSPACE;
    }

    @Override
    public String getContactPoints() {
        return CONTACT_POINT;
    }

    @Override
    protected int getPort() {
        return PORT;
    }
}
