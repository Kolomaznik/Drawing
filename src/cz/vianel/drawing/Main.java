package cz.vianel.drawing;/**
 * Created by xkoloma1 on 24.02.2016.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("screen.fxml"));
        primaryStage.setTitle("Drawing");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

    }
}
