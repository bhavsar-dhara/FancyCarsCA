package com.dhara.myfancycarsapp.backend;

import android.content.Context;

import com.dhara.myfancycarsapp.R;
import com.dhara.myfancycarsapp.fancycars.model.AvailabilityResponse;
import com.dhara.myfancycarsapp.fancycars.model.CarAvailability;
import com.dhara.myfancycarsapp.fancycars.model.CarsResponse;
import com.dhara.myfancycarsapp.fancycars.model.FancyCarDetails;
import com.google.gson.Gson;

import java.io.InputStreamReader;

import timber.log.Timber;

/**
 * class to mock the web service - with stubbed data
 */
public class LocalWebService implements ApiService {

    private static final String TAG = LocalWebService.class.getSimpleName();

    private Context context;
    private Gson gson;

    public LocalWebService(Context context) {
        this.context = context;
        this.gson = new Gson();
    }

    @Override
    public AvailabilityResponse availability(int id) throws Exception {
        Timber.d("AvailabilityResponse: ");
        // Thread.sleep(2000); // emulate network delay
        AvailabilityResponse availabilityResponse = new AvailabilityResponse();
        availabilityResponse.setAvailabilityList(gson.fromJson(new InputStreamReader(context.getResources().openRawResource(R.raw.cars_avail)), CarAvailability[].class));
        for (CarAvailability availability : availabilityResponse.getAvailabilityList()) {
            if (availability.getId() == id) {
                availabilityResponse.setAvailability(availability);
            }
        }
        return availabilityResponse;
    }

    @Override
    public CarsResponse cars() throws Exception {
        Timber.d("CarResponse: ");
        // Thread.sleep(2000); // emulate network delay
        CarsResponse carsResponse = new CarsResponse();
        carsResponse.setCarDetailsList(gson.fromJson(new InputStreamReader(context.getResources().openRawResource(R.raw.cars)), FancyCarDetails[].class));
        return carsResponse;
    }
}
