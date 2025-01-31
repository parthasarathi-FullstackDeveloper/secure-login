package org.login.dto;

import lombok.Data;

@Data
public class AttendanceDTO {
    private int id;
    private String employee;
    private String employeeEmail;
    private String date;
    private double Latitude;
    private double Longitude;
    private String logIn;
    private String in;
    private String out;
    private String totalHours;

}
