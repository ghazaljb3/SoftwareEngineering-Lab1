package com.example;

public class LoginResult {

    public enum Status {
        SUCCESS,
        ERROR,
        BLOCKED
    }


    private final Status status;
    
    public Status getStatus() {
        return status;
    }

    private final int remainingAttempts;
    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    private final int remainingSeconds;

    public int getRemainingSeconds() {
        return remainingSeconds;
    }

     public LoginResult(Status status, int remainingAttempts, int remainingSeconds) {
        this.status = status;
        this.remainingAttempts = remainingAttempts;
        this.remainingSeconds = remainingSeconds;
    }
   
    
    

}