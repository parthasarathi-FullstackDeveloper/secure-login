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

    public AttendanceDTO() {
    }

    public AttendanceDTO(int id, String employee, String employeeEmail, String date, double latitude, double longitude, String logIn, String in, String out, String totalHours) {
        this.id = id;
        this.employee = employee;
        this.employeeEmail = employeeEmail;
        this.date = date;
        Latitude = latitude;
        Longitude = longitude;
        this.logIn = logIn;
        this.in = in;
        this.out = out;
        this.totalHours = totalHours;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public String getLogIn() {
        return logIn;
    }

    public void setLogIn(String logIn) {
        this.logIn = logIn;
    }

    public String getIn() {
        return in;
    }

    public void setIn(String in) {
        this.in = in;
    }

    public String getOut() {
        return out;
    }

    public void setOut(String out) {
        this.out = out;
    }

    public String getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }
}
