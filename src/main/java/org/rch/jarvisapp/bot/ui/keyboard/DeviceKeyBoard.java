package org.rch.jarvisapp.bot.ui.keyboard;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.additional.ShowAdditionalPropertiesAction;
import org.rch.jarvisapp.bot.dataobject.DeviceCommandData;
import org.rch.jarvisapp.bot.exceptions.DeviceStatusIsUnreachable;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.DeviceButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class DeviceKeyBoard extends KeyBoard{
    private List<Button> TimersButtonRow = new ArrayList<>();

    public static final String TIMERS = "Таймеры";

    public DeviceKeyBoard() {
        this("");//todo
    }

    public DeviceKeyBoard(String place){
        super();
        TimersButtonRow.add(new Button(TIMERS, new ShowAdditionalPropertiesAction(place)));
    }

    @Override
    public void refresh(){
        DeviceCommandData dcdPattern = new DeviceCommandData();
        for (Button button : getButtonsList()) {
            if (button instanceof DeviceButton)
                dcdPattern.addDevices(((DeviceButton) button).getPatternCD());
        }

        if (!dcdPattern.isEmpty()) {
            DeviceCommandData dcdResponse = AppContextHolder.getApi().getStatusDevice(dcdPattern);
            //todo проверку на то что вернулись все запрошенные
            for (Button button : getButtonsList())
                if (button instanceof DeviceButton) {
                    DeviceButton btn = (DeviceButton) button;
                    try {
                        btn.setState(dcdResponse.getDeviceBooleanValue(btn.getDevice().getId()));//todo обработать если статуса нет
                    } catch (DeviceStatusIsUnreachable e) {

                    }
                    btn.setCaption();
                }
        }

    }

    @Override
    public List<List<InlineKeyboardButton>> getKeyboard() {
        List<List<InlineKeyboardButton>> kb = super.getKeyboard();

        for (Button button : getButtonsList()) {
            if (button instanceof DeviceButton) {
                kb.add(new ArrayList<>(TimersButtonRow));
                break;
            }
        }

        setKeyboard(kb);
        return kb;
    }
}