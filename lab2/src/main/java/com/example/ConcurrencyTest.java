package com.example;

public class ConcurrencyTest {

    private static final int THREAD_COUNT = 20;
    private static final int ATTEMPTS_PER_THREAD = 499;
    private static final int N = 10000;
    private static final int T = 30;

    public static void runTest() {
        AttemptTracker tracker = new AttemptTracker(N, T);
        String email="jabareenghazal1@example.com";
        Thread[] threads=new Thread[THREAD_COUNT];
        for(int i=0;i<THREAD_COUNT;i++){
            threads[i]=new Thread(()->{
                for (int j=0;j<ATTEMPTS_PER_THREAD;j++){
                    tracker.recordFailure(email);
                }
            });
            
        }
        for (Thread thread:threads){
            thread.start();
        }
        for (Thread thread:threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;            
            }
        }

        System.out.println("Failed attempts: "+tracker.getFailedAttempts(email));
        System.out.println("Blocked: "+tracker.isBlocked(email));

        for (int i=0;i<19;i++) {
            tracker.recordFailure(email);
        }
        
        System.out.println("After 9999 attempts:");
        System.out.println("Failed attempts: "+tracker.getFailedAttempts(email));
        System.out.println("Blocked: "+tracker.isBlocked(email));

        tracker.recordFailure(email);
        
        System.out.println("After 10000 attempts:");
        System.out.println("Blocked: "+tracker.isBlocked(email));
        System.out.println("Remaining seconds: "+tracker.getRemainingSeconds(email));


    }

    public static void main(String[] args) {
        runTest();
    }
}