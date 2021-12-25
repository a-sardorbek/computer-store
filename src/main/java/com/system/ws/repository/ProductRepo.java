package com.system.ws.repository;

import com.system.ws.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {

    Optional<Product> findByProductId(String productId);

    String update = "update product p set p.brand=?1, p.processor=?2," +
            " p.graphics_card=?3, p.diagonal_screen=?4, p.quantity=?5," +
            " p.cost=?6 where p.product_id=?7";

    @Modifying
    @Query(value = update,nativeQuery = true)
    void updateProduct(String brand, String processor, String graphicsCard, String diagonalScreen, Integer quantity, Double cost,String productId);

}
