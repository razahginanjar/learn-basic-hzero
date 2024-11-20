package com.hand.demo.api.dto;

import jdk.nashorn.internal.objects.annotations.Getter;
import org.codehaus.jackson.annotate.JsonProperty;
import org.mapstruct.Builder;

public class MessageRequest {
    @JsonProperty(value = "userName")
    private String userName;
    @JsonProperty(value = "email")
    private String email;
    @JsonProperty(value = "date")
    private String date;
    @JsonProperty(value = "employeeNumber")
    private String employeeNumber;

    public MessageRequest(String userName, String email, String date, String employeeNumber) {
        this.userName = userName;
        this.email = email;
        this.date = date;
        this.employeeNumber = employeeNumber;
    }

    public MessageRequest() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}
