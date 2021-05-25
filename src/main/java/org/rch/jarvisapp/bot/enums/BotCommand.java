package org.rch.jarvisapp.bot.enums;

public enum BotCommand {

    favorite    ("Избранное"),
    control     ("Панель управления"),
    status      ("Состояния"),
    warnings    ("Предупреждения"),
    scenario    ("Сценарии"),
    security    ("Охрана"),
    settings    ("Настройки"),
    access      ("Доступы"),

    //невидимые через основное меню
    bathroom    ("Ванные"),
    botSettings ("Настройки бота"),
    climate     ("Климат");

    String description;
    BotCommand(String description){
        this.description = description;
    }

    public String getDescription(){
        return description;
    }

}
