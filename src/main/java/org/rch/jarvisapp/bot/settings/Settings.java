package org.rch.jarvisapp.bot.settings;

import org.rch.jarvisapp.bot.enums.SettingsList;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Settings {
    public Map<SettingsList, Object> data = new HashMap<>();

    public Settings() {
        data.put(SettingsList.liveProbeTiming, "60 sec");
    }

    public Object getSetting(SettingsList key) {
        return data.get(key);
    }

    public void setSetting(SettingsList key, Object value) {
        data.put(key, value);
    }
}
