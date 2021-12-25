package com.system.ws.service.impl;

import com.system.ws.domain.entity.Product;
import com.system.ws.repository.ProductRepo;
import com.system.ws.service.ProductService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductRepo productRepo;

    @Autowired
    public ProductServiceImpl(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Override
    public Product addNewProduct(String brand, String processor, String graphicsCard, String diagonalScreen,Integer quantity, Double cost) {

        Product product = new Product();
        product.setProductId(generateProductId());
        product.setBrand(brand);
        product.setProcessor(processor);
        product.setGraphicsCard(graphicsCard);
        product.setDiagonalScreen(diagonalScreen);
        product.setQuantity(quantity);
        product.setCost(cost);
        productRepo.save(product);
        return product;
    }

    @Override
    public Product updateProduct(String brand, String processor, String graphicsCard, String diagonalScreen, Integer quantity, Double cost,String productId) {
        Product product = new Product();
        product.setBrand(brand);
        product.setProcessor(processor);
        product.setGraphicsCard(graphicsCard);
        product.setDiagonalScreen(diagonalScreen);
        product.setQuantity(quantity);
        product.setCost(cost);
        product.setProductId(productId);
        productRepo.updateProduct(product.getBrand(),product.getProcessor(),product.getGraphicsCard(),product.getDiagonalScreen(),product.getQuantity(),product.getCost(),product.getProductId());
        return product;
    }



    @Override
    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Optional<Product> findByProductId(String productId) {
        return productRepo.findByProductId(productId);
    }


    private String generateProductId() {
        return RandomStringUtils.randomNumeric(5);
    }
}
