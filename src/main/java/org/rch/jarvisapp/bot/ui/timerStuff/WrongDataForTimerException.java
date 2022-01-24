package org.rch.jarvisapp.bot.ui.timerStuff;

import org.rch.jarvisapp.bot.exceptions.BotException;

public class WrongDataForTimerException extends BotException {
    public WrongDataForTimerException(String message) {
        super(message);
    }

    public WrongDataForTimerException(String message, Throwable cause) {
        super(message, cause);
    }
}
