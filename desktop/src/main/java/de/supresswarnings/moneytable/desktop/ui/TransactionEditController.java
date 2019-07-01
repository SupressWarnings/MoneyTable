package de.supresswarnings.moneytable.desktop.ui;

import de.supresswarnings.moneytable.desktop.data.DataInserter;
import de.supresswarnings.moneytable.model.Account;
import de.supresswarnings.moneytable.model.transaction.Transaction;
import de.supresswarnings.moneytable.model.transaction.TransactionFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class TransactionEditController {

    private long id = -1;

    private Account account;

    private BaseSceneController baseSceneController;

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
    private void onClickSave(){
        nameErrorLabel.setText("");
        amountErrorLabel.setText("");
        dateErrorLabel.setText("");
        combinationErrorLabel.setText("");

        if(checkName() && checkAmount() && checkDate() && checkCombination()){
            // Retrieve data
            String name = nameField.getText();

            double amount = Double.parseDouble(amountField.getText().replaceAll(",", "."));
            amount = ((double)((int)(amount *100.0)))/100.0;

            long time = datePicker.getValue().toEpochDay()*24*60*60*1000;

            //Create the Transaction
            Transaction newValues = TransactionFactory.createTransaction(name, amount, time);
            if(id != -1){
                newValues.setId(id);
            }else{
                account.add(newValues);
            }

            // Save the Transaction
            DataInserter inserter = new DataInserter();
            inserter.transactionInput(account, newValues);

            // Navigate to Transaction view
            baseSceneController.showTransaction(newValues);
        }
    }

    void initData(Transaction transaction, Account account, BaseSceneController baseSceneController){
        this.account = account;
        this.baseSceneController = baseSceneController;

        if(transaction != null){
            saveButton.setText("Save");

            nameField.setText(transaction.getName());
            amountField.setText("" + transaction.getAmount());

            Date date = new Date(transaction.getTime());
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(date);
            datePicker.setValue(LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)));

            id = transaction.getId();
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
            Double.parseDouble(amountField.getText().replaceAll(",", "."));
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

        double amount = Double.parseDouble(amountField.getText().replaceAll(",", "."));
        amount = ((double)((int)(amount *100.0)))/100.0;

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
            combinationErrorLabel.setText("This transaction exists already");
            return false;
        }
    }
}
