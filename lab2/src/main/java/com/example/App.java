package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static AttemptTracker tracker;
    private static final BlockingQueue<FailedAttempt> failureQueue=new LinkedBlockingQueue<>();
    private static final UserService userService = new UserService();

    @Override
    public void start(Stage stage) throws IOException { //open the first window
        int n=Integer.parseInt(getParameters().getRaw().get(0));
        int t=Integer.parseInt(getParameters().getRaw().get(1));
        tracker=new AttemptTracker(n,t);
        for (int i = 0; i < 2; i++) {
            FailureWorker worker = new FailureWorker(failureQueue, tracker);
            worker.start();
        }
        scene = new Scene(loadFXML("primary"), 300, 300); //loadFXML reading the fike "primary" that contain the design of the first window
        stage.setTitle("Users Login"); //set the title of the window
        stage.setScene(scene); //scene is the content of he window that we declared in the previous line
        stage.show(); //show the window
    }

    static void setRoot(String fxml) throws IOException { //navigation between the windows
        scene.setRoot(loadFXML(fxml)); //replace the content of the window in another windoe
    }

    private static Parent loadFXML(String fxml) throws IOException { 
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml")); 
        return fxmlLoader.load();
    }

    public static AttemptTracker getTracker() {
    return tracker;
    }
    
    public static BlockingQueue<FailedAttempt> getFailureQueue() {
    return failureQueue;
    }
    
    public static UserService getUserService() {
    return userService;
}

    public static void main(String[] args) { //responible for open the application
        int n=-1;
        int t=-1;
        if(args.length==2){
            try{
                n=Integer.parseInt(args[0]);
                t=Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                System.out.println("Please enter two positive numbers for n and t");
            }
        }
        Scanner scanner=new Scanner(System.in);
        while(n<1||t<1){
            System.out.println("Enter n:");
            String n_input=scanner.nextLine();
            System.out.println("Enter t:");
            String t_input=scanner.nextLine();
            try{
                n=Integer.parseInt(n_input);
                t=Integer.parseInt(t_input);
            } catch (NumberFormatException e) {
                n=-1;
                t=-1;
                System.out.println("Please enter two positive numbers for n and t");
            }
        }
        launch(String.valueOf(n),String.valueOf(t));
    }

}