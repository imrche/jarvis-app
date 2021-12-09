package org.rch.jarvisapp.bot.ui.keyboard;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.bot.exceptions.HomeApiWrongResponseData;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.rch.jarvisapp.bot.ui.button.func_interface.GroupCaptionUpdater;

import java.util.*;
import java.util.stream.Collectors;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@EqualsAndHashCode
public class KeyBoard{
    List<List<Button>> buttons = new ArrayList<>();

    Map<String,Button> categorizedMap = new HashMap<>();
    GroupCaptionUpdater updater;

    public List<List<Button>> getVisibleButtons(){
        List<List<Button>> result = new ArrayList<>();
        for(List<Button> lBtn : buttons){
            List<Button> lBtnRes = new ArrayList<>();
            for (Button btn : lBtn){
                if (btn.isVisible())
                    lBtnRes.add(btn);
            }
            result.add(lBtnRes);
        }
        return result;
    }

    public KeyBoard(){}

    public void setUpdater(GroupCaptionUpdater updater) {
        this.updater = updater;
    }

    public List<List<Button>> getInlineButtons(){
        return buttons;
    }

    public KeyBoard addButton(int rowNum, Button button){
        return addButton(rowNum, null, button);
    }

    public KeyBoard addButton(int rowNum,String code, Button button){
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

        if (code != null)
            categorizedMap.put(code,button);//todo можно потом использовать в переборах

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

    private void groupCaptionUpdate(){
        Map<String,String> data = updater.getCaption();
        for (Map.Entry<String,String> entry : data.entrySet()){
            if (categorizedMap.containsKey(entry.getKey()))
                categorizedMap.get(entry.getKey()).setCaption(entry.getValue());
        }
    }

    public void refresh() throws HomeApiWrongResponseData {
        if (updater != null)
            groupCaptionUpdate();
        else
            for (Button button : getButtonsList())
                button.refresh();
    }
}
