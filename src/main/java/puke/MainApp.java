package puke;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/** Displays the graphical Puke application. */
public class MainApp extends Application {
    @Override
    public void start(Stage stage) {
        Label greeting = new Label("Hello from Puke!");
        Scene scene = new Scene(greeting);
        stage.setTitle("Puke");
        stage.setScene(scene);
        stage.show();
    }
}
