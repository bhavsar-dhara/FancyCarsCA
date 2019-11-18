package com.dhara.myfancycarsapp.fancycars.model;

import androidx.databinding.BaseObservable;
import androidx.lifecycle.MutableLiveData;

import com.dhara.myfancycarsapp.FancyCarsApplication;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class FancyCars extends BaseObservable {

    private List<FancyCarDetails> carsList = new ArrayList<>();
    private MutableLiveData<List<FancyCarDetails>> cars = new MutableLiveData<>();

    public MutableLiveData<List<FancyCarDetails>> getCars() {
        return cars;
    }

    public void fetchList() {
        try {
            carsList = FancyCarsApplication.getApiService().cars().getCarDetailsList();
            Timber.d("fetchList: #cars = %s", carsList.size());
            cars.setValue(carsList);
        } catch (Exception e) {
            Timber.e(e, "fetchList: ");
            e.printStackTrace();
        }
    }
}
