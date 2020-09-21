package com.movilizer.microservices.commons.models;

import java.util.Calendar;

public class Log {

    int id;
    Calendar calendar;
    String component;
    int severity;
    String message;
    String username;

    public Log(Calendar calendar, String component, int severity, String message, String username) {
        this.calendar = calendar;
        this.component = component;
        this.severity = severity;
        this.message = message;
        this.username = username;
    }

    public static Log constructLog(Calendar calendar, String component, int severity, String message, String username) {
        return new Log(calendar, component, severity, message, username);
    }

}
