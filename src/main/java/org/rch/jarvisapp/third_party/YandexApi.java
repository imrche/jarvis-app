package org.rch.jarvisapp.third_party;

import org.json.JSONObject;
import org.rch.jarvisapp.utils.NetUtil;

public class YandexApi {
    public static String url = "https://api.music.yandex.net/";
    final static String TRACKS = "/tracks/";

    public static JSONObject getTrackInfo(String id){
        String response = NetUtil.sendGET(url + TRACKS + id).get(NetUtil.RESPONSE);
        return new JSONObject(response);
    }
}