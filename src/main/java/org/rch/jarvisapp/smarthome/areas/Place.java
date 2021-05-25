package org.rch.jarvisapp.smarthome.areas;

import lombok.Data;

@Data
public abstract class Place {
    String code;
    String name;
    Place parent;

    public Place(String code, String name, Place parent) {
        this.code = code;
        this.name = name;
        this.parent = parent;
    }
}
