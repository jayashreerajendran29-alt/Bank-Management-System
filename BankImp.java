import java.util.*;
import java.io.*;
import java.time.LocalDateTime;


// ==================== ACCOUNT ====================

class Account {

    private int accountNumber;
    private String name;
    private double balance;

    public Account(int accountNumber, String name, double balance) {
        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}


// ==================== ACCOUNT DTO ====================

class AccountDTO {

    int accountNumber;
    String name;
    double balance;

    public AccountDTO(int accountNumber, String name,
                      double balance) {

        this.accountNumber = accountNumber;
        this.name = name;
        this.balance = balance;
    }
}


// ==================== TRANSACTION ====================

class Transaction {

    int accountNumber;
    String type;
    double amount;
    LocalDateTime dateTime;

    public Transaction(int accountNumber, String type,
                       double amount) {

        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.dateTime = LocalDateTime.now();
    }
}


// ==================== ACCOUNT REPOSITORY ====================

interface AccountRepository {

    void save(Account account);

    Account findById(int accountNumber);

    ArrayList<Account> findAll();

    boolean deleteById(int accountNumber);
}


// ==================== JSON FILE ACCOUNT REPOSITORY ====================

class JsonFileAccountRepository
        implements AccountRepository {

    private String fileName = "accounts.json";


    // Load all accounts

    private ArrayList<Account> loadAccounts() {

        ArrayList<Account> accounts =
                new ArrayList<>();

        File file = new File(fileName);

        if (!file.exists()) {
            return accounts;
        }

        try {

            Scanner fileScanner =
                    new Scanner(file);

            while (fileScanner.hasNextLine()) {

                String line =
                        fileScanner.nextLine();

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data =
                        line.split("\\|");

                if (data.length == 3) {

                    int accountNumber =
                            Integer.parseInt(data[0]);

                    String name =
                            data[1];

                    double balance =
                            Double.parseDouble(data[2]);

                    accounts.add(
                            new Account(
                                    accountNumber,
                                    name,
                                    balance
                            )
                    );
                }
            }

            fileScanner.close();

        } catch (Exception e) {

            System.out.println(
                    "Error loading accounts!"
            );
        }

        return accounts;
    }


    // Save all accounts

    private void saveAll(
            ArrayList<Account> accounts) {

        try {

            FileWriter writer =
                    new FileWriter(fileName);

            for (Account account : accounts) {

                writer.write(
                        account.getAccountNumber()
                                + "|"
                                + account.getName()
                                + "|"
                                + account.getBalance()
                                + "\n"
                );
            }

            writer.close();

        } catch (Exception e) {

            System.out.println(
                    "Error saving accounts!"
            );
        }
    }


    // Save Account

    public void save(Account account) {

        ArrayList<Account> accounts =
                loadAccounts();

        boolean found = false;

        for (int i = 0;
             i < accounts.size();
             i++) {

            if (accounts.get(i)
                    .getAccountNumber()
                    == account.getAccountNumber()) {

                accounts.set(i, account);

                found = true;

                break;
            }
        }

        if (!found) {

            accounts.add(account);
        }

        saveAll(accounts);
    }


    // Find Account by ID

    public Account findById(
            int accountNumber) {

        ArrayList<Account> accounts =
                loadAccounts();

        for (Account account : accounts) {

            if (account.getAccountNumber()
                    == accountNumber) {

                return account;
            }
        }

        return null;
    }


    // Find All Accounts

    public ArrayList<Account> findAll() {

        return loadAccounts();
    }


    // Delete Account

    public boolean deleteById(
            int accountNumber) {

        ArrayList<Account> accounts =
                loadAccounts();

        for (int i = 0;
             i < accounts.size();
             i++) {

            if (accounts.get(i)
                    .getAccountNumber()
                    == accountNumber) {

                accounts.remove(i);

                saveAll(accounts);

                return true;
            }
        }

        return false;
    }
}


// ==================== TRANSACTION REPOSITORY ====================

class TransactionRepository {

    private String fileName =
            "transactions.json";


    // Load Transactions

