package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import javafx.scene.control.Button;

public class PrimaryController {


    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML 
    private Label errorLabel;

    @FXML 
    private Button testButton;

    @FXML
    private void handleConcurrencyTest() {
        testButton.setDisable(true);
        errorLabel.setText("Running concurrency test...");
        Thread testThread = new Thread(() -> {
            ConcurrencyTest.runTest();
            javafx.application.Platform.runLater(() -> {
                errorLabel.setText("Concurrency test finished. Check Terminal.");
                testButton.setDisable(false);
            });
        });
        testThread.setDaemon(true);
        testThread.start();
    }

    @FXML 
    private void handleLogin()  {
        String enteredUsername = username.getText();
        String enteredPassword = password.getText();
        username.setDisable(true);
        password.setDisable(true);
        primaryButton.setDisable(true);
        errorLabel.setText("Checking login...");
        
        LoginTask task = new LoginTask(enteredUsername,enteredPassword,App.getTracker(),App.getUserService(),App.getFailureQueue(),this::handleLoginResult);   
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    private Button primaryButton;

    private Thread countdownThread;

    private void handleLoginResult(LoginResult result) {
    username.setDisable(false);
    password.setDisable(false);
    primaryButton.setDisable(false);

    if (result.getStatus()==LoginResult.Status.SUCCESS) {
        try {
            App.setRoot("secondary");
        }catch(IOException e) {
            errorLabel.setText("Unable to open welcome screen");}
        return;
    }

    if (result.getStatus()==LoginResult.Status.ERROR) {
        if (result.getRemainingAttempts() == -1) {
            errorLabel.setText("Invalid username or password");
        } else {
            errorLabel.setText("Invalid password. Remaining attempts: "+result.getRemainingAttempts());
        }
        return;
    }

    if (result.getStatus() == LoginResult.Status.BLOCKED) {
        username.setDisable(true);
        password.setDisable(true);
        primaryButton.setDisable(true);
        CountdownTask countdown=new CountdownTask(username.getText(),App.getTracker(),this::updateCountdown);
        countdownThread=new Thread(countdown);
        countdownThread.setDaemon(true);
        countdownThread.start();
    }
}
    private void updateCountdown(int seconds) {
        if (seconds > 0) {
            errorLabel.setText("Account blocked. Try again in "+seconds+" seconds");
        } else {
            errorLabel.setText("Account unlocked. You can try again.");
            username.setDisable(false);
            password.setDisable(false);
            primaryButton.setDisable(false);
        }
    }
}
    



