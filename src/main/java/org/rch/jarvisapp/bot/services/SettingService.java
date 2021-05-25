package org.rch.jarvisapp.bot.services;

import org.rch.jarvisapp.bot.dataobject.ActionData;
import org.rch.jarvisapp.bot.enums.ActionType;
import org.rch.jarvisapp.bot.enums.Settings;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.SettingButton;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SettingService {
    public Map<String, Object> tmpSettings = new HashMap<>();

    public SettingService() {
        tmpSettings.put(Settings.liveProbeTiming.name(), "60 sec");
    }

    public void showSettings(Tile tile, ActionData actionData){
        try {
            Settings.valueOf(actionData.getBody()).getKB(tile, actionData);
        } catch (IllegalArgumentException e){
            KeyBoard kb = new KeyBoard();
            int row = 1;
            for (Settings settings : Settings.values())
                kb.addButton(row++, new SettingButton(settings, new ActionData(actionData.getAction(), "", settings.name())));

            tile.update()
                    .setCaption(actionData.getAction().getDescription())
                    .setKeyboard(kb);
        }
    }

    public void setSettingsValue(Tile tile, ActionData actionData){
        String[] data = actionData.getBody().split("_");
        tmpSettings.put(Settings.valueOf(data[0]).name(), data[1]);
        tile.stepBack();
    }
    
    public void generateNumKeyBoard(Tile tile, ActionData actionData){
        KeyBoard kb = new KeyBoard();
        String tune = actionData.getBody();

        Integer[] interval = {1,2,5,10,15,30};
        String[] unit = {"sec", "min", "hour"};

        int row = 1;
        for (String s : unit) {
            for (Integer i : interval)
                kb.addButton(row, new Button(i + " " + s, new ActionData(ActionType.setSettings, "", tune + "_" + i + s)));
            row++;
        }

        String caption;
        try {
            caption = Settings.valueOf(actionData.getBody()).getDescription();
        } catch (IllegalArgumentException e){
            caption = actionData.getBody();
        }

        tile.update()
                .setCaption(caption)
                .setKeyboard(kb);
    }
}
