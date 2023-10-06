package com.example.paintapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class PaintApp extends Application {

    private final int CANVAS_WIDTH = 1750;
    private final int CANVAS_HEIGHT = 950;

    public void start(Stage stage) throws IOException {

        PaintCanvas paintCanvas = new PaintCanvas(CANVAS_HEIGHT, CANVAS_WIDTH);
        FIleControl fileControl = new FIleControl(paintCanvas, stage);
        //SuperMenu superMenu = new SuperMenu(paintCanvas, fileControl);
        //paintCanvas.setColorpicker(superMenu.colorpicker);
        //SaveClock timer = new SaveClock(fileControl, superMenu.timer);

        FXMLLoader fxmlLoader = new FXMLLoader(PaintApp.class.getResource("paint-view.fxml"));
        Parent root = fxmlLoader.load();
        SuperMenu controller = fxmlLoader.getController();
        Scene scene = new Scene(root);


        scene.setOnKeyPressed(keyEvent ->  {

            if(keyEvent.isControlDown()){
                switch (keyEvent.getCode()){
                    case S:  // Ctrl+S is menu save shortcut
                        controller.save();
                        break;
                    case A:
                        controller.saveAs();
                        break;
                    case O:
                        controller.open();
                        break;
                    case E:
                        controller.exit();
                        break;
                    case Z:
                        controller.undo();
                        break;
                    case X:
                        controller.redo();
                        break;

                }
            }
        });
        stage.setTitle("Paint");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args){
        launch(args);
    }
}
