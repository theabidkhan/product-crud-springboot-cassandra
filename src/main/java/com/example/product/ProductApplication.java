package com.example.product;

import com.example.product.model.Product;
import com.example.product.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.util.List;

@SpringBootApplication
@EnableCassandraRepositories
@ComponentScan("com.example.product")
public class ProductApplication {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		SpringApplication.run(ProductApplication.class, args);
	}

	@Autowired
	private ProductRepository productRepository;

	@Bean
	CommandLineRunner runner() {
		return args -> {
			List<Product> productList = productRepository.findAll();
			if (productList.isEmpty()) {
				Product prod = new Product(1001, "Highlighter Pen",100,22.50);
				logger.info("******* Inserting new product into DB *******");
				productRepository.save(prod);
			} else {
				logger.info("*******  Products count :: {}", productList.size());
				logger.info("*******  Products available :: {}", productList);
			}
		};
	}
}
