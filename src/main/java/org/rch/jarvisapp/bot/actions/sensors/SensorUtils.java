package org.rch.jarvisapp.bot.actions.sensors;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.dataobject.SensorData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.devices.Sensor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class SensorUtils{

    protected static SensorData getSensorValues(List<Sensor> list){
        SensorData patternSD = new SensorData();
        for (Sensor sensor : list)
            patternSD.addSensor(sensor);

        return AppContextHolder.getApi().getStatusSensor(patternSD);
    }
}
