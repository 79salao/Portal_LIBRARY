package com.movilizer.microservices.commons.models;

public class Log {

    public static final String INFO = "INFO";
    public static final String WARNING = "WARNING";
    public static final String DEBUG = "DEBUG";
    public static final String ERROR = "ERROR";
    public static final String CRITICAL = "CRITICAL";

    int id;
    String calendar;
    String component;
    String severity;
    String message;
    String username;

    public Log(String calendar, String component, String severity, String message, String username) {
        this.calendar = calendar;
        this.component = component;
        this.severity = severity;
        this.message = message;
        this.username = username;
    }

    public static Log constructLog(String calendar, String component, String severity, String message, String username) {
        return new Log(calendar, component, severity, message, username);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(String component) {
        this.component = component;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return id + " - " +
                component + " - " +
                calendar + " - " +
                severity + " - " +
                username + " - " +
                message;
    }
}

