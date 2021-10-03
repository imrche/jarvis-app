package org.rch.jarvisapp.bot.enums;

import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.actions.RunnableByPlace;
import org.rch.jarvisapp.bot.actions.StubAction;
import org.rch.jarvisapp.bot.actions.gates.ShowGates;
import org.rch.jarvisapp.bot.actions.lights.ShowLightAction;
import org.rch.jarvisapp.bot.actions.sensors.ShowSensorsStatus;
import org.rch.jarvisapp.bot.actions.sensors.type.*;
import org.rch.jarvisapp.bot.actions.settings.ShowSettings;
import org.rch.jarvisapp.smarthome.enums.SensorTypes;

public enum Menu {
    controlLights           (BotCommand.control,1,"Освещение", ShowLightAction.class,true),
    controlClimate          (BotCommand.control,2,BotCommand.climate.getDescription(), BotCommand.climate),

        climateGasBoiler    (BotCommand.climate,1,"Котел", Action.class),
        climateBreezier     (BotCommand.climate,2,"Бризеры", Action.class),
        climateRadiators    (BotCommand.climate,3,"Радиаторы", Action.class),

    controlBath             (BotCommand.control,3,"Ванные", BotCommand.bathroom),

        bathroomWaterSupply (BotCommand.bathroom,1,"Ввод воды", Action.class),
        bathroomHoods       (BotCommand.bathroom,2,"Вытяжки", Action.class),
        bathroomDryer       (BotCommand.bathroom,3,"Сушилки", Action.class),

    statusPlaceGroup        (BotCommand.status,1, ShowSensorsStatus.description,            ShowSensorsStatus.class, true),
    statusTemperature       (BotCommand.status,2, SensorTypes.temperature.getDescription(), ShowTemperature.class),
    statusHumidity          (BotCommand.status,2, SensorTypes.humidity.getDescription(),    ShowHumidity.class),
    statusIlluminance       (BotCommand.status,3, SensorTypes.illuminance.getDescription(), ShowIlluminance.class),
    statusSound             (BotCommand.status,3, SensorTypes.sound.getDescription(),       ShowSound.class),
    statusCO2               (BotCommand.status,4, SensorTypes.CO2.getDescription(),         ShowCO2.class),
    statusMotion            (BotCommand.status,4, SensorTypes.motion.getDescription(),      ShowMotion.class),

    warningsTemperature     (BotCommand.warnings,1,"Температура", Action.class),
    warningsHumidity        (BotCommand.warnings,2,"Влажность", Action.class),


    securityDoorLock        (BotCommand.security,1, "Входной замок", StubAction.class),
    securityGate            (BotCommand.security,2, ShowGates.description, ShowGates.class),
    securityMotionSensor    (BotCommand.security,3, "Датчики движения", StubAction.class),
    securityOpenWindows     (BotCommand.security,4, "Статус окон", StubAction.class),


    settingsBot             (BotCommand.settings,1, ShowSettings.description, ShowSettings.class),
        //settingsBotLiveProbe (BotCommand.botSettings,1, "Частота liveProbe", ActionType.showBotSettings),
    //settingsTmp2            (BotCommand.settings,2, "Настройка 2", BotCommand.security),

    accessAddUser           (BotCommand.access,1, "Добавить пользователя", Action.class)
    ;

    BotCommand botCommand;
    BotCommand produceCommand;
    Class<? extends Action> actionClass;
    int row;
    String description;
    boolean isPlaceGrouping;


    Menu(BotCommand botCommand, int row, String description, BotCommand produceCommand, boolean isPlaceGrouping) {
        this(botCommand, row, description);
        this.produceCommand = produceCommand;
        this.isPlaceGrouping = isPlaceGrouping;
    }

    <T extends Action> Menu(BotCommand botCommand, int row, String description, Class<T> action, boolean isPlaceGrouping) {
        this(botCommand, row, description);
        this.actionClass = action;
        this.isPlaceGrouping = isPlaceGrouping;
    }

    <T extends Action> Menu(BotCommand botCommand, int row, String description, Class<T> action) {
        this(botCommand,row,description,action,false);
    }

    Menu(BotCommand botCommand, int row, String description, BotCommand produceCommand) {
        this(botCommand, row, description);
        this.produceCommand = produceCommand;
    }

    Menu(BotCommand botCommand, int row, String description){
        this.botCommand = botCommand;
        this.row = row;
        this.description = description;
    }


    public BotCommand getBotCommand() {
        return botCommand;
    }
    public BotCommand getProduceCommand() {
        return produceCommand;
    }

    public int getRow() {
        return row;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPartOf(BotCommand botCommand){
        return this.botCommand == botCommand;
    }

    public boolean isPlaceGrouping(){
        return isPlaceGrouping;
    }

    public Action getActionInstance(){
        try {
            Action action = actionClass.newInstance();
            if (isPlaceGrouping && action instanceof RunnableByPlace)
                ((RunnableByPlace)action).setPlace("");

            return action;
        } catch (InstantiationException | IllegalAccessException e) {
            return null; //todo throw exception
        }
    }
    public boolean isProduceActionInstance(){
        return actionClass != null;
    }
}