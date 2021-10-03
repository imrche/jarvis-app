package org.rch.jarvisapp.bot.actions.sensors;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.dataobject.SensorData;
import org.rch.jarvisapp.bot.enums.ParseMode;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.utils.MD;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.devices.Sensor;
import org.rch.jarvisapp.smarthome.enums.SensorTypes;

import java.util.List;

public abstract class ShowSensorByType implements Action {
    private SensorTypes sensorType;
    SmartHome smartHome = AppContextHolder.getSH();

    public SensorTypes getSensorType() {
        return sensorType;
    }

    public void setSensorType(SensorTypes sensorType) {
        this.sensorType = sensorType;
    }

    @Override
    public void run(Tile tile) {
        List<Sensor> list = smartHome.getSensors(sensorType);
        SensorData responseSD = SensorUtils.getSensorValues(list);

        StringBuilder result = new StringBuilder(MD.italic(sensorType.getDescription()) + "\n");

        for (Sensor sensor : list){
            result.append(MD.fixWidth(sensor.getPlacement().getName(), 20))
                    .append(MD.bold(responseSD.getSensorValue(sensor)))
                    .append(sensorType.getUnit())
                    .append("\n");
        }

        tile.update()
                .setCaption(result.toString())
                .setParseMode(ParseMode.Markdown)
                .clearKeyboard();
    }
}
