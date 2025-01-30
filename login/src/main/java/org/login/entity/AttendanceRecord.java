package org.login.entity;


import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "attendance")
@Data
public class AttendanceRecord {
    @Id
    private int id;
    private String employee;
    private String employeeEmail;
    private String date;
    private String in;
    private String out;
    private String totalHours;

    public AttendanceRecord() {
    }

    public AttendanceRecord(int id, String employee, String employeeEmail, String date, String in, String out, String totalHours) {
        this.id = id;
        this.employee = employee;
        this.employeeEmail = employeeEmail;
        this.date = date;
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
