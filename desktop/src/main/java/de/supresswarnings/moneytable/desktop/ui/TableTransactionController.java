package de.supresswarnings.moneytable.desktop.ui;

import de.supresswarnings.moneytable.desktop.Main;
import de.supresswarnings.moneytable.model.transaction.Transaction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TableTransactionController extends ListCell<Transaction> {

    private Transaction transaction;

    private BaseSceneController baseSceneController;

    @FXML
    private HBox hbox;

    @FXML
    private Label name;

    @FXML
    private Label amount;

    @FXML
    private Label date;

    @FXML
    private Button details;

    private void onClickShow(){
        baseSceneController.showTransaction(transaction);
    }

    private void initData(Transaction transaction){
        if(transaction != null){
            this.transaction = transaction;

            name.setText(transaction.getName());
            DecimalFormat moneyFormat = new DecimalFormat("#.00");
            amount.setText(moneyFormat.format(transaction.getAmount()) + " \u20AC");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            date.setText(simpleDateFormat.format(new Date(transaction.getTime())));

            if(!transaction.isIncome()){
                amount.setTextFill(Color.RED);
            }
            details.setVisible(true);
        }else{
            Main.LOGGER.logException("ERROR (Code 901): No transaction values provided", new NullPointerException());
        }
    }

    @Override
    protected void updateItem(Transaction item, boolean empty){
        super.updateItem(item, empty);

        if(empty || item == null){
            setGraphic(null);
        }else{

            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("./fxml/table_transaction.fxml"));
            loader.setController(this);
            try {
                loader.load();
            } catch (IOException e) {
                Main.LOGGER.logException("Bla", e); //todo
                Main.LOGGER.writeLog();
            }

            details.setOnAction(e -> onClickShow());
            setGraphic(hbox);
            initData(item);
        }
    }

    void setBaseSceneController(BaseSceneController baseSceneController){
        this.baseSceneController = baseSceneController;
    }
}
