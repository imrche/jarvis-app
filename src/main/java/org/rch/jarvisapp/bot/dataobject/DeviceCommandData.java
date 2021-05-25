package org.rch.jarvisapp.bot.dataobject;

import org.json.JSONObject;

import javax.annotation.Nullable;
import java.util.Set;

public class DeviceCommandData extends JSONObject {

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
            if (!getRelayList().equals(dcd.getRelayList()))
                return false;
            for (String relay : dcd.getRelayList()){
                if (!getPortList(relay).equals(dcd.getPortList(relay)))
                    return false;
            }
        }

        return true;
    }

    public DeviceCommandData addDevices(DeviceCommandData deviceCommandData){
        for (String relay : deviceCommandData.getRelayList()){
            for (String port : deviceCommandData.getPortList(relay)){
                addDevice(relay,port);
            }
        }
        return this;
    }


    public DeviceCommandData addDevice(String relay, String port){
        return addDevice(relay,port,"");
    }

    public DeviceCommandData addDevice(String relay, String port, String value){
        if (!has(relay))
            put(relay,new JSONObject());

        setDeviceValue(relay,port,value);

        return this;
    }

    public Set<String> getRelayList(){
        return keySet();
    }

    public Set<String> getPortList(String relay){
        return ((JSONObject)get(relay)).keySet();
    }

    public DeviceCommandData setAllDevicesValue(String newValue){
        for (String relay : getRelayList())
            for (String port : getPortList(relay))
                setDeviceValue(relay, port, newValue);

        return this;
    }

    public void setDeviceValue(String relay, String port, String newValue){
        if (has(relay))
            ((JSONObject)get(relay)).put(port, newValue);
    }

    public void setDeviceValue(String relay, String port, Boolean newValue){
        setDeviceValue(relay, port, newValue ? "1" : "0");
    }

    @Nullable
    public String getDeviceValue(String relay, String port){
        if (has(relay)) {
            JSONObject relayObj = ((JSONObject) get(relay));
            if (relayObj.has(port))
                return ((JSONObject) get(relay)).getString(port);
        }
        return null;
    }

    @Nullable
    public Boolean getDeviceBooleanValue(String relay, String port){
        String result = getDeviceValue(relay, port);
        if (result == null) return null;
        switch (result) {
            case "0" : return false;
            case "1" : return true;
            default : return null;
        }
    }

    public DeviceCommandData reverse(){
        DeviceCommandData result = this;
        for (String relay : getRelayList())
            for (String port : getPortList(relay)){
                Boolean curState = result.getDeviceBooleanValue(relay, port);
                if (curState != null)
                    result.setDeviceValue(relay, port, !curState);
                //else
                    // warnings
            }

        return result;
    }


/*    public Map<Device,String> getDeviceList(SmartHome home){
        Map<Device,String> result = new HashMap<>();
        for (String relay : getRelayList())
            for (String port : getPortList(relay))
                result.put(home.getDevice(relay, port), this.getDeviceValue(relay, port));

        return result;
    }*/
}
