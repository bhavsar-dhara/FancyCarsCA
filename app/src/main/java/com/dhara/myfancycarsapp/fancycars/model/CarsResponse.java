package com.dhara.myfancycarsapp.fancycars.model;

import java.util.Arrays;
import java.util.List;

public class CarsResponse {

    private FancyCarDetails[] carDetailsList;

    public List<FancyCarDetails> getCarDetailsList() {
        return Arrays.asList(carDetailsList);
    }

    public void setCarDetailsList(FancyCarDetails[] carDetailsList) {
        this.carDetailsList = carDetailsList;
    }
}
