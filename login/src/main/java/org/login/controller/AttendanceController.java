package org.login.controller;


import org.login.Constants.CommonConstants;
import org.login.dto.AttendanceDTO;
import org.login.dto.LocationDTO;
import org.login.entity.AttendanceRecord;
import org.login.entity.Employee;
import org.login.entity.Location;
import org.login.mapper.CommomMapper;
import org.login.repository.AttendanceRepository;
import org.login.repository.EmployeeRepository;
import org.login.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static javax.servlet.RequestDispatcher.ERROR_MESSAGE;
import static org.login.Constants.CommonConstants.*;

@CrossOrigin
@RestController
@RequestMapping(SLASH + API + SLASH + ATTENDANCE)
public class AttendanceController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CommomMapper commomMapper;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    AttendanceRepository attendanceRepository;

    @PostMapping(CommonConstants.SET_LOCATION)
    public String createLocation(@RequestBody LocationDTO locationDTO) {
        Location location = commomMapper.toLocationEntity(locationDTO);
        locationRepository.save(location);
        return "Location Updated";
    }

    @PostMapping(GET_USER_BY_EMAIL)
    public ResponseEntity<Employee> getUserByEmail(@RequestBody Employee employee) {
        Employee data = employeeRepository.findByEmail(employee.getEmail().replace("^", ""));
        return data != null ? ResponseEntity.ok(data) : ResponseEntity.notFound().build();

    }

    @PostMapping("/createUser")
    public String createEmployee(@RequestBody Employee employee) {
        employeeRepository.save(employee);
        return "User  Created";
    }

    @PostMapping(SLASH + IN)
    public ResponseEntity<String> checkIn(@RequestBody AttendanceDTO attendanceDTO) {
        try {
            AttendanceRecord record = attendanceRepository
                    .findByEmployeeAndDate(attendanceDTO.getEmployee(), attendanceDTO.getDate());

if(record!=null){
    return  ResponseEntity.unprocessableEntity().body("Allready  Check In");
}
            Location location = locationRepository.findAll().stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("Location not found"));
            if (isValidLocation(attendanceDTO, location)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
                LocalTime checkInTime = LocalTime.parse(attendanceDTO.getIn(), formatter);
                LocalTime endTime = LocalTime.of(9, 30);

                AttendanceRecord attendanceRecord = commomMapper.toAttendanceRecordEntity(attendanceDTO);
                Integer maxId = attendanceRepository.findAll().stream().mapToInt(AttendanceRecord::getId).max().orElse(0);
                attendanceRecord.setId(maxId + 1);

                // Check if check-in is after 9:30 AM and store only the overtime minutes
                if (checkInTime.isAfter(endTime)) {
                    Duration overtime = Duration.between(endTime, checkInTime);
                    attendanceRecord.setOvertime(String.valueOf(overtime.toMinutes())); // Store only the number
                } else {
                    attendanceRecord.setOvertime("0"); // No overtime
                }

                attendanceRepository.save(attendanceRecord);
                return ResponseEntity.ok(CHECK_IN);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ERROR_MESSAGE);
        }
        return ResponseEntity.internalServerError().body(LOCATION_MESSAGE);
    }

    @PostMapping(SLASH + OUT)
    public ResponseEntity checkOut(@RequestBody AttendanceDTO attendanceDTO) {
        try {
            Location location = locationRepository.findAll().stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("Location not found"));

            if (isValidLocation(attendanceDTO, location)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
                LocalTime attendanceTime = LocalTime.parse(attendanceDTO.getOut(), formatter);
                LocalTime minCheckoutTime = LocalTime.of(18, 30); // 6:30 PM

                if (attendanceTime.isBefore(minCheckoutTime)) {
                    return ResponseEntity.badRequest().body("Check-out not allowed before 6:30 PM");
                }

                AttendanceRecord attendanceRecord = attendanceRepository
                        .findByEmployeeAndDate(attendanceDTO.getEmployee(), attendanceDTO.getDate());

                if (attendanceRecord != null) {
                    attendanceRecord.setOut(attendanceDTO.getOut());
                    Duration duration = Duration.between(LocalTime.parse(attendanceRecord.getIn(), formatter), attendanceTime);
                    attendanceRecord.setTotalHours(String.valueOf(duration.toHours()));
                    attendanceRepository.save(attendanceRecord);
                    return ResponseEntity.ok().body(CHECK_OUT);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ERROR_MESSAGE);
        }
        return ResponseEntity.internalServerError().body(LOCATION_MESSAGE);
    }


    private boolean isValidLocation(AttendanceDTO attendanceDTO, Location location) {
        // Get the latitude and longitude of both locations
        double lat1 = location.getLatitude();
        double lon1 = location.getLongitude();
        double lat2 = attendanceDTO.getLatitude();
        double lon2 = attendanceDTO.getLongitude();

        int firstDigitLat1 = (int) Math.floor(Math.abs(lat1));
        int firstDigitLon1 = (int) Math.floor(Math.abs(lon1));
        int firstDigitLat2 = (int) Math.floor(Math.abs(lat2));
        int firstDigitLon2 = (int) Math.floor(Math.abs(lon2));

        boolean latMatch = firstDigitLat1 == firstDigitLat2;
        boolean lonMatch = firstDigitLon1 == firstDigitLon2;

        return latMatch && lonMatch;
    }


    @PostMapping("/getByUserAttendance")
    public List<AttendanceRecord> getUserAttendaceList(@RequestBody AttendanceDTO attendanceDTO) {
        return attendanceRepository.findByEmployeeEmail(attendanceDTO.getEmployeeEmail());

    }

}
