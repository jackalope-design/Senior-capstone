package com.company;

import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application implements Int {

    public static void main(String[] args) {
        System.out.println();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Make a drawing");
        OptionsWindow ow = new OptionsWindow();
        ow.start(stage);
    }
}
