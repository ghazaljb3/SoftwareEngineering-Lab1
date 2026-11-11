package com.example;

public class UserState {
    private int failedAttempts=0;
    private long blockedUntilMillis=0;
    public synchronized boolean isBlocked(){
        long current_time=System.currentTimeMillis();
        if(current_time<blockedUntilMillis){
            return true;
        }
        else{
            if (current_time>=blockedUntilMillis && blockedUntilMillis>0){
                blockedUntilMillis=0;
                failedAttempts=0;
            }
            return false;
        }
    }


    public synchronized int recordFailure(int n,int t){
        if (isBlocked()){
            return 0;
        }
        else{
            failedAttempts++;
            if(failedAttempts>=n){
                blockedUntilMillis=System.currentTimeMillis()+t*1000L;
                return 0;
            } else{
                return n-failedAttempts;
            }
        }
    }

    /*public int recordFailure(int n,int t){
        if (isBlocked()){
            return 0;
        }
        else{
            failedAttempts++;
            if(failedAttempts>=n){
                blockedUntilMillis=System.currentTimeMillis()+t*1000L;
                return 0;
            } else{
                return n-failedAttempts;
            }
        }
    }*/

    public synchronized void reset(){
        failedAttempts=0;
        blockedUntilMillis=0;
    }

    public synchronized int getFailedAttempts(){
        isBlocked();
        return failedAttempts;
    }

    public synchronized int getRemainingSeconds(){
        if(isBlocked()){
            long remainingMillis=blockedUntilMillis-System.currentTimeMillis();
            return (int) Math.max(0,(remainingMillis+999)/1000);
            
        }
        else{
            return 0;
        }
    }
}
