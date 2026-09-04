import java.util.*;
import java.io.*;

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


class Transaction {

    int accountNumber;
    String type;
    double amount;

    Transaction(int accountNumber, String type, double amount) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
    }
}


// Account Repository
class AccountRepository {

    String fileName = "accounts.txt";

    void save(ArrayList<Account> accounts) {

        try {
            FileWriter writer = new FileWriter(fileName);

            for (Account account : accounts) {

                writer.write(
                    account.accountNumber + "," +
                    account.name + "," +
                    account.balance + "\n"
                );
            }

            writer.close();

        } catch (Exception e) {
            System.out.println("Error saving accounts!");
        }
    }


    ArrayList<Account> load() {

        ArrayList<Account> accounts = new ArrayList<>();

        try {

            File file = new File(fileName);

            if (!file.exists()) {
                return accounts;
            }

            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();

                String[] data = line.split(",");

                int accountNumber =
                    Integer.parseInt(data[0]);

                String name = data[1];

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

            fileScanner.close();

        } catch (Exception e) {

            System.out.println("Error loading accounts!");
        }

        return accounts;
    }
}


// Transaction Repository
class TransactionRepository {

    String fileName = "transactions.txt";

    void save(ArrayList<Transaction> transactions) {

        try {

            FileWriter writer = new FileWriter(fileName);

            for (Transaction transaction : transactions) {

                writer.write(
                    transaction.accountNumber + "," +
                    transaction.type + "," +
                    transaction.amount + "\n"
                );
            }

            writer.close();

        } catch (Exception e) {

            System.out.println(
                "Error saving transactions!"
            );
        }
    }


    ArrayList<Transaction> load() {

        ArrayList<Transaction> transactions =
            new ArrayList<>();

        try {

            File file = new File(fileName);

            if (!file.exists()) {
                return transactions;
            }

            Scanner fileScanner =
                new Scanner(file);

            while (fileScanner.hasNextLine()) {

                String line =
                    fileScanner.nextLine();

                String[] data =
                    line.split(",");

                int accountNumber =
                    Integer.parseInt(data[0]);

                String type =
                    data[1];

                double amount =
                    Double.parseDouble(data[2]);

                transactions.add(
                    new Transaction(
                        accountNumber,
                        type,
                        amount
                    )
                );
            }

            fileScanner.close();

        } catch (Exception e) {

            System.out.println(
                "Error loading transactions!"
            );
        }

        return transactions;
    }
}


// Main Class
public class BankFun {

    static Scanner sc =
        new Scanner(System.in);

    static AccountRepository accountRepository =
        new AccountRepository();

    static TransactionRepository transactionRepository =
        new TransactionRepository();


    static ArrayList<Account> accounts =
        new ArrayList<>();

    static ArrayList<Transaction> transactions =
        new ArrayList<>();


    // Find Account
    public static Account findAccount(
            int accountNumber) {

        for (Account account : accounts) {

            if (account.accountNumber ==
                    accountNumber) {

                return account;
            }
        }

        return null;
    }


    // Create Account
    public static void createAccount() {

        System.out.print(
            "Enter Account Number: "
        );

        int accountNumber =
            sc.nextInt();


        if (findAccount(accountNumber)
                != null) {

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


        Account account =
            new Account(
                accountNumber,
                name,
                balance
            );


        accounts.add(account);


        accountRepository.save(accounts);


        System.out.println(
            "Account Created Successfully!"
        );
    }


    // Deposit
    public static void deposit() {

        System.out.print(
            "Enter Account Number: "
        );

        int accountNumber =
            sc.nextInt();


        Account account =
            findAccount(accountNumber);


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


        account.balance =
            account.balance + amount;


        transactions.add(
            new Transaction(
                accountNumber,
                "DEPOSIT",
                amount
            )
        );


        accountRepository.save(accounts);

        transactionRepository.save(
            transactions
        );


        System.out.println(
            "Amount Deposited Successfully!"
        );

        System.out.println(
            "Current Balance: " +
            account.balance
        );
    }


    // Withdraw
    public static void withdraw() {

        System.out.print(
            "Enter Account Number: "
        );

        int accountNumber =
            sc.nextInt();


        Account account =
            findAccount(accountNumber);


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


        if (amount > account.balance) {

            System.out.println(
                "Insufficient Balance!"
            );

            return;
        }


        account.balance =
            account.balance - amount;


        transactions.add(
            new Transaction(
                accountNumber,
                "WITHDRAW",
                amount
            )
        );


        accountRepository.save(accounts);

        transactionRepository.save(
            transactions
        );


        System.out.println(
            "Amount Withdrawn Successfully!"
        );

        System.out.println(
            "Current Balance: " +
            account.balance
        );
    }


    // Balance Check
    public static void balanceCheck() {

        System.out.print(
            "Enter Account Number: "
        );

        int accountNumber =
            sc.nextInt();


        Account account =
            findAccount(accountNumber);


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
            "Account Number: " +
            account.accountNumber
        );

        System.out.println(
            "Account Holder Name: " +
            account.name
        );

        System.out.println(
            "Current Balance: " +
            account.balance
        );
    }


    // Main Method
    public static void main(
            String[] args) {


        // Load previously saved accounts
        accounts =
            accountRepository.load();


        // Load transaction history
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
                "5. Exit"
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

                    System.out.println(
                        "Thank You!"
                    );

                    break;


                default:

                    System.out.println(
                        "Invalid Choice!"
                    );
            }

        } while (choice != 5);


        sc.close(); 
    } 
}