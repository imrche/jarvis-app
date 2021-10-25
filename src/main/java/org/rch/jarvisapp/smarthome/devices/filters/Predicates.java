package org.rch.jarvisapp.smarthome.devices.filters;

import org.rch.jarvisapp.smarthome.areas.Place;
import org.rch.jarvisapp.smarthome.devices.Device;

import java.util.List;
import java.util.function.Predicate;

public class Predicates {
    public static <T extends Device> Predicate<T> accumulator(List<Predicate<T>> list){
        return list.stream().reduce(Predicate::and).orElse(x->true);
    }

    public static <T extends Device> Predicate<T> isPlaceLaying(Place place){
        return p -> p.getPlacement() == place;
    }

    public static <T extends Device> Predicate<Device> isTypeOf(Class<T> type){
        return type::isInstance;
    }
}