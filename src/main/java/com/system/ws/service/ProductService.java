package com.system.ws.service;

import com.system.ws.domain.entity.Product;
import com.system.ws.exception.ProductNotFoundException;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAllProducts();

    Optional<Product> findByProductId(String productId);

    Product addNewProduct(String brand, String processor, String graphicsCard, String diagonalScreen, Integer quantity, Double cost);

    Product updateProduct(String brand, String processor, String graphicsCard, String diagonalScreen, Integer quantity, Double cost,String productId);


}
