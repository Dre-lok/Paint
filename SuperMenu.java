package com.example.paintapp;

import java.time.Clock;
import java.util.Random;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SuperMenu {
    private final int CLOSING_WINDOW_WIDTH = 250;
    private final int CLOSING_WINDOW_HEIGHTH = 60;
    private final int CANVAS_WIDTH = 1750;
    private final int CANVAS_HEIGHT = 950;
    public ColorPicker colorpicker = new ColorPicker(Color.BLACK);
    @FXML
    protected Label timer;
    protected ColorPicker colorPicker = new ColorPicker(Color.DODGERBLUE);
    private PaintCanvas paintCanvas;
    private final int POLY_WINDOW_WIDTH = 450;
    private final int POLY_WINDOW_HEIGHT = 150;
    private int polygonSides;
    protected TextArea textA;

    private FIleControl fIleControl;
    private Thread thread;
    private double TIMER_WINDOW_HEIGHT = 150;
    private double TIMER_WINDOW_WIDTH = 450;
    @FXML
    private Tab starterTab;
    @FXML
    private TabPane paintTabs;
    /*
    public SuperMenu(PaintCanvas canvas, FIleControl filer) {
        paintCanvas = canvas;

        fIleControl = filer;

        timer = new Label();

        Label time = new Label();
        SaveClock timer = new SaveClock(fIleControl, time);
        thread = new Thread(timer);
        time.textProperty().bind(timer.getTime());
    }*/
    public void pencilT() {paintCanvas.choice = 0;}
    public void lineT() {paintCanvas.choice = 1;}
    public void rectT() {paintCanvas.choice = 2;}
    public void sqreT() {paintCanvas.choice = 3;}
    public void circT() {paintCanvas.choice = 4;}
    public void elpsT() {paintCanvas.choice = 5;}
    public void dashT() {paintCanvas.choice = 6;}
    public void dropperT() {paintCanvas.choice = 7;}
    public void emptyT() {paintCanvas.choice = 8;}
    public void roundrectT(){paintCanvas.choice = 9;}
    public void eraserT(){paintCanvas.choice = 10;}
    public void polygonT(){
        polyWindow();
        paintCanvas.choice = 11;}
    public void textT(){paintCanvas.choice = 12;}
    public void triangleT(){paintCanvas.choice = 13;}
    public void cropT(){paintCanvas.choice = 14;}
    public void stampT(){paintCanvas.choice = 16;}
    public void save(){fIleControl.save();}
    public void saveAs(){fIleControl.saveAs();}
    public void open(){fIleControl.open();}
    public void exit(){fIleControl.exit();}

    public void createTab(){
        Tab newTab = starterTab;
        paintTabs.getTabs().add(newTab);
    }

    public void undo() {
        if (paintCanvas.actionsU.isEmpty() == false) {
            Image tempImage = paintCanvas.actionsU.pop(); //pushes the popped off image from the undo stack to the redo stack
            paintCanvas.actionsR.push(tempImage);

            if (paintCanvas.actionsU.isEmpty() == false) //{displays the second most recent screenshot of the canvas on the canvas
                paintCanvas.gc.drawImage(paintCanvas.actionsU.peek(), 0, 0);
        }

        else
            System.out.println("Can't undo anymore");
    }


    public void redo(){
        paintCanvas.gc.drawImage(paintCanvas.actionsR.peek(), 0, 0);

        //if stack of images is not empty
        if (paintCanvas.actionsR.isEmpty() == false)
        {
            //gets rid of most recent snapshot of screen
            Image tempImage = paintCanvas.actionsR.pop();
            paintCanvas.actionsU.push(tempImage);
        }
        else
            System.out.println("Can't redo anymore");
    }

    public void polyWindow() {
        Button enter = new Button("Enter");
        Label sides = new Label("Number of Sides: ");
        TextField input = new TextField();  //creates textfield for user to input their number
        input.setPromptText("Number of Sides");  //adds hint for text field

        GridPane grid = new GridPane(); //gridpane formatting v
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(sides, 0, 1);  //adds label to grid
        grid.add(input, 1, 1);  //adds textfield to grid
        grid.add(enter, 0, 2);  //adds button to grid

        Scene polyScene = new Scene(grid, POLY_WINDOW_WIDTH, POLY_WINDOW_HEIGHT);
        Stage polyWindow = new Stage();  //creates the new window (stage)
        polyWindow.setTitle("Polygon Sides");
        polyWindow.setScene(polyScene);
        polyWindow.show();

        enter.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                //sets universal int variable polygon sides to users input
                polygonSides = Integer.parseInt(input.getText());
                polyWindow.close();
            }
        });
    }

    public void clearCanvas() {
        Button clearB = new Button("Yes, clear canvas");
        Button cancelB = new Button("Cancel");

        HBox thirdLayout = new HBox();
        thirdLayout.setPadding(new Insets(15, 12, 15, 12));
        thirdLayout.setSpacing(30);
        thirdLayout.getChildren().add(clearB);
        thirdLayout.getChildren().add(cancelB);

        clearB.setAlignment(Pos.CENTER);
        cancelB.setAlignment(Pos.CENTER);

        Scene thirdScene = new Scene(thirdLayout, CLOSING_WINDOW_WIDTH, CLOSING_WINDOW_HEIGHTH);

        Stage closingWindow = new Stage();  // creates the new window
        closingWindow.setTitle("Are you sure?");
        closingWindow.setScene(thirdScene);

        closingWindow.show();

        clearB.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                paintCanvas.gc.clearRect(0, 0, paintCanvas.canvasField.getWidth(), paintCanvas.canvasField.getHeight());
                closingWindow.close();
            }
        });

        cancelB.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                closingWindow.close();
            }
        });
    }

    public void initialize() {/*
        PaintCanvas paintCanvas = new PaintCanvas(CANVAS_HEIGHT, CANVAS_WIDTH);
        FIleControl fileControl = new FIleControl(paintCanvas, PaintApp.stage);
        SuperMenu superMenu = new SuperMenu(paintCanvas, fileControl);
        paintCanvas.setColorpicker(superMenu.colorpicker);
        SaveClock timer = new SaveClock(fileControl, superMenu.timer);
    */
    }
}
