package org.rch.jarvisapp.utils;

import lombok.SneakyThrows;
import org.rch.jarvisapp.bot.MessageBuilder;
import org.rch.jarvisapp.bot.enums.SettingsList;
import org.rch.jarvisapp.bot.enums.Stickers;
import org.rch.jarvisapp.bot.settings.Settings;
import org.rch.jarvisapp.smarthome.api.Api;
import org.springframework.stereotype.Service;

@Service
public class LiveProbe extends Thread {
    private final Api api;

    private final Settings settings;
    private boolean isDown;
    private final MessageBuilder messageBuilder;

    public LiveProbe(Api api, MessageBuilder messageBuilder, Settings settings){
        this.api = api;
        this.messageBuilder = messageBuilder;
        this.settings = settings;
        setDaemon(true);
    }

    private String getLiveProbeSetting(){
        return settings.getSetting(SettingsList.liveProbeTiming).toString();
    }

    @SneakyThrows
    @Override
    public void run() {
        String liveProbeTime = getLiveProbeSetting();
        int liveProbeTimeInt = Util.timeStr2Int(liveProbeTime);
        while (true){
            if (!api.isOnline()) {
                if (!isDown) {
                    messageBuilder.sendAsync("АЛЯРМ! Соединение с домом потеряно!");
                    messageBuilder.sendStickerAsync(Stickers.wait);
                    isDown = true;
                }
            }
            else {
                if (isDown) {
                    messageBuilder.sendAsync("Соединение восстановлено");
                    messageBuilder.sendStickerAsync(Stickers.notExactly);
                    isDown = false;
                }
            }

            //System.out.println("check");
            if (!liveProbeTime.equals(getLiveProbeSetting())){
                liveProbeTime = getLiveProbeSetting();
                liveProbeTimeInt = Util.timeStr2Int(liveProbeTime);
            }

            Thread.sleep(liveProbeTimeInt);
        }
    }
}
