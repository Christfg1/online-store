package com.pluralsight.ui;

import com.pluralsight.model.Cart;
import com.pluralsight.model.CartItem;
import com.pluralsight.model.Product;
import com.pluralsight.service.StoreService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

// Handles all user menus and input
public class UserInterface {

    private Scanner scanner;
    private StoreService storeService;
    private Cart cart;

    public UserInterface() {
        scanner = new Scanner(System.in);
        storeService = new StoreService();
        cart = new Cart();
    }

    // Home screen
    public void display() {
        while (true) {
            System.out.println("\n=== Online Store ===");
            System.out.println("1 - Display Products");
            System.out.println("2 - Display Cart");
            System.out.println("3 - Exit");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayProducts();
                    break;
                case "2":
                    displayCart();
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Products screen
    private void displayProducts() {
        while (true) {
            System.out.println("\n=== Products Screen ===");
            System.out.println("1 - List all products");
            System.out.println("2 - Search by product name");
            System.out.println("3 - Search by department");
            System.out.println("4 - Search by max price");
            System.out.println("5 - Add product to cart");
            System.out.println("6 - Go back");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    showProductList(storeService.loadProducts());
                    break;
                case "2":
                    searchByName();
                    break;
                case "3":
                    searchByDepartment();
                    break;
                case "4":
                    searchByMaxPrice();
                    break;
                case "5":
                    addProductToCart();
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Displays a list of products
    private void showProductList(ArrayList<Product> products) {
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        System.out.println("\n=== Products ===");

        for (Product product : products) {
            System.out.println(product);
        }
    }

    // Search by name
    private void searchByName() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        ArrayList<Product> results = storeService.searchByName(name);
        showProductList(results);
    }

    // Search by department
    private void searchByDepartment() {
        System.out.print("Enter department: ");
        String department = scanner.nextLine();

        ArrayList<Product> results = storeService.searchByDepartment(department);
        showProductList(results);
    }

    // Search by max price
    private void searchByMaxPrice() {
        System.out.print("Enter max price: ");

        try {
            double maxPrice = Double.parseDouble(scanner.nextLine());
            ArrayList<Product> results = storeService.searchByMaxPrice(maxPrice);
            showProductList(results);
        } catch (NumberFormatException e) {
            System.out.println("Invalid price. Please enter a number.");
        }
    }

    // Adds product to cart by SKU
    private void addProductToCart() {
        System.out.print("Enter SKU to add to cart: ");
        String sku = scanner.nextLine();

        Product product = storeService.findProductBySku(sku);

        if (product != null) {
            cart.addProduct(product);
            System.out.println("Product added to cart.");
        } else {
            System.out.println("Product not found.");
        }
    }

    // Cart screen
    private void displayCart() {
        while (true) {
            System.out.println("\n=== Cart Screen ===");
            cart.displayCart();

            System.out.println("\n1 - Check Out");
            System.out.println("2 - Remove product");
            System.out.println("3 - Go back");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    checkOut();
                    break;
                case "2":
                    removeProductFromCart();
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Removes product from cart by SKU
    private void removeProductFromCart() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        System.out.print("Enter SKU to remove from cart: ");
        String sku = scanner.nextLine();

        boolean removed = cart.removeProduct(sku);

        if (removed) {
            System.out.println("Product removed.");
        } else {
            System.out.println("Product not found in cart.");
        }
    }

    // Handles checkout
    private void checkOut() {
        if (cart.isEmpty()) {
            System.out.println("Cart is empty. Add products before checking out.");
            return;
        }

        double total = cart.getTotal();

        System.out.printf("Sales Total: $%.2f%n", total);
        System.out.print("Enter cash payment amount: $");

        try {
            double amountPaid = Double.parseDouble(scanner.nextLine());

            if (amountPaid < total) {
                System.out.println("Payment is not enough. Returning to cart.");
                return;
            }

            double change = amountPaid - total;

            String receipt = buildReceipt(total, amountPaid, change);

            System.out.println(receipt);
            saveReceipt(receipt);

            cart.clear();
            System.out.println("Cart cleared. Returning to home screen.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid payment amount. Please enter a number.");
        }
    }

    // Builds receipt text
    private String buildReceipt(double total, double amountPaid, double change) {
        StringBuilder receipt = new StringBuilder();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter displayFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        receipt.append("\n=== Sales Receipt ===\n");
        receipt.append("Order Date: ").append(now.format(displayFormat)).append("\n\n");

        receipt.append("Line Items:\n");

        for (CartItem item : cart.getItems()) {
            receipt.append(item).append("\n");
        }

        receipt.append("\nSales Total: $").append(String.format("%.2f", total)).append("\n");
        receipt.append("Amount Paid: $").append(String.format("%.2f", amountPaid)).append("\n");
        receipt.append("Change Given: $").append(String.format("%.2f", change)).append("\n");

        return receipt.toString();
    }

    // Saves receipt to Receipts folder
    private void saveReceipt(String receipt) {
        try {
            File folder = new File("Receipts");

            if (!folder.exists()) {
                folder.mkdir();
            }

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter fileFormat = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

            String fileName = "Receipts/" + now.format(fileFormat) + ".txt";

            FileWriter writer = new FileWriter(fileName);
            writer.write(receipt);
            writer.close();

            System.out.println("Receipt saved to: " + fileName);

        } catch (IOException e) {
            System.out.println("Error saving receipt.");
            e.printStackTrace();
        }
    }
}