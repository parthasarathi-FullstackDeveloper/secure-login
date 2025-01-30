package org.login.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employee")
public class Employee {
    private String id;
    private String email;
    private String employee;

    public Employee() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public Employee(String id, String email, String employee) {
        this.id = id;
        this.email = email;
        this.employee = employee;
    }
}
