package org.rch.jarvisapp.smarthome.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.rch.jarvisapp.bot.dataobject.*;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
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
    final String STATUS_DEVICE = "/status/device";
    final String STATUS_VALVE = "/status/valve";
    final String STATUS_SENSOR = "/status/sensors";
    final String STATUS_WINDOW = "/status/openings";
    final String STATUS_GATE = "/status/gate";
    final String STATUS_SW_MANAGER = "/switchManager/status";
    final String SET_SW_MANAGER = "/switchManager/set";
    final String SET_LIGHT = "/set/light";
    final String SET_DEVICE = "/set/device";
    final String SET_VALVE = "/set/valve";
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
        return new DeviceCommandData(response);
    }

    public DeviceCommandData getStatusDevice(DeviceCommandData req) {
        String response = NetUtil.sendPOST(getURL() + STATUS_DEVICE, req.toString()).get(NetUtil.RESPONSE);
        return new DeviceCommandData(response);
    }




    public SwitcherData getStatusSwitchManager(SwitcherData req) throws HomeApiWrongResponseData {
        String response = NetUtil.sendPOST(getURL() + STATUS_SW_MANAGER, req.getData()).get(NetUtil.RESPONSE);
        try {
            return new SwitcherData(response);
        } catch (JsonProcessingException e) {
            throw new HomeApiWrongResponseData("Установка вкл/выкл для переключателей - ответ " + response, e);
        }
    }

    public void setStatusSwitchManager(SwitcherData req) {
        NetUtil.sendPOST(getURL() + SET_SW_MANAGER, req.getData());
    }

    public void setStatusLight(String req) {
        NetUtil.sendPOST(getURL() + SET_LIGHT, req);
    }
    public void setStatusLight(DeviceCommandData req) {
        NetUtil.sendPOST(getURL() + SET_LIGHT, req.toString());
    }
    public void setStatusDevice(DeviceCommandData req) {
        NetUtil.sendPOST(getURL() + SET_DEVICE, req.toString());
    }
    public void setStatusValve(SwitcherData req) {
        NetUtil.sendPOST(getURL() + SET_VALVE, req.getData());
    }

    public SwitcherData getStatusValve(SwitcherData req) throws HomeApiWrongResponseData {
        String response = NetUtil.sendPOST(getURL() + STATUS_VALVE, req.getData()).get(NetUtil.RESPONSE);
        try {
            return new SwitcherData(response);
        } catch (JsonProcessingException e) {
            throw new HomeApiWrongResponseData("Статус вводных кранов - ответ " + response, e);
        }
    }

    public GateData setGatesAction(GateData req) throws HomeApiWrongResponseData {
        String response = NetUtil.sendPOST(getURL() + SET_GATE, req.getData()).get(NetUtil.RESPONSE);
        try {
            return new GateData(response);
        } catch (JsonProcessingException e) {
            throw new HomeApiWrongResponseData("Установка действия воротам - ответ " + response, e);
        }
    }

    public GateData getStatusGates(GateData req) throws HomeApiWrongResponseData {
        String response = NetUtil.sendPOST(getURL() + STATUS_GATE, req.getData()).get(NetUtil.RESPONSE);
        try {
            return new GateData(response);
        } catch (JsonProcessingException e) {
            throw new HomeApiWrongResponseData("Статус ворот - ответ " + response, e);
        }
    }

    public SensorData getStatusSensor(SensorData req) throws HomeApiWrongResponseData {
        String response = NetUtil.sendPOST(getURL() + STATUS_SENSOR, req.getData()).get(NetUtil.RESPONSE);
        try {
            return new SensorData(response);
        } catch (JsonProcessingException e) {
            throw new HomeApiWrongResponseData("Статус датчиков - ответ " + response, e);
        }
    }

    public WindowData getStatusWindow(WindowData req) throws HomeApiWrongResponseData {
        String response = NetUtil.sendPOST(getURL() + STATUS_WINDOW, req.getData()).get(NetUtil.RESPONSE);
        try {
            return new WindowData(response);
        } catch (JsonProcessingException e) {
            throw new HomeApiWrongResponseData("Статус окон - ответ " + response, e);
        }
    }


    public void setUpdatingMessage(String request){
        NetUtil.sendPOST(getURL() + SET_UPD_MESSAGE, request);
    }
}