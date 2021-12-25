package com.system.ws.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    private Integer quantity;

    private Double cost;

    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    private Set<Customer> customers = new HashSet<>();

    public Product() {
    }

    public Product(Long id, String productId, String brand, String processor, String graphicsCard, String diagonalScreen, Integer quantity, Double cost) {
        this.id = id;
        this.productId = productId;
        this.brand = brand;
        this.processor = processor;
        this.graphicsCard = graphicsCard;
        this.diagonalScreen = diagonalScreen;
        this.quantity = quantity;
        this.cost = cost;
    }

    public void addCustomer(Customer customer){
        this.customers.add(customer);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public String getGraphicsCard() {
        return graphicsCard;
    }

    public void setGraphicsCard(String graphicsCard) {
        this.graphicsCard = graphicsCard;
    }

    public String getDiagonalScreen() {
        return diagonalScreen;
    }

    public void setDiagonalScreen(String diagonalScreen) {
        this.diagonalScreen = diagonalScreen;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }
}
