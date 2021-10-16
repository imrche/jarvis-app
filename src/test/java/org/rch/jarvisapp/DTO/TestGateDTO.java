package org.rch.jarvisapp.DTO;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.jupiter.api.Test;
import org.rch.jarvisapp.bot.dataobject.GateData;

import static org.junit.jupiter.api.Assertions.*;

public class TestGateDTO {
    @Test
    void TestJSON2Gate2JSON(){
        String resp = "[{\"id\":1,\"status\":\"open\"}]";
        String result = "[{\"id\":1,\"status\":\"open\"}]";

        try {
            GateData data = new GateData(resp);
            assertEquals(data.getData(), result, "Результат не равен ожидаемому");
        } catch (JsonProcessingException ignored) {}
    }

    @Test
    void TestReceivingJSONWithUnknownFields(){
        String resp = "[{\"id\":1,\"someField\":\"open\"}]";
        String result = "[{\"id\":1}]";
        GateData data = null;
        try {
            data = new GateData(resp);
            
        } catch (JsonProcessingException e) {
            fail(e.getMessage());
        }
        assertNotNull(data, "Не удалось создать DTO");
        assertEquals(data.getData(), result, "Результат не равен ожидаемому");
    }

    @Test
    void TestIdIsNull(){
/*        String resp = "[{\"id\":null,\"status\":\"open\"}]";
        String result = "[{}]";
        try {
            GateData2 data = new GateData2(resp);

            assert result.equals(data.getData());

            System.out.println(data.getData());
        } catch (JsonProcessingException e) {}*/
    }

/*    @Test
    void NewDataSetGate(){
        GateData2 d = new GateData2();

        Gate device = new Gate();
        device.setId(1);
        d.addGate(device, GateData2.ActionValue.open);

        System.out.println(d.getData());
    }*/
}
