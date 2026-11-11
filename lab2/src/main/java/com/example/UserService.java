package com.example;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class UserService {

    public enum AuthStatus {
        SUCCESS,
        WRONG_PASSWORD,
        UNKNOWN_EMAIL
    }

    public AuthStatus authenticate(String email, String password) throws IOException {
        File file=new File("users.txt");
        try (Scanner reader=new Scanner(file)){

        while(reader.hasNextLine()){
            String line=reader.nextLine();
            String[] parts = line.split("\\s+"); 
            if (parts.length>=2){
                String savedEmail=parts[0];
                String savedPassword=parts[1];

                if (savedEmail.equals(email)){
                    if (savedPassword.equals(password)){
                        return AuthStatus.SUCCESS;
                    } else {
                        return AuthStatus.WRONG_PASSWORD;
                    }
                }
            }

        }
    }
    return AuthStatus.UNKNOWN_EMAIL;
    }
}
       
        
    
