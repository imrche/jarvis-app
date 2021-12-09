package org.rch.jarvisapp.bot.dataobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.rch.jarvisapp.smarthome.devices.Gate;
import org.rch.jarvisapp.smarthome.devices.status.GateStatus;

public class GateData extends DataObject{

    private static class GateElement extends DTOElement {

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public ActionValue action;//todo проверить как пройдет десер

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public String status;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        public String message;

        public GateElement() {}

        public GateElement(Integer id) {
            super(id);
        }
    }

    public enum ActionValue{
        open,
        close,
        stop,
        click
    }

    public GateData(String json) throws JsonProcessingException {
        super(json, GateElement.class);
    }

    public GateData() {
    }

    public GateElement addGate(Gate gate, ActionValue action){
        GateElement element = addGate(gate);
        element.action = action;

        return element;
    }

    public GateElement addGate(Gate gate){
        return (GateElement) addDevice(gate, GateElement.class);
    }

    public GateStatus getGateStatus(Gate gate){
        DTOElement e = getDeviceDTOElement(gate);

        if (e == null)
            return GateStatus.NA;

        return GateStatus.valueOf(((GateElement)e).status);
    }

    public String getGateMessage(Gate gate){
        DTOElement e = getDeviceDTOElement(gate);

        if (e == null)
            return null;

        return ((GateElement)e).message;
    }
}
