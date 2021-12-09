package org.rch.jarvisapp.bot.ui;

import org.json.JSONArray;
import org.rch.jarvisapp.bot.enums.BotCommand;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.MenuKeyBoard;
import org.rch.jarvisapp.smarthome.Scenario;
import org.rch.jarvisapp.smarthome.api.Api;
import org.rch.jarvisapp.smarthome.devices.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TilePool {
    @Autowired
    Api api;

    public List<Tile> tileList = new ArrayList<>();
    Map<Integer, List<Object>> feedBackLink = new HashMap<>();

    Tile tileWithTextInputActivated;

    public Tile build(BotCommand command){
        Tile tile = new Tile(command.getDescription(),new MenuKeyBoard(command));
        tileList.add(tile);
        return tile;
    }

    @Nullable
    public Tile getTileWith(Integer messageId){
        for (Tile tile : tileList){
            if (messageId.equals(tile.messageId))
                return tile;
        }
        return null;
    }

    public void addTile(Tile tile){
        tileList.add(tile);
    }
    public void removeTile(Tile tile){
        tileList.remove(tile);
    }

    public void setFeedBack(Integer messageId){
        Tile tile = getTileWith(messageId);
        if (tile == null)
            return;

        List<Object> listDevice = new ArrayList<>();
        for (KeyBoard kb : tile.content){
            if (kb instanceof DeviceContainer)
                listDevice.addAll(((DeviceContainer)kb).getDeviceList().stream().map(Device::getId).collect(Collectors.toList()));
            if (kb instanceof ScenarioContainer)
                listDevice.addAll(((ScenarioContainer)kb).getScenarioList().stream().map(Scenario::getCode).collect(Collectors.toList()));
        }

        listDevice = listDevice.stream().distinct().collect(Collectors.toList());

        if(!listDevice.isEmpty()){
            if (!feedBackLink.containsKey(messageId) || !feedBackLink.get(messageId).equals(listDevice)) {
                feedBackLink.put(messageId, listDevice);
                JSONArray arr = new JSONArray(listDevice);
                api.setUpdatingMessage("{\"" + messageId.toString() + "\" :"  + arr.toString() + "}");
            }
        } else if (feedBackLink.containsKey(messageId)) {
            clearFeedBack(messageId);
            feedBackLink.remove(messageId);
        }
    }

    public void clearFeedBack(){
        clearFeedBack(null);
    }

    public void clearFeedBack(Integer messageId){
        String body = messageId == null ? "{}" : "{\"" + messageId.toString() + "\" : \"\"}";
        api.setUpdatingMessage(body);
    }

    public Tile getTileWithTextInputActivated() {
        return tileWithTextInputActivated;
    }

    public void setTileWithTextInputActivated(Tile tile) {
        this.tileWithTextInputActivated = tile;
    }
}
