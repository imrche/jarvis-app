package org.rch.jarvisapp.home;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.io.IOException;


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
