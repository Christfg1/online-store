package com.pluralsight.model;

// Product class represents a single item in the store
public class Product {

    // Unique identifier for the product
    private String sku;

    // Name of the product
    private String name;

    // Price of the product
    private double price;

    // Department the product belongs to (e.g., Electronics, Games)
    private String department;

    // Constructor: initializes all product fields
    public Product(String sku, String name, double price, String department) {
        this.sku = sku;
        this.name = name;
        this.price = price;
        this.department = department;
    }

    // Getter for SKU
    public String getSku() {
        return sku;
    }

    // Getter for product name
    public String getName() {
        return name;
    }

    // Getter for product price
    public double getPrice() {
        return price;
    }

    // Getter for product department
    public String getDepartment() {
        return department;
    }

    // toString method: formats product for display in console
    @Override
    public String toString() {
        return sku + " | " + name + " | $" + String.format("%.2f", price) + " | " + department;
    }
}