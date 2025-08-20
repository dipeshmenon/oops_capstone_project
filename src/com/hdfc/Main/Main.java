package com.hdfc.Main;

import com.hdfc.Entities.Account;
import com.hdfc.Entities.Customer;
import com.hdfc.Entities.Transaction;


import java.util.*;

public class Main {
    private static final Map<String, Customer> customers = new HashMap<>();
    private static final Map<String, Account> accounts = new HashMap<>();
    private static final List<Transaction> transactions = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Welcome to HDFC Banking Application: ");
        try {
            while (true) {
                showMainMenu();

            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void showMainMenu() {
        System.out.println("\n --Main Menu--\n");
        System.out.println("1. Register new customer");
        System.out.println("2. Create account");
        System.out.println("3. Perform transaction");
        System.out.println("4. View account details");
        System.out.println("5. View transaction history");
        System.out.println("6. Exit");
        System.out.println("Enter your choice: ");

        int choice = getInput();


    }

    private static int getInput() {

        while (true) {
            try {
                return Integer.parseInt((scanner.nextLine().trim()));
            } catch (NumberFormatException e) {
                System.out.println("Please entre a valid number: ");
            }
        }

    }


    public static void registerCustomer(){
        System.out.println("\n=== Customer Registraion ===");
        System.out.println("Enter Customer ID: ");
        String customerId = scanner.nextLine().trim();
        if(customers.containsKey(customerId)){
            System.out.println("Customer already exists!");
            return;
        }
        System.out.println("Enter name: ");
        String name = scanner.nextLine().trim();
        System.out.println("Enter Email: ");
        String email = scanner.nextLine().trim();
}}
