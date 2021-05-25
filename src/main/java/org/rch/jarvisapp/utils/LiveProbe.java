package org.rch.jarvisapp.utils;

import lombok.SneakyThrows;
import org.rch.jarvisapp.bot.MessageBuilder;
import org.rch.jarvisapp.bot.enums.Settings;
import org.rch.jarvisapp.bot.enums.Stickers;
import org.rch.jarvisapp.bot.services.SettingService;
import org.rch.jarvisapp.smarthome.api.Api;
import org.springframework.stereotype.Service;

@Service
public class LiveProbe extends Thread {
    private final Api api;

    private final SettingService settingService;
    private boolean isDown;
    private final MessageBuilder messageBuilder;

    public LiveProbe(Api api, MessageBuilder messageBuilder, SettingService settingService){
        this.api = api;
        this.messageBuilder = messageBuilder;
        this.settingService = settingService;
        setDaemon(true);
    }

    private String getLiveProbeSetting(){
        return settingService.tmpSettings.get(Settings.liveProbeTiming.name()).toString();
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
