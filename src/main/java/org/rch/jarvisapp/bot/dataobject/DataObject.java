package org.rch.jarvisapp.bot.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.rch.jarvisapp.smarthome.devices.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public abstract class DataObject {
    protected final ObjectMapper mapper = new ObjectMapper();
    List<DTOElement> data = new ArrayList<>();
    Logger logger = LoggerFactory.getLogger(this.getClass());
    Boolean immutable = false;

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DTOElement {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public int id;
        DTOElement(Integer id){
            this.id = id;
        }
        DTOElement(){}
    }

    public DataObject(String json, Class<?> clazz) throws JsonProcessingException {
        JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        data = mapper.readValue(json, type);
    }

    public DataObject(){}

    public String getData(){
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            logger.error("Ошибка преобразования",e);//todo exception
        }
        return null;
    }

    protected DTOElement addDevice(Device device, Class<? extends DTOElement> clazz){
        DTOElement element = getDeviceDTOElement(device);
        if (element != null)
            return element;

        try {
            element = clazz.getConstructor(Integer.class).newInstance(device.getId());
        } catch (Exception e) {
            logger.error("Не удалось создать DTO",e);//todo exception
        }
        data.add(element);

        return element;
    }

    public Integer[] getListDevicesId(){
        Integer[] result = new Integer[data.size()];
        int i=0;
        for (DTOElement e : data)
            result[i++] = e.id;

        return result;
    }

    public DTOElement getDeviceDTOElement(Device device){
        return getDeviceDTOElement(device.getId());
    }

    public DTOElement getDeviceDTOElement(Integer id){
        for (DTOElement e : data){
            if (e.id == id)
                return e;
        }
        return null;
    }

    public List<DTOElement> getListElements(){
        return data;
    }

    public boolean isEmpty(){
        return data.isEmpty();
    }

    public void setImmutable(){
        immutable = true;
    }
}
