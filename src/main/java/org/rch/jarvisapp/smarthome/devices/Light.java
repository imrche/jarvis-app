package org.rch.jarvisapp.smarthome.devices;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.smarthome.init.JsonFields;

import static org.rch.jarvisapp.smarthome.init.enums.FieldTypes.*;

@Data
@FieldDefaults(level=AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class Light extends Device implements TimerSupport {

    @JsonProperty
    String name;

    @JsonProperty
    Integer row;

    @JsonProperty
    Integer priority;

    @JsonCreator
    public Light(JsonFields fields){
        super(fields.getAsString(PLACE), fields.getAsInteger(ID));

        name = fields.getAsString(NAME);
        row = fields.getAsInteger(ROW);
        priority = fields.getAsInteger(PRIORITY);
    }
}
