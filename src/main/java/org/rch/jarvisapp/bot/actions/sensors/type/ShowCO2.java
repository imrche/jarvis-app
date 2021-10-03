package org.rch.jarvisapp.bot.actions.sensors.type;

import org.rch.jarvisapp.bot.actions.sensors.ShowSensorByType;
import org.rch.jarvisapp.smarthome.enums.SensorTypes;

public class ShowCO2 extends ShowSensorByType {
    public ShowCO2() {
        setSensorType(SensorTypes.CO2);
    }
}
