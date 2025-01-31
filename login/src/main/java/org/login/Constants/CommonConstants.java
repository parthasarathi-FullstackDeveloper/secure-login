package org.login.Constants;


public interface CommonConstants {
    String GET_USER_BY_EMAIL = "/getUserByEmail";
    String SET_LOCATION = "/setLocation";
    String GET_USER_BY_ATTENDANCE = "/getByUserAttendance";
    String LOCATION_MESSAGE = "Wrong Location Please Go To Office";
    String CHECK_IN = "Check In Success Fully ";
    String CHECK_OUT = "Check Out Success Fully ";
    String IN = "in";
    String OUT = "out";
    String SLASH = "/";
    String API = "api";
    String ATTENDANCE = "attendance";
    String REGISTER = "/register";
    String AUTH = "/auth";
    String EXIST_USER_ERROR = "Username already taken.";
    String USER_CREATED = "User registered successfully !";
    String ERROR_CREATE_USER = "Error occurred during registration!";
    String LOGIN = "/login";
    String INVALID_CREDENTIALS = "Invalid username or password!";
    String ALREADY_CHECK_IN = "Already  Check In";
    String LOCATION_ERROR = "Location not found";
    String CHECK_OUT_ERROR = "Check-out not allowed before 6:30 PM";
}
