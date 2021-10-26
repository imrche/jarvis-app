package org.rch.jarvisapp.smarthome.areas;

public class HomeRoot extends Place {
    public static HomeRoot home = new HomeRoot();

    private HomeRoot() {
        super("root", "Дом", null, null, null);
    }
}
