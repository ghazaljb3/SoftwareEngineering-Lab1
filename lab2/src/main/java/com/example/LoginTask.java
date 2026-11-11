package com.example;

import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.function.Consumer;
import javafx.application.Platform;

public class LoginTask implements Runnable {

    private final String email;
    private final String password;
    private final AttemptTracker tracker;
    private final UserService userService;
    private final BlockingQueue<FailedAttempt> queue;
    private final Consumer<LoginResult> onResult;


    public LoginTask(String email, String password,AttemptTracker tracker,UserService userService,BlockingQueue<FailedAttempt> queue,Consumer<LoginResult> onResult) {
        this.email = email;
        this.password = password;
        this.tracker = tracker;
        this.userService = userService;
        this.queue = queue;
        this.onResult = onResult;
    }

    @Override
    public void run() {
        BlockCheckThread blockCheckThread=new BlockCheckThread(email,tracker);
        blockCheckThread.start();
        try{
            blockCheckThread.join();
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
            return;
        }

        if (blockCheckThread.wasBlocked()) {
            int remainingSeconds = tracker.getRemainingSeconds(email);
            LoginResult result=new LoginResult(LoginResult.Status.BLOCKED,0,remainingSeconds);
            sendResult(result);
            return;
        }

        try{
            UserService.AuthStatus authStatus=userService.authenticate(email,password);
            if(authStatus==UserService.AuthStatus.SUCCESS){
                tracker.reset(email);
                LoginResult result=new LoginResult(LoginResult.Status.SUCCESS,0,0);
                sendResult(result);
                return;}
            if(authStatus==UserService.AuthStatus.UNKNOWN_EMAIL){
                LoginResult result=new LoginResult(LoginResult.Status.ERROR,-1,0);
                sendResult(result);
                return;}
            
            if (authStatus == UserService.AuthStatus.WRONG_PASSWORD) {
                FailedAttempt attempt = new FailedAttempt(email);
                queue.put(attempt);
                LoginResult result = attempt.getReplyQueue().take();
                sendResult(result);
                return;
            }
                
            }
            catch (IOException e) {
            e.printStackTrace();
            LoginResult result =new LoginResult(LoginResult.Status.ERROR, -1, 0);
            sendResult(result);
            } catch (IllegalArgumentException e) {
                LoginResult result=new LoginResult(LoginResult.Status.ERROR, -1, 0);
                sendResult(result);
            } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }
    }
            

        private void sendResult(LoginResult result){
            Platform.runLater(()->onResult.accept(result));
        }
    }




    

