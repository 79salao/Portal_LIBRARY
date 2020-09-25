package com.movilizer.microservices.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.movilizer.microservices.commons.models.Employee;
import com.movilizer.microservices.commons.models.ExtraField;
import com.movilizer.microservices.commons.models.Log;
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
import java.util.*;

/***
 * This library contains the common methods used in the architecture.
 * @author Salahdin Belghiti
 *
 */
public class CommonService {

    public static final String CRITICAL_ERROR_MESSAGE = "CRITICAL ERROR. Communication with Monitoring failed 3 times.";

    private int counter = 0;


    /***
     *
     * Method to send logs to the Monitoring microservice.
     *
     * @param severity Severity of the log. It can be OK, INFO, WARNING, ERROR or CRITICAL.
     * @param message Message to be logged.
     * @param username Username using the service.
     * @param service Service that owns the log.
     * @param url Url to the Monitoring microservice.
     *
     * @return Returns the formed Log object, or returns null if the operation fails.
     */
    public Log log(String severity, String message, String username, String service, String url) {
        Log log = new Log(this.getDate(), service, severity, message, username);
        Map<String, Object> response = this.sendObjectAsJson(url, "POST", log);
        if (response == null) {
            return null;
        } else {
            return log;
        }
    }

    /***
     *
     * Method to send objects as JSON between microservices with token.
     *
     * @param url Service url to call.
     * @param method Http method to use.
     * @param payload Object to send.
     *
     * @throws IOException
     *
     * @return returns the response JSON as Map<String, Object>, but if failed, returns null.
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
            counter = 0;
            return map;
        } catch (IOException e) {
            if (counter < 3) {
                this.sendObjectAsJson(url, method, payload);
            }
            counter = 0;
            e.printStackTrace();
            return null;
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
     * @return response JSON as Map<String, Object>
     */
    public Map<String, Object> sendObjectAsJson(String url, String method, Object payload, String token) {
        try {
            java.net.URL urlObject = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Authorization", token);
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
            counter = 0;
            return map;
        } catch (IOException e) {
            if (counter < 3) {
                this.sendObjectAsJson(url, method, payload);
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
     *
     * @throws IOException
     *
     * @return returns the response as JSONArray, or returns null if the operation fails.
     */
    public JSONArray getJSONArrayFromURL(String url) {
        try {
            java.net.URL urlObject = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            JSONTokener jsonTokener = new JSONTokener(bufferedReader);
            counter = 0;
            return new JSONArray(jsonTokener);
        } catch (IOException e) {
            if (counter < 3) {
                this.getJSONArrayFromURL(url);
            }
            e.printStackTrace();
            counter = 0;
            return null;
        }
    }

    /***
     *
     * Sends GET request to another microservice and returns the response as JSONObject.
     *
     * @param url url of the microservice endpoint.
     *
     * @throws IOException
     *
     * @return returns the response as JSONObject, of returns null if the operation fails.
     */
    public JSONObject getJSONObjectFromURL(String url) {
        try {
            java.net.URL urlObject = new URL(url);
            HttpURLConnection con = (HttpURLConnection) urlObject.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            JSONTokener jsonTokener = new JSONTokener(bufferedReader);
            counter = 0;
            return new JSONObject(jsonTokener);
        } catch (IOException e) {
            if (counter < 3) {
                this.getJSONArrayFromURL(url);
            }
            e.printStackTrace();
            counter = 0;
            return null;

        }
    }

    /***
     *
     * Takes a JSONArray and transformes it to a list of a specified object class.
     *
     * @param jsonArray JSONArray object to transform.
     * @param tClass Class object to which the JSONArray will be transformed.
     * @return returns a list of the specified object class.
     * @throws IOException
     */
    public <T> List<T> convertJSONArrayToList(JSONArray jsonArray, Class<T> tClass) throws IOException {
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

    /***
     *
     * Takes a list and returns a list of employees, including the extra fields.
     *
     * @param list list of objects to transform.
     * @return returns the list transformed to an employee list.
     * @throws IOException
     */
    private <T> List<T> returnEmployeeList(List<T> list) throws IOException {
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

    /***
     *
     * Takes a list of employees and assigns the list of extrafields to each employee.
     *
     * @param employeeList employee list to which add the extrafields to each employee.
     * @param extraFieldsMap map of extrafield to add to each employee.
     * @return returns the complete employee list.
     */
    private List<Employee> addExtraFieldsToEmployeeList(List<Employee> employeeList, Map<Long, List<ExtraField>> extraFieldsMap) {
        List<Employee> definitiveList = new ArrayList<>();
        employeeList.forEach(employee -> {
            employee.setExtraFields(extraFieldsMap.get(employee.getId()));
        });
        return definitiveList;
    }

    /***
     *
     * Takes a generic map and converts it to an ExtraField map.
     *
     * @param parameterMap map to convert to extraFields map.
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

    private String getDate() {
        return Calendar.getInstance().get(Calendar.YEAR) + "/" +
                Calendar.getInstance().get(Calendar.MONTH) + "/" +
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + " - " +
                Calendar.getInstance().get(Calendar.HOUR) + ":" +
                Calendar.getInstance().get(Calendar.MINUTE) + ":" +
                Calendar.getInstance().get(Calendar.SECOND);
    }


}
