package org.rch.jarvisapp.bot.dataobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.rch.jarvisapp.bot.dataobject.status.StatusObjectInDTO;
import org.rch.jarvisapp.smarthome.devices.Sensor;

public class LightCommandData2 extends SwitcherData{
/*
    private static class LightCommandElement extends DTOElement {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public String specPar;

        public LightCommandElement() {
        }

        public LightCommandElement(Integer id) {
            super(id);
        }

        public LightCommandElement(int id, String value) {
            super(id);
            this.value = value;
        }
    }

    public LightCommandData2(String json) throws JsonProcessingException {
        super(json, LightCommandElement.class);
    }

    public LightCommandData2() {
    }


    public void addSensor(Sensor sensor){
        addDevice(sensor, LightCommandElement.class);
    }

    public String getSensorValue(Sensor sensor){
        DTOElement e = getDeviceDTOElement(sensor);
        if (e == null)
            return StatusObjectInDTO.NA.name();

        return ((LightCommandElement)e).value;
    }
*/
}
