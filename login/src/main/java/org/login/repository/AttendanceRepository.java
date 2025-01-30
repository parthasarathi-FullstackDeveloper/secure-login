package org.login.repository;


import org.login.entity.AttendanceRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends MongoRepository<AttendanceRecord, Integer> {
    // Custom query methods if needed
   AttendanceRecord findByEmployeeAndDate(String employee, String time);

List<AttendanceRecord> findByEmployeeEmail(String email);
}

