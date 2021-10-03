package org.rch.jarvisapp.smarthome.init;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.api.Api;
import org.rch.jarvisapp.smarthome.areas.Area;
import org.rch.jarvisapp.smarthome.areas.Place;
import org.rch.jarvisapp.smarthome.areas.Room;
import org.rch.jarvisapp.smarthome.devices.Device;
import org.rch.jarvisapp.smarthome.devices.Gate;
import org.rch.jarvisapp.smarthome.devices.Light;
import org.rch.jarvisapp.smarthome.devices.Sensor;
import org.rch.jarvisapp.smarthome.init.enums.FieldTypes;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class HomeInitializer {

    private Class<?> convert2class(String type) throws UnknownDeviceTypeException {
        switch (type){
            //for places
            case "area" : return Area.class;
            case "room" : return Room.class;

            //for devices
            case "light": return Light.class;
            case "gate": return Gate.class;
            case "sensor": return Sensor.class;
            //case "valve":
            //case "window":
            //case "endDevice":
            default: throw new UnknownDeviceTypeException("Не распознано - " + type);
        }
    }

    private Object createObject(ObjectMapper mapper, Object json) throws JsonProcessingException, UnknownDeviceTypeException {
        final String TYPE = FieldTypes.TYPE.name().toLowerCase(Locale.ROOT);
        JSONObject jsonObject = (JSONObject) json;
        return mapper.readValue(jsonObject.toString(),convert2class(jsonObject.get(TYPE).toString()));
    }


//todo подумать, может стоит перенести в рантайм
    public HomeInitializer(Api api, SmartHome smartHome) throws Exception {

        if (!api.isOnline())
            throw new Exception("Controller is unreachable!");

        final ObjectMapper mapper = new ObjectMapper();
        JSONArray places = api.getPlaces();
        JSONArray devices = api.getDevices();

        //todo обработать отсутствие ответа
        if (!places.isEmpty()) {
            for (Object obj : places)
                try {
                    smartHome.addPlace((Place) createObject(mapper, obj));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (UnknownDeviceTypeException e) {
                    System.out.println("Зона/помещение " + e.getMessage());
                }
        }

        if (!devices.isEmpty()){
            for (Object obj : devices)
                try {
                    smartHome.addDevice((Device) createObject(mapper, obj));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                } catch (UnknownDeviceTypeException e) {
                    System.out.println("Устройство " + e.getMessage());
                }
        }
        System.out.println("Done!");
    }
}
