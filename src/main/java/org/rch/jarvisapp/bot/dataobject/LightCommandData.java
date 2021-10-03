package org.rch.jarvisapp.bot.dataobject;

import org.json.JSONObject;

public class LightCommandData extends DeviceCommandData {

    public static final String SPEC_PAR = "specPar";
    public static final String BRIGHTNESS = "Brightness";
    public static final String HUE = "Hue";
    public static final String SATURATION = "Saturation";
    public static final String COLOR_TEMPERATURE = "ColorTemperature";
    public static final String SAVED_LIGHT_PATTERN = "SavedLightPattern";

    public LightCommandData setSpecPar(Integer id, SpecPar specPar){
        JSONObject obj = getDeviceByIdOrAdd(id);
        obj.put(SPEC_PAR, specPar);
        return this;
    }

    public static class SpecPar extends JSONObject {
        public SpecPar addBrightness(Integer value) {
            put(BRIGHTNESS, value);

            return this;
        }

        public SpecPar addHue(Integer value) {
            put(HUE, value == null ? "" : value);
            return this;
        }

        public SpecPar addSaturation(Integer value) {
            put(SATURATION, value);
            return this;
        }

        public SpecPar addColorTemperature(Integer value) {
            put(COLOR_TEMPERATURE, value);
            return this;
        }
    }
}
