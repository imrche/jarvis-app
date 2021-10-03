package org.rch.jarvisapp.smarthome.devices;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.smarthome.areas.Place;

@Data
public class Device {
    @JsonProperty
    Integer id;

    Place placement;

    public Device(String placement, Integer id){
        this.placement = AppContextHolder.getSH().getPlaceByCode(placement);
        this.id = id;
    }
}