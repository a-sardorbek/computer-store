package com.system.ws.domain.entity;

import javax.persistence.*;

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
    private String name;

    @Column(nullable = false)
    private String phone;

    public Customer(){}

    public Customer(Long id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
