package com.example;

import javafx.application.Platform;
import java.util.function.IntConsumer;

public class CountdownTask implements Runnable {

    private final String email;
    private final AttemptTracker tracker;
    private final IntConsumer onTick;

    public CountdownTask(String email, AttemptTracker tracker, IntConsumer onTick) {
        this.email = email;
        this.tracker = tracker;
        this.onTick = onTick;
    }

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()&&tracker.isBlocked(email)) {
            int seconds=tracker.getRemainingSeconds(email);
            Platform.runLater(() -> onTick.accept(seconds));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        if(!Thread.currentThread().isInterrupted()){
            Platform.runLater(() -> onTick.accept(0));
        }

    }
}