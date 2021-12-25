package com.system.ws.dto;

import com.system.ws.domain.entity.Product;

import java.util.HashSet;
import java.util.Set;

public class CustomerRequest {

    private String phone;
    private Set<Product> products = new HashSet<>();


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
