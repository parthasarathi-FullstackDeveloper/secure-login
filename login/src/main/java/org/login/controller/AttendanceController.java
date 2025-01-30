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
      Employee data=  employeeRepository.findByEmail(employee.getEmail().replace("^",""));
      return data!=null?ResponseEntity.ok(data):ResponseEntity.notFound().build();

    }


    @PostMapping(SLASH + IN)
    public ResponseEntity<String> checkIn(@RequestBody AttendanceDTO attendanceDTO) {
        try {
            Location location = locationRepository.findAll().stream().findFirst().orElseThrow(() -> new RuntimeException("Location not found"));
            if (isValidLocation(attendanceDTO, location)) {
                AttendanceRecord attendanceRecord = commomMapper.toAttendanceRecordEntity(attendanceDTO);
                Integer maxId = attendanceRepository.findAll().stream().mapToInt(AttendanceRecord::getId).max().orElse(0);
                attendanceRecord.setId(maxId + 1);
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
    public String checkOut(@RequestBody AttendanceDTO attendanceDTO) {
        try {
            Location location = locationRepository.findAll().stream().findFirst().orElseThrow(() -> new RuntimeException("Location not found"));
            if (isValidLocation(attendanceDTO, location)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
                LocalTime attendanceTime = LocalTime.parse(attendanceDTO.getOut(), formatter);
                LocalTime outTime = LocalTime.parse(OUT_TIME, formatter);

                AttendanceRecord attendanceRecord = attendanceRepository.findByEmployeeAndDate(attendanceDTO.getEmployee(), attendanceDTO.getDate());
                if (attendanceRecord != null) {
                    attendanceRecord.setOut(attendanceDTO.getOut());
                    if (!attendanceTime.isBefore(outTime)) {
                        Duration duration = Duration.between(LocalTime.parse(attendanceRecord.getIn(), formatter), attendanceTime);
                        attendanceRecord.setTotalHours(String.valueOf(duration.toHours()));
                        attendanceRepository.save(attendanceRecord);
                    }

                    return CHECK_OUT;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ERROR_MESSAGE;
        }
        return LOCATION_MESSAGE;
    }

    private static final double EARTH_RADIUS = 6371000; // Radius in meters

    private boolean isValidLocation(AttendanceDTO attendanceDTO, Location location) {
        // Convert latitudes and longitudes to radians
        double lat1 = Math.toRadians(location.getLatitude());
        double lon1 = Math.toRadians(location.getLongitude());
        double lat2 = Math.toRadians(attendanceDTO.getLatitude());
        double lon2 = Math.toRadians(attendanceDTO.getLongitude());

        // Haversine formula to calculate distance
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(lat1) * Math.cos(lat2) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double distance = EARTH_RADIUS * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return distance <= 100; // Return true if within 100 meters
    }


    @PostMapping("/getByUserAttendance")
    public List<AttendanceRecord> getUserAttendaceList(@RequestBody AttendanceDTO attendanceDTO) {
        return attendanceRepository.findByEmployeeEmail(attendanceDTO.getEmployeeEmail());

    }

}
