package com.example.OnlineShop.Model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Product> products;

    public Cart(){
        this.products = new ArrayList<>();
    }

    public List<Product> getProducts(){
        return products;
    }

    public void addProduct(Product product){
        this.products.add(product);
    }
    public void removeProduct(Product product){
        this.products.remove(product);
    }

    public double getTotalPrice(){
        return products.stream()
                .mapToDouble(Product::getPrice)
                .sum();
    }

    public void clear(){
        this.products.clear();
    }
}
