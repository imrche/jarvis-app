package org.rch.jarvisapp.bot.enums;

public enum StatusVisual {
    LIGHT_SWITCHER("\uD83C\uDF15","\uD83C\uDF11"),
    CONNECT_MANAGER("\uD83D\uDFE2","\uD83D\uDD34");

    private final String on;
    private final String off;
    private static final String unknown = "[❓]";

    StatusVisual(String on, String off){
        this.on = on;
        this.off = off;
    }

    public String get(Boolean status){
        if (status == null) return unknown;
        return status ? on : off;
    }
}