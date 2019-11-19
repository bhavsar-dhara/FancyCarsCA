package com.dhara.myfancycarsapp.fancycars.viewmodel;

import android.view.View;

import androidx.databinding.ObservableInt;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dhara.myfancycarsapp.R;
import com.dhara.myfancycarsapp.fancycars.model.FancyCarDetails;
import com.dhara.myfancycarsapp.fancycars.model.FancyCars;
import com.dhara.myfancycarsapp.fancycars.view.FancyCarsAdapter;

import java.util.List;

public class FancyCarsViewModel extends ViewModel {

    private static final String TAG = FancyCarsViewModel.class.getSimpleName();

    private FancyCarsAdapter adapter;
    private FancyCars fancyCars;
    public ObservableInt loading;
    public ObservableInt showEmpty;
    public ObservableInt showNoInternetConnection;
    public ObservableInt showList;

    public void init() {
        adapter = new FancyCarsAdapter(R.layout.car_item, this);
        fancyCars = new FancyCars();
        loading = new ObservableInt(View.GONE);
        showEmpty = new ObservableInt(View.GONE);
        showNoInternetConnection = new ObservableInt(View.GONE);
        showList = new ObservableInt((View.VISIBLE));
    }

    public void fetchList() {
        fancyCars.fetchList();
    }

    public MutableLiveData<List<FancyCarDetails>> getCars() {
        return fancyCars.getCars();
    }

    public FancyCarsAdapter getAdapter() {
        return adapter;
    }

    public void setCarsInAdapter(List<FancyCarDetails> cars) {
        this.adapter.setFancyCars(cars);
        this.adapter.notifyDataSetChanged();
    }

    public FancyCarDetails getCarDetailsAt(Integer index) {
        if (fancyCars.getCars().getValue() != null &&
                index != null &&
                fancyCars.getCars().getValue().size() > index) {
            return fancyCars.getCars().getValue().get(index);
        }
        return null;
    }

    public int buyBtnVisibility(Integer index) {
        if (getCarDetailsAt(index).getAvailability().equals("In Dealership")) {
            return View.VISIBLE;
        } else {
            return View.GONE;
        }
    }
}
