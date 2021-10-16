package org.rch.jarvisapp.smarthome.devices;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.smarthome.areas.Place;

@Data
public class TestDevice extends Device{
    @JsonProperty
    Integer id;


    public TestDevice(Integer id){
        this.id = id;
    }

    public TestDevice() {}
}