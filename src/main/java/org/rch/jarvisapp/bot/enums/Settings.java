package org.rch.jarvisapp.bot.enums;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.dataobject.ActionData;
import org.rch.jarvisapp.bot.services.SettingService;
import org.rch.jarvisapp.bot.ui.Tile;

public enum Settings {
    liveProbeTiming("Частота LiveProbe"){
        @Override
        public void getKB(Tile tile, ActionData actionData) {
            getSS().generateNumKeyBoard(tile, actionData);
        }
    };

    String description;

    Settings(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public abstract void getKB(Tile tile, ActionData actionData);

    SettingService getSS(){
        return AppContextHolder.getSettingService();
    }
}
