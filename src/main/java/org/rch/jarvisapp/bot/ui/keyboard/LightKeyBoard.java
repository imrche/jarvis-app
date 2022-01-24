package org.rch.jarvisapp.bot.ui.keyboard;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.SimpleRunAction;
import org.rch.jarvisapp.bot.dataobject.SwitcherData;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.DeviceContainer;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.LightButton;
import org.rch.jarvisapp.smarthome.api.Api;
import org.rch.jarvisapp.smarthome.devices.Device;

import static org.rch.jarvisapp.bot.ui.button.LightButton.*;

import java.util.*;
import java.util.stream.Collectors;

public class LightKeyBoard extends KeyBoard implements DeviceContainer {
    private static final Api api = AppContextHolder.getApi();
    GroupSwitchButtons groupSwitchButtons;
    private final List<Button> switcherButtonRow = new ArrayList<>();
    List<LightButton> lightButtonList = new ArrayList<>();
    SwitcherData cmdForRequest = new SwitcherData();

    private Mode currentMode = Mode.SWITCHER;
    private final Button switcherButton = new Button("+", new SimpleRunAction(this::switchMode), this::getSwitcherButtonName);

    private static class GroupSwitchButtons{
        private enum TypeButtons{
            HEADER("Весь свет :"),
            ON("ВКЛ"),
            OFF("ВЫКЛ");

            private final String description;

            TypeButtons(String description){
                this.description = description;
            }
        }

        private Mode currentMode;

        private final Map<TypeButtons,Button> groupButtonRow = new TreeMap<>();
        private final List<LightButton> lightButtons;

        SwitcherData cmd = new SwitcherData();

        GroupSwitchButtons(List<LightButton> buttons){
            lightButtons = buttons;

            for (TypeButtons typeButtons : TypeButtons.values())
                groupButtonRow.put(typeButtons, new Button(typeButtons.description, new SimpleRunAction(() -> run(typeButtons), SimpleRunAction.Type.UPDATE)));

            for (LightButton button : lightButtons)
                cmd.addSwitcher(button.getLight());
        }

        void defineVisibility(){
            long turnedOnCnt = lightButtons.stream().filter(LightButton::getStatus).count();
            groupButtonRow.get(TypeButtons.ON).setVisible(turnedOnCnt != lightButtons.size());
            groupButtonRow.get(TypeButtons.OFF).setVisible(turnedOnCnt != 0);
        }

        private void run(TypeButtons typeButtons){
            switch(typeButtons){
                case ON: cmd.setAllDevicesValue(true); break;
                case OFF: cmd.setAllDevicesValue(false); break;
                default: return;
            }

            switch (currentMode){
                case SWITCHER:
                    api.setStatusLight(cmd); break;
                case CONNECT_MANAGE:
                    api.setStatusSwitchManager(cmd); break;
                case TIMER:
                    System.out.println("Таймеры");; break;
            }
        }

        void hide(){
            for (Button btn : groupButtonRow.values())
                btn.setVisible(false);
        }

        void show(){
            groupButtonRow.get(TypeButtons.HEADER).setVisible(true);
            defineVisibility();
        }

        List<Button> get(){
            if (currentMode == Mode.LIGHT_PARAMS)
                return new ArrayList<>();

            return groupButtonRow.values().stream().filter(Button::isVisible).collect(Collectors.toList());
        }
    }

    public LightKeyBoard(){
        super();
        switcherButtonRow.add(switcherButton);
    }

    private void switchMode(){
        currentMode = currentMode.getNext();
        groupSwitchButtons.currentMode = currentMode;

        for (Button button : getButtonsList()) {
            if (button instanceof LightButton)
                ((LightButton) button).setMode(currentMode);
        }
    }

    private String getSwitcherButtonName(){
        return "РЕЖИМ: [" + currentMode.getName() + "]";
    }

    public void hideAllAdditionalBtn(){
        groupSwitchButtons.hide();
    }

    @Override
    public LightKeyBoard build(){
        getButtonsList().stream().filter(LightButton.class::isInstance).forEach(o -> lightButtonList.add((LightButton) o));

        groupSwitchButtons = new GroupSwitchButtons(lightButtonList);
        groupSwitchButtons.currentMode = currentMode;

        for (LightButton button : lightButtonList)
            cmdForRequest.addSwitcher(button.getLight());

        return this;
    }

    @Override
    public void refresh() throws HomeApiWrongResponseData {
        switcherButton.refresh();
        if (currentMode == Mode.TIMER || currentMode == Mode.LIGHT_PARAMS || cmdForRequest.isEmpty())
            return;

        SwitcherData cmdResponse;
        switch(currentMode){
            case SWITCHER:
                cmdResponse = AppContextHolder.getApi().getStatusLight(cmdForRequest); break;
            case CONNECT_MANAGE:
                cmdResponse = AppContextHolder.getApi().getStatusSwitchManager(cmdForRequest); break;
            default: cmdResponse = new SwitcherData();
        }

        for (LightButton lb : lightButtonList)
            lb.setStatus(cmdResponse.getDeviceValue(lb.getLight()));//todo обработать если статуса нет

        groupSwitchButtons.defineVisibility();
    }

    @Override
    public List<List<Button>> getVisibleButtons(){
        List<List<Button>> kb = new ArrayList<>(super.getVisibleButtons());

        for (Button button : getButtonsList())
            if (button instanceof LightButton) {
                kb.add(new ArrayList<>(groupSwitchButtons.get()));
                kb.add(new ArrayList<>(switcherButtonRow));
                break;
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