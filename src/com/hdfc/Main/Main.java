package com.hdfc.Main;

import com.hdfc.Entities.*;
import com.hdfc.Enums.AccountType;
import com.hdfc.Enums.TransactionType;
import com.hdfc.Exceptions.InsufficientBalanceException;
import com.hdfc.Exceptions.InvalidAccountException;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*public class Main {
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
}}*/

    public class Main {
        private static final Scanner scanner = new Scanner(System.in);
        private static final Map<String, Customer> customers = new HashMap<>();
        private static final Map<String, Account> accounts = new HashMap<>();
        private static final List<Transaction> transactions = new ArrayList<>();

        public static void main(String[] args) {
            System.out.println("Welcome to HDFC Bank Application");

            boolean menu = true;
            while (menu) {
                System.out.println("--Main Menu--");
                System.out.println("1. Register New Customer");
                System.out.println("2. Create Account");
                System.out.println("3. Perform Transactions");
                System.out.println("4. View Account Details");
                System.out.println("5. View Transaction History");
                System.out.println("6. Simulate Concurrent Transfers");
                System.out.println("7. Demo Mode (Complete Flow)");
                System.out.println("8. Exit");
                System.out.print("Select an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> registerCustomer();
                    case 2 -> createAccount();
                    case 3 -> performTransaction();
                    case 4 -> viewAccountDetails();
                    case 5 -> viewTransactionHistory();
                    case 6 -> simulateConcurrentTransfers();
                    case 7 -> runDemoMode();
                    case 8 -> {
                        System.out.println("Thank you");
                        menu=false;
                    }
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            }
        }


        private static void registerCustomer() {
            System.out.print("Enter Customer ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Email: ");
            String email = scanner.nextLine();
            System.out.print("Enter Phone: ");
            int phone = scanner.nextInt();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            System.out.print("Enter DOB (yyyy-mm-dd): ");
            LocalDate dob = LocalDate.parse(scanner.nextLine());

            Customer customer = new Customer(name, email, password, dob, phone, id);
            customers.put(id, customer);
            System.out.println("Customer registered");
        }


        private static void createAccount() {
            System.out.print("Enter Customer ID: ");
            String customerId = scanner.nextLine();
            Customer customer = customers.get(customerId);
            if (customer == null) {
                System.out.println("Customer not found.");
                return;
            }

            System.out.print("Enter Account Number: ");
            String accountNo = scanner.nextLine();
            System.out.print("Enter Initial Balance: ");
            BigDecimal balance = scanner.nextBigDecimal();
            scanner.nextLine();
            System.out.print("Account Type (SAVINGS/CURRENT): ");
            AccountType type = AccountType.valueOf(scanner.nextLine().toUpperCase());

            Account account;
            if (type == AccountType.SAVINGS) {
                account = new SavingsAccount(accountNo, customerId, balance, type);
            } else {
                account = new CurrentAccount(accountNo, customerId, type, balance);
            }

            accounts.put(accountNo, account);
            System.out.println("Account created.");
        }


        private static void performTransaction() {
            System.out.print("Enter Account Number: ");
            String accNo = scanner.nextLine();
            Account account = accounts.get(accNo);
            if (account == null) {
                System.out.println("Invalid account.");
                return;
            }

            System.out.println("Choose Transaction: 1. Deposit 2. Withdraw 3. Transfer");
            int option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 1 -> {
                    System.out.print("Enter amount to deposit: ");
                    BigDecimal amount = scanner.nextBigDecimal();
                    scanner.nextLine();
                    account.deposit(amount);
                    transactions.add(new Transaction());
                    System.out.println("Deposit successful.");
                }
                case 2 -> {
                    System.out.print("Enter amount to withdraw: ");
                    BigDecimal amount = scanner.nextBigDecimal();
                    scanner.nextLine();
                    try {
                        account.withdraw(amount);
                        String id =accNo+LocalDateTime.now();
                        transactions.add(new Transaction(id, amount,accNo, LocalDateTime.now(), TransactionType.WITHDRAW));
                        System.out.println("Withdrawal successful.");
                    } catch (InsufficientBalanceException e) {
                        System.out.println("Insufficient balance.");
                    }
                }
                case 3 -> {
                    System.out.print("Enter destination account number: ");
                    String toAcc = scanner.nextLine();
                    System.out.print("Enter amount to transfer: ");
                    BigDecimal amount = scanner.nextBigDecimal();
                    scanner.nextLine();
                    Account recipient = accounts.get(toAcc);
                    if (recipient == null) {
                        System.out.println("Invalid destination account.");
                        return;
                    }
                    try {
                        account.withdraw(amount);
                        recipient.deposit(amount);
                        String id =accNo+LocalDateTime.now();
                        transactions.add(new Transaction(id, amount,accNo, LocalDateTime.now(), TransactionType.TRANSFER));
                        System.out.println("Transfer successful.");
                    } catch (InsufficientBalanceException e) {
                        System.out.println("Insufficient balance.");
                    }
                }
            }
        }


        private static void viewAccountDetails() {
            System.out.print("Enter Account Number: ");
            String accNo = scanner.nextLine();
            Account account = accounts.get(accNo);
            if (account == null) {
                System.out.println("Account not found.");
                return;
            }
            String id = account.getCustomerId();
            Customer cust = customers.get(id);

            System.out.println("Customer name: " + cust.getName());
            System.out.println("Account No: " + account.getAccountNo());
            System.out.println("Balance: ₹" + account.getBalance());
            System.out.println("Interest: ₹" + account.calculateInterest());
        }


        private static void viewTransactionHistory() {
            System.out.print("Enter Account Number: ");
            String accNo = scanner.nextLine();

            transactions.stream()
                    .filter(t -> t.getAccountNo().equals(accNo))
                    .sorted(Comparator.comparing(Transaction::getTimestamp))
                    .forEach(t -> System.out.println(t.getType() + " - ₹" + t.getAmount() + " at " + t.getTimestamp()));
        }


        private static void simulateConcurrentTransfers() {
            System.out.print("Enter customer ID: ");
            String id = scanner.nextLine();
            System.out.print("Enter source account: ");
            String from = scanner.nextLine();
            System.out.print("Enter destination account: ");
            String to = scanner.nextLine();
            System.out.print("Enter amount to transfer: ");
            BigDecimal amount = scanner.nextBigDecimal();
            scanner.nextLine();

            ExecutorService executor = Executors.newFixedThreadPool(3);
            List<Callable<Boolean>> tasks = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                tasks.add(() -> {
                    try {
                        Account fromAcc = accounts.get(from);
                        Account toAcc = accounts.get(to);
                        if (fromAcc == null || toAcc == null) throw new InvalidAccountException("Account not found");

                        fromAcc.withdraw(amount);
                        toAcc.deposit(amount);
                        transactions.add(new Transaction(id, amount,from, LocalDateTime.now(),TransactionType.TRANSFER));
                        return true;
                    } catch (Exception e) {
                        System.out.println("Transfer failed: " + e.getMessage());
                        return false;
                    }
                });
            }

            try {
                List<Future<Boolean>> results = executor.invokeAll(tasks);
                for (Future<Boolean> result : results) {
                    if (result.get()) System.out.println("Transfer successful.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            } finally {
                executor.shutdown();
            }
        }


        private static void runDemoMode() {
            System.out.println("--Demo Mode--");


            Customer cust1 = new Customer("Dipesh", "dipesh@example.com","password",  LocalDate.of(1990, 5, 10),987654320,"C1");
            Customer cust2 = new Customer("Menon", "menon@example.com","password",  LocalDate.of(1980, 9, 20),123456890,"C2");
            customers.put("C1", cust1);
            customers.put("C2", cust2);
            System.out.println("Customers registered successfully");


            Account acc1 = new SavingsAccount("SAV001", cust1.getCustomerId(), new BigDecimal("5000"),AccountType.SAVINGS);
            Account acc2 = new CurrentAccount("CUR001", cust2.getCustomerId(), AccountType.CURRENT,new BigDecimal("2000"));
            accounts.put("SAV001", acc1);
            accounts.put("CUR001", acc2);
            System.out.println("✓ Accounts created successfully");


            acc1.deposit(new BigDecimal("1000"));
            acc1.withdraw(new BigDecimal("800"));
            acc2.deposit(new BigDecimal("300"));
            acc1.withdraw(new BigDecimal("500"));
            acc1.deposit(new BigDecimal("500"));
            acc1.deposit(new BigDecimal("500"));
            acc1.withdraw(new BigDecimal("300"));
            acc1.withdraw(new BigDecimal("400"));
            String id1=acc1.getAccountNo()+LocalDateTime.now();
            String id2=acc2.getAccountNo()+LocalDateTime.now();
            acc1.withdraw(new BigDecimal("800"));
            acc2.deposit(new BigDecimal("1000"));
            transactions.add(new Transaction(id1, new BigDecimal("1000"),"SAV001" ,LocalDateTime.now(),TransactionType.DEPOSIT));
            transactions.add(new Transaction(id2,new BigDecimal("800"),"SAV001", LocalDateTime.now(),TransactionType.TRANSFER ));

            System.out.println("✓ Deposit, Withdrawal & Transfer successful");


            System.out.println("Account details:");
            System.out.println("Savings Account: " + acc1.getBalance() + " (Interest: " + acc1.calculateInterest() + ")");
            System.out.println("Current Account: " + acc2.getBalance() + " (Interest: " + acc2.calculateInterest() + ")");


            System.out.println("Transaction history:");
            viewTransactionHistory();



            System.out.println("Demo completed");
        }
    }
