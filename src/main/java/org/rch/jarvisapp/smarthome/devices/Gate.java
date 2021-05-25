package org.rch.jarvisapp.smarthome.devices;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.smarthome.areas.Place;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class Gate extends Device{
    public static final String className = "gate";
    String code;

    public Gate(Place placement, String name, String code) {
        super(placement, name);
        this.code = code;
    }

}
