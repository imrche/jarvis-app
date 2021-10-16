package org.rch.jarvisapp.bot.exceptions;

public class HomeApiWrongResponseData extends Exception{
    public HomeApiWrongResponseData(String message) {
        super(message);
    }

    public HomeApiWrongResponseData(String message, Throwable cause) {
        super("Некорректный ответ: " + message, cause);
    }
}
