package com.example;
import java.util.concurrent.BlockingQueue;

public class FailureWorker extends Thread {

    private final BlockingQueue<FailedAttempt> queue;
    private final AttemptTracker tracker;

    public FailureWorker(BlockingQueue<FailedAttempt> queue,
                         AttemptTracker tracker) {
        this.queue = queue;
        this.tracker = tracker;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                FailedAttempt attempt = queue.take();
                String email = attempt.getEmail();
                int remaining = tracker.recordFailure(email);
                LoginResult result;
                if (remaining == 0) {
                    result = new LoginResult(LoginResult.Status.BLOCKED,0,tracker.getRemainingSeconds(email));
                } else {
                    result = new LoginResult(LoginResult.Status.ERROR, remaining, 0);
                }
                attempt.getReplyQueue().put(result);
            } catch (InterruptedException e) {
                interrupt();
                break;
            }
        }
    }
}
