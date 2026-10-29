package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException { //open the first window
        scene = new Scene(loadFXML("primary"), 640, 480); //loadFXML reading the fike "primary" that contain the design of the first window
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

    public static void main(String[] args) { //responible for open the application
        launch();
    }

}