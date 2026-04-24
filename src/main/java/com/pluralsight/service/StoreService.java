package com.pluralsight.service;

// Import Product class from the model package
import com.pluralsight.model.Product;

// Imports for reading the Products.csv file
import java.io.BufferedReader;
import java.io.FileReader;

// Import ArrayList to store products
import java.util.ArrayList;

public class StoreService {

    // Loads products from the Products.csv file
    public ArrayList<Product> loadProducts() {
        ArrayList<Product> products = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader("src/main/resources/Products.csv")
            );

            // Skip the first line because it is the header
            String line = reader.readLine();

            // Read each product line from the file
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");

                String sku = parts[0];
                String name = parts[1];
                double price = Double.parseDouble(parts[2]);
                String department = parts[3];

                Product product = new Product(sku, name, price, department);
                products.add(product);
            }

            reader.close();

        } catch (Exception e) {
            System.out.println("Error loading products.");
            e.printStackTrace();
        }

        return products;
    }

    // Displays all products in the inventory
    public void displayProducts(ArrayList<Product> products) {
        for (Product product : products) {
            System.out.println(product);
        }
    }
}