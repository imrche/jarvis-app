package org.rch.jarvisapp.bot.enums;

public enum CommonCallBack {
    empty,
    stepBackTile;

    public boolean is(String str){
        return this.name().equals(str);
    }
}