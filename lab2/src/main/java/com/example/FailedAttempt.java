package com.example;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class FailedAttempt {

    private final String email;

    public String getEmail() {
        return email;
    }

    private final BlockingQueue<LoginResult> replyQueue =
            new ArrayBlockingQueue<>(1);

    public FailedAttempt(String email) {
        this.email = email;
    }

    public BlockingQueue<LoginResult> getReplyQueue() {
        return replyQueue;
    }


}