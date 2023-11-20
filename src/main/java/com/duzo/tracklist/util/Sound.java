package com.duzo.tracklist.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;

public class Sound {
    public static String LOCATION = "/com/duzo/tracklist/sound/";
    private URL file;
    public Media media;
    public Sound(String file) {
        this.file = getSound(file);
        this.media = new Media(this.file.toString());
    }

    public URL getSound(String file) {
        try {
            return this.getClass().getResource(LOCATION + file);
        } catch (Exception e) {
            System.out.println("Could not find sound: " + file );
            return null;
        }
    }
}
