package org.rch.jarvisapp.bot.cache;

import org.rch.jarvisapp.bot.actions.Action;
import org.rch.jarvisapp.bot.exceptions.IncorrectCacheCallBack;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ActionCache {
   // Map<Integer, ActionData> data = new HashMap<>();
    Map<Integer, Action> data2 = new HashMap<>();

/*    public ActionData getCallBack(String key) throws IncorrectCacheCallBack {
        try {
            Integer keyInt = Integer.parseInt(key);
            ActionData actionData = data.get(keyInt);
            if (actionData == null)
                throw new IncorrectCacheCallBack("Данных по хэшкоду " + key + " не найдено");

            return actionData;
        } catch (NumberFormatException e ){
            throw new IncorrectCacheCallBack("Хэш callBack не число! (" + key + ")");
        }
    }*/

/*    public Integer setCallBack(ActionData callBack){
        Integer key = callBack.toString().hashCode();
        data.put(key, callBack);
        return key;
    }*/
    public Action getCallBack2(String key) throws IncorrectCacheCallBack {
        try {
            Integer keyInt = Integer.parseInt(key);
            Action actionData = data2.get(keyInt);
            if (actionData == null)
                throw new IncorrectCacheCallBack("Данных по хэшкоду " + key + " не найдено");

            return actionData;
        } catch (NumberFormatException e ){
            throw new IncorrectCacheCallBack("Хэш callBack не число! (" + key + ")");
        }
    }

    public Integer setCallBack2(Action callBack){
        Integer key = callBack.hashCode();
        data2.put(key, callBack);
        return key;
    }
}
//todo сделать очистку неактуального кэша