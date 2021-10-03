package org.rch.jarvisapp.bot.exceptions;

public class DeviceStatusIsUnreachable extends BotException{

    public DeviceStatusIsUnreachable(String message) {
        super(message);
    }

    public DeviceStatusIsUnreachable(String message, Throwable cause) {
        super(message, cause);
    }
}
