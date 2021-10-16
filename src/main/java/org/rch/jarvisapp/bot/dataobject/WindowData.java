package org.rch.jarvisapp.bot.dataobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.rch.jarvisapp.bot.dataobject.status.StatusObjectInDTO;
import org.rch.jarvisapp.smarthome.devices.Window;

public class WindowData extends DataObject{

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

    public WindowData(String json) throws JsonProcessingException {
        super(json, Element.class);
    }

    public WindowData() {
    }


    public void addWindow(Window window){
        addDevice(window, Element.class);
    }

    public String getWindowValue(Window window){
        DTOElement e = getDeviceDTOElement(window);
        if (e == null)
            return StatusObjectInDTO.NA.name();

        return ((Element)e).value;
    }

}