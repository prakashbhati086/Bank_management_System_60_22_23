import java.util.*;

class BankAccount {
    private double balance;
    private List<String> transactionHistory = new ArrayList<>();
    private String customerName;
    private String customerId;

    public BankAccount(String cname, String cid) {
        this.customerName = cname;
        this.customerId = cid;
        this.balance = 0.0;
        transactionHistory.add("Account created. Initial balance: $0.00");
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactionHistory.add("Deposited: $" + amount);
            System.out.println("Deposit successful! Your new balance is: $" + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
        } else if (amount > balance) {
            System.out.println("Insufficient balance!");
        } else {
            balance -= amount;
            transactionHistory.add("Withdrawn: $" + amount);
            System.out.println("Withdrawal successful! Your new balance is: $" + balance);
        }
    }

    public double getBalance() {
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
        if (rate <= 0) {
            System.out.println("Invalid interest rate.");
        } else {
            double interest = balance * (rate / 100);
            balance += interest;
            transactionHistory.add("Interest added: $" + interest);
            System.out.println("Interest added at " + rate + "%. New balance is: $" + balance);
        }
    }

    public void transfer(String recipientId, double amount) {
        if (BankingSystem.accountExists(recipientId)) {
            if (amount > 0 && amount <= balance) {
                BankAccount recipient = BankingSystem.getAccount(recipientId);
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

    public void showMenu() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Account Menu for " + customerName + " ---");
            System.out.println("1: Check Balance");
            System.out.println("2: Deposit");
            System.out.println("3: Withdraw");
            System.out.println("4: View Transaction History");
            System.out.println("5: Calculate Interest");
            System.out.println("6: Transfer Money");
            System.out.println("7: Exit");

            try {
                System.out.print("Enter your option: ");
                int option = sc.nextInt();

                switch (option) {
                    case 1:
                        System.out.println("Your balance is: $" + balance);
                        break;
                    case 2:
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = sc.nextDouble();
                        deposit(depositAmount);
                        break;
                    case 3:
                        System.out.print("Enter amount to withdraw: ");
                        double withdrawAmount = sc.nextDouble();
                        withdraw(withdrawAmount);
                        break;
                    case 4:
                        getTransactionHistory();
                        break;
                    case 5:
                        System.out.print("Enter interest rate: ");
                        double rate = sc.nextDouble();
                        calculateInterest(rate);
                        break;
                    case 6:
                        System.out.print("Enter recipient's customer ID: ");
                        String recipientId = sc.next();
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = sc.nextDouble();
                        transfer(recipientId, transferAmount);
                        break;
                    case 7:
                        System.out.println("Thank you for using our bank.");
                        return;
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
