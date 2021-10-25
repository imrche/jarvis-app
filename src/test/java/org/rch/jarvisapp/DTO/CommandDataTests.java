package org.rch.jarvisapp.DTO;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.rch.jarvisapp.bot.dataobject.DeviceCommandData;
import org.rch.jarvisapp.bot.dataobject.GateData;
import org.rch.jarvisapp.bot.dataobject.LightCommandData;
import org.rch.jarvisapp.bot.dataobject.SwitcherData;
import org.rch.jarvisapp.smarthome.devices.Gate;

//@SpringBootTest
public class CommandDataTests {

    @Test
    void start(){
        System.out.println("123");
    }

    @Test
    void DeviceCommand(){
        DeviceCommandData cd = new DeviceCommandData();
        //cd.addDevice(1);
        //cd.setDeviceValue(1,0);
        cd.setTimer(1, DeviceCommandData.TimerType.TIMER,10);
        System.out.println(cd.toString());
    }

    @Test
    void LightCommand(){
        LightCommandData cd = new LightCommandData();
        //cd.addDevice(1);
        //cd.setDeviceValue(1,0);
        cd.setTimer(1, DeviceCommandData.TimerType.TIMER,10);

        LightCommandData.SpecPar par1 = new LightCommandData.SpecPar().addBrightness(100).addHue(null);
        cd.setSpecPar(1, par1);

        //cd.setSpecPar(1,100);
        //cd.setSpecPar(1,100, -1, 30);
        System.out.println(cd.toString());
    }

    @Test
    void NewDataSet(){
        SwitcherData d = new SwitcherData();

        //Device device = new TestDevice(1);
        //d.addSwitcher(device);


       // d.addSwitcher(device);


        System.out.println(d.getData());
    }

    @Test
    void NewDataGet(){
        String resp = "[{\"id\":1,\"value\":null},{\"id\":null,\"value\":null},{\"value\":0}]";
        try {
            SwitcherData data = new SwitcherData(resp);
            System.out.println(data.getData());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("поломка");
        }
    }


    @Test
    void NewDataGetGate(){
        String resp = "[{\"id\":1,\"status\":null},{\"id\":null,\"status\":\"open\"}]";
        try {
            GateData data = new GateData(resp);
            System.out.println(data.getData());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            System.out.println("поломка");
        }
    }
    @Test
    void NewDataSetGate(){
        GateData d = new GateData();

        Gate device = new Gate();
        device.setId(1);
        d.addGate(device, GateData.ActionValue.open);

        System.out.println(d.getData());
    }
}
