package org.sepp;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GUI extends Application {

    public static void main (String[] args){
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Prototype");
        
        StackPane layout = new StackPane();
        Scene scene = new Scene (layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
}
