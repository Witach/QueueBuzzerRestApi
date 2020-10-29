package com.queuebuzzer.restapi.repository;

import com.queuebuzzer.restapi.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
