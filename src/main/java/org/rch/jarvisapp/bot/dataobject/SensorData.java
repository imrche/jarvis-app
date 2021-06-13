package org.rch.jarvisapp.bot.dataobject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rch.jarvisapp.smarthome.devices.Sensor;

import java.util.HashMap;
import java.util.Map;

public class SensorData extends JSONObject {
    public static final String DETAIL = "detail";

    public SensorData(){
        super();
        put(DETAIL, new JSONArray());
    }

    public SensorData(String str){
        super(str);
    }

    public void addSensor(Sensor sensor){
        Map<String,String> object = new HashMap<>();
        object.put("protocol", sensor.getProtocol().name());
        object.put("type", sensor.getSensorType().name());
        object.put("topic", sensor.getTopicId());
        object.put("value", "");

        ((JSONArray)this.get(DETAIL)).put(object);
    }

    public String getSensorValue(Sensor sensor){
        for (Object object : ((JSONArray)this.get(DETAIL))){
            JSONObject obj = ((JSONObject)object);
            if (sensor.getProtocol().name().equals(obj.get("protocol")) &&
                    sensor.getSensorType().name().equals(obj.get("type")) &&
                    sensor.getTopicId().equals(obj.get("topic"))
            ) {
                return obj.get("value").toString();
            }
        }
        return "";
    }

//    private boolean comp
}
