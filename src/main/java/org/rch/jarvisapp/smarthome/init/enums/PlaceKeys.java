package org.rch.jarvisapp.smarthome.init.enums;

import java.util.Map;

public enum PlaceKeys {
    type,
    parent,
    code,
    name;

    public String get(Map<String,Object> data){
        if (data.containsKey(name()))
            return data.get(name()).toString();
        else
            return null;
    }
}
