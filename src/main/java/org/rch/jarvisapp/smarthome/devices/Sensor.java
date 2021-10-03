package org.rch.jarvisapp.smarthome.devices;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.smarthome.enums.SensorTypes;
import org.rch.jarvisapp.smarthome.init.JsonFields;
import static org.rch.jarvisapp.smarthome.init.enums.FieldTypes.*;

@EqualsAndHashCode(callSuper = true)
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sensor extends Device{
    public static final String className = "sensor";

    SensorTypes sensorType;

    @JsonCreator
    public Sensor(JsonFields fields) {
        super(fields.getAsString(PLACE), fields.getAsInteger(ID));

        this.sensorType = SensorTypes.valueOf(fields.getAsString(PARAMETER));//todo обработать IllegalArgument
    }
    /*todo вынести единицы измерения в бэк*/
}
