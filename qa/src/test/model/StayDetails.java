package test.model;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;

public class StayDetails {

    private RoomType roomType;

    private String checkInDate;
    private String checkOutDate;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;

    public StayDetails(RoomType roomType, String checkInDate, String checkOutDate, String firstName, String lastName, String email, String phoneNumber) {
        this.roomType = roomType;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public RoomType getRoomType() { return roomType; }

    public String getCheckInDate() { return checkInDate; }
    public String getCheckOutDate() { return checkOutDate; }

    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }

    public enum RoomType {
        SINGLE,
        DOUBLE,
        SUITE;

        public String getValue() {
            return StringUtils.capitalize(this.name().toUpperCase());
        }
    }
}
