package org.rch.jarvisapp.bot.dataobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.rch.jarvisapp.smarthome.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ScenariosData{
    protected final ObjectMapper mapper = new ObjectMapper();
    Logger logger = LoggerFactory.getLogger(this.getClass());
    List<DTO> data = new ArrayList<>();

    @Data
    static class DTO{
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String code;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String status;

        public DTO(){}

        public DTO(String code){
            this.code = code;
        }
    }

    public ScenariosData(String json) throws JsonProcessingException {
        JavaType type = mapper.getTypeFactory().constructCollectionType(List.class, DTO.class);
        data = mapper.readValue(json, type);
    }

    public ScenariosData(){}

    public ScenariosData addScenario(Scenario scenario){
        data.add(new DTO(scenario.getCode()));

        return this;
    }

    public ScenariosData addScenario(List<Scenario> scenarioList){
        for (Scenario scenario : scenarioList)
            addScenario(scenario);

        return this;
    }

    public String getStatus(Scenario scenario){
        for(DTO obj : data){
            if (obj.getCode().equals(scenario.getCode()))
                return obj.getStatus();
        }
        return null;
    }

    public String getData(){
        try {
            return mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            logger.error("Ошибка преобразования",e);//todo exception
        }
        return null;
    }
}