package com.movilizer.microservices.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/***
 * This library contains the common methods used in the architecture.
 * @author Salahdin Belghiti
 *
 */
public class CommonService {

    public final String POST = "POST";
    public final String PUT = "PUT";

    /***
     *
     * @param url Service url to call.
     * @param method Http method to use.
     * @param payload Object to send.
     */
    public void sendObjectAsJson(String url, String method, Object payload) {
        try {
            java.net.URL urlObject = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            ObjectMapper mapper = new ObjectMapper();
            String json = null;
            try {
                json = mapper.writeValueAsString(payload);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = new byte[0];
                if (json != null) {
                    input = json.getBytes(StandardCharsets.UTF_8);
                }
                os.write(input, 0, input.length);
            }
            con.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public JSONArray getJSONArrayFromURL(String url) {
        try {
            java.net.URL urlObject = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            JSONTokener jsonTokener = new JSONTokener(bufferedReader);
            return new JSONArray(jsonTokener);
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public JSONObject getJSONObjectFromURL(String url) {
        try {
            java.net.URL urlObject = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            JSONTokener jsonTokener = new JSONTokener(bufferedReader);
            return new JSONObject(jsonTokener);
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    private <T> List<T> convertJSONArrayToList(JSONArray jsonArray, Class<T> tClass) {
        List<T> list = new ArrayList<>();
        Gson g = new Gson();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            list.add(g.fromJson(jsonObject.toString(), (Type) tClass));
        }
        return list;
    }

}
