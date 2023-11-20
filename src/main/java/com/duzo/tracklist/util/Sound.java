package com.duzo.tracklist.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

public class Sound {
    public static String LOCATION = "/com/duzo/tracklist/sound/";
    private URL file;
    public Media media;
    public Sound(File file) {
        this.file = getURLFromFile(file);
        this.media = new Media(this.file.toString());
    }

    public String getSongName() {
        return this.file.toString().substring(this.file.toString().lastIndexOf("/") + 1);
    }

    public URL getURLFromFile(File file) {
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public URL getSoundInResource(String file) {
        try {
            return this.getClass().getResource(LOCATION + file);
        } catch (Exception e) {
            System.out.println("Could not find sound: " + file );
            return null;
        }
    }
}
