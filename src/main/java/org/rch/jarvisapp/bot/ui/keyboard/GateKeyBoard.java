package org.rch.jarvisapp.bot.ui.keyboard;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.actions.gates.ClickGate;
import org.rch.jarvisapp.bot.actions.gates.CloseGate;
import org.rch.jarvisapp.bot.actions.gates.OpenGate;
import org.rch.jarvisapp.bot.dataobject.GateData;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.GateHeaderButton;
import org.rch.jarvisapp.smarthome.devices.Gate;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class GateKeyBoard extends KeyBoard {

    GateHeaderButton headerBtn;
    private final List<Button> fullControlButtonRow = new ArrayList<>();
    private final List<Button> simpleClickButtonRow = new ArrayList<>();

    private List<Button> currentCommandData = simpleClickButtonRow;

    public static final String ON = "Открыть";
    public static final String OFF = "Закрыть";
    public static final String CLICK = "Щелк";

    private GateData.StatusValue status;
    private final Gate gate;

    public GateKeyBoard(Gate gate){
        super();
        fullControlButtonRow.add(new Button(ON, new OpenGate(gate)));
        fullControlButtonRow.add(new Button(OFF, new CloseGate(gate)));
        simpleClickButtonRow.add(new Button(CLICK, new ClickGate(gate)));

        this.gate = gate;
        headerBtn = new GateHeaderButton(gate);

        addButton(1, headerBtn);
    }

    private void changeControlButton(){
        currentCommandData = GateData.StatusValue.NA.equals(status) ? simpleClickButtonRow : fullControlButtonRow;
    }

    private void updateStatus(){
        GateData reqData = new GateData();
        reqData.addGate(gate);

        GateData response = AppContextHolder.getApi().getStatusGates(reqData);

        this.status = response.getGateStatus(gate);
    }

    public Gate getGate() {
        return gate;
    }

    @Override
    public void refresh() {
        updateStatus();
        headerBtn.setCaption(status);
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
