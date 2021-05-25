package org.rch.jarvisapp.bot.dataobject;

import org.json.JSONObject;
import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.enums.ActionType;

public class ActionData extends JSONObject {
    public final static String ACTION = "action";
    public final static String PLACE = "place";
    public final static String BODY = "body";


    public ActionData(ActionType command, String place, Object body){
        this(command, body);
        this.put(PLACE, place);
    }

    public ActionData(ActionType command, String place){
        this(command);
        this.put(PLACE, place);
    }

    public ActionData(ActionType command, Object body){
        this(command);
        this.put(BODY, body.toString());
    }

    public ActionData(ActionType command){
        super();
        this.put(ACTION, command.name());
        this.put(BODY, "");
    }

/*    public ActionData(String str){
        super(str);
    }*/

    public ActionType getAction(){
        return ActionType.valueOf(getString(ACTION));
    }

    public String getPlace(){
        return getString(PLACE);
    }

    public String getBody(){
        return getString(BODY);
    }

    public String caching(){
        return AppContextHolder.getActionCache().setCallBack(this).toString();
    }

}
