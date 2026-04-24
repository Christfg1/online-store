package com.pluralsight;

import com.pluralsight.ui.UserInterface;

// Entry point of the application
public class OnlineStoreApp {

    public static void main(String[] args) {
        // Create UI and start application
        UserInterface ui = new UserInterface();
        ui.display();
    }
}