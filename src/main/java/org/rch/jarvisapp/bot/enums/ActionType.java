package org.rch.jarvisapp.bot.enums;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.dataobject.ActionData;
import org.rch.jarvisapp.bot.services.GateServices;
import org.rch.jarvisapp.bot.services.LightServices;
import org.rch.jarvisapp.bot.services.SensorService;
import org.rch.jarvisapp.bot.services.SettingService;
import org.rch.jarvisapp.bot.ui.Tile;

public enum ActionType {
    setLight    (""){
        @Override
        public void run(Tile tile, ActionData actionData) {
            getLightServices().setLight(tile,actionData);
        }
    },

    reverseLight(""){
        @Override
        public void run(Tile tile, ActionData actionData) {
            getLightServices().reverseLight(tile,actionData);
        }
    },

    places      (""){
        @Override
        public void run(Tile tile, ActionData actionData) {

        }
    },

    showGates   ("Гаражные ворота"){
        @Override
        public void run(Tile tile, ActionData actionData) {
            getGateServices().showGates(tile, actionData);
        }
    },
    showLights   ("Освещение"){
        @Override
        public void run(Tile tile, ActionData actionData) {
            getLightServices().showLights(tile, actionData);
        }
    },
    gates       (""){
        @Override
        public void run(Tile tile, ActionData actionData) {
            getGateServices().gates(tile,actionData);
        }
    },
    climate     (""){
        @Override
        public void run(Tile tile, ActionData actionData) {

        }
    },

    showBotSettings("Настройки бота"){
        @Override
        public void run(Tile tile, ActionData actionData) {
            getSettingService().showSettings(tile, actionData);
        }
    },

    setSettings("Установить настройку"){
        @Override
        public void run(Tile tile, ActionData actionData) {
            getSettingService().setSettingsValue(tile, actionData);
        }
    },

    showTemperature("Температура"){
        @Override
        public void run(Tile tile, ActionData actionData) {
            getSensorService().showTemperature(tile, actionData);
        }
    },

    showSensorsStatus("Статусы а помещении"){
        @Override
        public void run(Tile tile, ActionData actionData) {
            getSensorService().showSensorsStatus(tile, actionData);
        }
    },

    stub        (""){
        @Override
        public void run(Tile tile, ActionData actionData) {

        }
    }
;

    String description;

    ActionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public abstract void run(Tile tile, ActionData actionData);

    LightServices getLightServices(){
        return AppContextHolder.getContext().getBean(LightServices.class);
    }
    GateServices getGateServices(){
        return AppContextHolder.getContext().getBean(GateServices.class);
    }
    SettingService getSettingService(){
        return AppContextHolder.getContext().getBean(SettingService.class);
    }
    SensorService getSensorService(){
        return AppContextHolder.getContext().getBean(SensorService.class);
    }

}