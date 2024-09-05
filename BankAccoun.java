import java.util.*;
import java.io.*;

class BankAccount {
    private int balance;
    private List<String> transactionHistory = new ArrayList<>();
    private String customerName;
    private String customerId;

    public BankAccount(String cname, String cid) {
        customerName = cname;
        customerId = cid;
    }

    public void deposit(int amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: " + amount);
            System.out.println("Deposit successful! Your new balance is: " + balance);
        }
    }

    public void withdraw(int amount) {
        if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            balance -= amount;
            transactionHistory.add("Withdrawn: " + amount);
            System.out.println("Withdrawal successful! Your new balance is: " + balance);
        }
    }

    public int getBalance() {
        return balance;
    }

    public void getTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            System.out.println("Transaction History:");
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }

    public void calculateInterest(double rate) {
        double interest = balance * rate / 100;
        balance += interest;
        transactionHistory.add("Interest added: " + interest);
        System.out.println("Interest added at " + rate + "%. New balance is: " + balance);
    }

    public void saveAccountData() {
        try (FileWriter writer = new FileWriter(customerId + ".txt")) {
            writer.write("Customer Name: " + customerName + "\n");
            writer.write("Customer ID: " + customerId + "\n");
            writer.write("Balance: " + balance + "\n");
            writer.write("Transaction History: " + transactionHistory + "\n");
            System.out.println("Account data saved.");
        } catch (IOException e) {
            System.out.println("An error occurred while saving account data.");
        }
    }

    public void loadAccountData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(customerId + ".txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while loading account data.");
        }
    }

    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n1: Check Balance");
            System.out.println("2: Deposit");
            System.out.println("3: Withdraw");
            System.out.println("4: View Transaction History");
            System.out.println("5: Calculate Interest");
            System.out.println("6: Save Account Data");
            System.out.println("7: Load Account Data");
            System.out.println("8: Exit");

            try {
                System.out.print("Enter your option: ");
                int option = sc.nextInt();

                switch (option) {
                    case 1:
                        System.out.println("Balance = " + balance);
                        break;
                    case 2:
                        System.out.println("Enter amount to deposit: ");
                        int amount = sc.nextInt();
                        deposit(amount);
                        break;
                    case 3:
                        System.out.println("Enter amount to withdraw: ");
                        int amount2 = sc.nextInt();
                        withdraw(amount2);
                        break;
                    case 4:
                        getTransactionHistory();
                        break;
                    case 5:
                        System.out.println("Enter interest rate: ");
                        double rate = sc.nextDouble();
                        calculateInterest(rate);
                        break;
                    case 6:
                        saveAccountData();
                        break;
                    case 7:
                        loadAccountData();
                        break;
                    case 8:
                        System.out.println("Thank you for using our bank.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid Option! Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, please try again.");
                sc.nextLine();
            }
        }
    }
}
