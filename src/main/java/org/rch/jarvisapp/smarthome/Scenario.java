package org.rch.jarvisapp.smarthome;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.bot.utils.MD;

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


    private String getButtonDescription(String code){
        for (ScenarioBtn btn : buttons){
            if (btn.getCode().equals(code)){
                return btn.getName();
            }
        }
        return "";
    }

    public String visualizeStatus(String statusCode, Boolean detailed){
        if ("off".equals(statusCode))
            return "";

        if (!detailed)
            return " ⏩";

        String status = getButtonDescription(statusCode);

        return " \\[" + MD.bold("вкл. ") + MD.fixWidth(status, status.length()) + "]";
    }

}
