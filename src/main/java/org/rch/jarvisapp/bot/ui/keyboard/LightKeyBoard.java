package org.rch.jarvisapp.bot.ui.keyboard;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.lights.SetLight;
import org.rch.jarvisapp.bot.actions.additional.ShowAdditionalPropertiesAction;
import org.rch.jarvisapp.bot.dataobject.SwitcherData;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.DeviceContainer;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.LightButton;
import org.rch.jarvisapp.smarthome.areas.Place;
import org.rch.jarvisapp.smarthome.devices.Device;
import org.rch.jarvisapp.smarthome.devices.Light;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LightKeyBoard extends KeyBoard implements DeviceContainer {
    private final List<Button> groupButtonRow = new ArrayList<>();
    private final List<Button> additionalPropertiesButtonRow = new ArrayList<>();

    public static final String ON = "ON";
    public static final String OFF = "OFF";
    public static final String ADD = "Дополнительно";

    public LightKeyBoard() {
        this(null);//todo
    }

    public LightKeyBoard(Place place){
        super();
        groupButtonRow.add(new Button("[Весь свет]", CommonCallBack.empty.name()));
        groupButtonRow.add(new Button(ON, CommonCallBack.empty.name()));
        groupButtonRow.add(new Button(OFF, CommonCallBack.empty.name()));
        additionalPropertiesButtonRow.add(new Button(ADD, new ShowAdditionalPropertiesAction(place)));
    }

    private Button getGroupButton(String state){
        for (Button button : groupButtonRow)
            if (state.equals(button.getCaption()))
                return button;

        return new Button(state, CommonCallBack.empty.name()); //todo warnings
    }

    private List<Button> prepareGroupButton(){
        SwitcherData cmd = new SwitcherData();

        for (Button button : getButtonsList())
            if (button instanceof LightButton) {
                Light light = ((LightButton) button).getLight();
                cmd.addSwitcher(light);
            }
//todo если лампа одна, то не делать общ кнопки
        if (cmd.getDeviceCount() > 1) {
            getGroupButton(ON).setCallBack(new SetLight().setData(cmd.setAllDevicesValue(true)));//todo починить клонированием
            getGroupButton(OFF).setCallBack(new SetLight().setData(cmd.setAllDevicesValue(false)));

            defineVisibilityGroupButton();
        } else
            hideGroupButton();

        return groupButtonRow.stream().filter(Button::isVisible).collect(Collectors.toList());
    }

    public void hideAllAdditionalBtn(){
        hideGroupButton();//todo сделать по уму
        for (Button btn : additionalPropertiesButtonRow)
            btn.setVisible(false);
    }


    private void defineVisibilityGroupButton(){
        Button btnOn = getGroupButton(ON);
        Button btnOff = getGroupButton(OFF);
        btnOn.setVisible(true);
        btnOff.setVisible(true);

        int countButton = 0;
        int countOn = 0;

        for (Button button : getButtonsList()) {
            if (button instanceof LightButton) {
                countButton++;

                if (((LightButton) button).getStatus())
                    countOn++;
            }
        }

        if (countOn == 0)
            btnOff.setVisible(false);
        if (countButton == countOn)
            btnOn.setVisible(false);
    }

    private void hideGroupButton(){
        for (Button btn : groupButtonRow)
            btn.setVisible(false);//todo если тут оказались, то значит группа в этой клавиатуре никогда не будет нужна
        //getGroupButton(ON).setVisible(false);
        //getGroupButton(OFF).setVisible(false);
    }

    @Override
    public void refresh() throws HomeApiWrongResponseData {
        SwitcherData dcdPattern = new SwitcherData();
        for (Button button : getButtonsList()) {
            if (button instanceof LightButton)
                dcdPattern.mergeDTO(((LightButton) button).getPatternCD());
        }

        if (!dcdPattern.isEmpty()) {//todo  продублировать в аналогах
            SwitcherData dcdResponse = AppContextHolder.getApi().getStatusLight(dcdPattern);
            //todo проверку на то что вернулись все запрошенные
            for (Button button : getButtonsList()) {
                if (button instanceof LightButton) {
                    LightButton btn = (LightButton) button;
                    btn.setStatus(dcdResponse.getDeviceValue(btn.getLight()));//todo обработать если статуса нет

                    btn.setCaption();
                }
            }
            defineVisibilityGroupButton();
        }
    }

    @Override
    public List<List<Button>> getInlineButtons(){
        //List<List<Button>> kb = new ArrayList<>(super.getButtons());
        List<List<Button>> kb = new ArrayList<>(super.getVisibleButtons());

        for (Button button : getButtonsList()) {
            if (button instanceof LightButton) {
                kb.add(new ArrayList<>(prepareGroupButton()));
                kb.add(new ArrayList<>(additionalPropertiesButtonRow));
                break;
            }
        }

        return kb;
    }

    @Override
    public List<Device> getDeviceList() {
        List<Device> list = new ArrayList<>();

        for (Button button : getButtonsList()) {
            if (button instanceof LightButton)
                list.add(((LightButton)button).getLight());
        }

        return list;
    }
}

        /*for (LightButton button : getButtons().stream()
                                                .flatMap(Collection::stream)
                                                .filter(LightButton.class::isInstance)
                                                .map(LightButton.class::cast)
                                                .collect(Collectors.toList())))*/