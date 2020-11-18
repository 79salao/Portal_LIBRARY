package com.movilizer.microservices.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.movilizer.microservices.commons.models.ExtraField;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

/***
 * This library contains the common methods used in the architecture.
 * @author Salahdin Belghiti
 *
 */
public class CommonService {

    public String URL_MONITORING;

    public static final String CRITICAL_ERROR_MESSAGE = "CRITICAL ERROR. Communication with Monitoring failed 3 times.";

    private int counter = 0;

    /***
     *
     * Method to send objects as JSON between microservices with token.
     *
     * @param url Service url to call.
     * @param method Http method to use.
     * @param payload Object to send.
     * @param token Authorization token.
     * @param fromMicroservice Microservice making the request.
     * @param toMicroservice Microservice receiving the request.
     *
     *
     * @return returns the response JSON as Map<String, Object>
     */
    public Map<String, Object> sendObjectAsJson(String url, String method, Object payload, String token, String fromMicroservice, String toMicroservice) {
        try {
            java.net.URL urlObject = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Authorization", token);
            con.setDoOutput(true);
            con.setDoInput(true);
            if (payload != null) {
                ObjectMapper mapper = new ObjectMapper();
                String json = null;
                try {
                    json = mapper.writeValueAsString(payload);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = new byte[0];
                    if (json != null) {
                        input = json.getBytes(StandardCharsets.UTF_8);
                    }
                    os.write(input, 0, input.length);
                }
            }
            con.connect();
            Map<String, Object> map;
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                JSONObject jsonObject = new JSONObject(response.toString());
                map = jsonObject.toMap();
            }
            return this.checkErrors(map);
        } catch (IOException e) {
            if (counter < 3) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                counter += 1;
                this.sendObjectAsJson(url, method, payload, token, fromMicroservice, toMicroservice);
            }
            e.printStackTrace();
            counter = 0;
            return this.checkErrors(null);
        }
    }

    /***
     *
     * Method to send objects as JSON between microservices with token.
     *
     * @param url Service url to call.
     * @param method Http method to use.
     * @param payload Object to send.
     * @param token Authorization token.
     *
     *
     * @return returns the response JSON as Map<String, Object>
     */
    public List<Object> sendObjectsAsJson(String url, String method, Object payload, String token) {
        try {
            java.net.URL urlObject = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Authorization", token);
            con.setDoOutput(true);
            con.setDoInput(true);
            if (payload != null) {
                ObjectMapper mapper = new ObjectMapper();
                String json = null;
                try {
                    json = mapper.writeValueAsString(payload);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                try (OutputStream os = con.getOutputStream()) {
                    byte[] input = new byte[0];
                    if (json != null) {
                        input = json.getBytes(StandardCharsets.UTF_8);
                    }
                    os.write(input, 0, input.length);
                }
            }
            con.connect();
            List<Object> list;
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                JSONArray jsonArray = new JSONArray(response.toString());
                list = jsonArray.toList();
            }
            return list;
        } catch (IOException e) {
            if (counter < 3) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                counter += 1;
                this.sendObjectsAsJson(url, method, payload, token);
            }
            e.printStackTrace();
            counter = 0;
            return null;
        }
    }

    /***
     *
     * Sends GET request to another microservice and returns the response as JSONArray.
     *
     * @param url url to which make the request.
     * @param token Authorization token.
     *
     * @return returns the response as JSONArray, or returns null if the operation fails.
     */
    public JSONArray getJSONArrayFromURL(String url, String token) {
        try {
            java.net.URL urlObject = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", token);
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.connect();
            Map<String, Object> map;
            JSONArray jsonObject;
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                jsonObject = new JSONArray(response.toString());
            }
            return jsonObject;
        } catch (IOException e) {
            if (counter < 3) {
                counter += 1;
                this.getJSONArrayFromURL(url, token);
            }
            e.printStackTrace();
            counter = 0;
            return new JSONArray(this.checkErrors(null));
        }
    }


    /***
     *
     * Sends GET request to another microservice and returns the response as JSONObject.
     *
     * @param url url of the microservice endpoint.
     * @param token Authorization token.
     *
     * @return returns the response as JSONObject, of returns null if the operation fails.
     */
    public JSONObject getJSONObjectFromURL(String url, String token) {
        try {
            java.net.URL urlObject = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", token);
            con.connect();
            Map<String, Object> map;
            JSONObject jsonObject;
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                jsonObject = new JSONObject(response.toString());
            }
            counter = 0;
            return this.checkJSONObjectErrors(jsonObject);
        } catch (IOException e) {
            if (counter < 3) {
                counter += 1;
                this.getJSONObjectFromURL(url, token);
            }
            e.printStackTrace();
            counter = 0;
            return (JSONObject) this.checkErrors(null);

        }
    }

    /***
     *
     * Takes a JSONArray and transformes it to a list of a specified object class.
     *
     * @param jsonArray JSONArray object to transform.
     * @param tClass Class object to which the JSONArray will be transformed.
     *
     * @return returns a list of the specified object class, or null if th JSONArray is null.
     */
    public <T> List<T> convertJSONObjectToList(JSONArray jsonArray, Class<T> tClass) {
        List<T> list = new ArrayList<>();
        Gson g = new Gson();
        if (jsonArray == null) {
            return null;
        }
        jsonArray.toList().forEach(o -> {
            g.fromJson(o.toString(), tClass);
        });
        return list;
    }

    /***
     *
     * Takes a generic map and converts it to an ExtraField map.
     *
     * @param parameterMap map to convert to extraFields map.
     *
     * @return returns the ExtraField map.
     */
    private Map<Long, List<ExtraField>> convertGenericMapToExtraFieldsMap(Map<String, Object> parameterMap) {
        Map<Long, List<ExtraField>> definitiveMap = new HashMap<>();
        Gson g = new Gson();
        parameterMap.forEach((string, object) -> {
            List<ExtraField> extraFields = new ArrayList<>();
            List<String> objectList = (List<String>) object;
            objectList.forEach(s -> {
                extraFields.add(g.fromJson(s, ExtraField.class));
            });
            definitiveMap.put(Long.parseLong(string), extraFields);
        });
        return definitiveMap;
    }

    /***
     *
     * @param map Response object to check.
     *
     * @return Returns the original object if its correct, or returns a new map with the error information.
     *
     */
    private Map<String, Object> checkErrors(Map<String, Object> map) {
        Map<String, Object> response = new HashMap<>();
        if (map == null) {
            response.put("error", "Communication failed.");
            return response;
        }
        if (map.get("status") != null) {
            response.put("status", map.get("status"));
            response.put("error", "Authentication failed.");
        }
        return map;
    }

    private JSONObject checkJSONObjectErrors(JSONObject jsonObject) {
        return (JSONObject) this.checkErrors(jsonObject.toMap());
    }

    public String getDate() {
        return Calendar.getInstance().get(Calendar.YEAR) + "/" +
                Calendar.getInstance().get(Calendar.MONTH) + "/" +
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + " - " +
                Calendar.getInstance().get(Calendar.HOUR) + ":" +
                Calendar.getInstance().get(Calendar.MINUTE) + ":" +
                Calendar.getInstance().get(Calendar.SECOND);
    }


}
