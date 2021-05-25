package org.rch.jarvisapp;

import org.rch.jarvisapp.bot.JarvisBot;
import org.rch.jarvisapp.utils.LiveProbe;
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

		(context.getBean(LiveProbe.class)).start();
		AppContextHolder.getTilePool().clearFeedBack();
	}
}
