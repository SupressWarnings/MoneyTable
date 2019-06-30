package de.supresswarnings.moneytable.desktop.ui;

import de.supresswarnings.moneytable.desktop.Main;
import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.Transaction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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
        HBox root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("./fxml/transaction_list.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            Main.LOGGER.logException("bla bla bla", e);
            Main.LOGGER.writeLog();
        }
        TransactionListController transactionListController = loader.getController();
        transactionListController.initData(account, this);
        centerPane.getChildren().add(root);
    }

    void showTransaction(Transaction transaction){
        Parent transactionPane = null;
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("./fxml/transaction.fxml"));
        try {
            transactionPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
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
            Main.LOGGER.logException("bla", e); //todo
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
