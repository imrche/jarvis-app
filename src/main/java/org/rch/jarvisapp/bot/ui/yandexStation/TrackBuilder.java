package org.rch.jarvisapp.bot.ui.yandexStation;

import org.rch.jarvisapp.bot.dataobject.SpeakerStatusData;
import org.springframework.stereotype.Service;

@Service
public class TrackBuilder {
    CachedTracks cachedTracks;

    public TrackBuilder(CachedTracks cachedTracks) {
        this.cachedTracks = cachedTracks;
    }

    public Track build(String id, String title, String artist, String album, String coverURL, Integer duration){
        return cachedTracks.isCashed(id) ? cachedTracks.getFromCache(id) : new Track(id, title, artist, album, coverURL, duration);
    }
    public Track build(SpeakerStatusData.SpeakerElement se){
        return build(se.trackID,se.title,se.artist,null,se.coverURL,se.trackDuration);
    }


}
