package de.supresswarnings.moneytable.desktop.ui;

import de.supresswarnings.moneytable.desktop.Main;
import de.supresswarnings.moneytable.desktop.data.DataInserter;
import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.Transaction;

import de.supresswarnings.moneytable.model.transaction.TransactionFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TransactionEditController {

    private Transaction oldValues;

    private Account account;

    @FXML
    private TextField nameField;

    @FXML
    private Label nameErrorLabel;

    @FXML
    private TextField amountField;

    @FXML
    private Label amountErrorLabel;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label dateErrorLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Label combinationErrorLabel;

    @FXML
    private void onSaveClick(){
        nameErrorLabel.setText("");
        amountErrorLabel.setText("");
        dateErrorLabel.setText("");
        combinationErrorLabel.setText("");

        if(checkName() && checkAmount() && checkDate() && checkCombination()){
            String name = nameField.getText();
            double amount = Double.parseDouble(amountField.getText().replaceAll(",", "."));
            DecimalFormat df = new DecimalFormat("#.##");
            amount = Double.parseDouble(df.format(amount));
            long time = datePicker.getValue().toEpochDay()*24*60*60*1000;
            Transaction newValues = TransactionFactory.createTransaction(name, amount, time);

            DataInserter inserter = new DataInserter();
            inserter.transactionInput(account, newValues, oldValues);

            Stage primaryStage = (Stage)saveButton.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("./fxml/transaction.fxml"));
            try {
                primaryStage.setScene(new Scene(loader.load()));
            } catch (IOException e) {
                Main.LOGGER.logException("ERROR: Code 801 (Failed to return to transaction view).", e);
            }
            TransactionController transactionController = loader.getController();
            transactionController.initData(newValues, account);
        }
    }

    void initData(Transaction transaction, Account account){
        this.account = account;

        if(transaction != null){
            oldValues = transaction;
            saveButton.setText("Save");

            nameField.setText(transaction.getName());
            amountField.setText("" + transaction.getAmount());

            Date date = new Date(transaction.getTime());
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            datePicker.setValue(LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)));
        }
    }

    private boolean checkName(){
        if(nameField.getText() != null && ! "".equals(nameField.getText())){
            return true;
        }else{
            nameErrorLabel.setText("Please enter a name.");
            return false;
        }
    }

    private boolean checkAmount() {
        String amount = amountField.getText();
        if( amount == null ) return false;
        try {
            Double.parseDouble(amount);
            return true;
        } catch (NumberFormatException e) {
            amountErrorLabel.setText("Please enter a correct amount.");
            return false;
        }
    }

    private boolean checkDate(){
        if(datePicker.getValue() != null){
            return  true;
        }else{
            dateErrorLabel.setText("Please enter a date.");
            return false;
        }
    }

    private boolean checkCombination(){
        String name = nameField.getText();
        double amount = Double.parseDouble(amountField.getText());
        long time = datePicker.getValue().toEpochDay()*24*60*60*1000;
        boolean exists = false;
        for(Transaction transaction : account.getTransactions()){
            if(name.equals(transaction.getName()) && amount == transaction.getAmount() && time == transaction.getTime()){
                exists = true;
            }
        }
        if(!exists){
            return true;
        }else{
            combinationErrorLabel.setText("This transaction already exists");
            return false;
        }
    }
}
