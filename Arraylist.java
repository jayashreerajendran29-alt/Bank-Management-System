import java.util.ArrayList;
import java.util.Scanner;

class Account {
    int accNo;
    String name;
    double balance;

    Account(int accNo, String name, double balance) {
        this.accNo = accNo;
        this.name = name;
        this.balance = balance;
    }
}

public class Arraylist {
    static ArrayList<Account> list = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    // Create Account
    static void createAccount() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter Account Holder Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Initial Balance: ");
        double balance = sc.nextDouble();

        list.add(new Account(accNo, name, balance));
        System.out.println("Account Created Successfully!");
    }

    // Deposit
    static void deposit() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        for (Account a : list) {
            if (a.accNo == accNo) {
                System.out.print("Enter Deposit Amount: ");
                double amount = sc.nextDouble();

                a.balance += amount;
                System.out.println("Deposit Successful!");
                System.out.println("Current Balance: " + a.balance);
                return;
            }
        }

        System.out.println("Account Not Found!");
    }

    // Withdraw
    static void withdraw() {
        System.out.print("Enter Account Number: ");
        int accNo = sc.nextInt();

        for (Account a : list) {
            if (a.accNo == accNo) {
                System.out.print("Enter Withdraw Amount: ");
                double amount = sc.nextDouble();

                if (amount <= a.balance) {
                    a.balance -= amount;
                    System.out.println("Withdrawal Successful!");
                    System.out.println("Remaining Balance: " + a.balance);
                } else {
                    System.out.println("Insufficient Balance!");
                }
                return;
            }
        }

        System.out.println("Account Not Found!");
    }

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.print("Enter Choice: ");

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
                    System.out.println("Thank You!");
                    System.exit(0);
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }
}