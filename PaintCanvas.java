package com.example.paintapp;

import java.util.Stack;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class PaintCanvas {
    private final int CANVAS_WIDTH = 1750;
    private final int CANVAS_HEIGHT = 950;
    protected Canvas canvas;
    protected int choice;
    private double xClick, startY, startX, yClick;
    @FXML
    private TextField bsize;
    @FXML
    public Canvas canvasField;
    @FXML
    private ColorPicker colorpicker;
    @FXML
    private TextArea textA;
    private Line line;
    private Rectangle rect;
    private Circle circ;
    private Ellipse oval;
    private Polygon poly;
    private int polygonSides;
    private Rectangle cropRect;
    private ImageView cropImage;
    private int cropWidth, cropHeight;
    protected Stack<Image> actionsU = new Stack();
    protected Stack<Image> actionsR = new Stack();
    protected GraphicsContext gc;

    public PaintCanvas(){}

    public PaintCanvas(int x, int y) {
        canvasField = new Canvas(x, y);
        gc = canvasField.getGraphicsContext2D();
        addToStack(screenshot());

        canvasField.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                switch (choice) {
                    case 0 ->  //pencil
                    {
                        gc.beginPath();
                        gc.moveTo(event.getX(), event.getY());
                        gc.setStroke(colorpicker.getValue());
                        gc.setLineWidth(getSize());
                        gc.stroke();
                    }
                    case 1 ->  //line
                    {
                        // Creates line obj
                        line = new Line(event.getX(), event.getY(), event.getX(), event.getY());
                        line.setStroke(colorpicker.getValue());
                        line.setStrokeWidth(getSize());
                        line.setFill(colorpicker.getValue());
                        dp.getChildren().add(line);   // It can be a Pane if you dont add your line
                        gc.beginPath();
                        gc.moveTo(event.getX(), event.getY());
                        gc.setStroke(colorpicker.getValue());
                        gc.setLineWidth(getSize());
                    }
                    case 2 ->  //rectangle
                    {
                        rect = new Rectangle(event.getX(), event.getY(), 0, 0);  //creates rectangle
                        rect.setStroke(colorpicker.getValue());
                        rect.setStrokeWidth(getSize());
                        rect.setFill(colorpicker.getValue());
                        dp.getChildren().add(rect);
                        gc.beginPath();
                        startX = event.getX();
                        startY = event.getY();
                        gc.moveTo(event.getX(), event.getY());
                        gc.setStroke(colorpicker.getValue());
                        gc.setLineWidth(getSize());
                    }
                    case 3 ->  //square
                    {
                        rect = new Rectangle(event.getX(), event.getY(), 0, 0);  //creates rectangle
                        rect.setStroke(colorpicker.getValue());
                        rect.setStrokeWidth(getSize());
                        rect.setFill(colorpicker.getValue());
                        dp.getChildren().add(rect);
                        gc.beginPath();
                        startX = event.getX();
                        startY = event.getY();
                        gc.moveTo(event.getX(), event.getY());
                        gc.setStroke(colorpicker.getValue());
                        gc.setLineWidth(getSize());
                    }
                    case 4 ->  //circle
                    {
                        startX = event.getX();
                        startY = event.getY();
                        circ = new Circle(startX, startY, 0);
                        circ.setStroke(colorpicker.getValue());
                        circ.setStrokeWidth(getSize());
                        circ.setFill(colorpicker.getValue());
                        dp.getChildren().add(circ);
                        gc.beginPath();
                        startX = event.getX();
                        startY = event.getY();
                        gc.moveTo(event.getX(), event.getY());
                        gc.setStroke(colorpicker.getValue());
                        gc.setLineWidth(getSize());
                    }
                    case 5 ->  //ellipse
                    {
                        oval = new Ellipse(event.getX(), event.getY(),
                                0, 0);  //creates ellipse
                        oval.setStroke(colorpicker.getValue());
                        oval.setStrokeWidth(getSize());
                        oval.setFill(colorpicker.getValue());
                        dp.getChildren().add(oval);
                        gc.beginPath();
                        startX = event.getX();
                        startY = event.getY();
                        gc.moveTo(event.getX(), event.getY());
                        gc.setStroke(colorpicker.getValue());
                        gc.setLineWidth(getSize());
                    }
                    case 6 ->  //dash line
                    {
                        line = new Line(event.getX(), event.getY(), event.getX(), event.getY());
                        line.getStrokeDashArray().addAll(25d, 10d);
                        line.setStroke(colorpicker.getValue());
                        line.setStrokeWidth(getSize());
                        line.setFill(colorpicker.getValue());
                        dp.getChildren().add(line);
                        gc.beginPath();
                        gc.moveTo(event.getX(), event.getY());
                        gc.setStroke(colorpicker.getValue());
                        gc.setLineWidth(getSize());
                    }
                    case 7 ->  //dropper
                    {
                        Image image = screenshot();
                        PixelReader pr = image.getPixelReader();
                        int x = (int) event.getX();
                        int y = (int) event.getY();
                        Color c = pr.getColor(x, y);
                        test.setText("switch hit");
                        colorpicker.setValue(c);
                    }
                    case 8 ->  //empty hand / no tool
                    {
                    }
                    case 9 ->   //Round rectangle
                    {
                        rect = new Rectangle(event.getX() , event.getY(), 0 , 0);  //creates rectangle
                        rect.setStroke(colorpicker.getValue());
                        rect.setStrokeWidth(getSize());
                        rect.setFill(colorpicker.getValue());  //adds rectangle to drawing pane
                        dp.getChildren().add(rect);
                        gc.beginPath();  //variables to keep track of first click x
                        startX = event.getX();  //variables to keep track of first click X
                        startY = event.getY();
                        gc.moveTo(event.getX(), event.getY());  //Gets the color chosen by the user for the stroke
                        gc.setStroke(colorpicker.getValue());
                        gc.setLineWidth(gc.getLineWidth());
                    }
                    case 10 ->  //eraser
                    {
                        gc.beginPath();
                        gc.moveTo(event.getX(), event.getY());
                        gc.setStroke(Color.WHITE);//Makes color equal color of canvas
                        gc.setLineWidth(getSize());
                        gc.stroke();
                    }
                    case 11 -> //polygon
                    {
                        poly = new Polygon();
                        dp.getChildren().add(poly);
                        startX = event.getX();
                        startY = event.getY();
                        gc.beginPath();
                        startX = event.getX();
                        startY = event.getY();
                        gc.moveTo(event.getX(), event.getY());
                        gc.setStroke(colorpicker.getValue());
                        gc.setLineWidth(getSize());
                    }
                    case 12 -> //text
                    {
                        gc.setFont(Font.font("Verdana", 100));
                        gc.setFill(colorpicker.getValue());
                        gc.setStroke(colorpicker.getValue());
                        gc.setLineWidth(getSize());
                        gc.strokeText(textA.getText(), event.getX(), event.getY());
                        gc.fillText(textA.getText() , event.getX(), event.getY());
                    }
                    case 13 -> //triangle
                    {
                        poly = new Polygon();
                        dp.getChildren().add(poly);
                        startX = event.getX();
                        startY = event.getY();
                        gc.beginPath();
                        startX = event.getX();
                        startY = event.getY();
                        gc.moveTo(event.getX(), event.getY());
                        gc.setStroke(colorpicker.getValue());
                        gc.setLineWidth(getSize());
                    }
                    case 14 -> //crop
                    {
                        cropRect = new Rectangle(event.getX() , event.getY(), 0 , 0);  //creates rectangle
                        cropRect.setStroke(Color.BLACK);
                        cropRect.setFill(Color.TRANSPARENT);
                        cropRect.setStrokeWidth(getSize());
                        dp.getChildren().add(cropRect);
                        gc.beginPath();
                        startX = event.getX();
                        startY = event.getY();
                    }
                    case 15 -> //moving crop
                    {
                        gc.beginPath();
                        Paint tempColor = gc.getFill();
                        gc.setFill(Color.WHITE);
                        gc.fillRect(cropRect.getX(), cropRect.getY(), cropRect.getWidth(), cropRect.getHeight());
                        gc.setFill(tempColor);
                        gc.closePath();

                        Image selectedImage = actionsU.peek();
                        PixelReader pr = selectedImage.getPixelReader();
                        WritableImage newImage = new WritableImage(pr,
                                (int) cropRect.getX(), (int) cropRect.getY(),
                                (int) cropRect.getWidth(),
                                (int) cropRect.getHeight());

                        cropImage = new ImageView(newImage);
                        cropImage.setX(event.getX());
                        cropImage.setY(event.getY());
                        dp.getChildren().add(cropImage);
                    }
                    case 16 -> //stamp
                    {
                        cropRect = new Rectangle(event.getX() , event.getY() , 0 , 0);  //creates rectangle
                        cropRect.setStroke(Color.BLACK);
                        cropRect.setFill(Color.TRANSPARENT);
                        cropRect.setStrokeWidth(getSize());
                        dp.getChildren().add(cropRect);
                        gc.beginPath();
                        startX = event.getX();
                        startY = event.getY();
                    }
                    case 17 -> //moving stamp
                    {
                        startX = event.getX();
                        startY = event.getY();
                        Image selectedImage = actionsU.peek();
                        PixelReader pr = selectedImage.getPixelReader();
                        WritableImage newImage = new WritableImage(pr, (int) cropRect.getX(), (int) cropRect.getY(), cropWidth, cropHeight);
                        cropImage = new ImageView(newImage);
                        cropImage.setX(event.getX());
                        cropImage.setY(event.getY());
                        dp.getChildren().add(cropImage);
                    }
                }
            }
        });


        canvasField.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                switch (choice) {
                    case 0:  //pencil
                    {
                        gc.lineTo(event.getX(), event.getY());  //creats stroke with the drag
                        gc.setStroke(colorpicker.getValue());  //gets color user has chosen
                        gc.stroke();  //makes drag stroke
                        break;
                    }

                    case 1:  //line
                    {
                        line.setEndX(event.getX());  //follows the mouse for user to draw shape on a pane
                        line.setEndY(event.getY());
                        break;
                    }

                    case 2:  //rectangle
                    {
                        rect.setWidth(event.getX() - startX);  //follows the mouse for user to draw shape on a pane
                        rect.setHeight(event.getY() - startY);
                        break;
                    }

                    case 3:  //square
                    {
                        rect.setWidth(event.getY() - startY);  //follows the mouse for user to draw shape on a pane
                        rect.setHeight(event.getY() - startY);
                        break;
                    }

                    case 4:  //circle
                    {
                        double radius = (event.getY() - startY) / 2;  //follows the mouse for user to draw shape on a pane
                        circ.setCenterX(startX + radius);
                        circ.setCenterY(startY + radius);
                        circ.setRadius(radius);
                        break;
                    }

                    case 5:  //ellipse
                    {
                        double radiusX = (event.getX() - startX) / 2;  //follows the mouse for user to draw shape on a pane
                        double radiusY = (event.getY() - startY) / 2;
                        oval.setRadiusX((event.getX() - startX) / 2);
                        oval.setRadiusY((event.getY() - startY) / 2);
                        oval.setCenterX(startX - radiusX);
                        oval.setCenterY(startY - radiusY);
                        break;
                    }

                    case 6: //dash line
                    {
                        line.setEndX(event.getX());
                        line.setEndY(event.getY());
                        break;
                    }

                    case 7: //dropper
                    {
                        break;
                    }

                    case 8: //empty hand
                    {
                        break;
                    }

                    case 9: //Round Rect
                    {
                        rect.setWidth(event.getX() - startX);
                        rect.setHeight(event.getY() - startY);
                        rect.setArcHeight(50);
                        rect.setArcWidth(50);
                        break;
                    }

                    case 10: //eraser
                    {
                        gc.lineTo(event.getX(), event.getY());
                        gc.setStroke(Color.WHITE);  ////makes color pathing white (same color as canvas
                        gc.stroke();  //makes drag stroke
                        break;
                    }

                    case 11:    //polygon
                    {
                        poly.getPoints().addAll(event.getX(), event.getY());
                        final double angleStep = Math.PI * 2 / polygonSides;
                        double radius = Math.sqrt(((event.getX()-startX)* (event.getX()-startX)) + ((event.getY()-startY)* (event.getY()-startY)));
                        double angle = Math.atan2(event.getY()-startY, event.getX()-startX);
                        for (int i = 0; i < polygonSides; i++, angle += angleStep) {
                            poly.getPoints().addAll(Math.cos(angle) *
                                    radius + startX, Math.sin(angle) *
                                    radius + startY);
                        }
                        poly.setStroke(colorpicker.getValue());
                        poly.setStrokeWidth(gc.getLineWidth());
                        poly.setFill(colorpicker.getValue());
                        break;
                    }
                    case 12: {break;}   //text
                    case 13:    //triangle
                    {
                        poly.getPoints().addAll(event.getX(), event.getY());
                        final double angleStep = Math.PI * 2 / 3;
                        double radius = Math.sqrt(((event.getX()-startX)* (event.getX()-startX)) + ((event.getY()-startY)* (event.getY()-startY)));
                        double angle = Math.atan2(event.getY()-startY, event.getX()-startX);
                        for (int i = 0; i < 3; i++, angle += angleStep) {
                            poly.getPoints().addAll(Math.cos(angle) *
                                    radius + startX, Math.sin(angle) *
                                    radius + startY);
                        }
                        poly.setStroke(colorpicker.getValue());
                        poly.setStrokeWidth(gc.getLineWidth());
                        poly.setFill(colorpicker.getValue());
                        break;
                    }
                    case 14:    //crop
                    {
                        cropRect.setWidth(event.getX() - startX);
                        cropRect.setHeight(event.getY() - startY);
                        break;
                    }
                    case 15:    //moving crop
                    {
                        cropImage.setX(event.getX());
                        cropImage.setY(event.getY());
                        break;
                    }
                    case 16:    //stamp
                    {
                        cropRect.setWidth(event.getX() - startX);
                        cropRect.setHeight(event.getY() - startY);
                        break;
                    }
                    case 17:    //moving stamp
                    {
                        cropImage.setX(event.getX());
                        cropImage.setY(event.getY());
                        break;
                    }
                }

            }
        });

        canvasField.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                addToStack(screenshot());
                switch (choice) {
                    case 0:  //pencil
                    {
                        gc.closePath();
                        break;
                    }

                    case 1:  //line
                    {
                        dp.getChildren().remove(line);
                        gc.lineTo(event.getX(), event.getY());
                        gc.stroke();  //Makes the stroke between the points
                        gc.closePath();
                        break;
                    }

                    case 2:  //rectangle
                    {
                        dp.getChildren().remove(rect);
                        gc.setFill(colorpicker.getValue());
                        gc.rect(startX, startY, event.getX() - startX, event.getY() - startY);
                        gc.stroke();
                        gc.fill();  //fills the rectangle shape
                        gc.closePath();
                        break;
                    }

                    case 3:  //square
                    {
                        dp.getChildren().remove(rect);  //sets fill color to colorpicker
                        gc.setFill(colorpicker.getValue());
                        gc.rect(startX, startY, event.getY() - startY, event.getY() - startY);
                        gc.stroke();
                        gc.fill();  //fills the square shape
                        gc.closePath();
                        break;
                    }

                    case 4:  //circle
                    {
                        dp.getChildren().remove(circ);  //sets fill color to colorpicker
                        gc.setFill(colorpicker.getValue());
                        gc.strokeOval(startX, startY, event.getY() - startY, event.getY() - startY);
                        gc.fillOval(startX, startY, event.getY() - startY, event.getY() - startY);
                        gc.stroke();
                        gc.closePath();
                        break;
                    }

                    case 5:  //elipse
                    {
                        dp.getChildren().remove(oval);
                        gc.setFill(colorpicker.getValue());  //sets fill color to colorpicker
                        gc.strokeOval(startX, startY, event.getX() - startX, event.getY() - startY);
                        gc.fillOval(startX, startY, event.getX() - startX, event.getY() - startY);
                        gc.stroke();
                        gc.closePath();
                        break;
                    }

                    case 6:  //dash line
                    {
                        dp.getChildren().remove(line);
                        gc.setLineDashes(25, 10);
                        gc.lineTo(event.getX(), event.getY()); //makes a stroke between the first mouse press and the mouse release
                        gc.stroke(); //Makes the stroke between the points
                        gc.closePath();
                        gc.setLineDashes(0, 0);
                        break;
                    }

                    case 7:  //dropper
                    {
                        break;
                    }

                    case 8:  //empty hand
                    {
                        break;
                    }

                    case 9:  //round rect
                    {
                        dp.getChildren().remove(rect); //sets fill color to colorpicker
                        gc.setFill(colorpicker.getValue());
                        gc.strokeRoundRect(startX , startY, event.getX() - startX, event.getY() - startY, 50, 50 );
                        gc.fillRoundRect(startX , startY, event.getX() -
                                startX, event.getY() - startY, 50, 50 );
                        gc.stroke();
                        gc.fill();  //fills the rectangle shape
                        gc.closePath();
                        break;
                    }

                    case 10:    //eraser
                    {
                        gc.closePath();
                        break;
                    }

                    case 11:    //polygon
                    {
                        dp.getChildren().remove(poly);
                        gc.setFill(colorpicker.getValue());
                        gc.stroke();
                        gc.fill();  //fills the shape
                        gc.closePath();
                        drawPolygon(canvas, polygonSides, startX, startY, event.getX(), event.getY());
                        break;
                    }

                    case 12: {break;} //text
                    case 13:    //triangle
                    {
                        dp.getChildren().remove(poly);
                        gc.setFill(colorpicker.getValue());
                        gc.stroke();
                        gc.fill();  //fills the shape
                        gc.closePath();
                        drawPolygon(canvas, 3, startX, startY, event.getX(), event.getY());
                        break;
                    }
                    case 14:    //crop
                    {
                        int width = (int)(event.getX() - startX);
                        int height = (int)(event.getY() - startY);
                        dp.getChildren().remove(cropRect);
                        gc.closePath();
                        choice = 15;
                        break;
                    }
                    case 15:    //moving crop
                    {
                        dp.getChildren().remove(cropImage);
                        gc.drawImage(cropImage.getImage(), event.getX(), event.getY(), cropRect.getWidth(), cropRect.getHeight());
                        choice = 8;     //empty hand
                        break;
                    }
                    case 16:    //cropping stamp
                    {
                        cropWidth = (int)(event.getX() - startX);
                        cropHeight = (int)(event.getY() - startY);
                        dp.getChildren().remove(cropRect);
                        gc.closePath();
                        choice = 17;
                        break;
                    }
                    case 17:    //stamp canvas
                    {
                        dp.getChildren().remove(cropImage);
                        gc.drawImage(cropImage.getImage(), event.getX(), event.getY(), cropRect.getWidth(), cropRect.getHeight());
                        choice = 8;
                        break;
                    }
                }
            }
        });
        choice=8;
    }



    public void setColorpicker(ColorPicker cp) {
        colorpicker = cp;
    }

    /*Creates Image using snapshot of canvas */
    public Image screenshot() {
        WritableImage tempPic = new WritableImage(CANVAS_WIDTH, CANVAS_HEIGHT); //creates image the size of the canvas
        canvasField.snapshot(null, tempPic); // makes temp image equal to screenshot of canvas
        ImageView imageView = new ImageView(tempPic);
        return imageView.getImage();
    }

    public double getSize() {
        double size = Double.parseDouble(bsize.getText());
        return size;
    }

    public void addToStack(Image i) {
        actionsU.push(i);
    }

    public void setTextArea(TextArea ta) {
        TextArea textA = ta;
    }

    public int getCanvasHeight(){
        return CANVAS_HEIGHT;
    }
    public int getCANVAS_WIDTH(){
        return CANVAS_WIDTH;
    }

    public GraphicsContext getGc(){
        return gc;
    }

    public void drawPolygon(Canvas canvas, int numPoints, double xClickPoint, double yClickPoint, double secondClickX, double secondClickY){
        double[] xPoints = new double[numPoints+1];
        double[] yPoints = new double[numPoints+1];
        xPoints[0] = secondClickX;
        yPoints[0] = secondClickY;
        final double angleStep = Math.PI * 2 / numPoints;

        double radius = Math.sqrt(((secondClickX-xClickPoint)*
                (secondClickX-xClickPoint)) + ((secondClickY-yClickPoint)*
                (secondClickY-yClickPoint)));
        double angle = Math.atan2(secondClickY-yClickPoint,
                secondClickX-xClickPoint);
        for (int i = 0; i < numPoints+1; i++, angle += angleStep) {
            xPoints[i] = Math.cos(angle) * radius + xClickPoint;
            yPoints[i] = Math.sin(angle) * radius + yClickPoint;
        }

        gc.fillPolygon(xPoints, yPoints, numPoints+1);
        gc.strokePolygon(xPoints, yPoints, numPoints+1);
    }

}
