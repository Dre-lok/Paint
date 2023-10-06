package com.example.paintapp;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FIleControl {
    private final int CLOSING_WINDOW_WIDTH = 250;
    private final int CLOSING_WINDOW_HEIGHTH = 60;
    private double TIMER_WINDOW_HEIGHT = 150;
    private double TIMER_WINDOW_WIDTH = 450;
    private final int CANVAS_WIDTH = 1750;
    private final int CANVAS_HEIGHT = 950;
    private File selectedFile;
    private File savFile;
    private Image image;
    private PaintCanvas PaintCanvas;
    private Stage mainStage;
    private SuperMenu superMenu;

    //FileControl Constructor instantiates a PaintCanvas and Stage to allow file tasks
    public FIleControl(com.example.paintapp.PaintCanvas canvas, Stage stage) {
        PaintCanvas = canvas;
        mainStage = stage;
    }


    public void timer()
    {
        Button yes = new Button("Yes");
        Button no = new Button("No");

        //creates Label to ask user question on timer
        Label question = new Label("Would you like an automated save timer?");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        grid.add(question, 1, 0);  //adds label to grid
        grid.add(yes, 0, 1);
        grid.add(no, 2, 1);

        Scene timerScene = new Scene(grid, TIMER_WINDOW_WIDTH,
                TIMER_WINDOW_HEIGHT);  //adds the grid to the window

        Stage timerWindow = new Stage();  //creates the new window (stage)

        timerWindow.setTitle("Timer");
        timerWindow.setScene(timerScene);
        timerWindow.show();

        yes.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {
                timerWindow.close();
            }
        });

        no.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent e)
            {

                timerWindow.close();
            }
        });
    }

    @FXML
    void save() {/*
        FileChooser savefile = new FileChooser();
        savefile.setTitle("Save File");

        if (savFile != null) {
            try {
                WritableImage writableImage = new WritableImage(1080, 790);
                PaintCanvas.canvasField.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", savFile);
            } catch (IOException ex) {
                System.out.println("Error!");
            }
        }*/
    }

    @FXML
    void saveAs() {/*
        FileChooser fileChooser2 = new FileChooser();
        //Allows user to filter between png's and jpeg's
        fileChooser2.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TIF File", "*.tif"),
                new FileChooser.ExtensionFilter("JPG File", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG File", "*png"));

        savFile = fileChooser2.showSaveDialog(mainStage);

        if(savFile != null)
        {
            //timer.run();
            try
            {
                //Constructs an empty image the size of the canvas
                WritableImage writableImage = new WritableImage(CANVAS_WIDTH, CANVAS_HEIGHT);

                PaintCanvas.canvas.snapshot(null, writableImage);
                //Renders the writableimage
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, "png", savFile); //Saves the render
            }
            catch (IOException ex)
            {
            }
        }
        System.out.println("Started auto save");  //starts the thread*/
    }

    @FXML
    void open() {                      //Opens image from system
        FileChooser fileChooser = new FileChooser();//allows user to choose file
        //Allows user to filter between png's, jpeg's, and .tif's
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TIF File", "*.tif"),
                new FileChooser.ExtensionFilter("JPG File", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG File", "*png"));

        selectedFile = fileChooser.showOpenDialog(mainStage);
        try
        {
            //takes the address of file and assigns it to the "input" variable
            FileInputStream input = new FileInputStream(selectedFile);
            image = new Image(input);

            double x = image.getWidth();
            double y = image.getHeight();


            /*Sends the dimensions of the photo to a method that will change the
            canvas dimensions to the dimensions of the picture*/
            ChangeCanvas(x , y);
            /*This makes it so that the picture is as big as the variables
            defined above*/
            PaintCanvas.gc.drawImage(image, 0, 0, x, y);
            //adds screenshot of canvas after edit to stack of images
            PaintCanvas.addToStack(PaintCanvas.screenshot());
            addToMainStack(PaintCanvas.screenshot());
        }
        catch (IOException ex){}

    }

    @FXML
    void exit() {        // closes program and asks to save
        Button exitB = new Button("Yes, exit");
        Button smartSave = new Button("Save, than exit");

        HBox thirdLayout = new HBox();
        thirdLayout.setPadding(new Insets(15, 12, 15, 12));
        thirdLayout.setSpacing(30);
        thirdLayout.getChildren().add(exitB);
        thirdLayout.getChildren().add(smartSave);

        exitB.setAlignment(Pos.CENTER);
        smartSave.setAlignment(Pos.CENTER);

        Scene thirdScene = new Scene(thirdLayout, CLOSING_WINDOW_WIDTH, CLOSING_WINDOW_HEIGHTH);

        Stage closingWindow = new Stage();  // creates the new window
        closingWindow.setTitle("Are you sure?");
        closingWindow.setScene(thirdScene);

        closingWindow.show();

        smartSave.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                save();
                closingWindow.close();
                mainStage.close();
            }
        });

        exitB.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) {
                closingWindow.close();
                mainStage.close();
            }
        });
    }

    public void ChangeCanvas(double width , double height)
    {
        PaintCanvas.canvasField.setHeight(height);
        PaintCanvas.canvasField.setWidth(width);
    }

    public void addToMainStack(Image i)
    {
        PaintCanvas.actionsU.push(i);
    }

}
