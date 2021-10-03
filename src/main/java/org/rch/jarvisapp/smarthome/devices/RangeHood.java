package org.rch.jarvisapp.smarthome.devices;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.rch.jarvisapp.smarthome.areas.Place;
import org.rch.jarvisapp.smarthome.init.JsonFields;
import static org.rch.jarvisapp.smarthome.init.enums.FieldTypes.*;

public class RangeHood extends Device{
/*    public RangeHood(IntegerString placement, String name, Integer row, Integer priority) {
        super(placement, name, row, priority);
    }*/

    @JsonProperty
    String name;

    @JsonProperty
    Integer row;

    @JsonProperty
    Integer priority;

    @JsonCreator
    public RangeHood(JsonFields fields){
        super(fields.getAsString(PLACE), fields.getAsInteger(ID));
        name = fields.getAsString(NAME);
        row = fields.getAsInteger(ROW);
        priority = fields.getAsInteger(PRIORITY);
    }
}
