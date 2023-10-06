package com.example.paintapp;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class SaveClock extends Task {
    private FIleControl filer;

    private Timeline clock;  //creates a timer object

    private int timesAutoSaved = 0;  //The amount of times the file has been autosaved

    private int minutes, seconds;

    private StringProperty time;

    private boolean complete = false;  //Checks to complete timer

    public SaveClock(FIleControl fIleControl, Label label) {
        this.filer = fIleControl;

        time = new SimpleStringProperty();

        minutes = 2;
        seconds = 3;

        label.textProperty().bind(time);
    }

    public void SaveClock(FIleControl filer, Label label) {
        this.filer = filer;

        time = new SimpleStringProperty();

        minutes = 2;
        seconds = 3;

        label.textProperty().bind(time);
    }

    public void reset()
    {
        minutes = 2;
        seconds = 0;
        complete = false;
    }

    @Override
    protected Object call() throws Exception //This controls the timeline and time variable
    {
        final Duration PROBE_FREQ = Duration.seconds(1);

        clock = new Timeline(
                new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>()
                        {
                            @Override
                            public void handle (ActionEvent event){
                                time.setValue(tickTock());
                            }
                        }
                ), new KeyFrame(PROBE_FREQ)
        );
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
        return time.getValue();
    }

    private String tickTock() { // This subtracts from timer and returns formatted String
        if(complete) {
            timesAutoSaved++;
            System.out.println("AutoSaved " + timesAutoSaved + " times");
            filer.save();  //If thread hasnt been started then start in saveAs***
            reset();
        }

        if(minutes >= 0) {
            if(seconds>0) {
                seconds--;
                if(minutes == 0 && seconds == 0) {
                    System.out.println("Timer is complete");
                    complete = true;
                }
            }

            else {
                seconds = 59;
                minutes--;
            }

            String sec;
            if (seconds > 9) {
                sec = Integer.toString(seconds);
            }
            else {
                sec = ("0" + seconds);
            }
            time.setValue("0" + minutes + ":" + sec);

            return ("0" + minutes + ":" + sec);
        }

        else {
            System.out.println("Timer is complete");
            complete = true;
        }
        return "00:00";
    }

    ///  vvv Basic mutators vvv
    public StringProperty getTime() {return time;}
    public int getMinutes() {return minutes;}
    public int getSeconds() {return seconds;}
}
