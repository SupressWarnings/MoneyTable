package de.supresswarnings.moneytable.desktop.ui;

import de.supresswarnings.moneytable.desktop.Main;
import de.supresswarnings.moneytable.desktop.data.DataInserter;
import de.supresswarnings.moneytable.desktop.data.DataProvider;
import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.Transaction;
import de.supresswarnings.moneytable.model.transaction.TransactionFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setOnCloseRequest(event -> {
            Main.LOGGER.log("INFO: Application closed.");
            Main.LOGGER.writeLog();
        });

        Account account;
        DataProvider provider = new DataProvider();
        if(provider.getAccounts().isEmpty()){
            account = new Account("test", 0.0d);
            new DataInserter().insertAccount(account);
        }else{
            account = provider.getAccount(provider.getAccounts().get(0).getName());
        }
        primaryStage.setTitle("MoneyTable");
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("./fxml/base_scene.fxml"));
        Parent root = loader.load();
        for(int i = 1; i < 15; ++i){
            Transaction transaction = TransactionFactory.createTransaction("test" + i, i, System.currentTimeMillis());
            account.add(transaction);
            Main.LOGGER.log("INFO: " + i + ". Testtransaktion erstellt.");
        }
        BaseSceneController baseSceneController = loader.getController();
        baseSceneController.setAccount(account);
        baseSceneController.showTransactionList();

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.show();
    }
}
