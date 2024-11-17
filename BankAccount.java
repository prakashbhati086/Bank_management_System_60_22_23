import java.io.*;
import java.util.*;

public class BankAccount {
   private int balance;
   private List<String> transactionHistory = new ArrayList<>();
   private String customerName;
   private String customerId;

   public BankAccount(String name, String id) {
      this.customerName = name;
      this.customerId = id;
   }

   public void deposit(int amount) {
      if (amount > 0) {
         this.balance += amount;
         this.transactionHistory.add("Deposited: " + amount);
         System.out.println("Deposit successful! Your new balance is: " + this.balance);
      }
   }

   public void withdraw(int amount) {
      if (amount > this.balance) {
         System.out.println("Insufficient balance!");
      } else {
         this.balance -= amount;
         this.transactionHistory.add("Withdrawn: " + amount);
         System.out.println("Withdrawal successful! Your new balance is: " + this.balance);
      }
   }

   public int getBalance() {
      return this.balance;
   }

   public void getTransactionHistory() {
      if (this.transactionHistory.isEmpty()) {
         System.out.println("No transactions found.");
      } else {
         System.out.println("Transaction History:");
         for (String transaction : this.transactionHistory) {
            System.out.println(transaction);
         }
      }
   }

   public void calculateInterest(double rate) {
      double interest = this.balance * rate / 100.0;
      this.balance += interest;
      this.transactionHistory.add("Interest added: " + interest);
      System.out.println("Interest added at " + rate + "%. New balance is: " + this.balance);
   }

   public void saveAccountData() {
      try (FileWriter writer = new FileWriter(this.customerId + ".txt")) {
         writer.write("Customer Name: " + this.customerName + "\n");
         writer.write("Customer ID: " + this.customerId + "\n");
         writer.write("Balance: " + this.balance + "\n");
         writer.write("Transaction History: " + String.valueOf(this.transactionHistory) + "\n");
         System.out.println("Account data saved.");
      } catch (IOException e) {
         System.out.println("An error occurred while saving account data.");
      }
   }

   public void loadAccountData() {
      try (BufferedReader reader = new BufferedReader(new FileReader(this.customerId + ".txt"))) {
         String line;
         while ((line = reader.readLine()) != null) {
            System.out.println(line);
         }
      } catch (IOException e) {
         System.out.println("An error occurred while loading account data.");
      }
   }

   public void transferMoney(Map<String, BankAccount> accounts, String targetId, int amount) {
      if (amount <= this.balance && accounts.containsKey(targetId)) {
         BankAccount targetAccount = accounts.get(targetId);
         this.balance -= amount;
         targetAccount.deposit(amount);
         this.transactionHistory.add("Transferred: " + amount + " to " + targetAccount.customerId);
         targetAccount.transactionHistory.add("Received: " + amount + " from " + this.customerId);
         System.out.println("Transfer successful! Your new balance is: " + this.balance);
      } else {
         System.out.println("Transfer failed due to insufficient funds or invalid account.");
      }
   }

   public void updateCustomerInformation(String newName, String newId) {
      this.customerName = newName;
      this.customerId = newId;
      System.out.println("Customer information updated.");
   }

   public void showMenu(Map<String, BankAccount> accounts) {
      Scanner sc = new Scanner(System.in);

      while (true) {
         System.out.println("\n1: Check Balance");
         System.out.println("2: Deposit");
         System.out.println("3: Withdraw");
         System.out.println("4: View Transaction History");
         System.out.println("5: Calculate Interest");
         System.out.println("6: Save Account Data");
         System.out.println("7: Load Account Data");
         System.out.println("8: Transfer Money");
         System.out.println("9: Update Customer Info");
         System.out.println("10: Exit");

         try {
            System.out.print("Enter your option: ");
            int option = sc.nextInt();

            switch (option) {
               case 1:
                  System.out.println("Balance = " + this.balance);
                  break;
               case 2:
                  System.out.print("Enter amount to deposit: ");
                  int depositAmount = sc.nextInt();
                  this.deposit(depositAmount);
                  break;
               case 3:
                  System.out.print("Enter amount to withdraw: ");
                  int withdrawAmount = sc.nextInt();
                  this.withdraw(withdrawAmount);
                  break;
               case 4:
                  this.getTransactionHistory();
                  break;
               case 5:
                  System.out.print("Enter interest rate: ");
                  double rate = sc.nextDouble();
                  this.calculateInterest(rate);
                  break;
               case 6:
                  this.saveAccountData();
                  break;
               case 7:
                  this.loadAccountData();
                  break;
               case 8:
                  System.out.print("Enter target customer ID for transfer: ");
                  String targetId = sc.next();
                  System.out.print("Enter amount to transfer: ");
                  int transferAmount = sc.nextInt();
                  this.transferMoney(accounts, targetId, transferAmount);  // Pass accounts map here
                  break;
               case 9:
                  System.out.print("Enter new name: ");
                  String newName = sc.next();
                  System.out.print("Enter new customer ID: ");
                  String newId = sc.next();
                  this.updateCustomerInformation(newName, newId);
                  break;
               case 10:
                  System.out.println("Thank you for using our bank.");
                  System.exit(0);
                  break;
               default:
                  System.out.println("Invalid option! Please try again.");
                  break;
            }
         } catch (InputMismatchException e) {
            System.out.println("Invalid input, please try again.");
            sc.nextLine();  // Consume invalid input
         }
      }
   }
}
