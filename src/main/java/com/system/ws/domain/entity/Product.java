package com.system.ws.domain.entity;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    private final static String NUMBER_GENERATOR = "product_generator";
    private final static String SEQUENCE_NAME = "product_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = NUMBER_GENERATOR)
    @SequenceGenerator(name=NUMBER_GENERATOR, sequenceName = SEQUENCE_NAME,allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String productId;

    private String brand;

    private String processor;

    private String graphicsCard;

    private String diagonalScreen;

    private Double cost;


}
