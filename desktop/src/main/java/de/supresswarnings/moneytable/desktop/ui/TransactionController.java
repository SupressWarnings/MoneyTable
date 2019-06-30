package de.supresswarnings.moneytable.desktop.ui;

import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.Transaction;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class TransactionController {

    private Transaction transaction;

    private BaseSceneController baseSceneController;

    @FXML
    private Label nameLabel;

    @FXML
    private Label amountLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private void onClickEdit(){
        baseSceneController.editTransaction(transaction);
    }

    @FXML
    private void returnToList(){
        baseSceneController.showTransactionList();
    }

    void initData(Transaction transaction, BaseSceneController baseSceneController){
        this.transaction = transaction;
        this.baseSceneController = baseSceneController;

        nameLabel.setText(transaction.getName());

        DecimalFormat format = new DecimalFormat("0.00");
        String amount = format.format(transaction.getAmount()) + " \u20AC";
        amountLabel.setText(amount);

        Date date = new Date(transaction.getTime());
        dateLabel.setText(new SimpleDateFormat("dd.MM.yyyy").format(date));
    }
}
