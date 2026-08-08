import java.util.ArrayList;
import java.util.Scanner;

class Account {
    int accountNumber;
    String accountHolder;
    double balance;

    Account(int accountNumber, String accountHolder, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
    }
}

public class Arraylist {

    static ArrayList<Account> accounts = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    // Find account by account number
    static Account findAccount(int accNo) {
        for (Account acc : accounts) {
            if (acc.accountNumber == accNo) {
                return acc;
            }
        }
        return null;
    }

    // Create Account
    static void createAccount() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();
        sc.nextLine();

        if (findAccount(accNo) != null) {
            System.out.println("Account already exists!");
            return;
        }

        System.out.print("Enter Account Holder Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Initial Balance: ");
        double balance = sc.nextDouble();

        if (balance < 0) {
            System.out.println("Invalid Initial Balance!");
            return;
        }

        accounts.add(new Account(accNo, name, balance));

        System.out.println("Account Created Successfully!");
    }

    // Deposit
    static void deposit() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        Account acc = findAccount(accNo);

        if (acc != null) {
            System.out.print("Enter Deposit Amount: ");
            double amount = sc.nextDouble();

            if (amount > 0) {
                acc.balance = acc.balance + amount;

                System.out.println("Deposit Successful!");
                System.out.println("Current Balance: " + acc.balance);
            } else {
                System.out.println("Invalid Amount!");
            }
        } else {
            System.out.println("Account Not Found!");
        }
    }

    // Withdraw
    static void withdraw() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        Account acc = findAccount(accNo);

        if (acc != null) {
            System.out.print("Enter Withdraw Amount: ");
            double amount = sc.nextDouble();

            if (amount > 0 && amount <= acc.balance) {
                acc.balance = acc.balance - amount;

                System.out.println("Withdrawal Successful!");
                System.out.println("Remaining Balance: " + acc.balance);
            } else {
                System.out.println("Insufficient Balance or Invalid Amount!");
            }
        } else {
            System.out.println("Account Not Found!");
        }
    }

    // Check Balance
    static void checkBalance() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        Account acc = findAccount(accNo);

        if (acc != null) {
            System.out.println("Account Holder: " + acc.accountHolder);
            System.out.println("Current Balance: " + acc.balance);
        } else {
            System.out.println("Account Not Found!");
        }
    }

    // Display All Accounts
    static void displayAccounts() {

        if (accounts.isEmpty()) {
            System.out.println("No Accounts Available.");
            return;
        }

        System.out.println("\n----- Account Details -----");

        for (Account acc : accounts) {
            System.out.println("Account No : " + acc.accountNumber);
            System.out.println("Name       : " + acc.accountHolder);
            System.out.println("Balance    : " + acc.balance);
            System.out.println("---------------------------");
        }
    }

    // Main Method
    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== BANK MANAGEMENT SYSTEM =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Display All Accounts");
            System.out.println("6. Exit");

            System.out.print("Enter Your Choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    createAccount();
                    break;

                case 2:
                    deposit();
                    break;

                case 3:
                    withdraw();
                    break;

                case 4:
                    checkBalance();
                    break;

                case 5:
                    displayAccounts();
                    break;

                case 6:
                    System.out.println("Thank You!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}