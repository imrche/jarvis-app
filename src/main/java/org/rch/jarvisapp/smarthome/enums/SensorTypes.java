package org.rch.jarvisapp.smarthome.enums;

public enum SensorTypes {
    temperature("Температура", "°"),
    humidity("Влажность", "%"),
    CO2("CO2", ""),
    CO("Угарный газ", ""),
    motion("Движение", ""),
    sound("Шум", "dB"),
    illuminance("Освещенность", "лк");

    private final String description;
    private final String unit;

    SensorTypes(String description, String unit) {
        this.description = description;
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public String getUnit() {
        return unit;
    }

}
