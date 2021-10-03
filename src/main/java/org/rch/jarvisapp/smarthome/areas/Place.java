package org.rch.jarvisapp.smarthome.areas;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.rch.jarvisapp.AppContextHolder;

@Data
public abstract class Place {
    @JsonProperty
    String code;

    @JsonProperty
    String name;

    Place parent;

    public Place(String code, String name, String parent) {
        this.parent = AppContextHolder.getSH().getPlaceByCode(parent);
        this.code = code;
        this.name = name;
    }
}
