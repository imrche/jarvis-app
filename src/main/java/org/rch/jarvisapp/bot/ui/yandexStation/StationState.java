package org.rch.jarvisapp.bot.ui.yandexStation;

public enum StationState {
    IDLE("\uD83D\uDCA4"),
    BUSY("\uD83D\uDC40"),
    LISTENING("\uD83D\uDC42\uD83C\uDFFC"),
    SPEAKING("\uD83D\uDDE3"),
    PLAYING("\uD83D\uDD0A"),
    UNKNOWN("❓"),
    ;

    String symbol;

    StationState(String symbol){
        this.symbol = symbol;
    }

    public static String getStateSymbol(String state){
        try {
            return valueOf(state).symbol;
        } catch (IllegalArgumentException e){
            return UNKNOWN.symbol;
        }
    }
}
