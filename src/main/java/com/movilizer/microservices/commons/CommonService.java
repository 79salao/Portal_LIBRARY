package com.movilizer.microservices.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.movilizer.microservices.commons.models.Employee;
import com.movilizer.microservices.commons.models.ExtraField;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public Map<String, Object> sendObjectAsJson(String url, String method, Object payload) {
        try {
            java.net.URL urlObject = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            con.setDoInput(true);
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
            con.connect();
            Map<String, Object> map;
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                JSONObject jsonObject = new JSONObject(response);
                map = jsonObject.toMap();
            }
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
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

    public  <T> List<T> convertJSONArrayToList(JSONArray jsonArray, Class<T> tClass) {
        List<T> list = new ArrayList<>();
        Gson g = new Gson();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            list.add(g.fromJson(jsonObject.toString(), (Type) tClass));
        }
        if (tClass.getName().equals("Employee")) {
            return this.returnEmployeeList(list);
        }
        return list;
    }

    private <T> List<T> returnEmployeeList(List<T> list) {
        PropertiesConfiguration config = new PropertiesConfiguration();
        try {
            config.load("application.properties");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        String url = config.getString("someKey");

        List<Employee> provisionalEmployeeList = (List<Employee>) list;
        List<Long> employeeIds = new ArrayList<>();
        provisionalEmployeeList.forEach(employee -> employeeIds.add(employee.getId()));
        Map<Long, List<ExtraField>> map = this.convertGenericMapToExtraFieldsMap(this.sendObjectAsJson(url + "/employees/extraFields", "GET", employeeIds));
        List<Employee> definitiveEmployeeList = this.addExtraFieldsToEmployeeList(provisionalEmployeeList, map);
        return (List<T>) definitiveEmployeeList;
    }

    private List<Employee> addExtraFieldsToEmployeeList(List<Employee> employeeList, Map<Long, List<ExtraField>> extraFieldsMap) {
        List<Employee> definitiveList = new ArrayList<>();
        employeeList.forEach(employee -> {
            employee.setExtraFields(extraFieldsMap.get(employee.getId()));
        });
        return definitiveList;
    }

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

}
