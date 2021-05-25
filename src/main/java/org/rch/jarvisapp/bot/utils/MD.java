package org.rch.jarvisapp.bot.utils;

public class MD {
    public static String fixWidth(String str, Integer length){
        return "`" + String.format("%-" + length + "s", str) + "`";
    }

    public static String bold(String str){
        return "*" + str + "*";
    }
    public static String italic(String str){
        return "_" + str + "_";
    }
}