    public ArrayList<Transaction> load() {

        ArrayList<Transaction> transactions =
                new ArrayList<>();

        File file = new File(fileName);

        if (!file.exists()) {
            return transactions;
        }

        try {

            Scanner fileScanner =
                    new Scanner(file);

            while (fileScanner.hasNextLine()) {

                String line =
                        fileScanner.nextLine();

                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data =
                        line.split("\\|");

                if (data.length == 4) {

                    int accountNumber =
                            Integer.parseInt(data[0]);

                    String type =
                            data[1];

                    double amount =
                            Double.parseDouble(data[2]);

                    LocalDateTime dateTime =
                            LocalDateTime.parse(data[3]);


                    Transaction transaction =
                            new Transaction(
                                    accountNumber,
                                    type,
                                    amount
                            );


                    transaction.dateTime =
                            dateTime;


                    transactions.add(
                            transaction
                    );
                }
            }

            fileScanner.close();

        } catch (Exception e) {

            System.out.println(
                    "Error loading transactions!"
            );
        }

        return transactions;
    }


    // Save Transactions

    public void save(
            ArrayList<Transaction> transactions) {

        try {

            FileWriter writer =
                    new FileWriter(fileName);

            for (Transaction transaction :
                    transactions) {

                writer.write(
                        transaction.accountNumber
                                + "|"
                                + transaction.type
                                + "|"
                                + transaction.amount
                                + "|"
                                + transaction.dateTime
                                + "\n"
                );
            }

            writer.close();

        } catch (Exception e) {

            System.out.println(
                    "Error saving transactions!"
            );
        }
    }
}


// ==================== MAIN BANK MANAGEMENT SYSTEM ====================

public class BankImp {


    static Scanner sc =
            new Scanner(System.in);


    static AccountRepository accountRepository =
            new JsonFileAccountRepository();


    static TransactionRepository
            transactionRepository =
            new TransactionRepository();


    static ArrayList<Transaction>
            transactions =
            new ArrayList<>();


    // ================= CREATE ACCOUNT =================

    public static void createAccount() {

        System.out.print(
                "Enter Account Number: "
        );

        int accountNumber =
                sc.nextInt();


        Account existingAccount =
                accountRepository.findById(
                        accountNumber
                );


        if (existingAccount != null) {

            System.out.println(
                    "Account Already Exists!"
            );

            return;
        }


        sc.nextLine();


        System.out.print(
                "Enter Account Holder Name: "
        );

        String name =
                sc.nextLine();


        System.out.print(
                "Enter Initial Balance: "
        );

        double balance =
                sc.nextDouble();


        if (balance < 0) {

            System.out.println(
                    "Invalid Balance!"
            );

            return;
        }


        Account account =
                new Account(
                        accountNumber,
                        name,
                        balance
                );


        accountRepository.save(account);


        System.out.println(
                "Account Created Successfully!"
        );
    }


    // ================= DEPOSIT =================

    public static void deposit() {

        System.out.print(
                "Enter Account Number: "
        );

        int accountNumber =
                sc.nextInt();


        Account account =
                accountRepository.findById(
                        accountNumber
                );


        if (account == null) {

            System.out.println(
                    "Account Not Found!"
            );

            return;
        }


        System.out.print(
                "Enter Deposit Amount: "
        );

        double amount =
                sc.nextDouble();


        if (amount <= 0) {

            System.out.println(
                    "Invalid Amount!"
            );

            return;
        }


        account.setBalance(
                account.getBalance()
                        + amount
        );


        // Save updated account

        accountRepository.save(account);


        // Create transaction

        Transaction transaction =
                new Transaction(
                        accountNumber,
                        "DEPOSIT",
                        amount
                );


        transactions.add(transaction);


        // Save transaction

        transactionRepository.save(
                transactions
        );


        System.out.println(
                "Amount Deposited Successfully!"
        );

        System.out.println(
                "Current Balance: "
                        + account.getBalance()
        );
    }


    // ================= WITHDRAW =================

