package org.rch.jarvisapp.bot.actions;

import org.rch.jarvisapp.bot.dataobject.DataObject;

public interface DataContained {
    Action setData(DataObject data);//todo type
    Object getData();
}
