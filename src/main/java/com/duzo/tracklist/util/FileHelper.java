package com.duzo.tracklist.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileHelper {
    public static final List<String> AUDIO_SUFFIXES = List.of(
            "wav",
            "mp3"
    );

    public static Set<String> getFilesInDir(String dir) {
        return Stream.of(new File(dir).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    public static List<File> getAudioFilesInDir(String dir) {
        List<File> list = new ArrayList<>();
        String suffix;
        for (String name : getFilesInDir(dir)) {
            suffix = name.substring(name.lastIndexOf(".") + 1);
            if (!AUDIO_SUFFIXES.contains(suffix)) continue;

            list.add(new File(dir + "\\" + name));
        }
        return list;
    }
}
