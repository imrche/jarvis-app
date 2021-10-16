package org.rch.jarvisapp.bot.dataobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.rch.jarvisapp.bot.dataobject.status.StatusObjectInDTO;
import org.rch.jarvisapp.smarthome.devices.Sensor;

public class SensorData extends DataObject{

    private static class Element extends DTOElement {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public String value;

        public Element() {
        }

        public Element(Integer id) {
            super(id);
        }

        public Element(int id, String value) {
            super(id);
            this.value = value;
        }
    }

    public SensorData(String json) throws JsonProcessingException {
        super(json, Element.class);
    }

    public SensorData() {
    }


    public void addSensor(Sensor sensor){
        addDevice(sensor, Element.class);
    }

    public String getSensorValue(Sensor sensor){
        DTOElement e = getDeviceDTOElement(sensor);
        if (e == null)
            return StatusObjectInDTO.NA.name();

        return ((Element)e).value;
    }

}
