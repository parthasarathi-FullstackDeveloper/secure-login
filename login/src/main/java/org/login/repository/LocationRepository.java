package org.login.repository;


import org.login.entity.Location;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocationRepository extends MongoRepository<Location,String> {
}
