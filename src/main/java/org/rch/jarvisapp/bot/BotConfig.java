package org.rch.jarvisapp.bot;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration()
@ConfigurationProperties(prefix = "bot")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BotConfig {
    String name;
    String token;
    String path;
    Long mainchat;//todo перенести мб
}
