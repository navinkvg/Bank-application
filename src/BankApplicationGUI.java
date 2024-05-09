import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class BankApplicationGUI extends Application {

    private BankAccount bankAccount;

    @Override
    public void start(Stage primaryStage) {
        bankAccount = new BankAccount("", "");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField idField = new TextField();
        idField.setPromptText("Customer ID");
        Button loginButton = new Button("Login");

        gridPane.add(nameField, 0, 0);
        gridPane.add(idField, 1, 0);
        gridPane.add(loginButton, 2, 0);

        loginButton.setOnAction(event -> {
            String name = nameField.getText();
            String id = idField.getText();
            if (!name.isEmpty() && !id.isEmpty()) {
                bankAccount.setCustomerName(name);
                bankAccount.setCustomerId(id);
                showBankingScene(primaryStage);
            }
        });

        Scene scene = new Scene(gridPane, 600, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Bank Application");
        primaryStage.show();
    }

    private void showBankingScene(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label welcomeLabel = new Label("Welcome " + bankAccount.getCustomerName());
        Label idLabel = new Label("Your ID: " + bankAccount.getCustomerId());

        Button checkBalanceButton = new Button("Check Balance");
        Button depositButton = new Button("Deposit");
        Button withdrawButton = new Button("Withdraw");
        Button prevTransactionButton = new Button("Previous Transaction");
        Button exitButton = new Button("Exit");

        gridPane.add(welcomeLabel, 0, 0, 2, 1);
        gridPane.add(idLabel, 0, 1, 2, 1);
        gridPane.add(checkBalanceButton, 0, 2);
        gridPane.add(depositButton, 1, 2);
        gridPane.add(withdrawButton, 0, 3);
        gridPane.add(prevTransactionButton, 1, 3);
        gridPane.add(exitButton, 0, 4, 2, 1);

        checkBalanceButton.setOnAction(event -> {
            showAlert("Balance", "Your balance is: " + bankAccount.getBal());
        });

        depositButton.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Deposit");
            dialog.setHeaderText("Enter amount to deposit:");
            dialog.showAndWait().ifPresent(amount -> {
                bankAccount.deposit(Double.parseDouble(amount));
                showAlert("Deposit", "Amount deposited: " + amount);
            });
        });

        withdrawButton.setOnAction(event -> {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Withdraw");
            dialog.setHeaderText("Enter amount to withdraw:");
            dialog.showAndWait().ifPresent(amount -> {
                bankAccount.withdraw(Double.parseDouble(amount));
                showAlert("Withdraw", "Amount withdrawn: " + amount);
            });
        });

        prevTransactionButton.setOnAction(event -> {
            showAlert("Previous Transaction", bankAccount.getPreviousTrans());
        });

        exitButton.setOnAction(event -> {
            primaryStage.close();
        });

        Scene scene = new Scene(gridPane, 300, 200);
        primaryStage.setScene(scene);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class BankAccount {
    private double bal;
    private double prevTrans;
    private String customerName;
    private String customerId;

    public BankAccount(String customerName, String customerId) {
        this.customerName = customerName;
        this.customerId = customerId;
    }

    public double getBal() {
        return bal;
    }

    public double getPrevTrans() {
        return prevTrans;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void deposit(double amount) {
        if (amount != 0) {
            bal += amount;
            prevTrans = amount;
        }
    }

    public void withdraw(double amt) {
        if (amt != 0 && bal >= amt) {
            bal -= amt;
            prevTrans = -amt;
        } else if (bal < amt) {
            System.out.println("Bank balance insufficient");
        }
    }

    public String getPreviousTrans() {
        return null;
    }
}
