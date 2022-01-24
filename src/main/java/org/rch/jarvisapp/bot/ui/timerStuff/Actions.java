package org.rch.jarvisapp.bot.ui.timerStuff;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public enum Actions {
    ON(true,"включить"),
    OFF(false,"выключить");

    String description;
    Boolean value;

    Actions(Boolean value, String description){
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description.toUpperCase(Locale.ROOT);
    }

    public Boolean getValue() {
        return value;
    }

    public static Actions valueOfDescription(String description){
        for (Actions action : values()){
            if (action.getDescription().equals(description))
                return action;
        }
        throw new IllegalArgumentException("Не существует действия " + description);
    }

    public static List<String> getDescriptionList(){
        return Arrays.stream(values()).map(Actions::getDescription).collect(Collectors.toList());
    }

    public static Actions getForValue(Boolean value){
        for (Actions action : Actions.values()){
            if (action.getValue().equals(value))
                return action;
        }
        return null; //todo null not cool
    }
}
