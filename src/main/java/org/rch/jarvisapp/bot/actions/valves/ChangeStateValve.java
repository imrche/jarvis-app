package org.rch.jarvisapp.bot.actions.valves;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.dataobject.DeviceCommandData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.smarthome.devices.Valve;

public class ChangeStateValve implements Action {
    protected static final int OPEN_VALVE = 1;
    protected static final int CLOSE_VALVE = 0;

    Valve valve;
    int direction;

    public String qwe;

    public ChangeStateValve(Valve valve, int direction) {
        this.valve = valve;
        this.direction = direction;
    }

    @Override
    public void run(Tile tile){
        AppContextHolder.getApi().setStatusValve(new DeviceCommandData().addDevice(valve.getId(),direction));
        tile.refresh();
    }

    @Override
    public int hashCode() {
        return valve.hashCode() + this.getClass().hashCode();
    }
}
