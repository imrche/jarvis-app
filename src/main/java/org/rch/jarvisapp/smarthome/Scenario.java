package org.rch.jarvisapp.smarthome;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Scenario {

    @JsonProperty
    String code;

    @JsonProperty
    String name;

    @JsonProperty
    List<ScenarioBtn> buttons = new ArrayList<>();
}
