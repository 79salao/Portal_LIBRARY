package com.movilizer.microservices.commons.models;

public class ExtraField {

    private long id;
    private String name;
    private String value;
    private Employee employee;

    public ExtraField() {
    }

    public ExtraField(long id, String name, String value, Employee employee) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.employee = employee;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
