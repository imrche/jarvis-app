package org.rch.jarvisapp.smarthome.init.enums;

import java.util.Map;

public enum DeviceKeys {
    type,
    place,
    name,
    code,
    relay,
    port,
    row,
    priority,
    sensorType,
    protocol,
    topicId
    ;

    public String get(Map<String,Object> data){
        if (data.containsKey(name()))
            return data.get(name()).toString();
        else
            return null;
    }

    public Integer getInt(Map<String,Object> data){
        if (data.containsKey(name()))
            return (Integer) data.get(name());
        else
            return null;
    }
}
