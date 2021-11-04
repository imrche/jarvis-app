package org.rch.jarvisapp.bot.dataobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScenarioData {
    protected final ObjectMapper mapper = new ObjectMapper();
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Data
    static class DTO{
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String code;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        String command;
    }

    DTO dto = new DTO();

    public ScenarioData(String code, String command){
        dto.code = code;
        dto.command = command;
    }


    public String getData(){
        try {
            return mapper.writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            logger.error("Ошибка преобразования",e);//todo exception
        }
        return null;
    }

}
