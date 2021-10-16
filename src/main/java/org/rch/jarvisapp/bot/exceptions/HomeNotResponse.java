package org.rch.jarvisapp.bot.exceptions;

public class HomeNotResponse extends Exception{
    public HomeNotResponse(String message) {
        super(message);
    }

    public HomeNotResponse(String message, Throwable cause) {
        super(message, cause);
    }
}
