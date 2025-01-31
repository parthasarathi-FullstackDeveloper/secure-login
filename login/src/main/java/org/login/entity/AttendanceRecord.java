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
    private String Overtime;
}
