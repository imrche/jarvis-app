package org.rch.jarvisapp.smarthome.api;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.rch.jarvisapp.bot.dataobject.DeviceCommandData;
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

    final String STATUS_LIGHT = "/status/light";
    final String SET_LIGHT = "/set/light";
    final String SET_GATE = "/set/gate";
    final String SET_UPD_MESSAGE = "/set/updatingMessage";
    final String STUFF_INIT = "/objectsinit";

    private String getURL() {
        return proto + "://" + ip + ":" + port;
    }

    public boolean isOnline() {
        return String.valueOf(HttpStatus.SC_OK).equals(NetUtil.sendGET(getURL()).get(NetUtil.STATUS));
    }

    public JSONObject getHomeStuff(){
        String response = NetUtil.sendGET(getURL() + STUFF_INIT).get(NetUtil.RESPONSE);
        return new JSONObject(response);
    }

    public DeviceCommandData getStatusLight(DeviceCommandData req) {
        String response = NetUtil.sendPOST(getURL() + STATUS_LIGHT, req.toString()).get(NetUtil.RESPONSE);

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

    public void setUpdatingMessage(String request){
        NetUtil.sendPOST(getURL() + SET_UPD_MESSAGE, request);
    }

}