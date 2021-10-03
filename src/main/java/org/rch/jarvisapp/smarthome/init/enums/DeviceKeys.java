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
    topicId,
    id
    ;

    public String getFrom(Map<String,Object> data) throws Exception {
        if (data.containsKey(name()))
            return data.get(name()).toString();
        else
            throw new Exception("Отсутствует обязательное значение " + name() + "!");
    }

    public String getFrom(Map<String,Object> data, boolean exceptionSuppress) {
        try {
            return getFrom(data);
        } catch (Exception e){
            return null;
        }
    }

    public Integer getInt(Map<String,Object> data){
        if (data.containsKey(name()))
            return (Integer) data.get(name());
        else
            return null;
    }
}
