package org.rch.jarvisapp.bot.actions.sensors.type;

import org.rch.jarvisapp.bot.actions.sensors.ShowSensorByType;
import org.rch.jarvisapp.smarthome.enums.SensorTypes;

public class ShowSound extends ShowSensorByType {
    public ShowSound() {
        setSensorType(SensorTypes.sound);
    }
}
