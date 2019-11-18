package com.dhara.myfancycarsapp.backend;

import com.dhara.myfancycarsapp.fancycars.model.AvailabilityResponse;
import com.dhara.myfancycarsapp.fancycars.model.CarsResponse;

import timber.log.Timber;

/**
 * class to call the correct remote service - to be used in production
 */
public class RemoteWebService implements ApiService {

    private static final String TAG = RemoteWebService.class.getSimpleName();

    @Override
    public AvailabilityResponse availability(int id) throws Exception {
        Timber.d("AvailabilityResponse: ");
        return null;
    }

    @Override
    public CarsResponse cars() throws Exception {
        Timber.d("CarResponse: ");
        return null;
    }
}
