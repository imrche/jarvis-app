package org.rch.jarvisapp.bot.dataobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.rch.jarvisapp.smarthome.devices.Device;

import javax.annotation.Nullable;

public class LightData extends DataObject{


    private static class LightElement extends DTOElement {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public Integer brightness;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public Integer hue;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public Integer saturation;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public Integer temperature;

        public LightElement() {}

        public LightElement(Integer id) {
            super(id);
        }

/*        public LightElement(int id, Boolean value) {
            super(id);
            this.value = value;
        }*/
    }

    public LightData(String json, Class<?> clazz) throws JsonProcessingException {
        super(json, clazz);
    }

    public LightData(String json) throws JsonProcessingException {
        super(json, LightElement.class);
    }

    public LightData() {
    }

    public LightElement addLight(Device device){
        return (LightElement) addDevice(device, LightElement.class);
    }

    public LightData setBrightness(Device device, Integer brightness){
        ((LightElement)getDeviceDTOElement(device)).brightness = brightness;

        return this;
    }

    public LightData setTemperature(Device device, Integer temperature){
        ((LightElement)getDeviceDTOElement(device)).temperature = temperature;

        return this;
    }

    public LightData setHue(Device device, Integer hue){
        ((LightElement)getDeviceDTOElement(device)).hue = hue;

        return this;
    }

    @Nullable
    public Integer getBrightness(Device device){
        for (DTOElement e : data){
            if (e.id == device.getId())
                return ((LightElement)e).brightness;
        }
        return null;
    }

    public LightElement getDeviceOrAddIfNotExists(Device device){
        DTOElement element = getDeviceDTOElement(device);
        if (element != null)
            return (LightElement) element;

        return addLight(device);
    }

    public int getDeviceCount(){
        return data.size();
    }

    public void mergeDTO(LightData newData){
        data.addAll(newData.getListElements());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof LightData){
            LightData sd = (LightData) obj;
            if (sd.getDeviceCount() != getDeviceCount())
                return false;

            return sd.getListElements().equals(getListElements());
        } else
            return false;
    }


    public LightData getClone(){
        try {
            return new LightData(this.getData());
        } catch (JsonProcessingException e) {
            logger.error("Не удалось клонировать объект LightData",e);
            return new LightData();
        }
    }
}