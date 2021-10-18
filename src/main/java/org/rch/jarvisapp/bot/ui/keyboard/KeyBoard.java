package org.rch.jarvisapp.bot.ui.keyboard;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.button.Button;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@EqualsAndHashCode
public class KeyBoard{
    List<List<Button>> buttons = new ArrayList<>();

    public KeyBoard(){}

    public List<List<Button>> getInlineButtons(){
        return buttons;
    }

    public KeyBoard addButton(int rowNum, Button button){
        List<Button> row;
        boolean found = true;

        rowNum--;

        try{
            row = buttons.get(rowNum);
        } catch (IndexOutOfBoundsException e){
            found = false;
            row = new ArrayList<>();
        }

        row.add(button);

        if(!found)
            buttons.add(row);

        return this;
    }

    public void merge(KeyBoard mergingKB){
        buttons.addAll(mergingKB.getButtons());
    }

    public List<Button> getButtonsList(){
        return buttons.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public void refresh() throws HomeApiWrongResponseData {
        for (Button button : getButtonsList())
            button.refresh();
    }
}
