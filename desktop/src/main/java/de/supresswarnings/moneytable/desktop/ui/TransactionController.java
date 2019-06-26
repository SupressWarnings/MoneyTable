package de.supresswarnings.moneytable.desktop.ui;

import de.supresswarnings.moneytable.desktop.Main;
import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.Transaction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TransactionController {

    private Transaction transaction;
    private Account account;

    @FXML
    private Button editButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Label amountLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private void onEditClick(){
        Stage primaryStage = (Stage)editButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("./fxml/transaction_edit.fxml"));
        try {
            primaryStage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            Main.LOGGER.logException("ERROR: Code 701 (Failed to show transaction edit view).", e);
        }
        TransactionEditController transactionEditController = loader.getController();
        transactionEditController.initData(transaction, account);
    }

    void initData(Transaction transaction, Account account){
        this.account = account;
        this.transaction = transaction;

        nameLabel.setText(transaction.getName());

        DecimalFormat format = new DecimalFormat("0.00");
        String amount = format.format(transaction.getAmount()) + " €";
        amountLabel.setText(amount);

        Date date = new Date(transaction.getTime());
        dateLabel.setText(new SimpleDateFormat("dd.MM.yyyy").format(date));
    }
}
