package org.rch.jarvisapp.bot.ui.keyboard;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import org.rch.jarvisapp.bot.dataobject.ActionData;
import org.rch.jarvisapp.bot.enums.BotCommand;
import org.rch.jarvisapp.bot.enums.Menu;
import org.rch.jarvisapp.bot.ui.button.Button;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Data
@FieldDefaults(level= AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = true)
public class KeyBoard extends InlineKeyboardMarkup {
    List<List<Button>> buttons = new ArrayList<>();

    @Override
    public List<List<InlineKeyboardButton>> getKeyboard() {
        setKB();
        return super.getKeyboard();
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

    private void setKB(){
        List<List<InlineKeyboardButton>> result = new ArrayList<>();
        for (List<Button> list : buttons) {
            List<InlineKeyboardButton> row = new ArrayList<>(list);
            result.add(row);
        }
        setKeyboard(result);
    }

    public void merge(List<List<InlineKeyboardButton>>  mergingKB){
        for (List<InlineKeyboardButton> list : mergingKB) {
            List<Button> newList = new ArrayList<>();
            for (InlineKeyboardButton button : list)
                newList.add((Button)button);
            buttons.add(newList);
        }
    }

    public void merge(KeyBoard mergingKB){
        buttons.addAll(mergingKB.getButtons());
    }

    public List<Button> getButtonsList(){
        return buttons.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public KeyBoard(){
    }

    public void refresh(){
        for (Button button : getButtonsList())
            button.refresh();
    }

    public KeyBoard(BotCommand botCommand){
        for (Menu menuItem : Menu.values()){
            if (menuItem.isPartOf(botCommand)){
                if (menuItem.getActionType() != null) {
                    ActionData actionData;
                    if (menuItem.isPlaceGrouping())
                        actionData = new ActionData(menuItem.getActionType(),"");
                    else
                        actionData = new ActionData(menuItem.getActionType());
                    addButton(menuItem.getRow(), new Button(menuItem.getDescription(), actionData));
                }
                if (menuItem.getProduceCommand() != null)
                    addButton(menuItem.getRow(), new Button(menuItem.getDescription(), menuItem.getProduceCommand().name()));
            }
        }
    }
}
