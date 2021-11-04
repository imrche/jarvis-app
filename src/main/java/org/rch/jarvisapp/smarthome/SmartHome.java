package org.rch.jarvisapp.smarthome;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.smarthome.api.Api;
import org.rch.jarvisapp.smarthome.areas.Area;
import org.rch.jarvisapp.smarthome.areas.Place;
import org.rch.jarvisapp.smarthome.devices.Device;
import org.rch.jarvisapp.smarthome.devices.Sensor;
import org.rch.jarvisapp.smarthome.enums.SensorTypes;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SmartHome {
    List<Device> devices = new ArrayList<>();
    List<Place> places = new ArrayList<>();
    List<Scenario> scenarios = new ArrayList<>();

    Api api;

    public SmartHome(Api api){
        this.api = api;
    }

    public void addDevice(Device device){
        devices.add(device);
    }

    public void addPlace(Place place){
        places.add(place);
    }

    public void addScenario(Scenario scenario){
        scenarios.add(scenario);
    }

    public void clearData(){
        devices.clear();
        places.clear();
        scenarios.clear();
    }

    public Place getPlaceByCode(String code){
        for (Place place : places){
            if (place.getCode().equals(code))
                return place;
        }
        assert false;
        return null;//todo show warnings
    }

    public List<Place> getArea() {
        List<Place> result = new ArrayList<>();
        for (Place place : places){
            if (place instanceof Area)
                result.add(place);
        }
        return result;
    }

    public List<Place> getPlaceChildren(Place parent){
        List<Place> result = new ArrayList<>();
        for (Place place : places)
            if (place.getParent() == parent)
                result.add(place);

        return result;
    }

    public List<Sensor> getSensors(SensorTypes sensorTypes){
        List<Sensor> result = new ArrayList<>();

        for (Device device : devices)
            if (device instanceof Sensor && ((Sensor) device).getSensorType() == sensorTypes)
                result.add((Sensor) device);

        return result;
    }

    public <T extends Device> List<T> getDevicesByType(Class<T> type, Place place){
        return getDevicesByType(type)
                .stream()
                .filter(device -> device.getPlacement() == place)
                .collect(Collectors.toList());
    }

    public <T extends Device> List<T> getDevicesByType(Class<T> type){
        List<T> result = new ArrayList<>();

        for (Device device : devices)
            if (type.isInstance(device))
                result.add(type.cast(device));

        return result;
    }
}

