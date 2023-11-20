package com.duzo.tracklist.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class SoundManager {
    private static MediaPlayer player;
    private static ArrayList<Sound> queue = new ArrayList<>();
    public static boolean isPlayerFinished = true;
    public static Runnable onSongChange;

    public static void queueAllInFolder(String dir) {
        for (File file : FileHelper.getAudioFilesInDir(dir)) {
            addToQueue(new Sound(file));
        }
    }

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

                if (onSongChange != null) {
                    onSongChange.run();
                }
            });
        }

        if (onSongChange != null) {
            player.setOnPlaying(onSongChange);
            onSongChange.run();
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

    public static boolean isPlayerPaused() {
        if (player == null) return true;

        return player.getStatus() == MediaPlayer.Status.PAUSED;
    }
    public static void setOnSongChange(Runnable run) {
        onSongChange = run;
    }

    public static String getSongName() {
        if (player == null) return "";

        String source = player.getMedia().getSource();
        String uriEncoded = source.substring(source.lastIndexOf("/") + 1, source.lastIndexOf("."));

        try {
            return URLDecoder.decode(uriEncoded, StandardCharsets.UTF_8.toString());
        } catch (Exception e) {
            return "";
        }
    }
}
