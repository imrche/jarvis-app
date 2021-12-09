package org.rch.jarvisapp.bot.ui.yandexStation;

public enum PlayerSymbols {
    LIKE("\uD83D\uDC4D"),
    DISLIKE("\uD83D\uDC4E"),
    PREV_TRACK("⏮"),
    BACKWARD("⏪"),
    STOP("⏹"),
    PLAY("▶"),
    FORWARD("⏩"),
    NEXT_TRACK("⏭"),
    HANDLE("\uD83D\uDD38")
    ;

    private final String symbol;

    PlayerSymbols(String symbol){
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }
}
