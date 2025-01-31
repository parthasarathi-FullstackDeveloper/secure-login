package org.login.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "employee")
@Data
public class Employee {
    private String id;
    private String email;
    private String employee;
}
