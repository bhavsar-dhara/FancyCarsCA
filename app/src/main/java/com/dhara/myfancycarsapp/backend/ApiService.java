package com.dhara.myfancycarsapp.backend;

import com.dhara.myfancycarsapp.fancycars.model.AvailabilityResponse;
import com.dhara.myfancycarsapp.fancycars.model.CarsResponse;

public interface ApiService {

    public AvailabilityResponse availability(int id) throws Exception;
    public CarsResponse cars() throws Exception;
}
