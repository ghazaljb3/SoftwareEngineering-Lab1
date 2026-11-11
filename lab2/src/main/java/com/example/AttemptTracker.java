package com.example;

import java.util.concurrent.ConcurrentHashMap;

public class AttemptTracker {

    private final ConcurrentHashMap<String, UserState> states = new ConcurrentHashMap<>();

    private final int n;
    private final int t;

    public AttemptTracker(int n, int t) {//constructor
        this.n = n;
        this.t = t;
    }

    public int recordFailure(String email) {
    UserState state = states.computeIfAbsent(email, key -> new UserState());
    return state.recordFailure(n, t);
    }

    public boolean isBlocked(String email){
        if((states.get(email))==null){
            return false;

        } else{
            return (states.get(email)).isBlocked();
        }
    }

    public void reset(String email) {
        UserState state=states.get(email);
        if(state!=null){
            state.reset();
        }
    }
    
    public int getRemainingSeconds(String email) {
    UserState state=states.get(email);
    if(state==null){
        return 0;
    }else{
        return state.getRemainingSeconds();
    }
    }
    
    public int getFailedAttempts(String email) {
    UserState state=states.get(email);
    if(state==null){
        return 0;
    }else{
        return state.getFailedAttempts();
    }
    }

}