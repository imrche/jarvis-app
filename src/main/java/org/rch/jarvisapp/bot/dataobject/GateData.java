package org.rch.jarvisapp.bot.dataobject;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rch.jarvisapp.smarthome.devices.Gate;

public class GateData extends JSONArray {
    public static final String ID = "id";
    public static final String ACTION = "action";
    public static final String ACCEPT = "accept";
    public static final String MESSAGE = "message";
    public static final String STATUS = "status";

    public enum ActionValue{
        open,
        close,
        click
    }

    public enum StatusValue{
        open,
        close,
        NA,
        intermediate
    }


    public GateData(){
        super();
    }

/*    public void setAction(Gate gate, ActionValue action){
        JSONObject obj = getGateById(gate.getId());
        //todo обработка action null
        obj.put(ACTION, action.name());
    }*/

    public GateData(String str){
        super(str);
    }

    public void addGate(Gate gate){
        addGate(gate,null);
    }

    public void addGate(Gate gate,ActionValue action){
        Integer id = gate.getId();

        if (getGateById(id) != null)
            return;

        JSONObject obj = new JSONObject();

        obj.put(ID, id);
        if (action != null)
            obj.put(ACTION, action.name());

        put(obj);
    }


    private JSONObject getGateById(Integer id){
        for(Object o : this){
            if (((JSONObject)o).get(ID) == id)
                return (JSONObject)o;
        }
        return null;
    }


    public StatusValue getGateStatus(Gate gate){
        Integer id = gate.getId();
        JSONObject obj = getGateById(id);

        if (obj == null)
            return StatusValue.NA;
        //    throw new Exception();
        //todo

        return StatusValue.valueOf(obj.get(STATUS).toString());
    }

    public String getGateMessage(Gate gate){
        Integer id = gate.getId();
        JSONObject obj = getGateById(id);

        if (obj == null)
            return "";
        //    throw new Exception();
        //todo

        return obj.get(MESSAGE).toString();
    }
}
