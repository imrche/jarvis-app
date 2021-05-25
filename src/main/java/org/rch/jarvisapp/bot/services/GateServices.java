package org.rch.jarvisapp.bot.services;

import org.json.JSONObject;
import org.rch.jarvisapp.bot.JarvisBot;
import org.rch.jarvisapp.bot.dataobject.ActionData;
import org.rch.jarvisapp.bot.enums.ActionType;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.keyboard.GateKeyBoard;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.devices.Gate;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class GateServices {

    SmartHome smartHome;
    JarvisBot bot;

    public GateServices(SmartHome smartHome, JarvisBot bot) {
        this.smartHome = smartHome;
        this.bot = bot;
    }

    public void showGates(Tile tile, ActionData actionData){
        ActionType type = ActionType.showGates;
        tile.update()
                .setCaption(type.getDescription().toUpperCase(Locale.ROOT))
                .clearKeyboard();
        for (Gate gate : smartHome.getGates())
            tile.addKeyboard(new GateKeyBoard(gate));
    }

    public void gates(Tile tile, ActionData actionData){
        JSONObject response = new JSONObject(smartHome.getApi().setGatesAction(actionData.getBody()));
         bot.getMessageBuilder().popupAsync(bot.getLastCallBackId(), response.getString("message"));
        //if (!str.getBoolean("accept"))
    }
}
