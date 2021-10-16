package org.rch.jarvisapp.bot.actions.sensors;

import org.rch.jarvisapp.AppContextHolder;

import org.rch.jarvisapp.bot.dataobject.SensorData;
import org.rch.jarvisapp.bot.dataobject.WindowData;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;

import org.rch.jarvisapp.smarthome.devices.Sensor;
import org.rch.jarvisapp.smarthome.devices.Window;


import java.util.List;

public class SensorUtils{

    protected static SensorData getSensorValues(List<Sensor> list) throws HomeApiWrongResponseData {
        SensorData patternSD = new SensorData();
        for (Sensor sensor : list)
            patternSD.addSensor(sensor);

        return AppContextHolder.getApi().getStatusSensor(patternSD);
    }

    //todo вынести
    public static WindowData getWindowValues(List<Window> list) throws HomeApiWrongResponseData {
        WindowData patternSD = new WindowData();
        for (Window window : list)
            patternSD.addWindow(window);

        return AppContextHolder.getApi().getStatusWindow(patternSD);
    }
}
