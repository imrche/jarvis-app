package org.rch.jarvisapp.utils;

import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.rch.jarvisapp.AppContextHolder;
import org.rch.jarvisapp.bot.exceptions.HomeNotResponse;
import org.rch.jarvisapp.smarthome.api.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class NetUtil {
    static Logger logger = LoggerFactory.getLogger(NetUtil.class);

    private final static String authorization = getAuthorization();
    public static final String STATUS = "status";
    public static final String RESPONSE = "response";

    private static String getAuthorization(){
        Api api = AppContextHolder.getApi();
        return "Basic " + Base64.getEncoder().encodeToString((api.getLogin() + ":" + api.getPassword()).getBytes(StandardCharsets.UTF_8));
    }

    public static Map<String, String> sendGET(String url){
        logger.debug("SEND GET " + url);
        Map<String, String> result = new HashMap<>();
        result.put(STATUS, "");
        result.put(RESPONSE, "");
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader(HttpHeaders.CONNECTION,"close");
            httpGet.setHeader(HttpHeaders.AUTHORIZATION, authorization);
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

    //todo обработать таймаут
    public static Map<String, String> sendPOST(String url, String body)  {
        logger.debug("SEND POST " + url + " - " + body);
        Util.logStackTrace(10);

        Map<String, String> result = new HashMap<>();
        result.put(STATUS, "");
        result.put(RESPONSE, "");
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(url);

            byte[] entityBody = body.getBytes(StandardCharsets.UTF_8);
            InputStreamEntity entity = new InputStreamEntity(new ByteArrayInputStream(entityBody), entityBody.length);
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            httpPost.setHeader(HttpHeaders.USER_AGENT,"Jarvis");
            httpPost.setHeader(HttpHeaders.CONNECTION,"close");
            httpPost.setHeader(HttpHeaders.AUTHORIZATION,authorization);

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
