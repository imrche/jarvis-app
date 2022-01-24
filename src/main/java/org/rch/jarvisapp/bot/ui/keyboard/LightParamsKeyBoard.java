package org.rch.jarvisapp.bot.ui.keyboard;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.dataobject.LightData;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.smarthome.devices.Light;

public class LightParamsKeyBoard extends KeyBoard {
    private final Light light;
    LightData dto = new LightData();

    Integer currentBrightness;
    Integer currentTemperature;
    Integer currentHue;


    public LightParamsKeyBoard(Light light) {
        int i = 1;
        this.light = light;
        dto.addLight(light);
        addButton(i, new Button("----------[ЯРКОСТЬ]----------", CommonCallBack.empty.name()));

        i++;
        addButton(i, new Button("\uD83C\uDF11", s -> setBrightness(5)));
        addButton(i, new Button("\uD83C\uDF12", s -> setBrightness(30)));
        addButton(i, new Button("\uD83C\uDF13", s -> setBrightness(50)));
        addButton(i, new Button("\uD83C\uDF14", s -> setBrightness(80)));
        addButton(i, new Button("\uD83C\uDF15", s -> setBrightness(100)));


        i++;
        addButton(i, new Button("➖", s -> setBrightness(currentBrightness - 5)));
        addButton(i, new Button("0", CommonCallBack.empty.name(), this::getBrightness));
        addButton(i, new Button("➕", s -> setBrightness(currentBrightness + 5)));


        i++;
        addButton(i, new Button("--------[ТЕМПЕРАТУРА]--------", CommonCallBack.empty.name()));

        i++;
        addButton(i, new Button("\uD83D\uDFE7", s -> setTemperature(0)));
        addButton(i, new Button("⬜️", s -> setTemperature(4200)));
        addButton(i, new Button("\uD83D\uDFE6", s -> setTemperature(8000)));

        i++;
        addButton(i, new Button("➖", s -> setTemperature(currentTemperature - 100)));
        addButton(i, new Button("", CommonCallBack.empty.name(), this::getTemperature));
        addButton(i, new Button("➕", s -> setTemperature(currentTemperature + 100)));


        i++;
        addButton(i, new Button("------------[ЦВЕТ]------------", CommonCallBack.empty.name()));

        i++;
        addButton(i, new Button("\uD83D\uDD34", s -> setHue(0)));//red
        addButton(i, new Button("\uD83D\uDFE0", s -> setHue(20)));//orange
        addButton(i, new Button("\uD83D\uDFE1", s -> setHue(30)));//yellow
        addButton(i, new Button("\uD83D\uDFE2", s -> setHue(80)));//green
        addButton(i, new Button("\uD83D\uDD35", s -> setHue(230)));//deep blue
        addButton(i, new Button("\uD83D\uDFE3", s -> setHue(285)));//purple
        //addButton(i, new Button("", CommonCallBack.empty.name()));//pink
        //addButton(i, new Button("", CommonCallBack.empty.name()));//blue


        i++;
        addButton(i, new Button("➖", s -> setHue(currentHue - 10)));
        addButton(i, new Button("", CommonCallBack.empty.name(), this::getHue));
        addButton(i, new Button("➕", s -> setHue(currentHue + 10)));

        i++;
        addButton(i, new Button("----------[ШАБЛОНЫ]----------", CommonCallBack.empty.name()));
    }

    private void sendDTO(LightData dto){
        System.out.println(dto.getData());
        AppContextHolder.getApi().setParametersLight(dto);
    }

    private void setBrightness(Integer value){
        sendDTO(dto.getClone().setBrightness(light, value));
        currentBrightness = value;
    }

    private void setTemperature(Integer value){
        sendDTO(dto.getClone().setTemperature(light, value));
        currentTemperature = value;
    }

    private void setHue(Integer value){
        if (value > 360)
            value = 0;

        sendDTO(dto.getClone().setHue(light, value));
        currentHue = value;
    }

    private String getBrightness(){
        return currentBrightness + "%";
    }
    private String getHue(){
        return String.valueOf(currentHue);
    }

    private String getTemperature(){
        return String.valueOf(currentTemperature);
    }

}