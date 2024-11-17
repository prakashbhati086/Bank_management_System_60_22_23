import java.util.*;

public class BankingSystem {
    private static Map<String, BankAccount> accounts = new HashMap<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1: Create New Account");
            System.out.println("2: Access Existing Account");
            System.out.println("3: Delete Account");
            System.out.println("4: View All Accounts");
            System.out.println("5: Exit");

            System.out.print("Enter your option: ");
            int option = sc.nextInt();
            sc.nextLine();  // Consume newline character

            switch (option) {
                case 1:
                    System.out.println("Enter customer name: ");
                    String name = sc.nextLine();
                    System.out.println("Enter customer ID: ");
                    String id = sc.nextLine();

                    BankAccount newAccount = new BankAccount(name, id);
                    accounts.put(id, newAccount);
                    System.out.println("Account created successfully.");
                    break;

                case 2:
                    System.out.println("Enter your customer ID: ");
                    String customerId = sc.nextLine();

                    BankAccount account = accounts.get(customerId);
                    if (account != null) {
                        account.showMenu(accounts); // Pass 'accounts' to showMenu
                    } else {
                        System.out.println("No account found with ID: " + customerId);
                    }
                    break;

                case 3:
                    System.out.println("Enter customer ID to delete: ");
                    String deleteId = sc.nextLine();
                    if (accounts.containsKey(deleteId)) {
                        accounts.remove(deleteId);
                        System.out.println("Account deleted successfully.");
                    } else {
                        System.out.println("No account found with ID: " + deleteId);
                    }
                    break;

                case 4:
                    if (accounts.isEmpty()) {
                        System.out.println("No accounts available.");
                    } else {
                        System.out.println("All accounts:");
                        for (String accountId : accounts.keySet()) {
                            System.out.println(accountId);
                        }
                    }
                    break;

                case 5:
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
