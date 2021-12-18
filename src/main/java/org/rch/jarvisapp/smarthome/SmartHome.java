package org.rch.jarvisapp.smarthome;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.bot.dataobject.LightCommandData;
import org.rch.jarvisapp.bot.dataobject.SwitcherData;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.smarthome.api.Api;
import org.rch.jarvisapp.smarthome.areas.Area;
import org.rch.jarvisapp.smarthome.areas.Place;
import org.rch.jarvisapp.smarthome.devices.Device;
import org.rch.jarvisapp.smarthome.devices.Light;
import org.rch.jarvisapp.smarthome.devices.Sensor;
import org.rch.jarvisapp.smarthome.devices.filters.Predicates;
import org.rch.jarvisapp.smarthome.enums.SensorTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SmartHome {
    List<Device> devices = new ArrayList<>();
    List<Place> places = new ArrayList<>();
    List<Scenario> scenarios = new ArrayList<>();

    private final Map<Device, Boolean> deviceStatusCache = new HashMap<>();

    public void initCacheDeviceStatus(){
        for (Device device : devices)
            deviceStatusCache.put(device,null);
    }

    public Boolean getCachedStatus(Device device){
        return deviceStatusCache.get(device);
    }

    public void cacheDeviceStatus(){
        deviceStatusCache.replaceAll((d, v) -> null);

        SwitcherData lightCD = new SwitcherData();
        for (Device device : devices) {
            if (device instanceof Light)
                lightCD.addSwitcher(device);
        }

        try {
            SwitcherData sdResponse = api.getStatusLight(lightCD);

            for (Device device : devices){
                Boolean b = sdResponse.getDeviceValue(device);
                if (b != null)
                    deviceStatusCache.put(device, b);
            }

        } catch (HomeApiWrongResponseData homeApiWrongResponseData) {
            homeApiWrongResponseData.printStackTrace();
        }
    }


  //  @Autowired
   // Predicates predicates;

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

    public <T extends Device> Boolean hasDevicesOfType(Class<T> type, Place place){
        for (Device device : devices) {
            if (type.isInstance(device) && device.getPlacement().equals(place))
                return true;
        }
        return false;
    }

    public <T extends Device> List<T> getDevicesWithFilter(List<Predicate<Device>> list, Class<T> class2convert){
        //if (list.contains(Predicates.statusIs().))
        cacheDeviceStatus();
        List<Device> listDevice = devices
                                    .stream()
                                    .filter(Predicates.accumulator(list))
                                    .collect(Collectors.toList());

        return  listDevice.stream()
                .filter(class2convert::isInstance)
                .map(class2convert::cast)
                .collect(Collectors.toList());

//        return ((List<T>) (Object) listDevice);

    }


/*    public List<Device> getAllDeviceOfPlace(Place place){
        List<Device> result = new ArrayList<>();
        List<Place> places = getPlaceChildren(place);

        for (Place pl : places){
            result.add()
        }


        for(Device dev : getDevices()){
            if (dev.getPlacement() == place)

        }
    }*/

}

