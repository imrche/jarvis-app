package org.rch.jarvisapp.bot.ui.keyboard;

import org.rch.jarvisapp.bot.actions.gates.ClickGate;
import org.rch.jarvisapp.bot.actions.gates.CloseGate;
import org.rch.jarvisapp.bot.actions.gates.OpenGate;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.GateButton;
import org.rch.jarvisapp.smarthome.devices.Gate;
import org.rch.jarvisapp.smarthome.devices.status.GateStatus;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class GateKeyBoard extends KeyBoard {
    private final GateButton headerBtn;
    private final List<Button> fullControlButtonRow = new ArrayList<>();
    private final List<Button> simpleClickButtonRow = new ArrayList<>();

    private List<Button> currentCommandData = simpleClickButtonRow;

    public static final String ON = "Открыть";
    public static final String OFF = "Закрыть";
    public static final String CLICK = "Щелк";

    private final Gate gate;

    public GateKeyBoard(Gate gate) throws HomeApiWrongResponseData {
        super();
        fullControlButtonRow.add(new Button(ON, new OpenGate(gate)));
        fullControlButtonRow.add(new Button(OFF, new CloseGate(gate)));
        simpleClickButtonRow.add(new Button(CLICK, new ClickGate(gate)));

        this.gate = gate;
        headerBtn = new GateButton(gate);

        addButton(1, headerBtn);
    }

    private void changeControlButton(){
        currentCommandData = GateStatus.NA.equals(headerBtn.getStatus()) ? simpleClickButtonRow : fullControlButtonRow;
    }

    public Gate getGate() {
        return gate;
    }

    @Override
    public void refresh() throws HomeApiWrongResponseData {
        headerBtn.refresh();
        changeControlButton();
    }

    @Override
    public List<List<InlineKeyboardButton>> getKeyboard() {
        List<List<InlineKeyboardButton>> kb = super.getKeyboard();

        kb.add(new ArrayList<>(currentCommandData));

        setKeyboard(kb);
        return kb;
    }
}
