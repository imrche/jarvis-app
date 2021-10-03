package org.rch.jarvisapp;

import org.rch.jarvisapp.bot.cache.ActionCache;
import org.rch.jarvisapp.bot.JarvisBot;
import org.rch.jarvisapp.bot.MessageBuilder;
import org.rch.jarvisapp.bot.settings.Settings;
import org.rch.jarvisapp.bot.ui.TilePool;
import org.rch.jarvisapp.smarthome.SmartHome;
import org.rch.jarvisapp.smarthome.api.Api;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AppContextHolder implements ApplicationContextAware {
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppContextHolder.context = applicationContext;
    }

    public static ApplicationContext getContext(){
        return context;
    }

    public static Api getApi(){return context.getBean(Api.class);}

    public static JarvisBot getBot() {
        return context.getBean(JarvisBot.class);
    }

    public static SmartHome getSH(){ return context.getBean(SmartHome.class);}

    public static MessageBuilder getMessageBuilder() {
        return AppContextHolder.getContext().getBean(MessageBuilder.class);
    }

    public static ActionCache getActionCache() {
        return AppContextHolder.getContext().getBean(ActionCache.class);
    }
    public static TilePool getTilePool() {
        return AppContextHolder.getContext().getBean(TilePool.class);
    }

    public static Settings getSettings() {
        return AppContextHolder.getContext().getBean(Settings.class);
    }

}
