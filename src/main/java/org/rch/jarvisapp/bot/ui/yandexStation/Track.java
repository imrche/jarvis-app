package org.rch.jarvisapp.bot.ui.yandexStation;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.json.JSONException;
import org.json.JSONObject;
import org.rch.jarvisapp.third_party.YandexApi;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Track {
    String id;
    String title;
    String artist;
    String album;
    String coverURL;
    Integer duration;
    String year;
    String genre;
    String albumTrackCount;
    String albumLikesCount;

    public Track(String id, String title, String artist, String album, String coverURL, Integer duration) {

        this.id = id;
        this.title = title;
        this.artist = artist;
        this.coverURL = coverURL;
        this.duration = duration;

        JSONObject response = YandexApi.getTrackInfo(id);
        if (!response.isEmpty()) {
            JSONObject albumResponse = response.getJSONArray("result").getJSONObject(0).getJSONArray("albums").getJSONObject(0);

            this.album = getFieldValue(albumResponse, "title");
            this.genre = getFieldValue(albumResponse, "genre");
            this.year = getFieldValue(albumResponse, "year");
            this.albumTrackCount = getFieldValue(albumResponse, "trackCount");
            this.albumLikesCount = getFieldValue(albumResponse, "likesCount");
        }
    }

    public String getDescription(){
        return artist + " - " +  title + " (" + getDurationFormatted() + ")";
    }

    public String getDurationFormatted(){
        return duration / 60 + ":" + duration%60;
    }

    public String getCoverURL(Integer size){
        return getCoverURL() + getCoverSizeDimension(size);
    }

    private String getCoverSizeDimension(Integer size){
        return size + "x" + size;
    }

    private String getFieldValue(JSONObject obj, String fieldName){
        try{
            return String.valueOf(obj.get(fieldName));
        } catch (JSONException e){
            return "N/A";
        }
    }
}
