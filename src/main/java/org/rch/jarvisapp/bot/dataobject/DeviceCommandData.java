package org.rch.jarvisapp.bot.dataobject;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class DeviceCommandData extends JSONArray {

    public static final String ID = "id";
    public static final String VALUE = "value";

    public enum TimerType {
        TIMER("timer"),
        ON_TIMER("onTimer"),
        OFF_TIMER("offTimer");

        private String description;

        TimerType(String description){
            this.description = description;
        }

        public String getDescription(){
            return description;
        }
    }


    public DeviceCommandData(String json){
        super(json);
    }
    public DeviceCommandData(){
        super();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DeviceCommandData){
            DeviceCommandData dcd = (DeviceCommandData) obj;
            if (dcd.length() != length())
                return false;

            List<Integer> dcdIdList = new ArrayList<>();
            List<Integer> thisIdList = new ArrayList<>();
            for (Object o : dcd) dcdIdList.add((Integer) ((JSONObject)o).get(ID));
            for (Object o : this) thisIdList.add((Integer) ((JSONObject)o).get(ID));

            return dcdIdList.equals(thisIdList);
        } else
            return false;
    }

    public DeviceCommandData addDevices(DeviceCommandData deviceCommandData){
        for ( Object device : deviceCommandData)
            put(device);

        return this;
    }


    public DeviceCommandData addDevice(Integer id){
        return addDevice(id,null);
    }

    public DeviceCommandData addDevice(Integer id, Integer value){
        setDeviceValue(id,value);

        return this;
    }


    public DeviceCommandData setAllDevicesValue(Integer newValue){
        for(Object o : this)
            setDeviceValue((Integer) ((JSONObject)o).get(ID), newValue);

        return this;
    }


    public void setDeviceValue(Integer id, Integer value){
        for(Object o : this)
            if (((JSONObject)o).get(ID) == id) {
                if (value != null)
                    ((JSONObject) o).put(VALUE, value);
                return;
            }

        JSONObject newObj = new JSONObject();
        newObj.put(ID, id);
        if (value != null)
            newObj.put(VALUE, value);

        put(newObj);
    }


    public void setDeviceValue(Integer id, Boolean newValue){
        setDeviceValue(id, newValue ? 1 : 0);
    }

    @Nullable
    public Integer getDeviceValue(Integer id){
        for(Object o : this)
            if (((JSONObject)o).get(ID) == id)
                return Integer.valueOf(((JSONObject) o).get(VALUE).toString());

        return null;
    }

    @Nullable
    public Boolean getDeviceBooleanValue(Integer id){
        Integer result = getDeviceValue(id);
        if (result == null) return null;
        switch (result) {
            case 0 : return false;
            case 1 : return true;
            default : return null;
        }
    }

    public DeviceCommandData reverse(){
        DeviceCommandData result = this;
        for(Object o : this){
            Integer id = (Integer) ((JSONObject)o).get(ID);
            Boolean curState = result.getDeviceBooleanValue(id);
            if (curState != null)
                result.setDeviceValue(id, !curState);
            //else
                // warnings
        }

        return result;
    }

    public DeviceCommandData setTimer(Integer id, TimerType type, Integer value){
        JSONObject obj = getDeviceByIdOrAdd(id);
        obj.put(type.getDescription(), value);

        return this;
    }

    JSONObject getDeviceByIdOrAdd(Integer id){
        for(Object o : this)
            if (((JSONObject)o).get(ID) == id) {
                return (JSONObject) o;
            }

        JSONObject newObj = new JSONObject();
        newObj.put(ID, id);

        put(newObj);
        return newObj;
    }


/*    public Map<Device,String> getDeviceList(SmartHome home){
        Map<Device,String> result = new HashMap<>();
        for (String relay : getRelayList())
            for (String port : getPortList(relay))
                result.put(home.getDevice(relay, port), this.getDeviceValue(relay, port));

        return result;
    }*/
}
