package org.rch.jarvisapp.smarthome.init;

import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.devices.Device;
import org.rch.jarvisapp.smarthome.devices.Gate;
import org.rch.jarvisapp.smarthome.devices.Light;
import org.rch.jarvisapp.smarthome.devices.Sensor;
import org.rch.jarvisapp.smarthome.init.enums.DeviceKeys;

import java.util.Map;

public class DeviceBuilder {
    public static Device create(Map<String,Object> data, SmartHome smartHome){
        String type;
        if ((type = DeviceKeys.type.get(data)) != null)
            switch (type){
                case (Light.className) :
                    return new Light(
                            smartHome.getPlaceByCode(DeviceKeys.place.get(data)),
                            DeviceKeys.name.get(data),
                            DeviceKeys.relay.get(data),
                            DeviceKeys.port.get(data),
                            DeviceKeys.row.getInt(data),
                            DeviceKeys.priority.getInt(data));
                case (Sensor.className) :
                    return new Sensor(
                            smartHome.getPlaceByCode(DeviceKeys.place.get(data)),
                            DeviceKeys.sensorType.get(data),
                            DeviceKeys.protocol.get(data),
                            DeviceKeys.topicId.get(data));
                case (Gate.className) :
                    return new Gate(
                            smartHome.getPlaceByCode(DeviceKeys.place.get(data)),
                            DeviceKeys.name.get(data),DeviceKeys.code.get(data));
            }
        return null;
    }
}
