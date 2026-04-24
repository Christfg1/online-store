package com.pluralsight.ui;

// Import Product class (data model)
import com.pluralsight.model.Product;

// Import StoreService (handles business logic)
import com.pluralsight.service.StoreService;

// Import ArrayList to store products
import java.util.ArrayList;

// Import Scanner to read user input
import java.util.Scanner;

public class UserInterface {

    // Scanner for reading user input from console
    private Scanner scanner;

    // Service layer to handle product logic
    private StoreService storeService;

    // List of products loaded from CSV
    private ArrayList<Product> products;

    // Constructor: initializes scanner, service, and loads products
    public UserInterface() {
        scanner = new Scanner(System.in);
        storeService = new StoreService();
        products = storeService.loadProducts();
    }

    // Main UI loop (controls the application flow)
    public void display() {
        boolean running = true;

        // Loop runs until user chooses to exit
        while (running) {
            System.out.println();
            System.out.println("=== Online Store ===");
            System.out.println("1- Display Products");
            System.out.println("2- Display Cart");
            System.out.println("3- Exit");
            System.out.print("Enter command: ");

            // Read user input
            String command = scanner.nextLine();

            // Handle user choice
            switch (command) {

                case "1":
                    // Go to products screen
                    displayProductsScreen();
                    break;

                case "2":
                    // Cart feature not implemented yet
                    System.out.println("Cart feature coming soon...");
                    break;

                case "3":
                    // Exit the application
                    System.out.println("Goodbye!");
                    running = false;
                    break;

                default:
                    // Handle invalid input
                    System.out.println("Invalid command. Please try again.");
                    break;
            }
        }
    }

    // Displays all products in the store
    private void displayProductsScreen() {
        System.out.println();
        System.out.println("=== Products ===");

        // Call service to print all products
        storeService.displayProducts(products);
    }
}