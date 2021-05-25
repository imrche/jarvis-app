package org.rch.jarvisapp.smarthome.devices;

import lombok.Data;
import org.rch.jarvisapp.smarthome.areas.Place;

@Data
public class Device {
    Place placement;
    String name;
    public Device(Place placement, String name){
        this.placement = placement;
        this.name = name;
    }

    public Device() {
    }
}