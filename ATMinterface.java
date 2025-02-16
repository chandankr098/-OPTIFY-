import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

class Account {
    private String userId;
    private String userPin;
    private double balance;
    private ArrayList<String> transactionHistory;

    public Account(String userId, String userPin) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPin() {
        return userPin;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        transactionHistory.add("Deposited: " + amount);
    }

    public boolean withdraw(double amount) {
        if (amount > balance) {
            return false;
        }
        balance -= amount;
        transactionHistory.add("Withdrew: " + amount);
        return true;
    }

    public void transfer(Account recipient, double amount) {
        if (amount <= balance) {
            balance -= amount;
            recipient.deposit(amount);
            transactionHistory.add("Transferred: " + amount + " to " + recipient.getUserId());
        }
    }

    public ArrayList<String> getTransactionHistory() {
        return transactionHistory;
    }
}

class ATM {
    private HashMap<String, Account> accounts;

    public ATM() {
        accounts = new HashMap<>();
    }

    public void addAccount(String userId, String userPin) {
        accounts.put(userId, new Account(userId, userPin));
    }

    public Account authenticate(String userId, String userPin) {
        Account account = accounts.get(userId);
        if (account != null && account.getUserPin().equals(userPin)) {
            return account;
        }
        return null;
    }

    public void displayMenu(Account account, Scanner scanner) {
        while (true) {
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw Amount");
            System.out.println("3. Deposit Amount");
            System.out.println("4. View Mini Statement");
            System.out.println("5. Transfer");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    ArrayList<String> history = account.getTransactionHistory();
                    if (history.isEmpty()) {
                        System.out.println("No transactions yet.");
                    } else {
                        for (String transaction : history) {
                            System.out.println(transaction);
                        }
                    }
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    if (account.withdraw(withdrawAmount)) {
                        System.out.println("Withdrawal successful.");
                    } else {
                        System.out.println("Insufficient balance.");
                    }
                    break;
                case 3:
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    account.deposit(depositAmount);
                    System.out.println("Deposit successful.");
                    break;
                case 4:
                    System.out.println("Current Balance: " + account.getBalance());
                    break;
                case 5:
                    System.out.print("Enter recipient user ID: ");
                    String recipientId = scanner.next();
                    Account recipient = accounts.get(recipientId);
                    if (recipient != null) {
                        System.out.print("Enter amount to transfer: ");
                        double transferAmount = scanner.nextDouble();
                        account.transfer(recipient, transferAmount);
                        System.out.println("Transfer successful.");
                    } else {
                        System.out.println("Recipient not found.");
                    }
                    break;
                case 6:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }
}

public class ATMInterface {
    public static void main(String[] args) {
        ATM atm = new ATM();
        Scanner scanner = new Scanner(System.in);

        atm.addAccount("chandan", "1234");
        atm.addAccount("Rahul", "5678");

        System.out.print("Enter user ID: ");
        String userId = scanner.next();
        System.out.print("Enter PIN: ");
        String userPin = scanner.next();

        Account account = atm.authenticate(userId, userPin);

        if (account != null) {
            atm.displayMenu(account, scanner);
        } else {
            System.out.println("Authentication failed. Exiting...");
        }

        scanner.close();
    }
}
