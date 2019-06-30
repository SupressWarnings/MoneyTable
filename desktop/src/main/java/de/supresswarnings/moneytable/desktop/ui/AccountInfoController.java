package de.supresswarnings.moneytable.desktop.ui;

import de.supresswarnings.moneytable.model.Account;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.text.DecimalFormat;

public class AccountInfoController {

    @FXML
    private Label name;

    @FXML
    private Label balance;

    @FXML
    private Label transactions;

    void initData(Account account){
        name.setText(account.getName());
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        balance.setText(decimalFormat.format(account.getBalance()) + " \u20AC");
        transactions.setText(account.getTransactions().size() + "");
    }
}
