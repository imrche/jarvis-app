package org.rch.jarvisapp.bot.ui.button.comparators;

import org.rch.jarvisapp.smarthome.devices.Light;

import java.util.Comparator;

public class LightComparator implements Comparator<Light> {

    @Override
    public int compare(Light o1, Light o2) {
        int rowCompare = o1.getRow() - o2.getRow();
        if (rowCompare == 0)
            return o1.getPriority() - o2.getPriority();
        return rowCompare;
    }
}