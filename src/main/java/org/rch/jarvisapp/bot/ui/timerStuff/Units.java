package org.rch.jarvisapp.bot.ui.timerStuff;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public enum Units {
    SECONDS("секунд",1,     Arrays.asList("5","10","15","20","30","45","50")),
    MINUTES("минут", 60,    Arrays.asList("5","10","15","20","30","45","50")),
    HOUR(   "часов", 60*24, Arrays.asList("1","2","3","5","8","10","12","15","20","24"));

    String description;
    Integer perSeconds;
    List<String> options;

    Units(String description, Integer perSeconds, List<String> options){
        this.description = description;
        this.perSeconds = perSeconds;
        this.options = options;
    }

    public String getDescription() {
        return description.toUpperCase(Locale.ROOT);
    }

/*    public Integer getPerSeconds() {
        return perSeconds;
    }*/

    public List<String> getOptions() {
        return options;
    }

    public static Units valueOfDescription(String description){
        for (Units unit : values()){
            if (unit.getDescription().equals(description))
                return unit;
        }
        throw new IllegalArgumentException("Не существует единицы времени " + description);
    }

    public static List<String> getDescriptionList(){
        return Arrays.stream(values()).map(Units::getDescription).collect(Collectors.toList());
    }
}