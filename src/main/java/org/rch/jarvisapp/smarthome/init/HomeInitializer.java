package org.rch.jarvisapp.smarthome.init;

import org.json.JSONObject;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.api.Api;
import org.springframework.stereotype.Service;

@Service
public class HomeInitializer {
//todo подумать, может стоит перенести в рантайм
    public HomeInitializer(Api api, SmartHome smartHome) {

        JSONObject stuff = api.getHomeStuff();

        if(!stuff.isEmpty()) {
            for (Object key : stuff.getJSONArray("places"))
                smartHome.getPlaces().add(PlaceBuilder.create(((JSONObject) key).toMap(), smartHome));

            for (Object key : stuff.getJSONArray("devices"))
                smartHome.getDevices().add(DeviceBuilder.create(((JSONObject) key).toMap(), smartHome));
        }
    }
}
