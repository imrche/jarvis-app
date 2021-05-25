package org.rch.jarvisapp.smarthome.devices;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.smarthome.areas.Place;

@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class Light extends Device {
    public static final String className = "light";

    public static final String relayNamePrefix = "SW_RELAY";
    public static final String relayPortPrefix = "K";

    String relayName;
    String relayPort;
    Integer row;
    Integer priority;

    public Light(Place placement, String name, String relayName, String relayPort, Integer row, Integer priority){
        super(placement,name);
        this.relayName = relayNamePrefix + relayName;
        this.relayPort = relayPortPrefix + relayPort;
        this.row = row;
        this.priority = priority;
    }
}
