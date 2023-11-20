package com.duzo.tracklist.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.ArrayList;

public class SoundManager {
    private static MediaPlayer player;
    private static ArrayList<Sound> queue = new ArrayList<>();
    public static boolean isPlayerFinished = true;

    public static void addToQueue(Sound sound) {
        boolean wasEmpty = queue.isEmpty();

        queue.add(sound);

        if (wasEmpty && isPlayerFinished) {
            playNextInQueue();
        }
    }
    private static void playNextInQueue() {
        if (queue.isEmpty()) return;

        Sound next = queue.get(0);
        play(next,false);
        queue.remove(0);
    }

    public static void play(Sound sound, boolean looping) {
        if (sound.media == null) return;

        player = new MediaPlayer(sound.media);
        isPlayerFinished = false;

        if (looping) {
            player.setOnEndOfMedia(() -> {
                player.seek(Duration.ZERO);
            });
        } else {
            player.setOnEndOfMedia(() -> {
                isPlayerFinished = true;
                playNextInQueue();
            });
        }

        player.play();
    }
    public static void stop() {
        if (player == null) return;

        player.stop();
    }
    public static void pause() {
        if (player == null) return;

        player.pause();
    }
    public static void unpause() {
        if (player == null) return;

        player.play();
    }
    public static void skip() {
        if (player == null) return;

        player.stop();
        isPlayerFinished = true;
        playNextInQueue();
    }
    public static Duration getTimeLeft() {
        return player.getStopTime().subtract(player.getCurrentTime());
    }
}
