package org.rch.jarvisapp.smarthome;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ScenarioBtn {
    @JsonProperty
    String code;
    @JsonProperty
    String name;
}
