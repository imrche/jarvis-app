package org.rch.jarvisapp.smarthome.api;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.rch.jarvisapp.bot.dataobject.DeviceCommandData;
import org.rch.jarvisapp.bot.dataobject.SensorData;
import org.rch.jarvisapp.utils.NetUtil;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties(prefix = "home")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Api {
    String proto;
    String ip;
    String port;

    final String STATUS_LIGHT = "/v2/status/light2";
    final String STATUS_SENSOR = "/v2/status/sensors2";
    final String SET_LIGHT = "/v2/set/light2";
    final String SET_GATE = "/set/gate";
    final String SET_UPD_MESSAGE = "/set/updatingMessage";

    final String PLACES_INIT = "/areas";//todo places
    final String DEVICES_INIT = "/devices";

    private String getURL() {
        return proto + "://" + ip + ":" + port;
    }

    public boolean isOnline() {
        return String.valueOf(HttpStatus.SC_OK).equals(NetUtil.sendGET(getURL()).get(NetUtil.STATUS));
    }

    public JSONArray getPlaces(){
        String response = NetUtil.sendGET(getURL() + PLACES_INIT).get(NetUtil.RESPONSE);
        //System.out.println("1 - "+response);
        return new JSONArray(response);
    }

    public JSONArray getDevices(){
        String response = NetUtil.sendGET(getURL() + DEVICES_INIT).get(NetUtil.RESPONSE);
        //System.out.println("2 - "+response);
        return new JSONArray(response);
    }

    public DeviceCommandData getStatusLight(DeviceCommandData req) {
        String response = NetUtil.sendPOST(getURL() + STATUS_LIGHT, req.toString()).get(NetUtil.RESPONSE);
        System.out.println("3");
        return new DeviceCommandData(response);
    }

    public void setStatusLight(String req) {
        NetUtil.sendPOST(getURL() + SET_LIGHT, req);
    }

    public void setStatusLight(DeviceCommandData req) {
        NetUtil.sendPOST(getURL() + SET_LIGHT, req.toString());
    }

    public String setGatesAction(String req) {
        String response = NetUtil.sendPOST(getURL() + SET_GATE, req).get(NetUtil.RESPONSE);
        return response;
    }

    public SensorData getStatusSensor(SensorData req) {
        String response = NetUtil.sendPOST(getURL() + STATUS_SENSOR, req.toString()).get(NetUtil.RESPONSE);

        return new SensorData(response);
    }


    public void setUpdatingMessage(String request){
        NetUtil.sendPOST(getURL() + SET_UPD_MESSAGE, request);
    }

}