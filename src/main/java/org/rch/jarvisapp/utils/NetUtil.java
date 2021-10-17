package org.rch.jarvisapp.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.rch.jarvisapp.bot.exceptions.HomeNotResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class NetUtil {
    static Logger logger = LoggerFactory.getLogger(NetUtil.class);
    public static final String STATUS = "status";
    public static final String RESPONSE = "response";

    public static Map<String, String> sendGET(String url){
        logger.debug("SEND GET " + url);
        Map<String, String> result = new HashMap<>();
        result.put(STATUS, "");
        result.put(RESPONSE, "");
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
             HttpGet httpGet = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                result.put(STATUS, String.valueOf(response.getStatusLine().getStatusCode()));
                result.put(RESPONSE, EntityUtils.toString(response.getEntity()));
            }
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }

        if (!result.get(STATUS).equals("200")){
            logger.warn("Request returned err code:" + result.get(STATUS));
        }

        return result;
    }

    public static Map<String, String> sendPOST(String url, String body)  {
        logger.debug("SEND POST " + url + " - " + body);
        Util.logStackTrace(10);

        Map<String, String> result = new HashMap<>();
        result.put(STATUS, "");
        result.put(RESPONSE, "");
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            InputStreamEntity entity = new InputStreamEntity(new ByteArrayInputStream(body.getBytes(StandardCharsets.UTF_8)), body.length());
            entity.setContentType("application/json");
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                result.put(STATUS, String.valueOf(response.getStatusLine().getStatusCode()));
                result.put(RESPONSE, EntityUtils.toString(response.getEntity()));
            } catch (IOException e) {
                logger.error(e.getMessage(),e);
               // throw new HomeNotResponse("Дом не отвечает!");//todo!!!
            }
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }

        if (!result.get(STATUS).equals("200")){
            logger.warn("Request returned err code:" + result.get(STATUS));
            //throw new HomeNotResponse(result.get(STATUS) + " - " + url + " (with body: " + body + ")");
        }

        return result;
    }
}
