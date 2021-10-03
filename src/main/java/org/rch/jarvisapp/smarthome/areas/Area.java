package org.rch.jarvisapp.smarthome.areas;

import com.fasterxml.jackson.annotation.JsonCreator;
import org.rch.jarvisapp.smarthome.init.JsonFields;
import static org.rch.jarvisapp.smarthome.init.enums.FieldTypes.*;

public class Area extends Place {
    @JsonCreator
    public Area(JsonFields fields) {
        super(
                fields.getAsString(CODE),
                fields.getAsString(NAME),
                fields.getAsString(PARENT)
        );
    }
}
