package org.rch.jarvisapp.smarthome.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONObject;
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
    final String SET_CURRENT_BOT_URL = "/set/currentBotUrl";
    final String PLACES_INIT = "/areas";//todo places
    final String DEVICES_INIT = "/devices";
    final String SCENARIOS_INIT = "/scenarios";
    final String SCENARIO_STATUS = "/scenario/status";
    final String SCENARIO_RUN = "/scenario";
    final String SPEAKER_COMMAND = "/speaker/command";
    final String SPEAKER_STATUS = "/speaker/status";
    final String SPEAKER_SETTINGS_GET = "/speaker/settings/get";
    final String SPEAKER_SETTINGS_SET = "/speaker/settings/set";

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

    public JSONArray getScenarios(){
        String response = NetUtil.sendGET(getURL() + SCENARIOS_INIT).get(NetUtil.RESPONSE);
        //System.out.println("2 - "+response);
        return new JSONArray(response);
    }

    public ScenariosData getScenariosStatus(ScenariosData req) throws HomeApiWrongResponseData {
        String response = NetUtil.sendPOST(getURL() + SCENARIO_STATUS, req.getData()).get(NetUtil.RESPONSE);
        try {
            return new ScenariosData(response);
        } catch (JsonProcessingException e) {
            throw new HomeApiWrongResponseData("Получение статусов сценариев - ответ " + response, e);
        }
    }

    public void sendSpeakerCommand(SpeakerData req) {
        NetUtil.sendPOST(getURL() + SPEAKER_COMMAND, req.getData());
    }


    public SpeakerStatusData getSpeakerStatus(SpeakerData req) throws HomeApiWrongResponseData {
        String response = NetUtil.sendPOST(getURL() + SPEAKER_STATUS, req.getData()).get(NetUtil.RESPONSE);
        try {
            return new SpeakerStatusData(response);
        } catch (JsonProcessingException e) {
            throw new HomeApiWrongResponseData("Статус колонок - ответ " + response, e);
        }
    }

    public SpeakerSettings getSpeakerSettings(SpeakerSettings req) throws HomeApiWrongResponseData {
        String response = NetUtil.sendPOST(getURL() + SPEAKER_SETTINGS_GET, req.getData()).get(NetUtil.RESPONSE);
        try {
            return new SpeakerSettings(response);
        } catch (JsonProcessingException e) {
            throw new HomeApiWrongResponseData("Настройки колонок - ответ " + response, e);
        }
    }

    public void setSpeakerSettings(SpeakerSettings req) {
        NetUtil.sendPOST(getURL() + SPEAKER_SETTINGS_SET, req.getData());
    }

    public SwitcherData getStatusLight(SwitcherData req) throws HomeApiWrongResponseData {
        String response = NetUtil.sendPOST(getURL() + STATUS_LIGHT, req.getData()).get(NetUtil.RESPONSE);
        try {
            return new SwitcherData(response);
        } catch (JsonProcessingException e) {
            throw new HomeApiWrongResponseData("Статус ламп - ответ " + response, e);
        }
    }

    public void setStatusLight(SwitcherData req) {
        NetUtil.sendPOST(getURL() + SET_LIGHT, req.getData());
    }

    public void runScenario(ScenarioData req) {
        NetUtil.sendPOST(getURL() + SCENARIO_RUN, req.getData());
    }


    public SwitcherData getStatusDevice(SwitcherData req) throws HomeApiWrongResponseData {
        String response = NetUtil.sendPOST(getURL() + STATUS_DEVICE, req.getData()).get(NetUtil.RESPONSE);
        try {
            return new SwitcherData(response);
        } catch (JsonProcessingException e) {
            throw new HomeApiWrongResponseData("Статус устройств - ответ " + response, e);
        }
    }

    public void setStatusDevice(SwitcherData req) {
        NetUtil.sendPOST(getURL() + SET_DEVICE, req.getData());
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


    public void setBotUrl(String url){
        JSONObject obj = new JSONObject();
        obj.put("url", url);
        NetUtil.sendPOST(getURL() + SET_CURRENT_BOT_URL, obj.toString());
    }
}