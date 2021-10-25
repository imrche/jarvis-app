package org.rch.jarvisapp.bot.ui.keyboard;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.additional.ShowAdditionalPropertiesAction;
import org.rch.jarvisapp.bot.dataobject.SwitcherData;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.DeviceButton;
import org.rch.jarvisapp.smarthome.areas.Place;

import java.util.ArrayList;
import java.util.List;

public class DeviceKeyBoard extends KeyBoard{
    private final List<Button> TimersButtonRow = new ArrayList<>();

    public static final String TIMERS = "Таймеры";

    public DeviceKeyBoard() {
        this(null);//todo
    }

    public DeviceKeyBoard(Place place){
        super();
        TimersButtonRow.add(new Button(TIMERS, new ShowAdditionalPropertiesAction(place)));
    }

    @Override
    public void refresh() throws HomeApiWrongResponseData {
        SwitcherData dcdPattern = new SwitcherData();
        for (Button button : getButtonsList()) {
            if (button instanceof DeviceButton)
                dcdPattern.mergeDTO(((DeviceButton) button).getPatternCD());
        }

        if (!dcdPattern.isEmpty()) {
            SwitcherData dcdResponse = AppContextHolder.getApi().getStatusDevice(dcdPattern);
            //todo проверку на то что вернулись все запрошенные
            for (Button button : getButtonsList())
                if (button instanceof DeviceButton) {
                    DeviceButton btn = (DeviceButton) button;
                    btn.setState(dcdResponse.getDeviceValue(btn.getDevice()));//todo обработать если статуса нет
                    btn.setCaption();
                }
        }

    }

    @Override
    public List<List<Button>> getInlineButtons(){
        List<List<Button>> kb = new ArrayList<>(super.getButtons());

        for (Button button : getButtonsList())
            if (button instanceof DeviceButton) {
                kb.add(new ArrayList<>(TimersButtonRow));
                break;
            }

        return kb;
    }
}