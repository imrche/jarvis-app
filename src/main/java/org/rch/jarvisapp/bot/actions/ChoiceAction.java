package org.rch.jarvisapp.bot.actions;

import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.Tile;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.keyboard.KeyBoard;
import org.rch.jarvisapp.bot.ui.timerStuff.ChoiceSetter;
import org.rch.jarvisapp.bot.ui.timerStuff.OptionListGetter;

public class ChoiceAction implements Action {
    KeyBoard kb = new KeyBoard();

    ChoiceSetter choiceSetter;
    OptionListGetter optionListGetter;

    public ChoiceAction(OptionListGetter optionListGetter,ChoiceSetter choiceSetter) {
        this.choiceSetter = choiceSetter;
        this.optionListGetter = optionListGetter;
    }

    @Override
    public void run(Tile tile) throws HomeApiWrongResponseData {
        kb.clearButtons();

        int i = 1;
        for(String str : optionListGetter.getOptionList())
            kb.addButton(i++, new Button(str, new SimpleRunAction(s -> choiceSetter.setChoice(str), SimpleRunAction.Type.STEP_BACK)));
        tile.update().setKeyboard(kb);
    }

    @Override
    public int hashCode() {
        return (choiceSetter.hashCode() + optionListGetter.hashCode() + this.getClass().hashCode());
    }
}