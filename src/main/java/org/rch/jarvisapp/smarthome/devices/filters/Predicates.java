package org.rch.jarvisapp.smarthome.devices.filters;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.areas.Place;
import org.rch.jarvisapp.smarthome.devices.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;


public class Predicates {

   // private static SmartHome smartHome = AppContextHolder.getSH();

    public static <T extends Device> Predicate<T> accumulator(List<Predicate<T>> list){
        return list.stream().reduce(Predicate::and).orElse(x->true);
    }

    public static <T extends Device> Predicate<T> isPlaceLaying(Place place){
        return p -> p.getPlacement() == place;
    }

    public static <T extends Device> Predicate<T> isPlaceLayingAll(Place place){
        return p -> p.getPlacement() == place || AppContextHolder.getSH().getPlaceChildren(place).contains(p.getPlacement());
    }

    public static <T extends Device> Predicate<Device> isTypeOf(Class<T> type){
        return type::isInstance;
    }
}