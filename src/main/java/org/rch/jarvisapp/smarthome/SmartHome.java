package org.rch.jarvisapp.smarthome;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.smarthome.api.Api;
import org.rch.jarvisapp.smarthome.areas.Area;
import org.rch.jarvisapp.smarthome.areas.Place;
import org.rch.jarvisapp.smarthome.devices.Device;
import org.rch.jarvisapp.smarthome.devices.Gate;
import org.rch.jarvisapp.smarthome.devices.Light;
import org.rch.jarvisapp.smarthome.devices.Sensor;
import org.rch.jarvisapp.smarthome.enums.SensorTypes;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SmartHome {
    List<Device> devices = new ArrayList<>();
    List<Place> places = new ArrayList<>();

    Api api;

    public SmartHome(Api api){
        this.api = api;
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

    public List<Place> getPlaceChildren(String parent){
        Place parentPlace = getPlaceByCode(parent);
        List<Place> result = new ArrayList<>();
        for (Place place : places)
            if (place.getParent() == parentPlace)
                result.add(place);

        return result;
    }

    public List<Light> getLights(String place){
        Place parentPlace = getPlaceByCode(place);
        List<Light> result = new ArrayList<>();

        for (Device device : devices)
            if (device instanceof Light && device.getPlacement() == parentPlace)
                result.add((Light) device);

        return result;
    }

    public List<Sensor> getSensors(SensorTypes sensorTypes){
        List<Sensor> result = new ArrayList<>();

        for (Device device : devices)
            if (device instanceof Sensor && ((Sensor) device).getSensorType() == sensorTypes)
                result.add((Sensor) device);

        return result;
    }

    public List<Sensor> getSensors(String place){
        Place parentPlace = getPlaceByCode(place);
        List<Sensor> result = new ArrayList<>();

        for (Device device : devices)
            if (device instanceof Sensor && device.getPlacement() == parentPlace)
                result.add((Sensor) device);

        return result;
    }

/*   public <T, clazz> List<T> getDevices(Class<T> clazz){
        List<T> result = new ArrayList<>();
        for (Device device : devices){
            if (device instanceof )
            result.add(clazz);
        }
        return new T();

    }*/

    public List<Gate> getGates(){
        List<Gate> result = new ArrayList<>();
        for (Device device : devices){
            if (device instanceof Gate)
                result.add((Gate)device);
        }
        return result;
    }
}

