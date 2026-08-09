import java.util.HashMap;
import java.util.Scanner;

class Account {

    int customerId;
    int accountNumber;
    String name;
    double balance;

    Account(int customerId, int accountNumber, String name, double balance) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
    }
}

public class Arraylist {

    // ==================================================
    // MULTI-KEY CUSTOMER INDEXING
    // ==================================================

    // Key 1: Account Number
    static HashMap<Integer, Account> accounts = new HashMap<>();

    // Key 2: Customer ID
    static HashMap<Integer, Account> customerIndex = new HashMap<>();

    // Key 3: Customer Name
    static HashMap<String, Account> nameIndex = new HashMap<>();


    // ==================================================
    // VARIABLES FOR LAST TRANSFER
    // ==================================================

    static int lastSenderNumber = 0;
    static int lastReceiverNumber = 0;
    static double lastTransferAmount = 0;
    static boolean transferAvailable = false;


    // ==================================================
    // CREATE ACCOUNT
    // ==================================================

    static void createAccount(Scanner sc) {

        System.out.print("Enter Customer ID: ");
        int customerId = sc.nextInt();

        if (customerIndex.containsKey(customerId)) {
            System.out.println("Customer ID already exists!");
            return;
        }

        System.out.print("Enter Account Number: ");
        int accountNumber = sc.nextInt();
        sc.nextLine();

        if (accounts.containsKey(accountNumber)) {
            System.out.println("Account Number already exists!");
            return;
        }

        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

        if (nameIndex.containsKey(name)) {
            System.out.println("Customer Name already exists!");
            return;
        }

        System.out.print("Enter Initial Deposit: ");
        double balance = sc.nextDouble();

        if (balance < 0) {
            System.out.println("Invalid amount!");
            return;
        }

        Account account = new Account(
            customerId,
            accountNumber,
            name,
            balance
        );

        // Store using Account Number
        accounts.put(accountNumber, account);

        // Store using Customer ID
        customerIndex.put(customerId, account);

        // Store using Customer Name
        nameIndex.put(name, account);

        System.out.println("\nAccount created successfully!");

        System.out.println("Customer ID    : " + customerId);
        System.out.println("Account Number : " + accountNumber);
        System.out.println("Customer Name  : " + name);
        System.out.println("Balance        : ₹" + balance);
    }


    // ==================================================
    // DEPOSIT
    // ==================================================

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


    // ==================================================
    // WITHDRAW
    // ==================================================

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


    // ==================================================
    // TRANSFER MONEY
    // ==================================================

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
            System.out.println(
                "Sender and receiver cannot be the same!"
            );
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

        // Transfer money
        sender.balance = sender.balance - amount;
        receiver.balance = receiver.balance + amount;

        // Save last transfer details
        lastSenderNumber = senderNumber;
        lastReceiverNumber = receiverNumber;
        lastTransferAmount = amount;
        transferAvailable = true;

        System.out.println("\n===== TRANSFER SUCCESSFUL =====");

        System.out.println("Sender Account   : "
                + sender.accountNumber);

        System.out.println("Sender Name      : "
                + sender.name);

        System.out.println("Receiver Account : "
                + receiver.accountNumber);

        System.out.println("Receiver Name    : "
                + receiver.name);

        System.out.println("Transfer Amount  : ₹"
                + amount);

        System.out.println("\nSender Balance   : ₹"
                + sender.balance);

