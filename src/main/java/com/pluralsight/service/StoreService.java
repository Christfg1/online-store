package com.pluralsight.service;

import com.pluralsight.model.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

// Handles product loading and searching
public class StoreService {

    // Loads products from Products.csv
    public ArrayList<Product> loadProducts() {
        ArrayList<Product> products = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader("src/main/resources/Products.csv")
            );

            String line;

            // Skip header line
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                String sku = parts[0];
                String productName = parts[1];
                double price = Double.parseDouble(parts[2]);
                String department = parts[3];

                Product product = new Product(sku, productName, price, department);
                products.add(product);
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Error loading Products.csv file.");
            e.printStackTrace();
        }

        return products;
    }

    // Finds product by SKU
    public Product findProductBySku(String sku) {
        ArrayList<Product> products = loadProducts();

        for (Product product : products) {
            if (product.getSku().equalsIgnoreCase(sku)) {
                return product;
            }
        }

        return null;
    }

    // Searches products by name
    public ArrayList<Product> searchByName(String name) {
        ArrayList<Product> products = loadProducts();
        ArrayList<Product> results = new ArrayList<>();

        for (Product product : products) {
            if (product.getProductName().toLowerCase().contains(name.toLowerCase())) {
                results.add(product);
            }
        }

        return results;
    }

    // Searches products by department
    public ArrayList<Product> searchByDepartment(String department) {
        ArrayList<Product> products = loadProducts();
        ArrayList<Product> results = new ArrayList<>();

        for (Product product : products) {
            if (product.getDepartment().toLowerCase().contains(department.toLowerCase())) {
                results.add(product);
            }
        }

        return results;
    }

    // Searches products by maximum price
    public ArrayList<Product> searchByMaxPrice(double maxPrice) {
        ArrayList<Product> products = loadProducts();
        ArrayList<Product> results = new ArrayList<>();

        for (Product product : products) {
            if (product.getPrice() <= maxPrice) {
                results.add(product);
            }
        }

        return results;
    }
}