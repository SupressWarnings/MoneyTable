package de.supresswarnings.moneytable.desktop.ui;

import de.supresswarnings.moneytable.desktop.Main;
import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.Transaction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class BaseSceneController {

    @FXML
    private StackPane centerPane;

    @FXML
    private AccountInfoController accountInfoController;

    private Account account;

    void setAccount(Account account){
        this.account = account;
    }

    void showTransactionList(){
        accountInfoController.initData(account);
        HBox transactionList = null;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("./fxml/transaction_list.fxml"));
        try {
            transactionList = loader.load();
        } catch (IOException e) {
            Main.LOGGER.logException("ERROR: Code <> (Transaction List not loading properly)", e); //todo add error code
        }
        TransactionListController transactionListController = loader.getController();
        transactionListController.initData(account, this);
        centerPane.getChildren().add(transactionList);
    }

    void showTransaction(Transaction transaction){
        Parent transactionPane = null;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("./fxml/transaction.fxml"));
        try {
            transactionPane = loader.load();
        } catch (IOException e) {
            Main.LOGGER.logException("ERROR: Code <> (Transaction Pane does not load properly)", e); //todo add error code
        }
        TransactionController transactionController = loader.getController();
        transactionController.initData(transaction, this);
        centerPane.getChildren().clear();
        centerPane.getChildren().add(transactionPane);
    }

    void editTransaction(Transaction transaction){
        Parent editPane = null;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("./fxml/transaction_edit.fxml"));
        try {
            editPane = loader.load();
        } catch (IOException e) {
            Main.LOGGER.logException("ERROR: Code <> (Edit Pane does not load properly)", e); //todo add error code
        }
        TransactionEditController transactionEditController = loader.getController();
        transactionEditController.initData(transaction, account, this);
        centerPane.getChildren().clear();
        centerPane.getChildren().add(editPane);
    }

    @FXML
    void createTransaction(){
        editTransaction(null);
    }
}
