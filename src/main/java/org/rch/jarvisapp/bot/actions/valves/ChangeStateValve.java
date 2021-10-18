package org.rch.jarvisapp.bot.actions.valves;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.dataobject.SwitcherData;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.smarthome.devices.Valve;

public class ChangeStateValve implements Action {
    Valve valve;
    Boolean direction; //true - open, false - close

    public ChangeStateValve(Valve valve, Boolean direction) {
        this.valve = valve;
        this.direction = direction;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        SwitcherData sd = new SwitcherData();
        sd.addSwitcher(valve,direction);

        AppContextHolder.getApi().setStatusValve(sd);
       // tile.refresh();
    }

    @Override
    public int hashCode() {
        return valve.hashCode() + this.getClass().hashCode();
    }
}
