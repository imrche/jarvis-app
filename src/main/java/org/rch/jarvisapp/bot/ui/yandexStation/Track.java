package org.rch.jarvisapp.bot.ui.yandexStation;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Track {
    String id;
    String title;
    String artist;
    String album;
    String coverURL;
    Integer duration;

    public Track(String id, String title, String artist, String album, String coverURL, Integer duration) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.coverURL = coverURL;
        this.duration = duration;
    }
}
