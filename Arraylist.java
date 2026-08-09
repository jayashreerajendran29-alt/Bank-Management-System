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

public class Arraylist{

    // HashMap for hashing
    static HashMap<Integer, Account> accounts = new HashMap<>();

    // Create Account
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

        // Store account using hashing
        accounts.put(accountNumber, account);

        System.out.println("Account created successfully!");
    }

    // Deposit
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

    // Withdraw
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

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== BANK MANAGEMENT SYSTEM =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");

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
                    System.out.println("Thank you for using Bank Management System!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}