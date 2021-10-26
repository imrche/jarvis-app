package org.rch.jarvisapp.smarthome.init;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.rch.jarvisapp.bot.ui.button.comparators.PlaceComparator;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.api.Api;
import org.rch.jarvisapp.smarthome.areas.Area;
import org.rch.jarvisapp.smarthome.areas.HomeRoot;
import org.rch.jarvisapp.smarthome.areas.Place;
import org.rch.jarvisapp.smarthome.areas.Room;
import org.rch.jarvisapp.smarthome.devices.*;
import org.rch.jarvisapp.smarthome.init.enums.FieldTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class HomeInitializer {

    Logger logger = LoggerFactory.getLogger(HomeInitializer.class);
    Api api;
    SmartHome smartHome;

    private Class<?> convert2class(String type) throws UnknownDeviceTypeException {
        switch (type){
            //for places
            case "area" : return Area.class;
            case "room" : return Room.class;

            //for devices
            case "light": return Light.class;
            case "gate": return Gate.class;
            case "sensor": return Sensor.class;
            case "rangehood": return RangeHood.class;
            case "valve": return Valve.class;
            case "window": return Window.class;
            default: throw new UnknownDeviceTypeException("Не распознано - " + type);
        }
    }

    private Object createObject(ObjectMapper mapper, Object json) throws JsonProcessingException, UnknownDeviceTypeException {
        final String TYPE = FieldTypes.TYPE.name().toLowerCase(Locale.ROOT);
        JSONObject jsonObject = (JSONObject) json;
        return mapper.readValue(jsonObject.toString(),convert2class(jsonObject.get(TYPE).toString()));
    }

    public HomeInitializer(Api api, SmartHome smartHome) {
        this.api = api;
        this.smartHome = smartHome;

    }

    public void init() throws Exception {
        if (!api.isOnline())
            throw new Exception("Controller is unreachable!");

        final ObjectMapper mapper = new ObjectMapper();
        JSONArray places = api.getPlaces();
        JSONArray devices = api.getDevices();

        smartHome.clearData();

        //todo обработать отсутствие ответа
        if (!places.isEmpty()) {
            for (Object obj : places)
                try {
                    smartHome.addPlace((Place) createObject(mapper, obj));
                } catch (JsonProcessingException e) {
                    logger.error("Ошибка в Json",e);
                } catch (UnknownDeviceTypeException e) {
                    logger.error("Зона/помещение " + e.getMessage(),e);
                }
        }

        smartHome.getPlaces().sort(new PlaceComparator());

        if (!devices.isEmpty()){
            for (Object obj : devices)
                try {
                    smartHome.addDevice((Device) createObject(mapper, obj));
                } catch (JsonProcessingException e) {
                    logger.error("Ошибка в Json",e);
                } catch (UnknownDeviceTypeException e) {
                    logger.error("Устройство " + e.getMessage(),e);
                }
        }

        logger.info("DONE! Home was initialized!");
    }
}
