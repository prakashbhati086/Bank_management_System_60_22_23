import java.util.*;

public class BankingSystem {
    private static Map<String, BankAccount> accounts = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Welcome to the Banking System ---");
            System.out.println("1: Create New Account");
            System.out.println("2: Access Existing Account");
            System.out.println("3: Display All Accounts");
            System.out.println("4: Exit");

            System.out.print("Enter your option: ");
            int option = sc.nextInt();
            sc.nextLine();  

            switch (option) {
                case 1:
                    System.out.print("Enter customer name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter customer ID: ");
                    String id = sc.nextLine();

                    if (accounts.containsKey(id)) {
                        System.out.println("An account with this ID already exists.");
                    } else {
                        BankAccount newAccount = new BankAccount(name, id);
                        accounts.put(id, newAccount);
                        System.out.println("Account created successfully.");
                    }
                    break;

                case 2:
                    System.out.print("Enter your customer ID: ");
                    String customerId = sc.nextLine();

                    BankAccount account = accounts.get(customerId);
                    if (account != null) {
                        account.showMenu();
                    } else {
                        System.out.println("No account found with ID: " + customerId);
                    }
                    break;

                case 3:
                    if (accounts.isEmpty()) {
                        System.out.println("No accounts available.");
                    } else {
                        System.out.println("\n--- Account Holders ---");
                        for (String key : accounts.keySet()) {
                            BankAccount acc = accounts.get(key);
                            System.out.println("ID: " + acc.getCustomerId() + ", Name: " + acc.getCustomerName());
                        }
                    }
                    break;

                case 4:
                    System.out.println("Exiting system. Thank you!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid Option! Please try again.");
                    break;
            }
        }
    }
}

class BankAccount {
    private String customerName;
    private String customerId;
    private double balance;
    private List<String> transactionHistory = new ArrayList<>();

    public BankAccount(String customerName, String customerId) {
        this.customerName = customerName;
        this.customerId = customerId;
        this.balance = 0.0;
        transactionHistory.add("Account created. Initial balance: $0.00");
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void showMenu() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Account Menu for " + customerName + " ---");
            System.out.println("1: Check Balance");
            System.out.println("2: Deposit");
            System.out.println("3: Withdraw");
            System.out.println("4: Transfer");
            System.out.println("5: View Transaction History");
            System.out.println("6: Calculate Interest");
            System.out.println("7: Exit");

            System.out.print("Enter your option: ");
            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    System.out.println("Your balance is: $" + balance);
                    break;

                case 2:
                    System.out.print("Enter deposit amount: ");
                    double depositAmount = sc.nextDouble();
                    if (depositAmount > 0) {
                        deposit(depositAmount);
                        System.out.println("Successfully deposited $" + depositAmount);
                    } else {
                        System.out.println("Invalid amount.");
                    }
                    break;

                case 3:
                    System.out.print("Enter withdrawal amount: ");
                    double withdrawalAmount = sc.nextDouble();
                    if (withdrawalAmount > 0 && withdrawalAmount <= balance) {
                        withdraw(withdrawalAmount);
                        System.out.println("Successfully withdrew $" + withdrawalAmount);
                    } else {
                        System.out.println("Insufficient balance or invalid amount.");
                    }
                    break;

                case 4:
                    System.out.print("Enter recipient's customer ID: ");
                    String recipientId = sc.nextLine();
                    System.out.print("Enter transfer amount: ");
                    double transferAmount = sc.nextDouble();
                    transfer(recipientId, transferAmount);
                    break;

                case 5:
                    System.out.println("\n--- Transaction History ---");
                    for (String transaction : transactionHistory) {
                        System.out.println(transaction);
                    }
                    break;

                case 6:
                    System.out.print("Enter annual interest rate (in %): ");
                    double interestRate = sc.nextDouble();
                    calculateInterest(interestRate);
                    break;

                case 7:
                    System.out.println("Exiting account menu.");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    private void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: $" + amount);
    }

    private void withdraw(double amount) {
        balance -= amount;
        transactionHistory.add("Withdrew: $" + amount);
    }

    private void transfer(String recipientId, double amount) {
        if (BankingSystem.accounts.containsKey(recipientId)) {
            if (amount > 0 && amount <= balance) {
                BankAccount recipient = BankingSystem.accounts.get(recipientId);
                this.withdraw(amount);
                recipient.deposit(amount);
                transactionHistory.add("Transferred: $" + amount + " to ID " + recipientId);
                System.out.println("Successfully transferred $" + amount + " to " + recipient.getCustomerName());
            } else {
                System.out.println("Insufficient balance or invalid amount.");
            }
        } else {
            System.out.println("Recipient account not found.");
        }
    }

    private void calculateInterest(double annualRate) {
        if (annualRate > 0) {
            double interest = balance * (annualRate / 100);
            System.out.println("Annual interest on your balance: $" + interest);
        } else {
            System.out.println("Invalid interest rate.");
        }
    }
}
