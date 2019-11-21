package com.dhara.myfancycarsapp.fancycars.model;

public class AvailabilityResponse {

    private CarAvailability availability;
    private CarAvailability[] availabilityList;

    public CarAvailability getAvailability() {
        return availability;
    }

    public void setAvailability(CarAvailability availability) {
        this.availability = availability;
    }

    public CarAvailability[] getAvailabilityList() {
        return availabilityList;
    }

    public void setAvailabilityList(CarAvailability[] availabilityList) {
        this.availabilityList = availabilityList;
    }
}