    public static void withdraw() {

        System.out.print(
                "Enter Account Number: "
        );

        int accountNumber =
                sc.nextInt();


        Account account =
                accountRepository.findById(
                        accountNumber
                );


        if (account == null) {

            System.out.println(
                    "Account Not Found!"
            );

            return;
        }


        System.out.print(
                "Enter Withdraw Amount: "
        );

        double amount =
                sc.nextDouble();


        if (amount <= 0) {

            System.out.println(
                    "Invalid Amount!"
            );

            return;
        }


        if (amount > account.getBalance()) {

            System.out.println(
                    "Insufficient Balance!"
            );

            return;
        }


        account.setBalance(
                account.getBalance()
                        - amount
        );


        // Save updated account

        accountRepository.save(account);


        // Create transaction

        Transaction transaction =
                new Transaction(
                        accountNumber,
                        "WITHDRAW",
                        amount
                );


        transactions.add(transaction);


        // Save transaction

        transactionRepository.save(
                transactions
        );


        System.out.println(
                "Amount Withdrawn Successfully!"
        );

        System.out.println(
                "Current Balance: "
                        + account.getBalance()
        );
    }


    // ================= BALANCE CHECK =================

    public static void balanceCheck() {

        System.out.print(
                "Enter Account Number: "
        );

        int accountNumber =
                sc.nextInt();


        Account account =
                accountRepository.findById(
                        accountNumber
                );


        if (account == null) {

            System.out.println(
                    "Account Not Found!"
            );

            return;
        }


        System.out.println(
                "\n----- ACCOUNT DETAILS -----"
        );

        System.out.println(
                "Account Number: "
                        + account.getAccountNumber()
        );

        System.out.println(
                "Account Holder Name: "
                        + account.getName()
        );

        System.out.println(
                "Current Balance: "
                        + account.getBalance()
        );
    }


    // ================= VIEW ALL ACCOUNTS =================

    public static void viewAllAccounts() {

        ArrayList<Account> accounts =
                accountRepository.findAll();


        if (accounts.isEmpty()) {

            System.out.println(
                    "No Accounts Found!"
            );

            return;
        }


        System.out.println(
                "\n----- ALL ACCOUNTS -----"
        );


        for (Account account : accounts) {

            System.out.println(
                    "\nAccount Number: "
                            + account.getAccountNumber()
            );

            System.out.println(
                    "Name: "
                            + account.getName()
            );

            System.out.println(
                    "Balance: "
                            + account.getBalance()
            );
        }
    }


    // ================= DELETE ACCOUNT =================

    public static void deleteAccount() {

        System.out.print(
                "Enter Account Number: "
        );

        int accountNumber =
                sc.nextInt();


        boolean deleted =
                accountRepository.deleteById(
                        accountNumber
                );


        if (deleted) {

            System.out.println(
                    "Account Deleted Successfully!"
            );

        } else {

            System.out.println(
                    "Account Not Found!"
            );
        }
    }


    // ================= MAIN METHOD =================

    public static void main(
            String[] args) {


        // Load previous transactions

        transactions =
                transactionRepository.load();


        int choice;


        do {

            System.out.println(
                    "\n===== BANK MANAGEMENT SYSTEM ====="
            );

            System.out.println(
                    "1. Create Account"
            );

            System.out.println(
                    "2. Deposit"
            );

            System.out.println(
                    "3. Withdraw"
            );

            System.out.println(
                    "4. Balance Check"
            );

            System.out.println(
                    "5. View All Accounts"
            );

            System.out.println(
                    "6. Delete Account"
            );

            System.out.println(
                    "7. Exit"
            );


            System.out.print(
                    "Enter Your Choice: "
            );


            choice =
                    sc.nextInt();


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

                    balanceCheck();

                    break;


                case 5:

                    viewAllAccounts();

                    break;


                case 6:

                    deleteAccount();

                    break;


                case 7:

                    System.out.println(
                            "Thank You!"
                    );

                    break;


                default:

                    System.out.println(
                            "Invalid Choice!"
                    );
            }

        } while (choice != 7);


        sc.close();
    }
} 