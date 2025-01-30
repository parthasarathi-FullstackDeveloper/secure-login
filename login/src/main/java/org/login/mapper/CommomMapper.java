package org.login.mapper;


import org.login.dto.AttendanceDTO;
import org.login.dto.LocationDTO;
import org.login.entity.AttendanceRecord;
import org.login.entity.Location;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CommomMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public LocationDTO toLocationDTO(Location location) {
        return modelMapper.map(location, LocationDTO.class);
    }

    public  Location toLocationEntity(LocationDTO dto) {
        return modelMapper.map(dto, Location.class);
    }

    public AttendanceRecord toAttendanceRecordEntity(AttendanceDTO attendanceDTO){
       return  modelMapper.map(attendanceDTO, AttendanceRecord.class);
    }

    public AttendanceDTO toAttendanceRecordDTO(AttendanceRecord attendanceRecord){
        return  modelMapper.map(attendanceRecord, AttendanceDTO.class);
    }
}