        System.out.println("Receiver Balance : ₹"
                + receiver.balance);
    }


    // ==================================================
    // REVERSE TRANSFER
    // ==================================================

    static void reverseTransfer() {

        if (!transferAvailable) {
            System.out.println(
                "No transfer available for reversal!"
            );
            return;
        }

        Account sender = accounts.get(lastSenderNumber);
        Account receiver = accounts.get(lastReceiverNumber);

        if (sender == null || receiver == null) {
            System.out.println("Account not found!");
            return;
        }

        if (receiver.balance < lastTransferAmount) {
            System.out.println(
                "Reversal failed! Receiver has insufficient balance."
            );
            return;
        }

        // Reverse transaction
        receiver.balance =
            receiver.balance - lastTransferAmount;

        sender.balance =
            sender.balance + lastTransferAmount;

        System.out.println("\n===== TRANSFER REVERSED =====");

        System.out.println(
            "Amount Reversed : ₹" + lastTransferAmount
        );

        System.out.println(
            "From Account    : " + lastReceiverNumber
        );

        System.out.println(
            "To Account      : " + lastSenderNumber
        );

        System.out.println(
            "Sender Balance   : ₹" + sender.balance
        );

        System.out.println(
            "Receiver Balance : ₹" + receiver.balance
        );

        // Clear transfer information
        transferAvailable = false;
        lastSenderNumber = 0;
        lastReceiverNumber = 0;
        lastTransferAmount = 0;
    }


    // ==================================================
    // SEARCH BY ACCOUNT NUMBER
    // ==================================================

    static void searchByAccountNumber(Scanner sc) {

        System.out.print("Enter Account Number: ");
        int accountNumber = sc.nextInt();

        Account account = accounts.get(accountNumber);

        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        displayDetails(account);
    }


    // ==================================================
    // SEARCH BY CUSTOMER ID
    // ==================================================

    static void searchByCustomerId(Scanner sc) {

        System.out.print("Enter Customer ID: ");
        int customerId = sc.nextInt();

        Account account = customerIndex.get(customerId);

        if (account == null) {
            System.out.println("Customer not found!");
            return;
        }

        displayDetails(account);
    }


    // ==================================================
    // SEARCH BY CUSTOMER NAME
    // ==================================================

    static void searchByName(Scanner sc) {

        sc.nextLine();

        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

        Account account = nameIndex.get(name);

        if (account == null) {
            System.out.println("Customer not found!");
            return;
        }

        displayDetails(account);
    }


    // ==================================================
    // DISPLAY ACCOUNT
    // ==================================================

    static void displayAccount(Scanner sc) {

        System.out.print("Enter Account Number: ");
        int accountNumber = sc.nextInt();

        Account account = accounts.get(accountNumber);

        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        displayDetails(account);
    }


    // ==================================================
    // DISPLAY DETAILS
    // ==================================================

    static void displayDetails(Account account) {

        System.out.println("\n========== CUSTOMER DETAILS ==========");

        System.out.println(
            "Customer ID    : " + account.customerId
        );

        System.out.println(
            "Account Number : " + account.accountNumber
        );

        System.out.println(
            "Customer Name  : " + account.name
        );

        System.out.println(
            "Balance        : ₹" + account.balance
        );

        System.out.println(
            "======================================"
        );
    }


    // ==================================================
    // DISPLAY ALL ACCOUNTS
    // ==================================================

    static void displayAllAccounts() {

        if (accounts.isEmpty()) {
            System.out.println("No accounts available!");
            return;
        }

        System.out.println(
            "\n========== ALL CUSTOMERS =========="
        );

        for (Account account : accounts.values()) {

            System.out.println("----------------------------------");

            System.out.println(
                "Customer ID    : " + account.customerId
            );

            System.out.println(
                "Account Number : " + account.accountNumber
            );

            System.out.println(
                "Customer Name  : " + account.name
            );

            System.out.println(
                "Balance        : ₹" + account.balance
            );
        }

        System.out.println("----------------------------------");
    }


    // ==================================================
    // MAIN METHOD
    // ==================================================

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println(
                "\n========================================"
            );

            System.out.println(
                "       BANK MANAGEMENT SYSTEM"
            );

            System.out.println(
                "========================================"
            );

            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Money");
            System.out.println("5. Reverse Transfer");
            System.out.println("6. Search by Account Number");
            System.out.println("7. Search by Customer ID");
            System.out.println("8. Search by Customer Name");
            System.out.println("9. Display Account");
            System.out.println("10. Display All Accounts");
            System.out.println("11. Exit");

            System.out.print("\nEnter your choice: ");
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
                    reverseTransfer();
                    break;

                case 6:
                    searchByAccountNumber(sc);
                    break;

                case 7:
                    searchByCustomerId(sc);
                    break;

                case 8:
                    searchByName(sc);
                    break;

                case 9:
                    displayAccount(sc);
                    break;

                case 10:
                    displayAllAccounts();
                    break;

                case 11:

                    System.out.println(
                        "Thank you for using Bank Management System!"
                    );

                    sc.close();
                    return;

                default:
                    System.out.println(
                        "Invalid choice!"
                    );
            }
        }
    }
}