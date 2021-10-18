package org.rch.jarvisapp.bot.ui.button.comparators;

import org.rch.jarvisapp.smarthome.areas.Place;

import java.util.Comparator;

public class PlaceComparator implements Comparator<Place> {

    @Override
    public int compare(Place o1, Place o2) {
        int rowCompare = o1.getRow() - o2.getRow();
        if (rowCompare == 0)
            return o1.getPriority() - o2.getPriority();
        return rowCompare;
    }
}