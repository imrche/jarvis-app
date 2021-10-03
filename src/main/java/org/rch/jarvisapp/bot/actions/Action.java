package org.rch.jarvisapp.bot.actions;

import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.ui.Tile;

public interface Action {
    void run(Tile tile);
   // void setPlace(String place);

    //void setData(Object data);//todo type
    //Object getData();

    default String caching(){
        return AppContextHolder.getActionCache().setCallBack2(this).toString();
    }
}
