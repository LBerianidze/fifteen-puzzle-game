package com.fifteenpuzzle.fifteenpuzzlegame;

import com.fifteenpuzzle.fifteenpuzzlegame.database.DBClient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PuzzleApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(PuzzleApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 503, 600);
        stage.setTitle("15 Puzzle");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}