package com.pluralsight.model;

import java.util.ArrayList;

// Represents the customer's shopping cart
public class Cart {

    private ArrayList<CartItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    // Adds a product to the cart. If already there, increase quantity.
    public void addProduct(Product product) {
        for (CartItem item : items) {
            if (item.getProduct().getSku().equalsIgnoreCase(product.getSku())) {
                item.increaseQuantity();
                return;
            }
        }

        items.add(new CartItem(product));
    }

    // Removes one quantity of a product by SKU
    public boolean removeProduct(String sku) {
        for (CartItem item : items) {
            if (item.getProduct().getSku().equalsIgnoreCase(sku)) {
                item.decreaseQuantity();

                if (item.getQuantity() <= 0) {
                    items.remove(item);
                }

                return true;
            }
        }

        return false;
    }

    // Displays the cart and total
    public void displayCart() {
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.println("\n=== Your Cart ===");

        for (CartItem item : items) {
            System.out.println(item);
        }

        System.out.printf("Total: $%.2f%n", getTotal());
    }

    // Calculates total price
    public double getTotal() {
        double total = 0;

        for (CartItem item : items) {
            total += item.getLineTotal();
        }

        return total;
    }

    public ArrayList<CartItem> getItems() {
        return items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }
}