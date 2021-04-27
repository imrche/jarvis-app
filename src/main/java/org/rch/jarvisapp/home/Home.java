package org.rch.jarvisapp.home;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.rch.jarvisapp.bot.JarvisBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.ApiConstants;
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@ConfigurationProperties(prefix = "home")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Home {
    String proto;
    String ip;
    String port;

    public boolean isOnline(){
        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpGet httpGet = new HttpGet(proto+"://"+ip+":"+port);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                return response.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
            }
        } catch (IOException e){
            return false;
        }
    }
}
