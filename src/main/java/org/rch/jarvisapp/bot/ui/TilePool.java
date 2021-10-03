package org.rch.jarvisapp.bot.ui;

import org.rch.jarvisapp.bot.dataobject.DeviceCommandData;
import org.rch.jarvisapp.bot.enums.BotCommand;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.LightButton;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.keyboard.MenuKeyBoard;
import org.rch.jarvisapp.smarthome.api.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.*;

@Component
public class TilePool {
    @Autowired
    Api api;

    public List<Tile> tileList = new ArrayList<>();
    Map<Integer, DeviceCommandData> feedBackLink = new HashMap<>();

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

    public void setFeedBack(Integer messageId){
        Tile tile = getTileWith(messageId);
        if (tile == null)
            return;

        DeviceCommandData dcdPattern = new DeviceCommandData();
        for (KeyBoard kb : tile.content){
            for (Button button : kb.getButtonsList()){
                if (button instanceof LightButton)
                    dcdPattern.addDevices(((LightButton) button).getPatternCD());
            }
        }

        if (!dcdPattern.isEmpty()) {
            if (!feedBackLink.containsKey(messageId) || !feedBackLink.get(messageId).equals(dcdPattern)) {
                feedBackLink.put(messageId, dcdPattern);
                api.setUpdatingMessage("{\"" + messageId.toString() + "\" : " + dcdPattern + "}");
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
}
