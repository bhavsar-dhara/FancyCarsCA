package com.dhara.myfancycarsapp.fancycars.model;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;

import com.dhara.myfancycarsapp.FancyCarsApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import timber.log.Timber;

public class FancyCars extends BaseObservable {

    private List<FancyCarDetails> carsList = new ArrayList<>();
    private List<FancyCarDetails> carsCopyList = new ArrayList<>();
    private MutableLiveData<List<FancyCarDetails>> cars = new MutableLiveData<>();

    public List<FancyCarDetails> getCarsCopyList() {
        return carsCopyList;
    }

    public MutableLiveData<List<FancyCarDetails>> getCars() {
        return cars;
    }

    public void fetchList() {
        try {
            carsList = FancyCarsApplication.getApiService().cars().getCarDetailsList();
            CarAvailability availability;
            for (ListIterator itr = carsList.listIterator(); itr.hasNext(); ) {
                FancyCarDetails fancyCarDetails =  (FancyCarDetails) itr.next();
                availability = fetchAvailability(fancyCarDetails.getId());
                fancyCarDetails.setAvailability(availability.getAvailability());
                itr.set(fancyCarDetails);
            }
            carsCopyList.addAll(carsList);
            Timber.d("fetchList: #cars = %s", carsList.size());
            Timber.d("fetchList: #total cars = %s", carsCopyList.size());
            cars.setValue(carsCopyList);
        } catch (Exception e) {
            Timber.e(e, "fetchList: ");
            e.printStackTrace();
        }
    }

    public CarAvailability fetchAvailability(Integer index) {
        CarAvailability availability = null;
        try {
            availability = FancyCarsApplication.getApiService().availability(index).getAvailability();
        } catch (Exception e) {
            Timber.e(e, "fetchAvailability: ");
            e.printStackTrace();
        }
        return availability;
    }
}
