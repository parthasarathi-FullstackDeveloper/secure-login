package org.login.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "location")
@Data
public class Location {
    @Id
    private String id;
    private double Latitude;
    private double Longitude;

}
