package com.duzo.tracklist;

import com.duzo.tracklist.util.Sound;
import com.duzo.tracklist.util.SoundManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Sound one = new Sound("gameover.wav");
            Sound two = new Sound("second.mp3");

            SoundManager.addToQueue(two);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> System.out.println(SoundManager.getTimeLeft().toSeconds())));
            timeline.play();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}