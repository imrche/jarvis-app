package org.rch.jarvisapp;

import org.rch.jarvisapp.bot.JarvisBot;
import org.rch.jarvisapp.smarthome.init.HomeInitializer;
import org.rch.jarvisapp.utils.LiveProbe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@SpringBootApplication
public class JarvisAppApplication {

	public static void main(String[] args) {
		ApiContextInitializer.init();
		ConfigurableApplicationContext context = SpringApplication.run(JarvisAppApplication.class, args);

		JarvisBot bot = context.getBean(JarvisBot.class);
		try {
			bot.setWebhook(bot.getBotPath(),null);
		} catch (TelegramApiRequestException e) {
			e.printStackTrace();
		}

		try {
			HomeInitializer initializer = context.getBean(HomeInitializer.class);
			initializer.init();
		} catch (Exception e) {
			e.printStackTrace();
		}

		(context.getBean(LiveProbe.class)).start();
		AppContextHolder.getTilePool().clearFeedBack();
		AppContextHolder.getApi().setBotUrl(bot.getBotPath());
	}
}
