package org.rch.jarvisapp.bot.actions.settings;


import org.rch.jarvisapp.bot.ui.Tile;


public class SettingsUtils {
    public static void generateNumKeyBoard(Tile tile){
/*        KeyBoard kb = new KeyBoard();
        String tune = actionData.getBody();

        Integer[] interval = {1,2,5,10,15,30};
        String[] unit = {"sec", "min", "hour"};

        int row = 1;
        for (String s : unit) {
            for (Integer i : interval)
                kb.addButton(row, new Button(i + " " + s, new ActionData(ActionType.setSettings, "", tune + "_" + i + s)));
            row++;
        }

        String caption;
        try {
            caption = SettingsList.valueOf(actionData.getBody()).getDescription();
        } catch (IllegalArgumentException e){
            caption = actionData.getBody();
        }

        tile.update()
                .setCaption(caption)
                .setKeyboard(kb);*/
    }
}
