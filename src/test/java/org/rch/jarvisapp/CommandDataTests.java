package org.rch.jarvisapp;

import org.junit.jupiter.api.Test;
import org.rch.jarvisapp.bot.dataobject.DeviceCommandData;
import org.rch.jarvisapp.bot.dataobject.LightCommandData;

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

}
