import java.util.HashMap;
import java.util.Scanner;

class Account {

    int accountNumber;
    String name;
    double balance;

    Account(int accountNumber, String name, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
    }
}

public class Arraylist {

    // HashMap for storing multiple accounts
    static HashMap<Integer, Account> accounts = new HashMap<>();

    // ================= CREATE ACCOUNT =================
    static void createAccount(Scanner sc) {

        System.out.print("Enter Account Number: ");
        int accountNumber = sc.nextInt();
        sc.nextLine();

        if (accounts.containsKey(accountNumber)) {
            System.out.println("Account already exists!");
            return;
        }

        System.out.print("Enter Account Holder Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Initial Deposit: ");
        double balance = sc.nextDouble();

        if (balance < 0) {
            System.out.println("Invalid amount!");
            return;
        }

        Account account = new Account(accountNumber, name, balance);

        accounts.put(accountNumber, account);

        System.out.println("Account created successfully!");
    }

    // ================= DEPOSIT =================
    static void deposit(Scanner sc) {

        System.out.print("Enter Account Number: ");
        int accountNumber = sc.nextInt();

        Account account = accounts.get(accountNumber);

        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter Deposit Amount: ");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid deposit amount!");
            return;
        }

        account.balance = account.balance + amount;

        System.out.println("Deposit successful!");
        System.out.println("Current Balance: ₹" + account.balance);
    }

    // ================= WITHDRAW =================
    static void withdraw(Scanner sc) {

        System.out.print("Enter Account Number: ");
        int accountNumber = sc.nextInt();

        Account account = accounts.get(accountNumber);

        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter Withdrawal Amount: ");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount!");
            return;
        }

        if (amount > account.balance) {
            System.out.println("Insufficient balance!");
            return;
        }

        account.balance = account.balance - amount;

        System.out.println("Withdrawal successful!");
        System.out.println("Current Balance: ₹" + account.balance);
    }

    // ================= TRANSFER MONEY =================
    static void transfer(Scanner sc) {

        System.out.print("Enter Sender Account Number: ");
        int senderNumber = sc.nextInt();

        Account sender = accounts.get(senderNumber);

        if (sender == null) {
            System.out.println("Sender account not found!");
            return;
        }

        System.out.print("Enter Receiver Account Number: ");
        int receiverNumber = sc.nextInt();

        Account receiver = accounts.get(receiverNumber);

        if (receiver == null) {
            System.out.println("Receiver account not found!");
            return;
        }

        if (senderNumber == receiverNumber) {
            System.out.println("Sender and receiver cannot be the same!");
            return;
        }

        System.out.print("Enter Transfer Amount: ");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid transfer amount!");
            return;
        }

        if (amount > sender.balance) {
            System.out.println("Insufficient balance!");
            return;
        }

        // Deduct money from sender
        sender.balance = sender.balance - amount;

        // Add money to receiver
        receiver.balance = receiver.balance + amount;

        System.out.println("\n===== TRANSFER SUCCESSFUL =====");
        System.out.println("Sender Account   : " + sender.accountNumber);
        System.out.println("Sender Name      : " + sender.name);
        System.out.println("Receiver Account : " + receiver.accountNumber);
        System.out.println("Receiver Name    : " + receiver.name);
        System.out.println("Transfer Amount  : ₹" + amount);

        System.out.println("\nSender Balance   : ₹" + sender.balance);
        System.out.println("Receiver Balance : ₹" + receiver.balance);
    }

    // ================= DISPLAY ACCOUNT =================
    static void displayAccount(Scanner sc) {

        System.out.print("Enter Account Number: ");
        int accountNumber = sc.nextInt();

        Account account = accounts.get(accountNumber);

        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.println("\n===== ACCOUNT DETAILS =====");
        System.out.println("Account Number : " + account.accountNumber);
        System.out.println("Account Holder : " + account.name);
        System.out.println("Balance        : ₹" + account.balance);
    }

    // ================= DISPLAY ALL ACCOUNTS =================
    static void displayAllAccounts() {

        if (accounts.isEmpty()) {
            System.out.println("No accounts available!");
            return;
        }

        System.out.println("\n========== ALL ACCOUNTS ==========");

        for (Account account : accounts.values()) {

            System.out.println("----------------------------------");
            System.out.println("Account Number : " + account.accountNumber);
            System.out.println("Account Holder : " + account.name);
            System.out.println("Balance        : ₹" + account.balance);
        }

        System.out.println("----------------------------------");
    }

    // ================= MAIN METHOD =================
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n=================================");
            System.out.println("       BANK MANAGEMENT SYSTEM");
            System.out.println("=================================");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Money");
            System.out.println("5. Display Account");
            System.out.println("6. Display All Accounts");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    createAccount(sc);
                    break;

                case 2:
                    deposit(sc);
                    break;

                case 3:
                    withdraw(sc);
                    break;

                case 4:
                    transfer(sc);
                    break;

                case 5:
                    displayAccount(sc);
                    break;

                case 6:
                    displayAllAccounts();
                    break;

                case 7:
                    System.out.println(
                        "Thank you for using Bank Management System!"
                    );
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}