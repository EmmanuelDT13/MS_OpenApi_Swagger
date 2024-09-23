package com.emmanuel.swagger.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emmanuel.swagger.domain.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer>{

}
