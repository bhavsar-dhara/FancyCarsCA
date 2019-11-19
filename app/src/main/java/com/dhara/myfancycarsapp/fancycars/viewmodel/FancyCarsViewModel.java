package com.dhara.myfancycarsapp.fancycars.viewmodel;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dhara.myfancycarsapp.R;
import com.dhara.myfancycarsapp.fancycars.model.FancyCarDetails;
import com.dhara.myfancycarsapp.fancycars.model.FancyCars;
import com.dhara.myfancycarsapp.fancycars.view.FancyCarsAdapter;

import java.util.List;

import timber.log.Timber;

public class FancyCarsViewModel extends AndroidViewModel {

    private static final String TAG = FancyCarsViewModel.class.getSimpleName();

    private static Context context;
    private FancyCarsAdapter adapter;
    private FancyCars fancyCars;
    public ObservableInt loading;
    public ObservableInt showEmpty;
    public ObservableInt showNoInternetConnection;
    public ObservableInt showList;

    public FancyCarsViewModel(@NonNull Application application) {
        super(application);
        context = application.getApplicationContext();
    }

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

    public void onSelectItem(AdapterView<?> parent, View view, int pos, long id) {
//        Timber.d("position = %s", pos);
//        Timber.d("selected = %s", parent.getAdapter().getItem(pos).toString());
        if (parent.getAdapter().getItem(pos).toString().equals("Sort by Name")) {
            Timber.d("Sort by name");
            adapter.sortBasedOnName();
        } else {
            Timber.d("Sort by availability");
            adapter.sortBasedOnAvailability();
        }
    }
}
