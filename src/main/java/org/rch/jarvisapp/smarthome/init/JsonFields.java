package org.rch.jarvisapp.smarthome.init;

import org.rch.jarvisapp.smarthome.init.enums.FieldTypes;

import java.util.HashMap;

public class JsonFields extends HashMap<String, Object> {
    public Integer getAsInteger(FieldTypes type){
        return (Integer) get(type.name().toLowerCase());
    }
    public String getAsString(FieldTypes type){
        return String.valueOf(get(type.name().toLowerCase()));
    }
    //todo проверки на exists и тп
}
