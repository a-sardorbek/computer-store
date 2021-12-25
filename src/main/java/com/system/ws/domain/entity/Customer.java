package com.system.ws.domain.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "customer")
public class Customer {

    private final static String NUMBER_GENERATOR = "customer_generator";
    private final static String SEQUENCE_NAME = "customer_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = NUMBER_GENERATOR)
    @SequenceGenerator(name=NUMBER_GENERATOR, sequenceName = SEQUENCE_NAME,allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    private String customerId;


    @Column(nullable = false)
    private String phone;

    @ManyToMany(cascade = {CascadeType.MERGE,CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(name = "customer_products",
            joinColumns = {@JoinColumn(name = "customer_id",referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "product_id",referencedColumnName = "id")})
    private Set<Product> products = new HashSet<>();

    public Customer(){}

    public Customer(Long id, String phone) {
        this.id = id;
        this.phone = phone;
    }

    public Customer(Long id, String customerId, String phone, Set<Product> products) {
        this.id = id;
        this.customerId = customerId;
        this.phone = phone;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return id.equals(customer.id) && customerId.equals(customer.customerId) && phone.equals(customer.phone) && products.equals(customer.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, customerId, phone, products);
    }
}
