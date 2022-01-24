package org.rch.jarvisapp.bot.dataobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.rch.jarvisapp.smarthome.devices.Device;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class SwitcherData extends DataObject{


    private static class SwitcherElement extends DTOElement {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public Boolean value;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public Integer onTimer;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public Integer offTimer;

        public SwitcherElement() {}

        public SwitcherElement(Integer id) {
            super(id);
        }

        public SwitcherElement(int id, Boolean value) {
            super(id);
            this.value = value;
        }
    }

    public SwitcherData(String json, Class<?> clazz) throws JsonProcessingException {
        super(json, clazz);
    }

    public SwitcherData(String json) throws JsonProcessingException {
        super(json, SwitcherElement.class);
    }

    public SwitcherData() {
    }

    public SwitcherElement addSwitcher(Device device){
        return (SwitcherElement) addDevice(device, SwitcherElement.class);
    }

    public SwitcherElement addSwitcher(Device device, Boolean value){
        SwitcherElement element = (SwitcherElement) addDevice(device, SwitcherElement.class);
        element.value = value;
        return element;
    }

    public void setDeviceValue(Device device, Boolean value){
        ((SwitcherElement)getDeviceDTOElement(device)).value = value;
    }

    public void setTimer(Device device, Boolean value, Integer duration){
        setTimer(device, value, 0, duration);
    }
    public void setTimer(Device device, Boolean value, Integer delay, Integer duration){
        SwitcherElement element = ((SwitcherElement)getDeviceDTOElement(device));
        duration += delay;
        element.onTimer = value ? delay : duration;
        element.offTimer = !value ? delay : duration;
    }

    public Map<String,Integer> getTimer(Device device){
        Map<String,Integer> result =  new HashMap<>();
        SwitcherElement element = (SwitcherElement)getDeviceDTOElement(device);

        result.put("ON", element.onTimer);
        result.put("OFF", element.offTimer);

        return result;
    }

    public void setDeviceValue(SwitcherElement element, Boolean value){
        element.value = value;
    }

    public SwitcherData setAllDevicesValue(Boolean newValue){
        for (DTOElement e : data)
            ((SwitcherElement) e).value = newValue;
        return this;
    }

    @Nullable
    public Boolean getDeviceValue(Device device){
        for (DTOElement e : data){
            if (e.id == device.getId())
                return ((SwitcherElement)e).value;
        }
        return null;
    }

    @Nullable
    public Boolean getDeviceValue(Integer id){
        for (DTOElement e : data){
            if (e.id == id)
                return ((SwitcherElement)e).value;
        }
        return null;
    }

    public SwitcherData reverse(){
        for (DTOElement e : data){
            ((SwitcherElement)e).value = !((SwitcherElement)e).value;
        }
        return this;
    }

    public SwitcherElement getDeviceOrAddIfNotExists(Device device){
        DTOElement element = getDeviceDTOElement(device);
        if (element != null)
            return (SwitcherElement) element;

        return addSwitcher(device);
    }

    public int getDeviceCount(){
        return data.size();
    }

    public void mergeDTO(SwitcherData newData){
        data.addAll(newData.getListElements());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SwitcherData){
            SwitcherData sd = (SwitcherData) obj;
            if (sd.getDeviceCount() != getDeviceCount())
                return false;

            return sd.getListElements().equals(getListElements());
        } else
            return false;
    }


    public SwitcherData getClone(){
        try {
            return new SwitcherData(this.getData());
        } catch (JsonProcessingException e) {
            logger.error("Не удалось клонировать объект SwitcherData",e);
            return new SwitcherData();
        }
    }
}