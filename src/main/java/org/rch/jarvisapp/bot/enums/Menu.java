package org.rch.jarvisapp.bot.enums;


public enum Menu {

   // controlLights           (BotCommand.control,1,"Освещение", ActionType.places),
    controlLights           (BotCommand.control,1,"Освещение", ActionType.showLights,true),
    controlClimate          (BotCommand.control,2,BotCommand.climate.getDescription(), BotCommand.climate),

        climateGasBoiler    (BotCommand.climate,1,"Котел", ActionType.climate),
        climateBreezier     (BotCommand.climate,2,"Бризеры", ActionType.climate),
        climateRadiators    (BotCommand.climate,3,"Радиаторы", ActionType.climate),

    controlBath             (BotCommand.control,3,"Ванные", BotCommand.bathroom),

        bathroomWaterSupply (BotCommand.bathroom,1,"Ввод воды", ActionType.stub),
        bathroomHoods       (BotCommand.bathroom,2,"Вытяжки", ActionType.stub),
        bathroomDryer       (BotCommand.bathroom,3,"Сушилки", ActionType.stub),

    statusPlaceGroup        (BotCommand.status,1, ActionType.showSensorsStatus.getDescription(), ActionType.showSensorsStatus, true),
    statusTemperature       (BotCommand.status,2, ActionType.showTemperature.getDescription(), ActionType.showTemperature),
    statusHumidity          (BotCommand.status,2, ActionType.showHumidity.getDescription(), ActionType.showHumidity),
    statusIlluminance       (BotCommand.status,3, ActionType.showIlluminance.getDescription(), ActionType.showIlluminance),
    statusSound             (BotCommand.status,3, ActionType.showSound.getDescription(), ActionType.showSound),
    statusCO2               (BotCommand.status,4, ActionType.showCO2.getDescription(), ActionType.showCO2),
    statusMotion            (BotCommand.status,4, ActionType.showMotion.getDescription(), ActionType.showMotion),

    warningsTemperature     (BotCommand.warnings,1,"Температура", ActionType.stub),
    warningsHumidity        (BotCommand.warnings,2,"Влажность", ActionType.stub),


    securityDoorLock        (BotCommand.security,1, "Входной замок", ActionType.stub),
    securityGate            (BotCommand.security,2, ActionType.showGates.getDescription(), ActionType.showGates),
    securityMotionSensor    (BotCommand.security,3, "Датчики движения", ActionType.stub),
    securityOpenWindows     (BotCommand.security,4, "Статус окон", ActionType.stub),


    settingsBot             (BotCommand.settings,1, ActionType.showBotSettings.getDescription(), ActionType.showBotSettings),
        //settingsBotLiveProbe (BotCommand.botSettings,1, "Частота liveProbe", ActionType.showBotSettings),
    settingsTmp2            (BotCommand.settings,2, "Настройка 2", BotCommand.security),

    accessAddUser           (BotCommand.access,1, "Добавить пользователя", ActionType.stub)

    ;

    BotCommand botCommand;
    int row;
    String description;
    ActionType actionType;
    BotCommand produceCommand;
    boolean isPlaceGrouping;


    Menu(BotCommand botCommand, int row, String description, BotCommand produceCommand, boolean isPlaceGrouping) {
        this(botCommand, row, description);
        this.produceCommand = produceCommand;
        this.isPlaceGrouping = isPlaceGrouping;
    }

    Menu(BotCommand botCommand, int row, String description, ActionType actionType, boolean isPlaceGrouping) {
        this(botCommand, row, description);
        this.actionType = actionType;
        this.isPlaceGrouping = isPlaceGrouping;
    }

    Menu(BotCommand botCommand, int row, String description, ActionType actionType) {
        this(botCommand, row, description);
        this.actionType = actionType;
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

    public ActionType getActionType() {
        return actionType;
    }

    public boolean isPartOf(BotCommand botCommand){
        return this.botCommand == botCommand;
    }

    public boolean isPlaceGrouping(){
        return isPlaceGrouping;
    }
}