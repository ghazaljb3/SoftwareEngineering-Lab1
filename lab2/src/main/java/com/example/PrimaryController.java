package com.example;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.scene.control.Label;

public class PrimaryController {

    private ArrayList<User> users = new ArrayList<User>();

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML 
    private Label errorLabel;
    @FXML 
    private void handleLogin() throws IOException {
       String enteredUsername=username.getText();
       String enteredPassword=password.getText();
       for (User user:users){
        if ((user.getName().equals(enteredUsername))&&(user.getPassword().equals(enteredPassword))){
           App.setRoot("secondary");
           return;

        }
       }
       errorLabel.setText("Invalid username or password");
    }

    @FXML
    private void initialize(){
        try{
        File file=new File("users.txt");
        Scanner reader=new Scanner(file);
        while(reader.hasNextLine()){
            String line=reader.nextLine();
            String[] parts = line.split("\\s+"); //for example:sara@mail.com    abc123!@, parst[0]=sara@mail.com, parts[1]=abc123!@
            if (parts.length>=2){
                try{
                    User user=new User(parts[0],parts[1]);
                    users.add(user);
                } catch (IllegalArgumentException e) {
                    System.out.println(line+" "+e.getMessage());
                }
            }
            
        }
        reader.close();
        System.out.println("Valid users: "+users.size());
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }

    }
}


