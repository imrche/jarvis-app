package org.rch.jarvisapp.bot.cache;

import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.exceptions.IncorrectCacheCallBack;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ActionCache {
    Map<Integer, Action> data = new HashMap<>();

    public Action getCallBack(String key) throws IncorrectCacheCallBack {
        try {
            Integer keyInt = Integer.parseInt(key);
            Action actionData = data.get(keyInt);
            if (actionData == null)
                throw new IncorrectCacheCallBack("Данных по хэшкоду " + key + " не найдено");

            return actionData;
        } catch (NumberFormatException e ){
            throw new IncorrectCacheCallBack("Хэш callBack не число! (" + key + ")");
        }
    }

    public Integer setCallBack(Action callBack){
        Integer key = callBack.hashCode();
        data.put(key, callBack);
        return key;
    }
}
//todo сделать очистку неактуального кэша