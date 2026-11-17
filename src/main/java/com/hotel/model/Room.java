
package com.hotel.model;

import java.math.BigDecimal;

public class Room {

    private int roomId;
    private String roomNumber;
    private String roomType;
    private BigDecimal price;
    private String facilities;
    private String availability;

    public Room(int roomId, String roomNumber,
                String roomType, BigDecimal price,
                String facilities, String availability) {

        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.price = price;
        this.facilities = facilities;
        this.availability = availability;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public String getFacilities() {
        return facilities;
    }

    public String getAvailability() {
        return availability;
    }
}
