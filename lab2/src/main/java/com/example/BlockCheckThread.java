package com.example;

public class BlockCheckThread extends Thread {
    private final String email;
    private final AttemptTracker tracker;
    private boolean blocked;

    public BlockCheckThread(String email, AttemptTracker tracker) {
        this.email = email;
        this.tracker = tracker;
        this.blocked = false;
    }

    @Override
    public void run(){
        blocked=tracker.isBlocked(email);
    }

    public boolean wasBlocked(){
        return blocked;
    }
    
}
