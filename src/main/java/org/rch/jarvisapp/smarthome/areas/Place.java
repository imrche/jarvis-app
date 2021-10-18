package org.rch.jarvisapp.smarthome.areas;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.rch.jarvisapp.AppContextHolder;

@Data
public abstract class Place {
    private static final Integer defaultSortValue = 100;

    @JsonProperty
    String code;

    @JsonProperty
    String name;

    @JsonProperty
    Integer row;

    @JsonProperty
    Integer priority;

    Place parent;

    public Place(String code, String name, String parent, Integer row, Integer priority) {
        this.parent = AppContextHolder.getSH().getPlaceByCode(parent);
        this.code = code;
        this.name = name;
        this.row = row != null ? row : defaultSortValue;
        this.priority = priority != null ? priority : defaultSortValue;
    }
}
