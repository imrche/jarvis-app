package org.rch.jarvisapp.bot.dataobject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rch.jarvisapp.smarthome.devices.Window;

public class WindowData extends JSONArray {
    public static final String ID = "id";
    public static final String VALUE = "value";

    public WindowData(){
        super();
    }

    public WindowData(String str){
        super(str);
    }

    public void addWindow(Window window){
        Integer id = window.getId();

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


    public String getWindowValue(Window window){
        Integer id = window.getId();
        JSONObject obj = getSensorById(id);

        if (obj == null)
            return "";
        //    throw new Exception();
        //todo

        return obj.get(VALUE).toString();
    }
}
