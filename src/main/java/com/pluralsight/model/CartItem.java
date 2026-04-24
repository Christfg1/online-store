package com.pluralsight.model;

// Represents one product line in the cart with quantity
public class CartItem {

    private Product product;
    private int quantity;

    public CartItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity() {
        quantity++;
    }

    public void decreaseQuantity() {
        quantity--;
    }

    public double getLineTotal() {
        return product.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return product.getSku() + " | " +
                product.getProductName() + " | $" +
                String.format("%.2f", product.getPrice()) +
                " | Qty: " + quantity +
                " | Line Total: $" + String.format("%.2f", getLineTotal());
    }
}