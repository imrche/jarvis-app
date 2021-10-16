package org.rch.jarvisapp.bot.dataobject.oldDTO;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rch.jarvisapp.bot.exceptions.DeviceStatusIsUnreachable;
import org.rch.jarvisapp.smarthome.devices.status.SwitchManagerStatus;

public class SwitchManagerData_old extends JSONArray {
    public static final String ID = "id";
    public static final String STATUS = "status";

    public SwitchManagerData_old(){
        super();
    }

    public SwitchManagerData_old(String str){
        super(str);
    }

    public void addSwM(Integer swM){
        Integer id = swM;

        if (getSwMById(id) != null)
            return;

        JSONObject obj = new JSONObject();
        obj.put(ID, id);
        put(obj);
    }


    private JSONObject getSwMById(Integer id){
        for(Object o : this){
            if (((JSONObject)o).get(ID) == id)
                return (JSONObject)o;
        }
        return null;
    }


    public void setSwMStatus(Integer id, SwitchManagerStatus value){
        for(Object o : this)
            if (((JSONObject)o).get(ID) == id) {
                if (value != null)
                    ((JSONObject) o).put(STATUS, value.name());
                return;
            }

        JSONObject newObj = new JSONObject();
        newObj.put(ID, id);
        if (value != null)
            newObj.put(STATUS, value.name());

        put(newObj);
    }

    public String getSwMStatus(Integer id){
        //Integer id = gate.getId();
        JSONObject obj = getSwMById(id);

        if (obj == null)
            return SwitchManagerStatus.NA.name();
        //    throw new Exception();
        //todo
//todo add incorrect status
        return obj.get(STATUS).toString();
    }

    private SwitchManagerStatus reverseStatus(String status){
        SwitchManagerStatus st = SwitchManagerStatus.valueOf(status);
        switch(st){
            case on : return SwitchManagerStatus.off;
            case off : return SwitchManagerStatus.on;
            default : return null;
        }
    }

    public SwitchManagerData_old reverse() throws DeviceStatusIsUnreachable {
        SwitchManagerData_old result = this;
        for(Object o : this){
            Integer id = (Integer) ((JSONObject)o).get(ID);
            String curState = result.getSwMStatus(id);
            if (curState != null)
                result.setSwMStatus(id, reverseStatus(curState));
            //else
            // warnings
        }

        return result;
    }
}
