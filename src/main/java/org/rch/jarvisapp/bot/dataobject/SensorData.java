package org.rch.jarvisapp.bot.dataobject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rch.jarvisapp.smarthome.devices.Sensor;

import java.util.HashMap;
import java.util.Map;

public class SensorData extends JSONArray {
    public static final String ID = "id";
    public static final String VALUE = "value";

    public SensorData(){
        super();
    }

    public SensorData(String str){
        super(str);
    }

    public void addSensor(Sensor sensor){
        Integer id = sensor.getId();

        if (getSensorById(id) != null)
            return;

        JSONObject obj = new JSONObject();

        obj.put(ID, id);
        obj.put(VALUE, "");

        put(obj);
    }


    private JSONObject getSensorById(Integer id){
        for(Object o : this){
            if (((JSONObject)o).get(ID) == id)
                return (JSONObject)o;
        }
        return null;
    }


    public String getSensorValue(Sensor sensor){
        Integer id = sensor.getId();
        JSONObject obj = getSensorById(id);

        if (obj == null)
            return "";
        //    throw new Exception();
        //todo

        return obj.get(VALUE).toString();
    }
}
