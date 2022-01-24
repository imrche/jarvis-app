package org.rch.jarvisapp.bot.ui.timerStuff;

import java.util.Map;

public class TimerData {
    public static class TimeValue{
        Integer value = 0;
        Units unit = Units.MINUTES;

        public void setValue(Integer value) {
            this.value = value != null ? value : 0;
        }

        public String getValue() {
            return String.valueOf(value);
        }

        public Units getUnit() {
            return unit;
        }

        public void setUnit(String unit) throws WrongDataForTimerException {
            try {
                this.unit = Units.valueOfDescription(unit);
            } catch (IllegalArgumentException e){
                throw new WrongDataForTimerException(e.getMessage(), e);
            }
        }

        public Integer toSeconds(){
            return value * unit.perSeconds;
        }

        public void fromSeconds(Integer value){
            if (value < 60){
                this.value = value;
                unit = Units.SECONDS;
            } else if (value < 60*24){
                this.value = value/60;
                unit = Units.MINUTES;
            } else {
                this.value = value/60/24;
                unit = Units.HOUR;
            }
        }

        public boolean isSet(){
            return value > 0 && unit != null;
        }

        private void clear(){
            value = 0;
            unit = Units.MINUTES;
        }
    }

    TimeValue delay = new TimeValue();
    TimeValue duration = new TimeValue();
    Actions action;

    public TimeValue getDelay() {
        return delay;
    }
    public TimeValue getDuration() {
        return duration;
    }
    public Actions getAction() {
        return action;
    }

    public void delete(){
        action = null;
        delay.clear();
        duration.clear();
    }

    public boolean isComplete(){
        return action != null && (duration.isSet() || delay.isSet());
    }

    public void setAction(String action) throws WrongDataForTimerException {
        try {
            this.action = Actions.valueOfDescription(action);
        } catch (IllegalArgumentException e){
            throw new WrongDataForTimerException(e.getMessage(), e);
        }
    }

    public void setTimer (Map<String, Integer> timers){
        Integer on = timers.getOrDefault("ON", 0);
        Integer off = timers.getOrDefault("OFF", 0);
        on = on != null ? on : 0;
        off = off != null ? off : 0;

        Boolean hasOn = on > 0;
        Boolean hasOff = off > 0;

        if (!(hasOn || hasOff)) {
            delete();
            return;
        }

        if (hasOn && hasOff) {
            action = (on < off) ? Actions.ON : Actions.OFF;
            delay.fromSeconds(Math.min(on, off));
            duration.fromSeconds(Math.abs(off - on));
        } else {
            action = hasOn ? Actions.ON : Actions.OFF;
            delay.fromSeconds(on + off);
        }
    }
}
