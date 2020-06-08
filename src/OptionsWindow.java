package com.company;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class OptionsWindow {

    boolean hasVars;
    boolean hasComm;
    String comp;
    String scheme;
    String dir;
    ParcerMan pm = null;

    public void start(Stage stage){

        Label label = new Label("Select a root folder\nwith only .java files.");
        DirectoryChooser dirChooser = new DirectoryChooser();


        EventHandler<ActionEvent> event = e -> {
            File file = dirChooser.showDialog(stage);
            dir = file.getAbsolutePath();
            label.setText(dir);
        };

        VBox layout = new VBox(20);
        Button button = new Button("Find Directory");
        button.setOnAction(event);
        VBox fileStuff = new VBox(5, label, button);
        fileStuff.setAlignment(Pos.CENTER);
        fileStuff.setLayoutY(250);
        layout.setAlignment(Pos.CENTER);

        CheckBox cb1 = new CheckBox("With comments?");
        //cb1.setSelected(true);
        cb1.setDisable(true);
        CheckBox cb2 = new CheckBox("With Variables?");
        cb2.setSelected(true);
        CheckBox cb3 = new CheckBox("Shape Variance?");
        cb3.setDisable(true);

        ComboBox<String> comB1 = new ComboBox<>();
        comB1.getItems().addAll("80's", "Icecream", "Nice", "Dark Rainbow", "Yellow Mono", "Rainbow","Sunset", "Hot Garbage");
        comB1.setPromptText("Colorscheme");

        ComboBox<String> comB2 = new ComboBox<>();
        comB2.getItems().addAll("Wide, Ascending", "Wide, Descending");
        comB2.setPromptText("Layout");

        Button b1 = new Button("Accept");

        layout.getChildren().add(cb1);
        layout.getChildren().add(cb2);
        layout.getChildren().add(cb3);
        layout.getChildren().add(comB1);
        layout.getChildren().add(comB2);
        layout.getChildren().add(fileStuff);
        layout.getChildren().add(b1);

        Scene scene = new Scene(layout, 250, 400);

        stage.setScene(scene);
        stage.show();

        EventHandler<ActionEvent> close = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                hasVars = cb1.isSelected();
                hasComm = cb2.isSelected();
                scheme = comB1.getValue();
                System.out.println(scheme);
                comp = comB2.getValue();
                System.out.println(comp);
                stage.close();
                pm = new ParcerMan(dir, hasComm, hasComm, scheme, comp);
            }
        };

        b1.setOnAction(close);
    }
}
