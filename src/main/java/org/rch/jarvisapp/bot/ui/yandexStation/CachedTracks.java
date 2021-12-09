package org.rch.jarvisapp.bot.ui.yandexStation;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class CachedTracks extends HashMap<String,Track> {
    public boolean isCashed(String id){
        return containsKey(id);
    }

    public void putIntoCache(Track track){
        put(track.getId(),track);
    }

    public Track getFromCache(String track){
        return get(track);
    }
}
