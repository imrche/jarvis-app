package org.rch.jarvisapp.bot.ui.keyboard;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.dataobject.ActionData;
import org.rch.jarvisapp.bot.dataobject.DeviceCommandData;
import org.rch.jarvisapp.bot.enums.ActionType;
import org.rch.jarvisapp.bot.enums.CommonCallBack;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.LightButton;
import org.rch.jarvisapp.smarthome.devices.Light;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LightKeyBoard extends KeyBoard{
    private List<Button> groupButton = new ArrayList<>();

    public static final String ON = "ON";
    public static final String OFF = "OFF";

    public LightKeyBoard() {
        super();
        groupButton.add(new Button("[Весь свет]", CommonCallBack.empty.name()));
        groupButton.add(new Button(ON, CommonCallBack.empty.name()));
        groupButton.add(new Button(OFF, CommonCallBack.empty.name()));
    }

    private Button getGroupButton(String state){
        for (Button button : groupButton)
            if (state.equals(button.getText()))
                return button;

        return new Button(state, CommonCallBack.empty.name()); //todo warnings
    }

    private List<Button> prepareGroupButton(){
        DeviceCommandData cmd = new DeviceCommandData();

        for (Button button : getButtonsList())
            if (button instanceof LightButton) {
                Light light = ((LightButton) button).getLight();
                cmd.addDevice(light.getRelayName(), light.getRelayPort());
            }

        getGroupButton(ON).setCallbackData(new ActionData(ActionType.setLight, cmd.setAllDevicesValue("1")).caching());
        getGroupButton(OFF).setCallbackData(new ActionData(ActionType.setLight,cmd.setAllDevicesValue("0")).caching());

        setVisibleGroupButton();

        return groupButton.stream().filter(Button::isVisible).collect(Collectors.toList());
    }

    private void setVisibleGroupButton(){
        Button btnOn = getGroupButton(ON);
        Button btnOff = getGroupButton(OFF);
        btnOn.setVisible(true);
        btnOff.setVisible(true);

        int countButton = 0;
        int countOn = 0;

        for (Button button : getButtonsList()) {
            countButton++;

            if (((LightButton) button).getState2())
                countOn++;
        }

        if (countOn == 0)
            btnOff.setVisible(false);
        if (countButton == countOn)
            btnOn.setVisible(false);
    }

    @Override
    public void refresh(){
        DeviceCommandData dcdPattern = new DeviceCommandData();
        for (Button button : getButtonsList()) {
            if (button instanceof LightButton)
                dcdPattern.addDevices(((LightButton) button).getPatternCD());
        }

        if (!dcdPattern.isEmpty()) {
            DeviceCommandData dcdResponse = AppContextHolder.getApi().getStatusLight(dcdPattern);
            for (Button button : getButtonsList()) {
                if (button instanceof LightButton) {
                    LightButton btn = (LightButton) button;
                    btn.setState2(dcdResponse.getDeviceBooleanValue(btn.getLight().getRelayName(), btn.getLight().getRelayPort()));
                    btn.setCaption();
                }
            }
            setVisibleGroupButton();
        }

    }

    @Override
    public List<List<InlineKeyboardButton>> getKeyboard() {
        List<List<InlineKeyboardButton>> kb = super.getKeyboard();

        for (Button button : getButtonsList())
            if (button instanceof LightButton) {
                kb.add(new ArrayList<>(prepareGroupButton()));
                break;
            }

        setKeyboard(kb);
        return kb;
    }
}

        /*for (LightButton button : getButtons().stream()
                                                .flatMap(Collection::stream)
                                                .filter(LightButton.class::isInstance)
                                                .map(LightButton.class::cast)
                                                .collect(Collectors.toList())))*/