package org.rch.jarvisapp.bot.enums;

import org.rch.jarvisapp.bot.ui.Tile;

public enum SettingsList {
    liveProbeTiming("Частота LiveProbe"){
        @Override
        public void getKB(Tile tile) {
            //getSS().generateNumKeyBoard(tile, actionData);
        }
    };

    String description;

    SettingsList(String description) {
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

    public abstract void getKB(Tile tile);
}
