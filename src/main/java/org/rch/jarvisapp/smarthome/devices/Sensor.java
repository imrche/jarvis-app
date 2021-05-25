package org.rch.jarvisapp.smarthome.devices;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.smarthome.areas.Place;
import org.rch.jarvisapp.smarthome.enums.SensorTypes;

@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sensor extends Device{
    public static final String className = "sensor";

    private enum Protocol { zigbee, mqtt }

    SensorTypes sensorType;
    Protocol protocol;
    String topicId;

    public Sensor(Place placement, String sensorType, String protocol, String topicId) {
        super(placement, "");
        this.topicId = topicId;
        this.protocol = Protocol.valueOf(protocol);
        this.sensorType = SensorTypes.valueOf(sensorType);//todo обработать IllegalArgument
    }
}
