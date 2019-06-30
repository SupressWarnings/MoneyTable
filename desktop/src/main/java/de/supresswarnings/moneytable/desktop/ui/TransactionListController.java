package de.supresswarnings.moneytable.desktop.ui;

import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;

import java.util.Comparator;

public class TransactionListController {

    @FXML
    private ListView<Transaction> listView;

    @FXML
    private ChoiceBox<String> sort;

    private ObservableList<Transaction> transactionObservableList;

    public void initialize(){
        ObservableList<String> sortChoices = FXCollections.observableArrayList();
        sortChoices.add("Name");
        sortChoices.add("Smallest Amount");
        sortChoices.add("Biggest Amount");
        sortChoices.add("Earliest");
        sortChoices.add("Latest");
        sort.setItems(sortChoices);
        sort.setOnAction(e -> changeSort());
    }

    void initData(Account account, BaseSceneController baseSceneController){
        transactionObservableList = FXCollections.observableList(account.getTransactions());
        listView.setItems(transactionObservableList);
        listView.setCellFactory(v -> {
            TableTransactionController tableTransactionController = new TableTransactionController();
            tableTransactionController.setBaseSceneController(baseSceneController);
            return tableTransactionController;
        });
        sort.setValue("Latest");
    }

    private void changeSort(){
        switch (sort.getValue()){
            case "Name":
                transactionObservableList.sort(Comparator.comparing(Transaction::getName));break;
            case "Smallest Amount":
                transactionObservableList.sort(Comparator.comparing(Transaction::getAmount));break;
            case "Biggest Amount":
                transactionObservableList.sort(Comparator.comparing(Transaction::getAmount));
                FXCollections.reverse(transactionObservableList);break;
            case "Earliest":
                transactionObservableList.sort(Comparator.comparing(Transaction::getTime));break;
            case "Latest":
                transactionObservableList.sort(Comparator.comparing(Transaction::getTime));
                FXCollections.reverse(transactionObservableList);
        }
    }
}
